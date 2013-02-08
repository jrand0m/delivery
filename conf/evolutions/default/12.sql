# --- !Ups
CREATE SEQUENCE restaurant_descriptions_seq
 INCREMENT 1
 START 10;


CREATE TABLE vd_restaurant_descriptions (
  id              int4 NOT NULL DEFAULT nextval('restaurant_descriptions_seq') PRIMARY KEY,
  lang            varchar(5) not null default 'en',
  description     varchar(255) not null,
  restaurant_id   int4 not null,
  FOREIGN KEY ( restaurant_id ) REFERENCES vd_restaurant (id)
)
--WITH (OIDS=FALSE)
;

--//@UNDO
# --- !Downs
DROP TABLE vd_restaurant_descriptions;

DROP SEQUENCE restaurant_descriptions_seq;