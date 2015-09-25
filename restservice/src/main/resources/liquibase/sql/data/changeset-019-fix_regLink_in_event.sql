UPDATE event
SET reg_link = CONCAT('http://', reg_link);
--rollback UPDATE event SET reg_link = replace(reg_link, 'http://', '');
