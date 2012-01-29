--// create city table and sequence
CREATE SEQUENCE "city_seq"
 INCREMENT 1
 START 1;

CREATE TABLE "vd_city" (
  "city_id" int4 NOT NULL default nextval('city_seq') PRIMARY KEY ,
  "city_name_key" varchar(255) not null,
  "city_alias_name" varchar(255) not null,
  "display" bool NOT NULL
)
--WITH (OIDS=FALSE)
;

--//@UNDO

drop table "vd_city" cascade ;

drop SEQUENCE "city_seq" cascade ;



