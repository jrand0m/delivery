--// create addresses table and sequence
# --- !Ups
CREATE SEQUENCE "address_seq"
 INCREMENT 1
 START 10;

CREATE TABLE "vd_address" (
  "address_id" int8 NOT NULL DEFAULT nextval('address_seq') PRIMARY KEY,
  "buildingNumber" varchar(30),
  "apartmentsNumber" varchar(20),
  "additionalInfo" varchar(255),
  "deleted" bool NOT NULL Default false,
  "city_id" int4 not null,
  "street_id" int4 not null,
   FOREIGN KEY ( "city_id" ) REFERENCES vd_city ("city_id"),
   FOREIGN KEY ( "street_id" ) REFERENCES vd_street ("street_id")
)
--WITH (OIDS=FALSE)
;

--//@UNDO
# --- !Downs
DROP TABLE "vd_address";

DROP  SEQUENCE "address_seq";



