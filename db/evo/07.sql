--// create addresses table and sequence
# --- !Ups
CREATE SEQUENCE "workhours_seq"
 INCREMENT 1
 START 1;


CREATE TABLE "vd_restaurant_workhours" (
  "id"         int4 NOT NULL DEFAULT nextval('workhours_seq') PRIMARY KEY,

  "mon_start"  time  not null,
  "mon_end"    time  not null,
  "tue_start"  time  not null,
  "tue_end"    time  not null,
  "wed_start"  time  not null,
  "wed_end"    time  not null,
  "thu_start"  time  not null,
  "thu_end"    time  not null,
  "fri_start"  time  not null,
  "fri_end"    time  not null,
  "sat_start"  time  not null,
  "sat_end"    time  not null,
  "sun_start"  time  not null,
  "sun_end"    time  not null
)
--WITH (OIDS=FALSE)
;

--//@UNDO
# --- !Downs
DROP TABLE "vd_restaurant_workhours";

DROP  SEQUENCE "workhours_seq";



