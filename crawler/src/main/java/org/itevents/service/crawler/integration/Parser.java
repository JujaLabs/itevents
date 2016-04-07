package org.itevents.service.crawler.integration;

import org.itevents.service.crawler.Entity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by vaa25 on 06.04.2016.
 */
@SuppressWarnings("PMD.LawOfDemeter")
public final class Parser {

    private static final String TAG_DD = ".dd";

    private final Document document;

    public Parser(final String html) {
        this.document = Jsoup.parse(html);
    }

    public Entity parse() {
        final Entity entity = new Entity();
        entity.setTitle(this.getTitle());
        entity.setDate(this.getDate());
        entity.setTime(this.getTime());
        entity.setAddress(this.getAddress());
        entity.setCity(this.getCity());
        entity.setRegistrationLink(this.getRegistrationLink());
        entity.setDescription(this.getDescription());
        entity.setPrice(this.getPrice());
        return entity;
    }

    private String getTitle() {
        return this.document.select(".title").text();
    }

    private String getDate() {
        return this.document.select(Parser.TAG_DD).first().text();
    }

    private String getTime() {
        return this.document.select(Parser.TAG_DD).get(1).text();
    }

    private String getAddress() {
        final String[] split = this.document.select(Parser.TAG_DD).get(2).text().split(",");
        return String.format("%s,%s", split[1], split[2]);
    }

    private String getCity() {
        return this.document.select(Parser.TAG_DD).get(0).text();
    }

    private String getRegistrationLink() {
        return this.document.select(":containsOwn(регистр) a")
            .first().attr("href");
    }

    private String getDescription() {
        return this.document.select(".b-typo").first().text();
    }

    private String getPrice() {
        String price = this.document.select(":containsOwn(Цена)").text();
        if (price.contains("у.е")) {
            price = price.split("у.е")[0].replaceAll("\\D", "");
        }
        return price;
    }
}
