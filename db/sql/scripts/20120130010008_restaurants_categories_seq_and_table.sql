--// create addresses table and sequence

CREATE SEQUENCE "restaurants_categories_seq"
 INCREMENT 1
 START 1;


CREATE TABLE "vd_restaurants_categories" (
  "id"         int4 NOT NULL DEFAULT nextval('restaurants_categories_seq') PRIMARY KEY,
  "category_display_name_en" varchar(255) not null,
  "category_display_name_ru" varchar(255),
  "category_display_name_ua" varchar(255) not null

)
--WITH (OIDS=FALSE)
;

--//@UNDO

DROP TABLE "vd_restaurants_categories";

DROP  SEQUENCE "restaurants_categories_seq";