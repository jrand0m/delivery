--// create Chernobyl street
# --- !Ups

insert into vd_street (street_id, title_ua,title_en,title_ru,display,city_id) values (1,'пр. Науки', 'Science av.', 'пр. Науки', false, 1);


--//@UNDO
# --- !Downs

delete from vd_street where street_id = 1;

