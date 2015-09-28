package org.itevents.dbunit;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.xml.sax.InputSource;

import java.io.InputStream;

public class SpatialAwareFlatXmlDataSet extends FlatXmlDataSet {

    public SpatialAwareFlatXmlDataSet(final InputStream inputStream) throws DataSetException {
        super(new FlatXmlProducer(new InputSource(inputStream)));
    }

    @Override
    public void row(final Object[] values) throws DataSetException {
        convertGeometryColumnsFromTextToBinary(values);
        super.row(values);
    }

    private void convertGeometryColumnsFromTextToBinary(final Object[] values) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null && values[i].toString().startsWith("GEOM_")) {
                values[i] = GeometryUtil.convertFromTextToBinary(values[i].toString().substring(5));
            }
        }
    }
}
