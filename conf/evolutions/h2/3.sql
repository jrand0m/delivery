--// create Street table
# --- !Ups
CREATE SEQUENCE street_seq
 INCREMENT 1
 START 1;


CREATE TABLE vd_street (
  street_id int4 NOT NULL DEFAULT nextval('street_seq') PRIMARY KEY,
  title_ua varchar(255),
  title_en varchar(255),
  title_ru varchar(255),
  display bool NOT NULL,
  city_id int4 not null,
   FOREIGN KEY ( city_id ) REFERENCES vd_city (city_id)
)
--WITH (OIDS=FALSE)

;


--//@UNDO
-- SQL to undo the change goes here.
# --- !Downs
DROP TABLE vd_street CASCADE ;

DROP SEQUENCE  street_seq CASCADE ;

