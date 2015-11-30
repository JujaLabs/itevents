package org.itevents.test_utils;


import org.itevents.model.*;
import org.itevents.model.builder.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vaa25 on 30.09.2015.
 */
public class BuilderUtil {

    public static City buildCityKyiv() {
        return CityBuilder.aCity()
                .name("Kyiv")
                .location(new Location(50.4505, 30.523))
                .id(-1)
                .build();

    }

    public static City buildCityBoyarka() {
        return CityBuilder.aCity()
                .name("Boyarka")
                .location(new Location(50.32917, 30.28861))
                .id(-3)
                .build();

    }

    public static City buildCityOdessa() {
        return CityBuilder.aCity()
                .name("Odessa")
                .location(new Location(46.46667, 30.73333))
                .id(-2)
                .build();

    }

    public static City buildCityTest() {
        return CityBuilder.aCity()
                .name("TestCity")
                .details("Test city details")
                .location(new Location(0.0, 0.0))
                .id(-5)
                .build();
    }


    public static Event buildEventJava() throws ParseException {
        List<Technology> technologies = new ArrayList<>();
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
                .build();
    }

    public static Event buildEventPhp() throws ParseException {
        List<Technology> technologies = new ArrayList<>();
        technologies.add(buildTechnologyJavaScript());
        return EventBuilder.anEvent()
                .title("PHP")
                .eventDate(parseDate("20.07.2115"))
                .regLink("http://www.php.com.ua")
                .address("Shulyavska")
                .location(new Location(50.454605, 30.445495))
                .contact("php@gmail.com")
                .city(buildCityBoyarka())
                .technologies(technologies)
                .id(-2)
                .build();
    }

    public static Event buildEventJs() throws ParseException {
        List<Technology> technologies = new ArrayList<>();
        technologies.add(buildTechnologyPhp());
        return EventBuilder.anEvent()
                .title("JS")
                .eventDate(parseDate("30.07.2115"))
                .regLink("http://www.js.com.ua")
                .address("Nyvky")
                .location(new Location(50.458651, 30.403965))
                .contact("js@gmail.com")
                .city(buildCityKyiv())
                .technologies(technologies)
                .price(20)
                .currency(buildCurrencyEuro())
                .id(-3)
                .build();
    }

    public static Event buildEventCplus() throws ParseException {
        List<Technology> technologies = new ArrayList<>();
        technologies.add(buildTechnologyJava());
        technologies.add(buildTechnologyPhp());
        return EventBuilder.anEvent()
                .title("CPlusPlus")
                .eventDate(parseDate("15.07.2115"))
                .regLink("http://www.cplusplus.com.ua")
                .address("Impact Hub")
                .location(new Location(46.481782, 30.747175))
                .contact("cplusplus@gmail.com")
                .city(buildCityOdessa())
                .technologies(technologies)
                .price(20)
                .currency(buildCurrencyEuro())
                .id(-4)
                .build();
    }

    public static Event buildEventObjectiveC() throws ParseException {
        List<Technology> technologies = new ArrayList<>();
        technologies.add(buildTechnologyGradle());
        return EventBuilder.anEvent()
                .title("ObjectiveC")
                .eventDate(parseDate("05.07.2115"))
                .regLink("http://www.objective-c.com.ua")
                .address("Provectus")
                .location(new Location(46.472508, 30.758417))
                .contact("objectivec@gmail.com")
                .city(buildCityOdessa())
                .technologies(technologies)
                .price(20)
                .currency(buildCurrencyEuro())
                .id(-5)
                .build();
    }

    public static Event buildEventCsharp() throws ParseException {
        List<Technology> technologies = new ArrayList<>();
        technologies.add(buildTechnologyMaven());
        return EventBuilder.anEvent()
                .title("CSharp")
                .eventDate(parseDate("09.07.2115"))
                .regLink("http://www.c#.com.ua")
                .address("Khreschatyk")
                .location(new Location(50.447694, 30.52239))
                .contact("csharp@gmail.com")
                .city(buildCityBoyarka())
                .price(20)
                .currency(buildCurrencyEuro())
                .technologies(technologies)
                .id(-6)
                .build();
    }

