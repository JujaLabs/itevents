package org.itevents.mybatis.mapper.dbunit;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;
import org.dbunit.dataset.IDataSet;
import org.springframework.core.io.Resource;

public class SpatialAwareFlatXmlDataSetLoader extends AbstractDataSetLoader {
    @Override
    protected IDataSet createDataSet(final Resource resource) throws Exception {
        return new SpatialAwareFlatXmlDataSet(resource.getInputStream());
    }
}
