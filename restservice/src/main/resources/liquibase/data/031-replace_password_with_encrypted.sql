--liquibase formatted sql

--changeset vaa25:31

UPDATE user_profile
SET password = CASE
               WHEN login = 'vlasov@email.com'
                 THEN '$2a$10$uB.nFUPkpIIoY1HpYmsM5.YHNiGFEYMDJbaK1Swt6KkknCWPjtGkm'
               WHEN login = 'kuchin@email.com'
                 THEN '$2a$10$aPyCWJ8WsJb0gTlz.IL/u.7kB7WiyZr67PUDoEO7x5D40OFOz1rWq'
               WHEN login = 'anakin@email.com'
                 THEN '$2a$10$XHrRyJdlnIWe3EHbWAO6teR1LYjif1r4J4t5OvwfnLZy7pnmlANlq'
               END
WHERE id != -1;

--rollback UPDATE user_profile SET password = CASE WHEN login = 'vlasov@email.com' THEN 'alex' WHEN login = 'kuchin@email.com' THEN 'viktor' WHEN login = 'anakin@email.com' THEN 'alex' END WHERE id != -1;