    public static Event buildEventDelphi() throws ParseException {
        List<Technology> technologies = new ArrayList<>();
        technologies.add(buildTechnologyAnt());
        return EventBuilder.anEvent()
                .title("Delphi")
                .eventDate(parseDate("05.07.2115"))
                .regLink("http://www.delphi.com.ua")
                .address("Arsenalna")
                .location(new Location(50.442843, 30.547603))
                .contact("delphi@gmail.com")
                .price(30)
                .currency(buildCurrencyUsd())
                .city(buildCityKyiv())
                .technologies(technologies)
                .id(-7)
                .build();
    }

    public static Event buildEventRuby() throws ParseException {
        return EventBuilder.anEvent()
                .title("Ruby")
                .eventDate(parseDate("20.07.2115"))
                .regLink("http://www.ruby.com.ua")
                .address("Shulyavska")
                .location(new Location(50.454605, 30.445495))
                .contact("ruby@gmail.com")
                .city(buildCityKyiv())
                .id(-8)
                .build();
    }

    private static Date parseDate(String date) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy").parse(date);
    }

    public static Currency buildCurrencyUsd() {
        return CurrencyBuilder.aCurrency()
                .name("USD")
                .id(-1)
                .build();
    }

    public static Currency buildCurrencyEuro() {
        return CurrencyBuilder.aCurrency()
                .name("Euro")
                .id(-3)
                .build();
    }

    public static Currency buildCurrencyTest() {
        return CurrencyBuilder.aCurrency()
                .name("Test")
                .id(-4)
                .build();
    }

    public static Technology buildTechnologyJava() {
        return TechnologyBuilder.aTechnology()
                .name("Java")
                .id(-1)
                .build();
    }

    public static Technology buildTechnologyJavaScript() {
        return TechnologyBuilder.aTechnology()
                .name("JavaScript")
                .id(-2)
                .build();
    }

    public static Technology buildTechnologyPhp() {
        return TechnologyBuilder.aTechnology()
                .name("PHP")
                .id(-3)
                .build();
    }

    public static Technology buildTechnologyLiquibase() {
        return TechnologyBuilder.aTechnology()
                .name("Liquibase")
                .id(-4)
                .build();
    }

    public static Technology buildTechnologyGradle() {
        return TechnologyBuilder.aTechnology()
                .name("Gradle")
                .id(-5)
                .build();
    }

    public static Technology buildTechnologyMaven() {
        return TechnologyBuilder.aTechnology()
                .name("Maven")
                .id(-6)
                .build();
    }

    public static Technology buildTechnologyAnt() {
        return TechnologyBuilder.aTechnology()
                .name("Ant")
                .id(-7)
                .build();
    }

    public static Technology buildTechnologySpring() {
        return TechnologyBuilder.aTechnology()
                .name("Spring")
                .id(-8)
                .build();
    }

    public static Technology buildTechnologyMyBatis() {
        return TechnologyBuilder.aTechnology()
                .name("MyBatis")
                .id(-9)
                .build();
    }

    public static Technology buildTechnologySql() {
        return TechnologyBuilder.aTechnology()
                .name("SQL")
                .id(-10)
                .build();
    }

    public static Technology buildTechnologyTest() {
        return TechnologyBuilder.aTechnology()
                .name("TestTechnology")
                .id(-11)
                .build();
    }

    public static Role buildRoleGuest() {
        return RoleBuilder.aRole()
                .name("guest")
                .id(-1)
                .build();
    }

    public static Role buildRoleAdmin() {
        return RoleBuilder.aRole()
                .name("admin")
                .id(-2)
                .build();
    }

    public static Role buildRoleSubscriber() {
        return RoleBuilder.aRole()
                .name("subscriber")
                .id(-3)
                .build();
    }

    public static Role buildRoleTest() {
        return RoleBuilder.aRole()
                .name("testRole")
                .id(-4)
                .build();
    }

    public static User buildUserGuest() {
        return UserBuilder.anUser()
                .login("guest")
                .password("guest")
                .role(buildRoleGuest())
                .id(-1)
                .build();
    }

    public static User buildUserAnakin() {
        return UserBuilder.anUser()
                .login("anakin@email.com")
                .password("$2a$10$XHrRyJdlnIWe3EHbWAO6teR1LYjif1r4J4t5OvwfnLZy7pnmlANlq") //alex
                .role(buildRoleAdmin())
                .id(-2)
                .build();
    }

    public static User buildUserKuchin() {
        return UserBuilder.anUser()
                .login("kuchin@email.com")
                .password("$2a$10$aPyCWJ8WsJb0gTlz.IL/u.7kB7WiyZr67PUDoEO7x5D40OFOz1rWq") //viktor
                .role(buildRoleAdmin())
                .id(-3)
                .build();
    }

    public static User buildUserVlasov() {
        return UserBuilder.anUser()
                .login("vlasov@email.com")
                .password("$2a$10$uB.nFUPkpIIoY1HpYmsM5.YHNiGFEYMDJbaK1Swt6KkknCWPjtGkm") //alex
                .role(buildRoleSubscriber())
                .id(-4)
                .build();
    }

    public static User buildUserTest() {
        return UserBuilder.anUser()
                .login("testUser")
                .password("testUserPassword")
                .role(buildRoleGuest())
                .id(-5)
                .build();
    }

    public static User buildSubscriberTest() {
        return UserBuilder.anUser()
                .login("testSubscriber")
                .password("testSubscriberPassword")
                .role(buildRoleSubscriber())
                .subscribed(true)
                .id(-6)
                .build();
    }

    public static VisitLog buildVisitLogFirst() throws ParseException {
        return VisitLogBuilder.aVisitLog()
                .user(buildUserGuest())
                .event(buildEventJava())
                .date(parseDate("20.07.2015"))
                .id(-1)
                .build();
    }

    public static VisitLog buildVisitLogSecond() throws ParseException {
        return VisitLogBuilder.aVisitLog()
                .event(buildEventJava())
                .user(buildUserAnakin())
                .date(parseDate("20.07.2015"))
                .id(-2)
                .build();
    }

    public static VisitLog buildVisitLogSeventh() throws ParseException {
        return VisitLogBuilder.aVisitLog()
                .event(buildEventJava())
                .user(buildUserKuchin())
                .date(parseDate("20.09.2015"))
                .id(-7)
                .build();
    }

    public static VisitLog buildVisitLogTest() throws ParseException {
        return VisitLogBuilder.aVisitLog()
                .user(buildUserGuest())
                .event(buildEventJs())
                .date(parseDate("20.06.2015"))
                .id(-8)
                .build();
    }

    public static List<VisitLog> buildListVisitLogJava() throws ParseException {
        List<VisitLog> result = new ArrayList<>();
        result.add(buildVisitLogSecond());
        result.add(buildVisitLogFirst());
        result.add(buildVisitLogSeventh());
        return result;
    }

    public static Filter buildFilterFirst() {
        return FilterBuilder.aFilter()
                .city(buildCityKyiv())
                .technology(buildTechnologyJava())
                .technology(buildTechnologyMaven())
                .technology(buildTechnologyMyBatis())
                .technology(buildTechnologySpring())
                .id(-1)
                .build();
    }

    public static Filter buildFilterFifth() {
        return FilterBuilder.aFilter()
                .city(buildCityKyiv())
                .free(false)
                .technologies(new ArrayList<Technology>())
                .id(-5)
                .build();
    }

    public static Filter buildFilterTest() throws ParseException {
        return FilterBuilder.aFilter()
                .city(buildCityOdessa())
                .free(true)
                .createDate(parseDate("16.07.2015"))
                .technology(buildTechnologyJava())
                .technology(buildTechnologyJavaScript())
                .id(-6)
                .build();
    }

}
