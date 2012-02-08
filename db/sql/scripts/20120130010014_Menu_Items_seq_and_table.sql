 CREATE SEQUENCE "menu_items_seq"
 INCREMENT 1
 START 1;


CREATE TABLE "vd_menu_items" (
  "id"              int8 NOT NULL DEFAULT nextval('menu_items_seq') PRIMARY KEY,
  "name"            varchar(255) not null,
  "description"     varchar(255) not null,
  "menu_item_created" timestamp not null default now(),
  "available"       bool not null default true,
  "currency"        varchar(3)  not null default 'UAH',
  "price"           money,
  "deleted"         bool not null,
  "restaurant_id"   int4 not null,
  "menu_item_group_id"   int4 not null,
  FOREIGN KEY ( "restaurant_id" ) REFERENCES vd_restaurant ("id"),
  FOREIGN KEY ( "menu_item_group_id" ) REFERENCES vd_menu_items_groups ("id")
);



--//@UNDO

DROP TABLE vd_menu_items;
DROP SEQUENCE menu_items_seq;