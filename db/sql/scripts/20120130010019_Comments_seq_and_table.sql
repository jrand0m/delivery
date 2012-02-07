CREATE SEQUENCE "comments_seq"
 INCREMENT 1
 START 1;

CREATE TABLE "vd_comments" (
    "id"                int8 NOT NULL DEFAULT nextval('comments_seq') PRIMARY KEY,
    "name"              varchar (255) not null,


    "menu_item_id"     int8 not null,

    FOREIGN KEY ( "menu_item_id" ) REFERENCES vd_menu_items ("id"),
);

--//@UNDO

DROP TABLE vd_comments;
DROP SEQUENCE comments_seq;