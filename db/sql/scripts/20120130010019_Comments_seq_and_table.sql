CREATE SEQUENCE "comments_seq"
 INCREMENT 1
 START 1;

CREATE TABLE "vd_comments" (
    "id"                int8 NOT NULL DEFAULT nextval('comments_seq') PRIMARY KEY,
    "comment_text"      varchar (255) not null,
    "common_rating"     int4 not null default 0,
    "commented_at"      timestamp not null default now(),
    "status"            varchar(100) not null default 'NOT_REVIEWED',
    "show_as_anonymous" bool default false,
    "order_id"          int8 not null,
    FOREIGN KEY ( "order_id" ) REFERENCES vd_order ("id")
);

--//@UNDO

DROP TABLE vd_comments;
DROP SEQUENCE comments_seq;