package org.itevents.service.geocoder;

import org.itevents.dao.model.Location;

/**
 * Created by zavodnyuk on 03.04.2016.
 */
public interface AddressLocationService {

    Location getLocationByAddress(String address);

}
