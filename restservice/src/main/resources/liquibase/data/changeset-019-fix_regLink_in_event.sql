UPDATE events
SET reg_link = CONCAT('http://', reg_link);
--rollback UPDATE events SET reg_link = replace(reg_link, 'http://', '');
