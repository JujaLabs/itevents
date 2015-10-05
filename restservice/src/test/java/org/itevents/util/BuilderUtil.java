package org.itevents.util;

import org.itevents.model.*;
import org.itevents.util.builder.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vaa25 on 30.09.2015.
 */
public class BuilderUtil {
    public static City buildCityKyiv() {
        return CityBuilder.aCity()
                .setName("Kyiv")
                .setLocation(new Location(50.4505, 30.523))
                .setId(-1)
                .build();

    }

    public static City buildCityBoyarka() {
        return CityBuilder.aCity()
                .setName("Boyarka")
                .setLocation(new Location(50.32917, 30.28861))
                .setId(-3)
                .build();

    }

    public static City buildCityOdessa() {
        return CityBuilder.aCity()
                .setName("Odessa")
                .setLocation(new Location(46.46667, 30.73333))
                .setId(-2)
                .build();

    }

    public static City buildCityTest() {
        return CityBuilder.aCity()
                .setName("TestCity")
                .setDetails("Test city details")
                .setLocation(new Location(0.0, 0.0))
                .setId(-5)
                .build();
    }


    public static Event buildEventJava() throws ParseException {
        Set<Technology> technologies = new HashSet<>();
        technologies.add(buildTechnologyMyBatis());
        technologies.add(buildTechnologySpring());
        technologies.add(buildTechnologyLiquibase());
        technologies.add(buildTechnologyJava());
        return EventBuilder.anEvent()
                .setTitle("Java")
                .setEventDate(parseDate("10.07.2015"))
                .setRegLink("http://www.java.com.ua")
                .setAddress("Beresteyska")
                .setLocation(new Location(50.458585, 30.742017))
                .setContact("java@gmail.com")
                .setCity(buildCityKyiv())
                .setTechnologies(technologies)
                .setId(-1)
                .build();
    }

    public static Event buildEventPhp() throws ParseException {
        Set<Technology> technologies = new HashSet<>();
        technologies.add(buildTechnologyJavaScript());
        return EventBuilder.anEvent()
                .setTitle("PHP")
                .setEventDate(parseDate("20.07.2015"))
                .setRegLink("http://www.php.com.ua")
                .setAddress("Shulyavska")
                .setLocation(new Location(50.454605, 30.445495))
                .setContact("php@gmail.com")
                .setCity(buildCityBoyarka())
                .setTechnologies(technologies)
                .setId(-2)
                .build();
    }

    public static Event buildEventJs() throws ParseException {
        Set<Technology> technologies = new HashSet<>();
        technologies.add(buildTechnologyJavaScript());
        return EventBuilder.anEvent()
                .setTitle("JS")
                .setEventDate(parseDate("30.07.2015"))
                .setRegLink("http://www.js.com.ua")
                .setAddress("Nyvky")
                .setLocation(new Location(50.458651, 30.403965))
                .setContact("js@gmail.com")
                .setCity(buildCityKyiv())
                .setTechnologies(technologies)
                .setPrice(20)
                .setCurrency(buildCurrencyEuro())
                .setId(-3)
                .build();
    }

    public static Event buildEventCplus() throws ParseException {
        Set<Technology> technologies = new HashSet<>();
        technologies.add(buildTechnologyJava());
        technologies.add(buildTechnologyPhp());
        return EventBuilder.anEvent()
                .setTitle("CPlusPlus")
                .setEventDate(parseDate("15.07.2015"))
                .setRegLink("http://www.cplusplus.com.ua")
                .setAddress("Impact Hub")
                .setLocation(new Location(46.481782, 30.747175))
                .setContact("cplusplus@gmail.com")
                .setCity(buildCityOdessa())
                .setTechnologies(technologies)
                .setPrice(20)
                .setCurrency(buildCurrencyEuro())
                .setId(-4)
                .build();
    }

    public static Event buildEventObjectiveC() throws ParseException {
        Set<Technology> technologies = new HashSet<>();
        technologies.add(buildTechnologyGradle());
        return EventBuilder.anEvent()
                .setTitle("ObjectiveC")
                .setEventDate(parseDate("05.07.2015"))
                .setRegLink("http://www.objective-c.com.ua")
                .setAddress("Provectus")
                .setLocation(new Location(46.472508, 30.758417))
                .setContact("objectivec@gmail.com")
                .setCity(buildCityOdessa())
                .setTechnologies(technologies)
                .setId(-5)
                .build();
    }

