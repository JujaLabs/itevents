package org.itevents.util;

import org.itevents.model.*;
import org.itevents.util.builder.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
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
                .setAddress("Nyvky")
                .setLocation(new Location(50.458585, 30.742017))
                .setContact("java@gmail.com")
                .setCity(buildCityKyiv())
                .setTechnologies(technologies)
                .setId(-1)
                .build();
    }

    public static Event buildEventJs() throws ParseException {
        List<Technology> technologies = new ArrayList<>();
        technologies.add(buildTechnologyJavaScript());
        return EventBuilder.anEvent()
                .setTitle("JS")
                .setEventDate(new SimpleDateFormat("dd.MM.yyyy").parse("30.07.2015"))
                .setRegLink("http://www.js.com.ua")
                .setAddress("Beresteyska")
                .setLocation(new Location(50.458651, 30.403965))
                .setContact("js@gmail.com")
                .setCity(buildCityKyiv())
                .setTechnologies(technologies)
                .setPrice(20)
                .setCurrency(buildCurrencyEuro())
                .setId(-3)
                .build();
    }

    public static Currency buildCurrencyEuro() {
        return CurrencyBuilder.aCurrency()
                .setName("Euro")
                .setId(-3)
                .build();
    }

    public static Technology buildTechnologyJavaScript() {
        return TechnologyBuilder.aTechnology()
                .setName("JavaScript")
                .setId(-2)
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

    public static Role buildRoleGuest() {
        return RoleBuilder.aRole()
                .setName("guest")
                .setId(-1)
                .build();
    }

    public static Role buildRoleAdmin() {
        return RoleBuilder.aRole()
                .setName("admin")
                .setId(-2)
                .build();
    }

    public static Role buildRoleTest() {
        return RoleBuilder.aRole()
                .setName("testRole")
                .setId(-4)
                .build();
    }

    public static User buildUserGuest() {
        return UserBuilder.anUser()
                .setLogin("guest")
                .setPassword("guest")
                .setRole(buildRoleGuest())
                .setId(-1)
                .build();
    }

    public static User buildUserAnakin() {
        return UserBuilder.anUser()
                .setLogin("anakin@email.com")
                .setPassword("alex")
                .setRole(buildRoleAdmin())
                .setId(-2)
                .build();
    }

    public static User buildUserKuchin() {
        return UserBuilder.anUser()
                .setLogin("kuchin@email.com")
                .setPassword("viktor")
                .setRole(buildRoleAdmin())
                .setId(-3)
                .build();
    }

    public static User buildUserTest() {
        return UserBuilder.anUser()
                .setLogin("testUser")
                .setPassword("testUserPassword")
                .setRole(buildRoleGuest())
                .setId(-5)
                .build();
    }

    public static VisitLog buildFirstVisitLog() throws ParseException {
        return VisitLogBuilder.aVisitLog()
                .setUser(buildUserGuest())
                .setEvent(buildEventJava())
                .setDate(new GregorianCalendar(2016, 6, 20).getTime())
                .setId(-1)
                .build();
    }

    public static VisitLog buildVisitLogTest() throws ParseException {
        return VisitLogBuilder.aVisitLog()
                .setUser(buildUserGuest())
                .setEvent(buildEventJs())
                .setDate(new GregorianCalendar(2016, 6, 20).getTime())
                .setId(-8)
                .build();
    }

    public static List<VisitLog> buildListVisitLogJava() throws ParseException {
        List<VisitLog> result = new ArrayList<>(3);
        result.add(VisitLogBuilder.aVisitLog()
                .setEvent(buildEventJava())
                .setUser(buildUserGuest())
                .setId(-1)
                .build());
        result.add(VisitLogBuilder.aVisitLog()
                .setEvent(buildEventJava())
                .setUser(buildUserAnakin())
                .setId(-2)
                .build());
        result.add(VisitLogBuilder.aVisitLog()
                .setEvent(buildEventJava())
                .setUser(buildUserKuchin())
                .setId(-7)
                .build());
        return result;
    }
}
