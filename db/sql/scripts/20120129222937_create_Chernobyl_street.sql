--// create Chernobyl street
-- Migration SQL that makes the change goes here.

insert into vd_street (title_ua,title_en,title_ru,display,city_id) values ('пр. Науки', 'Science av.', 'пр. Науки', false, 1);


--//@UNDO
-- SQL to undo the change goes here.

delete from vd_street where title_en = 'Science av.' and city_id=1;

