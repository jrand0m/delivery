--// create addresses table and sequence

CREATE SEQUENCE "user_seq"
 INCREMENT 1
 START 1001;


CREATE TABLE "vd_user" (
  "id"          int8 NOT NULL DEFAULT nextval('user_seq') PRIMARY KEY,
  "vd_login"        varchar(255)  not null,
  "vd_email"        varchar(255),
  "vd_phone_number" varchar(255)  not null,
  "vd_password"     varchar(255)  not null,
  "vd_name"         varchar(255)  not null,
  "vd_user_type"    varchar(100)  not null,
  "last_login_date" timestamp     not null,
  "created_date"    timestamp     not null,
  "updated_date"    timestamp     not null default localtimestamp,
  "deleted"         bool          not null default false
)
--WITH (OIDS=FALSE)
;

--//@UNDO

DROP TABLE "vd_user";

DROP SEQUENCE "user_seq";