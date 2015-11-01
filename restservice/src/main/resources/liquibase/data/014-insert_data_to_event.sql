--liquibase formatted sql

--changeset vaa25:14
INSERT INTO event (id, title, event_date, create_date, reg_link, address, point, contact, price, currency_id, city_id)
VALUES
  (-1, 'Java', '10.07.2115', NULL, 'http://www.java.com.ua', 'Beresteyska', 'POINT(30.742017 50.458585)',
   'java@gmail.com',
   NULL, NULL, -1),
  (-2, 'PHP', '20.07.2115', NULL, 'http://www.php.com.ua', 'Shulyavska', 'POINT(30.445495 50.454605)', 'php@gmail.com',
   NULL, NULL, -3),
  (-3, 'JS', '30.07.2115', NULL, 'http://www.js.com.ua', 'Nyvky', 'POINT(30.403965 50.458651)', 'js@gmail.com', 20, -3,
   -1),
  (-4, 'CPlusPlus', '15.07.2115', NULL, 'http://www.cplusplus.com.ua', 'Impact Hub', 'POINT(30.747175 46.481782)',
   'cplusplus@gmail.com', 20, -3, -2),
  (-5, 'ObjectiveC', '05.07.2115', NULL, 'http://www.objective-c.com.ua', 'Provectus', 'POINT(30.758417 46.472508)',
   'objectivec@gmail.com', 20, -3, -2),
  (-6, 'CSharp', '09.07.2115', NULL, 'http://www.c#.com.ua', 'Khreschatyk', 'POINT(30.522390 50.447694)',
   'csharp@gmail.com', 20, -3, -3),
  (-7, 'Delphi', '05.07.2115', NULL, 'http://www.delphi.com.ua', 'Arsenalna', 'POINT(30.547603 50.442843)',
   'delphi@gmail.com', 30, -1, -1);

--rollback DELETE FROM event;