--// create addresses table and sequence

CREATE SEQUENCE "address_seq"
 INCREMENT 1
 START 1;


CREATE TABLE "vd_address" (
  "address_id" int8 NOT NULL DEFAULT nextval('address_seq') PRIMARY KEY,
  "building_number" varchar(30),
  "apartments_number" varchar(20),
  "title_ru" varchar(255),
  "display" bool NOT NULL,
  "city_id" int4 not null,
   FOREIGN KEY ( "city_id" ) REFERENCES vd_city ("city_id")
)
--WITH (OIDS=FALSE)

;



--//@UNDO




