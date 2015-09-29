--liquibase formatted sql

--changeset vaa25:14
INSERT INTO event (title, event_date, create_date, reg_link, address, GEOM_point, contact, price, currency_id, city_id)
VALUES
  ('Java', '10.07.2015', NULL, 'http://www.java.com.ua', 'Beresteyska', 'POINT(30.742017 50.458585)', 'java@gmail.com',
   NULL, NULL, 1),
  ('PHP', '20.07.2015', NULL, 'http://www.php.com.ua', 'Shulyavska', 'POINT(30.445495 50.454605)', 'php@gmail.com',
   NULL, NULL, 3),
  ('JS', '30.07.2015', NULL, 'http://www.js.com.ua', 'Nyvky', 'POINT(30.403965 50.458651)', 'js@gmail.com', 20, 3, 1),
  ('CPlusPlus', '15.07.2015', NULL, 'http://www.cplusplus.com.ua', 'Vokzalna', 'POINT(30.489324 50.441353)',
   'cplusplus@gmail.com', 20, 3, 2),
  ('ObjectiveC', '05.07.2015', NULL, 'http://www.objective-c.com.ua', 'Universytet', 'POINT(30.506104 50.444387)',
   'objectivec@gmail.com', 20, 3, 2),
  ('CSharp', '09.07.2015', NULL, 'http://www.c#.com.ua', 'Khreschatyk', 'POINT(30.522390 50.447694)',
   'csharp@gmail.com', 20, 3, 3),
  ('Delphi', '05.07.2015', NULL, 'http://www.delphi.com.ua', 'Arsenalna', 'POINT(30.547603 50.442843)',
   'delphi@gmail.com', 30, 1, 1);

--rollback DELETE FROM event;