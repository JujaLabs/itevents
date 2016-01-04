package org.itevents.test_utils.dbunit.dataset_loader;

import com.github.springtestdbunit.dataset.DataSetLoader;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vaa25 on 13.10.2015.
 */
public abstract class AbstractReplacementDataSetLoader implements DataSetLoader {

    private Map<Object, Object> objectReplacements;

    private Map<String, String> subStringReplacements;

    protected IDataSet dataSet;

    public AbstractReplacementDataSetLoader() {
        objectReplacements = new HashMap<>();
        subStringReplacements = new HashMap<>();
    }

    protected final void replace(Object oldValue, Object newValue) {
        objectReplacements.put(oldValue, newValue);
    }

    protected final void replace(String oldValue, String newValue) {
        subStringReplacements.put(oldValue, newValue);
    }

    protected abstract void replace() throws DataSetException;

    public final IDataSet loadDataSet(Class<?> testClass, String location) throws Exception {
        this.dataSet = new FlatXmlDataSetLoader().loadDataSet(testClass, location);
        replace();
        return new ReplacementDataSet(this.dataSet, objectReplacements, subStringReplacements);
    }

}