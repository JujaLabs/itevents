package org.itevents.service.geocoder;

import com.google.gson.Gson;
import com.yandex.geocoder.api.geocoder.Geocoder;
import com.yandex.geocoder.api.geocoding.GeocoderRequest;
import com.yandex.geocoder.api.geocoding.GeocoderResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.inject.Inject;
import java.io.IOException;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by zavodnyuk on 09.04.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class YandexGeocoderServiceTest {

    private String ADDRESS = "Киев, площадь Независимости, 1";

    @Inject
    @InjectMocks
    private YandexGeocoderService addressLocationService;

    @Inject
    @Mock
    private Geocoder geocoder;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGeocode() throws IOException {
        String validResponse =  "{\"response\":{\"GeoObjectCollection\":{\"metaDataProperty\":{\"GeocoderResponseMetaData\":{\"request\":\"Киев, площадь Независимости, 1\",\"found\":\"24\",\"results\":\"1\"}},\"featureMember\":[{\"GeoObject\":{\"metaDataProperty\":{\"GeocoderMetaData\":{\"kind\":\"house\",\"text\":\"Украина, Киев, площадь Независимости, 1\",\"precision\":\"exact\",\"AddressDetails\":{\"Country\":{\"AddressLine\":\"Киев, площадь Независимости, 1\",\"CountryNameCode\":\"UA\",\"CountryName\":\"Украина\",\"AdministrativeArea\":{\"AdministrativeAreaName\":\"Киев\",\"Locality\":{\"LocalityName\":\"Киев\",\"Thoroughfare\":{\"ThoroughfareName\":\"площадь Независимости\",\"Premise\":{\"PremiseNumber\":\"1\"}}}}}}}},\"description\":\"Киев, Украина\",\"name\":\"площадь Независимости, 1\",\"boundedBy\":{\"Envelope\":{\"lowerCorner\":\"30.514396 50.44568\",\"upperCorner\":\"30.530853 50.456188\"}},\"Point\":{\"pos\":\"30.522624 50.450934\"}}}]}}}";
        GeocoderResponse response = new Gson().fromJson(validResponse, GeocoderResponse.class);
        GeocoderRequest request = new GeocoderRequest.Builder(ADDRESS).get();
        when(geocoder.geocode(request)).thenReturn(response);

        addressLocationService.getLocationByAddress(ADDRESS);
        verify(geocoder).geocode(request);
    }
}
