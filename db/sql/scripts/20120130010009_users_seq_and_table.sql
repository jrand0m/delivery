--// create addresses table and sequence

CREATE SEQUENCE "user_seq"
 INCREMENT 1
 START 1001;


CREATE TABLE "vd_user" (
  "id"          int8 NOT NULL DEFAULT nextval('user_seq') PRIMARY KEY,
  "login"       varchar(255)  not null,
  "email"       varchar(255),
  "phoneNumber" varchar(255)  not null,
  "password"    varchar(255) not null,
  "name"        varchar(255) not null default ' ',
  "userType"    varchar(100)  not null default 'ANONYMOUS',
  "lastLoginDate" timestamp,
  "createdDate"   timestamp  not null default localtimestamp,
  "updatedDate"   timestamp  not null default localtimestamp,
  "deleted"       bool      not null default false
)
--WITH (OIDS=FALSE)
;

--//@UNDO

DROP TABLE "vd_user";

DROP SEQUENCE "user_seq";