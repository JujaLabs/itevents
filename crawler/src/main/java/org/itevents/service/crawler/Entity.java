package org.itevents.service.crawler;

/**
 * Created by vaa25 on 06.04.2016.
 */
public class Entity {
    private String title;
    private String date;
    private String time;
    private String address;
    private String registrationLink;
    private String city;
    private String description;
    private String price;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(final String time) {
        this.time = time;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getRegistrationLink() {
        return this.registrationLink;
    }

    public void setRegistrationLink(final String registrationLink) {
        this.registrationLink = registrationLink;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(final String price) {
        this.price = price;
    }


    public static class EntityBuilder {
        private String title;
        private String date;
        private String time;
        private String address;
        private String registrationLink;
        private String city;
        private String description;
        private String price;

        public EntityBuilder() {
        }

        public Entity.EntityBuilder price(String price) {
            this.price = price;
            return this;
        }

        public Entity.EntityBuilder but() {
            return Entity.EntityBuilder.anEntity().title(this.title).date(this.date).time(this.time).address(this.address).registrationLink(this.registrationLink).city(this.city).description(this.description);
        }

        public static Entity.EntityBuilder anEntity() {
            return new Entity.EntityBuilder();
        }

        public Entity.EntityBuilder title(String title) {
            this.title = title;
            return this;
        }

        public Entity.EntityBuilder date(String date) {
            this.date = date;
            return this;
        }

        public Entity.EntityBuilder time(String time) {
            this.time = time;
            return this;
        }

        public Entity.EntityBuilder address(String address) {
            this.address = address;
            return this;
        }

        public Entity.EntityBuilder registrationLink(String registrationLink) {
            this.registrationLink = registrationLink;
            return this;
        }

        public Entity.EntityBuilder city(String city) {
            this.city = city;
            return this;
        }

        public Entity.EntityBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Entity build() {
            Entity entity = new Entity();
            entity.setTitle(this.title);
            entity.setDate(this.date);
            entity.setTime(this.time);
            entity.setAddress(this.address);
            entity.setRegistrationLink(this.registrationLink);
            entity.setCity(this.city);
            entity.setDescription(this.description);
            entity.setPrice(this.price);
            return entity;
        }
    }
}
