package org.itevents_utils.builder;

import org.itevents.model.City;
import org.itevents.model.Location;

/**
 * Created by vaa25 on 30.09.2015.
 */
public class CityBuilder {
    private int id;
    private String name;
    private String details;
    private Location location;

    private CityBuilder() {
    }

    public static CityBuilder aCity() {
        return new CityBuilder();
    }

    public CityBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public CityBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CityBuilder setDetails(String details) {
        this.details = details;
        return this;
    }

    public CityBuilder setLocation(Location location) {
        this.location = location;
        return this;
    }

    public CityBuilder but() {
        return aCity().setId(id).setName(name).setDetails(details).setLocation(location);
    }

    public City build() {
        City city = new City();
        city.setId(id);
        city.setName(name);
        city.setDetails(details);
        city.setLocation(location);
        return city;
    }
}
