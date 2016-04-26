package org.itevents.service.geocoder;

import com.yandex.geocoder.api.geocoder.Geocoder;
import com.yandex.geocoder.api.geocoding.GeocoderRequest;
import com.yandex.geocoder.api.geocoding.GeocoderResponse;
import com.yandex.geocoder.api.geocoding.LatLng;
import org.itevents.dao.model.Location;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by zavodnyuk on 09.04.2016.
 */

@Service
public class YandexGeocoderService implements AddressLocationService {

    @Inject
    private Geocoder geocoder;

    @Override
    public Location getLocationByAddress(String address) {

        Location resultLocation = null;
        GeocoderRequest request = new GeocoderRequest.Builder(address).get();
        try {
            GeocoderResponse response = geocoder.geocode(request);
            LatLng latLng = response.getMostAccurateResult().getPoint().toLatLng();
            resultLocation = new Location(latLng.getLat(),latLng.getLng());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultLocation;
    }
}
