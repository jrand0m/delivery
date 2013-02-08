# --- !Ups
CREATE SEQUENCE restaurant_seq
 INCREMENT 1
 START 10;


CREATE TABLE vd_restaurant (
  id              int4 NOT NULL DEFAULT nextval('restaurant_seq') PRIMARY KEY,
  title           varchar(255)  not null,
  deleted         bool not  null default false ,
  showOnIndex   bool not  null default false,
  raiting         integer default 0,
  deviceLogin    varchar(255)  not null,
  devicePassword varchar(100)  not null,
  lastPing       timestamp     ,
  discount        integer not null default 10,
  twoLetters      varchar(2)    not null default '__',
  city_id         int8 not null,
  address_id      int8 not null,
  category_id     int4 not null,
  workhours_id    int4 not null,
  user_id         int8 not null,
  FOREIGN KEY ( city_id ) REFERENCES vd_city (city_id),
  FOREIGN KEY ( address_id ) REFERENCES vd_address (address_id),
  FOREIGN KEY ( category_id ) REFERENCES vd_restaurants_categories (id),
  FOREIGN KEY ( workhours_id ) REFERENCES vd_restaurant_workhours (id),
  FOREIGN KEY ( user_id ) REFERENCES vd_user (id)
)
--WITH (OIDS=FALSE)
;

--//@UNDO
# --- !Downs
DROP TABLE vd_restaurant;

DROP SEQUENCE restaurant_seq;