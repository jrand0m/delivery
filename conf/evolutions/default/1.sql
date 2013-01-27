# --- !Ups
CREATE SEQUENCE "city_seq"
 INCREMENT 1
 START 10;

CREATE TABLE "vd_city" (
  "city_id" int4 NOT NULL default nextval('city_seq') PRIMARY KEY ,
  "cityNameKey" varchar(255) not null,
  "cityAliasName" varchar(255) not null,
  "display" bool NOT NULL
)
--WITH (OIDS=FALSE)
;

--//@UNDO
# --- !Downs
drop table "vd_city" cascade ;

drop SEQUENCE "city_seq" cascade ;



