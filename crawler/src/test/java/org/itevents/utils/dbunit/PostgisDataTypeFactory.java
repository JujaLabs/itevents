package org.itevents.utils.dbunit;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.ext.postgresql.GeometryType;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;

/**
 * Created by vaa25 on 29.09.2015.
 */
public class PostgisDataTypeFactory extends PostgresqlDataTypeFactory {

    @Override
    public DataType createDataType(final int sqlType, final String sqlTypeName)
        throws DataTypeException {
        DataType result = super.createDataType(sqlType, sqlTypeName);
        if (result instanceof GeometryType) {
            result = new PostgisGeometryType();
        }
        return result;
    }
}
