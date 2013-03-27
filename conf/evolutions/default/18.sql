# --- !Ups
CREATE SEQUENCE comments_seq
 INCREMENT 1
 START 10;

CREATE TABLE vd_comments (
    id                int8 NOT NULL DEFAULT nextval('comments_seq') PRIMARY KEY,
    commentText      varchar (255) not null,
    commonRating     int4 not null default 0,
    commentedAt      timestamp not null default now(),
    status            varchar(100) not null default 'NOT_REVIEWED',
    showAsAnonymous bool default false,
    order_id          int8 not null,
    FOREIGN KEY ( order_id ) REFERENCES vd_order (id)
);

--//@UNDO
# --- !Downs
DROP TABLE vd_comments;
DROP SEQUENCE comments_seq;