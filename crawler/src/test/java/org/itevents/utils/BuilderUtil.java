package org.itevents.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.itevents.dao.model.City;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.Location;
import org.itevents.dao.model.Technology;
import org.itevents.dao.model.builder.CityBuilder;
import org.itevents.dao.model.builder.EventBuilder;
import org.itevents.dao.model.builder.TechnologyBuilder;

/**
 * Created by vaa25 on 30.09.2015.
 */
@SuppressWarnings("PMD.LawOfDemeter")
public final class BuilderUtil {

    private BuilderUtil() {
    }

    public static Event buildEventJava() throws ParseException {
        final List<Technology> technologies = new ArrayList<>();
        technologies.add(buildTechnologyJava());
        technologies.add(buildTechnologyLiquibase());
        technologies.add(buildTechnologyMyBatis());
        technologies.add(buildTechnologySpring());
        return EventBuilder.anEvent()
            .title("Java")
            .eventDate(parseDate("10.07.2115"))
            .regLink("http://www.java.com.ua")
            .address("Beresteyska")
            .location(new Location(50.458585, 30.742017))
            .contact("java@gmail.com")
            .city(buildCityKyiv())
            .technologies(technologies)
            .id(-1)
            .description("Java event in Kyiv")
            .build();
    }

    private static Date parseDate(final String date) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(date);
    }

    public static Technology buildTechnologyJava() {
        return TechnologyBuilder.aTechnology()
            .name("Java")
            .id(-1)
            .build();
    }

    public static City buildCityKyiv() {
        return CityBuilder.aCity()
            .name("Kyiv")
            .location(new Location(50.4505, 30.523))
            .id(-1)
            .build();

    }

    public static Technology buildTechnologySpring() {
        return TechnologyBuilder.aTechnology()
            .name("Spring")
            .id(-8)
            .build();
    }

    public static Technology buildTechnologyLiquibase() {
        return TechnologyBuilder.aTechnology()
            .name("Liquibase")
            .id(-4)
            .build();
    }

    public static Technology buildTechnologyMyBatis() {
        return TechnologyBuilder.aTechnology()
            .name("MyBatis")
            .id(-9)
            .build();
    }
}
