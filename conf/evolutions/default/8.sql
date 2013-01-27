--// create addresses table and sequence
# --- !Ups
CREATE SEQUENCE "restaurants_categories_seq"
 INCREMENT 1
 START 1;


CREATE TABLE "vd_restaurants_categories" (
  "id"         int4 NOT NULL DEFAULT nextval('restaurants_categories_seq') PRIMARY KEY,
  "categoryDisplayNameEN" varchar(255) not null,
  "categoryDisplayNameRU" varchar(255),
  "categoryDisplayNameUA" varchar(255) not null

)
--WITH (OIDS=FALSE)
;

--//@UNDO
# --- !Downs
DROP TABLE "vd_restaurants_categories";

DROP  SEQUENCE "restaurants_categories_seq";