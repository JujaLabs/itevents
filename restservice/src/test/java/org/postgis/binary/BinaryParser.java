/*
 * BinaryParser.java
 * 
 * PostGIS extension for PostgreSQL JDBC driver - Binary Parser
 * 
 * (C) 2005 Markus Schaber, markus.schaber@logix-tt.com
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 2.1 of the License.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA or visit the web at
 * http://www.gnu.org.
 * 
 * $Id: BinaryParser.java 9324 2012-02-27 22:08:12Z pramsey $
 */
package org.postgis.binary;

import org.postgis.Geometry;
import org.postgis.Point;
import org.postgis.binary.ByteGetter.StringByteGetter;

/**
 * Parse binary representation of geometries.
 * <p>
 * It should be easy to add char[] and CharSequence ByteGetter instances,
 * although the latter one is not compatible with older jdks.
 * <p>
 * I did not implement real unsigned 32-bit integers or emulate them with long,
 * as both java Arrays and Strings currently can have only 2^31-1 elements
 * (bytes), so we cannot even get or build Geometries with more than approx.
 * 2^28 coordinates (8 bytes each).
 *
 * @author Markus Schaber <markus.schaber@logix-tt.com>
 */
public class BinaryParser {

    /**
     * Get the appropriate ValueGetter for my endianness
     *
     * @param bytes The appropriate Byte Getter
     * @return the ValueGetter
     */
    public static ValueGetter valueGetterForEndian(ByteGetter bytes) {
        if (bytes.get(0) == ValueGetter.XDR.NUMBER) { // XDR
            return new ValueGetter.XDR(bytes);
        } else if (bytes.get(0) == ValueGetter.NDR.NUMBER) {
            return new ValueGetter.NDR(bytes);
        } else {
            throw new IllegalArgumentException("Unknown Endian type:" + bytes.get(0));
        }
    }

    /**
     * Parse a hex encoded geometry
     * <p>
     * Is synchronized to protect offset counter. (Unfortunately, Java does not
     * have neither call by reference nor multiple return values.)
     */
    public synchronized Geometry parse(String value) {
        StringByteGetter bytes = new StringByteGetter(value);
        return parseGeometry(valueGetterForEndian(bytes));
    }

    /**
     * Parse a geometry starting at offset.
     */
    protected Geometry parseGeometry(ValueGetter data) {
        byte endian = data.getByte(); // skip and test endian flag
        if (endian != data.endian) {
            throw new IllegalArgumentException("Endian inconsistency!");
        }
        int typeword = data.getInt();

        int realtype = typeword & 0x1FFFFFFF; // cut off high flag bits

        boolean haveZ = (typeword & 0x80000000) != 0;
        boolean haveM = (typeword & 0x40000000) != 0;
        boolean haveS = (typeword & 0x20000000) != 0;

        int srid = Geometry.UNKNOWN_SRID;

        if (haveS) {
            srid = Geometry.parseSRID(data.getInt());
        }
        Geometry result1;
        switch (realtype) {
            case Geometry.POINT:
                result1 = parsePoint(data, haveZ, haveM);
                break;
            default:
                throw new IllegalArgumentException("Unknown Geometry Type: " + realtype);
        }

        Geometry result = result1;

        if (srid != Geometry.UNKNOWN_SRID) {
            result.setSrid(srid);
        }
        return result;
    }

    private Point parsePoint(ValueGetter data, boolean haveZ, boolean haveM) {
        double X = data.getDouble();
        double Y = data.getDouble();
        Point result;
        if (haveZ) {
            double Z = data.getDouble();
            result = new Point(X, Y, Z);
        } else {
            result = new Point(X, Y);
        }

        if (haveM) {
            result.setM(data.getDouble());
        }

        return result;
    }

}