    public static Event buildEventCsharp() throws ParseException {
        Set<Technology> technologies = new HashSet<>();
        technologies.add(buildTechnologyMaven());
        return EventBuilder.anEvent()
                .setTitle("CSharp")
                .setEventDate(parseDate("09.07.2015"))
                .setRegLink("http://www.c#.com.ua")
                .setAddress("Khreschatyk")
                .setLocation(new Location(50.447694, 30.52239))
                .setContact("csharp@gmail.com")
                .setCity(buildCityBoyarka())
                .setTechnologies(technologies)
                .setId(-6)
                .build();
    }

    public static Event buildEventDelphi() throws ParseException {
        Set<Technology> technologies = new HashSet<>();
        technologies.add(buildTechnologyAnt());
        return EventBuilder.anEvent()
                .setTitle("Delphi")
                .setEventDate(parseDate("05.07.2015"))
                .setRegLink("http://www.delphi.com.ua")
                .setAddress("Arsenalna")
                .setLocation(new Location(50.442843, 30.547603))
                .setContact("delphi@gmail.com")
                .setCity(buildCityBoyarka())
                .setTechnologies(technologies)
                .setId(-7)
                .build();
    }

    public static Event buildEventRuby() throws ParseException {
        return EventBuilder.anEvent()
                .setTitle("Ruby")
                .setEventDate(parseDate("20.07.2015"))
                .setRegLink("http://www.ruby.com.ua")
                .setAddress("Shulyavska")
                .setLocation(new Location(50.454605, 30.445495))
                .setContact("ruby@gmail.com")
                .setCity(buildCityKyiv())
                .setId(-8)
                .build();
    }

    private static Date parseDate(String date) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy").parse(date);
    }

    public static Currency buildCurrencyUsd() {
        return CurrencyBuilder.aCurrency()
                .setName("USD")
                .setId(-1)
                .build();
    }

    public static Currency buildCurrencyEuro() {
        return CurrencyBuilder.aCurrency()
                .setName("Euro")
                .setId(-3)
                .build();
    }

    public static Currency buildCurrencyTest() {
        return CurrencyBuilder.aCurrency()
                .setName("Test")
                .setId(-4)
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

    public static Technology buildTechnologyPhp() {
        return TechnologyBuilder.aTechnology()
                .setName("Php")
                .setId(-3)
                .build();
    }

    public static Technology buildTechnologyLiquibase() {
        return TechnologyBuilder.aTechnology()
                .setName("Liquibase")
                .setId(-4)
                .build();
    }

    public static Technology buildTechnologyGradle() {
        return TechnologyBuilder.aTechnology()
                .setName("Gradle")
                .setId(-5)
                .build();
    }

    public static Technology buildTechnologyMaven() {
        return TechnologyBuilder.aTechnology()
                .setName("Maven")
                .setId(-6)
                .build();
    }

    public static Technology buildTechnologyAnt() {
        return TechnologyBuilder.aTechnology()
                .setName("Ant")
                .setId(-7)
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

    public static Technology buildTechnologySql() {
        return TechnologyBuilder.aTechnology()
                .setName("Sql")
                .setId(-10)
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

    public static Set<VisitLog> buildListVisitLogJava() throws ParseException {
        Set<VisitLog> result = new HashSet<>();
        result.add(VisitLogBuilder.aVisitLog()
                .setEvent(buildEventJava())
                .setUser(buildUserGuest())
                .setDate(parseDate("20.07.2016"))
                .setId(-1)
                .build());
        result.add(VisitLogBuilder.aVisitLog()
                .setEvent(buildEventJava())
                .setUser(buildUserAnakin())
                .setDate(parseDate("20.07.2016"))
                .setId(-2)
                .build());
        result.add(VisitLogBuilder.aVisitLog()
                .setEvent(buildEventJava())
                .setUser(buildUserKuchin())
                .setDate(parseDate("20.09.2016"))
                .setId(-7)
                .build());
        return result;
    }
}
