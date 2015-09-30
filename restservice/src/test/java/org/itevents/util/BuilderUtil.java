package org.itevents.util;

import org.itevents.model.City;
import org.itevents.model.Event;
import org.itevents.model.Location;
import org.itevents.model.Technology;
import org.itevents.model.builder.CityBuilder;
import org.itevents.model.builder.EventBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaa25 on 30.09.2015.
 */
public class BuilderUtil {
    public static City buildCityKyiv() {
        double kyivLatitude = 50.4505;
        double kyivLongitude = 30.523;
        return CityBuilder.aCity()
                .setName("Kyiv")
                .setLocation(new Location(kyivLatitude, kyivLongitude))
                .build();

    }

    public static City buildCityTest() {
        return CityBuilder.aCity()
                .setName("TestCity")
                .setDetails("Test city details")
                .setLocation(new Location(0.0, 0.0))
                .build();
    }

    public static Event buildEventRuby() throws ParseException {
        return EventBuilder.anEvent()
                .setTitle("Ruby")
                .setEventDate(new SimpleDateFormat("dd.MM.yyyy").parse("20.07.2015"))
                .setRegLink("http://www.ruby.com.ua")
                .setAddress("Shulyavska")
                .setLocation(new Location(50.454605, 30.445495))
                .setContact("ruby@gmail.com")
                .build();
    }

    public static Event buildEventJava() throws ParseException {
        List<Technology> technologies = new ArrayList<>();
        technologies.add(new Technology("Java"));
        technologies.add(new Technology("Liquibase"));
        technologies.add(new Technology("Spring"));
        technologies.add(new Technology("MyBatis"));
        return EventBuilder.anEvent()
                .setTitle("Java")
                .setEventDate(new SimpleDateFormat("dd.MM.yyyy").parse("10.07.2015"))
                .setRegLink("http://www.java.com.ua")
                .setAddress("Beresteyska")
                .setLocation(new Location(50.458585, 30.742017))
                .setContact("java@gmail.com")
                .setCity(buildCityKyiv())
                .setTechnologies(technologies)
                .build();
    }

}
