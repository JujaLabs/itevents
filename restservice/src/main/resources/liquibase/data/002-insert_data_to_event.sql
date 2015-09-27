--liquibase formatted sql

INSERT INTO event (title, event_date, create_date, reg_link, address, point, contact) VALUES
  ('Java', '10.07.2015', NULL, 'www.java.com.ua', 'Beresteyska', 'POINT(30.742017 50.458585)', 'java@gmail.com'),
  ('PHP', '20.07.2015', NULL, 'www.php.com.ua', 'Shulyavska', 'POINT(30.445495 50.454605)', 'php@gmail.com'),
  ('JS', '30.07.2015', NULL, 'www.js.com.ua', 'Nyvky', 'POINT(30.403965 50.458651)', 'js@gmail.com'),
  ('CPlusPlus', '15.07.2015', NULL, 'www.cplusplus.com.ua', 'Vokzalna', 'POINT(30.489324 50.441353)',
   'cplusplus@gmail.com'),
  ('ObjectiveC', '05.07.2015', NULL, 'www.objective-c.com.ua', 'Universytet', 'POINT(30.506104 50.444387)',
   'objectivec@gmail.com'),
  ('CSharp', '09.07.2015', NULL, 'www.c#.com.ua', 'Khreschatyk', 'POINT(30.522390 50.447694)', 'csharp@gmail.com'),
  ('Delphi', '05.07.2015', NULL, 'www.delphi.com.ua', 'Arsenalna', 'POINT(30.547603 50.442843)', 'delphi@gmail.com');

--rollback DELETE * FROM event;