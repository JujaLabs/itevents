package org.itevents.dbunit;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKBWriter;
import com.vividsolutions.jts.io.WKTReader;

public class GeometryUtil {
    public static byte[] convertFromTextToBinary(final String wktString) {
        WKTReader fromText = new WKTReader();
        byte[] geometryBlob;
        try {
            Geometry geometry = fromText.read(wktString);
            WKBWriter writer = new WKBWriter();
            geometryBlob = writer.write(geometry);
        } catch (com.vividsolutions.jts.io.ParseException e) {
            throw new RuntimeException("Not a WKT string:" + wktString);
        }
        return geometryBlob;
    }

}
