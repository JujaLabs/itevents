<?xml version="1.0" encoding="UTF-8"?>

<databaseChangeLog xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
                   xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
                   http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-2.0.xsd">
    <changeSet id="021" author="Igor">
        <sql>
            CREATE TABLE subscriptions (
            user_id INT NOT NULL,
            event_id INT NOT NULL,
            FOREIGN KEY (event_id) REFERENCES events (id),
            FOREIGN KEY (user_id) REFERENCES users (id)
            );
        </sql>
        <rollback>
            DROP TABLE visit;
        </rollback>
    </changeSet>

</databaseChangeLog>