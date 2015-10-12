package org.itevents_utils.dbunit.dataset_loader;

import com.github.springtestdbunit.dataset.DataSetLoader;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vaa25 on 13.10.2015.
 */
public abstract class AbstractReplacementDataSetLoader implements DataSetLoader {

    protected Map<Object, Object> objectReplacements;

    protected Map<String, String> subStringReplacements;

    public AbstractReplacementDataSetLoader() {
        objectReplacements = new HashMap<>();
        subStringReplacements = new HashMap<>();
        replaceObjects();
        replaceStrings();
    }

    protected void replaceObjects() {

    }

    protected void replaceStrings() {

    }

    public IDataSet loadDataSet(Class<?> testClass, String location) throws Exception {
        IDataSet dataSet = new FlatXmlDataSetLoader().loadDataSet(testClass, location);
        return new ReplacementDataSet(dataSet, objectReplacements, subStringReplacements);
    }

}
