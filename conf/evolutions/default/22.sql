# --- !Ups
INSERT INTO vd_restaurant_descriptions (id, lang, description, restaurant_id) VALUES (1, 'ua', 'Test Description', 1);
--//@UNDO
# --- !Downs
delete from vd_restaurant_descriptions where id = 1;
