package org.itevents.dao.model.builder;

import org.itevents.dao.model.City;
import org.itevents.dao.model.Location;

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

    public CityBuilder id(int id) {
        this.id = id;
        return this;
    }

    public CityBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CityBuilder details(String details) {
        this.details = details;
        return this;
    }

    public CityBuilder location(Location location) {
        this.location = location;
        return this;
    }

    public CityBuilder but() {
        return aCity().id(id).name(name).details(details).location(location);
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
