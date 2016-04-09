package org.itevents.service.geocoder;

import org.itevents.dao.model.Location;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by zavodnyuk on 09.04.2016.
 */

public class YandexGeocoderServiceTest {

    private String ADDRESS = "Киев, площадь Независимости, 1";

    private AddressLocationService addressLocationService = new YandexGeocoderService();


    /*
    * @TODO: Need to refactor this test by the rules. Now test PASSED with using internet. But test shouldn't use internet.
    * issue 206
    * https://github.com/JuniorsJava/itevents/issues/206
    */
    @Ignore
    @Test
    public void shouldReturnLocationByAddress() throws Exception {
        Location expectedLocation = new Location(50.450934, 30.522624);

        Location actualLocation = addressLocationService.getLocationByAddress(ADDRESS);
        assertEquals(expectedLocation,actualLocation);
    }
}
