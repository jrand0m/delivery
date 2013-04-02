--// create addresses table and sequence
# --- !Ups

CREATE TABLE vd_user (
  id          varchar(38) NOT NULL PRIMARY KEY,
  login       varchar(255)  not null,
  email       varchar(255),
  phoneNumber varchar(255)  not null,
  password    varchar(255) not null,
  name        varchar(255) not null default ' ',
  userType    varchar(100)  not null default 'ANONYMOUS',
  lastLoginDate timestamp,
  createdDate   timestamp  not null default localtimestamp,
  updatedDate   timestamp  not null default localtimestamp,
  deleted       bool      not null default false
)
--WITH (OIDS=FALSE)
;

--//@UNDO
# --- !Downs
DROP TABLE vd_user;
