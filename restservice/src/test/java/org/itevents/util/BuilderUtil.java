package org.itevents.util;

import org.itevents.model.City;
import org.itevents.model.Event;
import org.itevents.model.Location;
import org.itevents.model.Technology;
import org.itevents.util.builder.CityBuilder;
import org.itevents.util.builder.EventBuilder;
import org.itevents.util.builder.TechnologyBuilder;

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
                .setId(-1)
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
        technologies.add(buildTechnologyMyBatis());
        technologies.add(buildTechnologySpring());
        technologies.add(buildTechnologyLiquibase());
        technologies.add(buildTechnologyJava());
        return EventBuilder.anEvent()
                .setTitle("Java")
                .setEventDate(new SimpleDateFormat("dd.MM.yyyy").parse("10.07.2015"))
                .setRegLink("http://www.java.com.ua")
                .setAddress("Beresteyska")
                .setLocation(new Location(50.458585, 30.742017))
                .setContact("java@gmail.com")
                .setCity(buildCityKyiv())
                .setTechnologies(technologies)
                .setId(-1)
                .build();
    }

    public static Technology buildTechnologyJava() {
        return TechnologyBuilder.aTechnology()
                .setName("Java")
                .setId(-1)
                .build();
    }

    public static Technology buildTechnologyLiquibase() {
        return TechnologyBuilder.aTechnology()
                .setName("Liquibase")
                .setId(-4)
                .build();
    }

    public static Technology buildTechnologySpring() {
        return TechnologyBuilder.aTechnology()
                .setName("Spring")
                .setId(-8)
                .build();
    }

    public static Technology buildTechnologyMyBatis() {
        return TechnologyBuilder.aTechnology()
                .setName("MyBatis")
                .setId(-9)
                .build();
    }
}
