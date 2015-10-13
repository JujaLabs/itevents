package org.itevents_utils.dbunit;

import org.dbunit.dataset.datatype.TypeCastException;
import org.dbunit.ext.postgresql.GeometryType;
import org.postgis.binary.BinaryParser;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by vaa25 on 29.09.2015.
 */
public class PostgisGeometryType extends GeometryType {

    @Override
    public Object getSqlValue(int column, ResultSet resultSet) throws SQLException, TypeCastException {
        return new BinaryParser().parse((String) super.getSqlValue(column, resultSet));
    }
}
