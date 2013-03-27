--// create addresses table and sequence
# --- !Ups
CREATE SEQUENCE system_settings_seq
 INCREMENT 1
 START 1;


CREATE TABLE vd_system_settings (
  id         int4 NOT NULL DEFAULT nextval('system_settings_seq') PRIMARY KEY,
  _stg_key   varchar(32) not null,
  _stg_value varchar(255) not null,
  isDefault bool NOT NULL Default false,
  startDate date,
  endDate   date
)
--WITH (OIDS=FALSE)
;

--//@UNDO
# --- !Downs
DROP TABLE vd_system_settings;

DROP  SEQUENCE system_settings_seq;



