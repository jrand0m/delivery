# --- !Ups
CREATE SEQUENCE menu_items_groups_seq
 INCREMENT 1
 START 10;


CREATE TABLE vd_menu_items_groups (
  id              int4 NOT NULL DEFAULT nextval('menu_items_groups_seq') PRIMARY KEY,
  name            varchar(255) not null,
  description     varchar(255) not null,
  deleted         bool not null,
  restaurant_id   int4 not null,
  FOREIGN KEY ( restaurant_id ) REFERENCES vd_restaurant (id)
);



--//@UNDO
# --- !Downs
DROP TABLE vd_menu_items_groups;
DROP SEQUENCE menu_items_groups_seq;