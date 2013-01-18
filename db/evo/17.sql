# --- !Ups
CREATE SEQUENCE "menu_item_component_seq"
 INCREMENT 1
 START 10;

CREATE TABLE "vd_menu_item_components" (
    "id"                int8 NOT NULL DEFAULT nextval('menu_item_component_seq') PRIMARY KEY,
    "name"              varchar (255) not null,
    "description"       varchar(255) not null,

    "price"             money,
    "price_currency"    varchar(3) not null default 'UAH',

    "deleted"           bool not null default false,

    "requiredIds"      int8 ARRAY,
    "notCompatibleIds" int8 ARRAY,

    "menu_item_id"     int8 not null,

    FOREIGN KEY ( "menu_item_id" ) REFERENCES vd_menu_items ("id")
);

--//@UNDO
# --- !Downs
DROP TABLE vd_menu_item_components;
DROP SEQUENCE menu_item_component_seq;