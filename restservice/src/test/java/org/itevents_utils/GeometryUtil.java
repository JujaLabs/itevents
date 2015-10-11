package org.itevents_utils;

import org.itevents.model.Location;
import org.postgis.Geometry;
import org.postgis.Point;
import org.postgis.binary.BinaryParser;
import org.postgis.binary.BinaryWriter;

/**
 * Created by vaa25 on 29.09.2015.
 */

// don't need this class
public class GeometryUtil {
    public static void main(String[] args) {
        GeometryUtil util = new GeometryUtil();
        Location location = new Location(234.5, 345.3);
        Point point = util.toPoint(location);
        String hexLoc = util.toString(location);
        String hexPoi = util.toString(point);
        System.out.println(location);
        System.out.println(point);
        System.out.println(hexLoc);
        System.out.println(hexPoi);
        System.out.println(util.toPoint(hexLoc));
        System.out.println(util.toLocation(hexPoi));
        System.out.println(util.getType(hexLoc));
        System.out.println(util.toGeometry(hexLoc));
    }

    public Point toPoint(Location location) {
        return new Point(location.getLongitude(), location.getLatitude());
    }

    public Point toPoint(String hex) {
        return (Point) toGeometry(hex);
    }

    public Geometry toGeometry(String hex) {
        return new BinaryParser().parse(hex);
    }

    public String getType(String hex) {
        return toGeometry(hex).getTypeString();
    }

    public Location toLocation(Point point) {
        return new Location(point.getY(), point.getX());
    }

    public Location toLocation(String hex) {
        return toLocation(toPoint(hex));
    }

    public String toString(Point point) {
        return new BinaryWriter().writeHexed(point);
    }

    public String toString(Location location) {
        return new BinaryWriter().writeHexed(toPoint(location));
    }
}
