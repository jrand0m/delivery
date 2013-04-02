# --- !Ups
CREATE SEQUENCE menu_items_seq
 INCREMENT 1
 START 10;


CREATE TABLE vd_menu_items (
  id              int8 NOT NULL DEFAULT nextval('menu_items_seq') PRIMARY KEY,
  name            varchar(255) not null,
  description     varchar(255) not null,
  menuItemCreated timestamp not null default now(),
  available       bool not null default true,
  currency        varchar(3)  not null default 'UAH',
  price           numeric(16,0),
  deleted         bool not null,
  restaurant_id   int4 not null,
  menu_item_group_id   int4 not null,
  FOREIGN KEY ( restaurant_id ) REFERENCES vd_restaurant (id),
  FOREIGN KEY ( menu_item_group_id ) REFERENCES vd_menu_items_groups (id)
);



--//@UNDO
# --- !Downs
DROP TABLE vd_menu_items;
DROP SEQUENCE menu_items_seq;