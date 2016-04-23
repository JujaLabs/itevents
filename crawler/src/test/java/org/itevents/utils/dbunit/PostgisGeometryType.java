package org.itevents.utils.dbunit;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.dbunit.dataset.datatype.TypeCastException;
import org.dbunit.ext.postgresql.GeometryType;
import org.postgis.binary.BinaryParser;

/**
 * Created by vaa25 on 29.09.2015.
 */
public class PostgisGeometryType extends GeometryType {

    @Override
    public Object getSqlValue(final int column, final ResultSet resultSet)
        throws SQLException, TypeCastException {
        return new BinaryParser()
            .parse((String) super.getSqlValue(column, resultSet));
    }
}
