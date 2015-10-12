package org.itevents_utils.dbunit.dataset_loader;

/**
 * Created by vaa25 on 13.10.2015.
 */
public class CityReplacementDataSetLoader extends AbstractReplacementDataSetLoader {

    @Override
    protected void replaceStrings() {
        subStringReplacements.put("Kyiv", "New York");
    }
}
