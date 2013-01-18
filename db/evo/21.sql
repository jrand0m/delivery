# --- !Ups

CREATE TABLE "vd_attachments" (
    "id"             uuid NOT NULL  PRIMARY KEY,
    "commentText"    varchar (255) not null,
    "createdAt"      timestamp not null default now(),
    "fileType"       varchar(100) not null default 'UNKNOWN',
    "fileExt"        varchar(10) not null default ''
);
ALTER TABLE vd_restaurant ADD COLUMN logo_id uuid;
ALTER TABLE vd_restaurant ADD foreign key ("logo_id") references vd_attachments ("id");
--//@UNDO
# --- !Downs
ALTER TABLE vd_restaurant drop constraint "vd_restaurant_logo_id_fkey";
ALTER TABLE vd_restaurant drop COLUMN logo_id;
DROP TABLE vd_attachments;
