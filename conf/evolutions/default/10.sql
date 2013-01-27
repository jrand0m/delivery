--//
# --- !Ups
insert into "vd_user" (
  "id"          ,
  "login"        ,
  "email"       ,
  "phoneNumber" ,
  "password"     ,
  "name"         ,
  "userType"    ,
  "lastLoginDate" ,
  "createdDate"    ,
  "updatedDate"    ,
  "deleted"
) values (
 43,
 'mickey123',
 'jays.demons@gmail.com',
 '+380630683088',
 'Dl39PhnIg3Sqs7SUh39pEwFHUwE=', -- sha1 hash on password31415
 'Mickey The Mouse',
 'VD_ADMIN',
 now(),
 now(),
 now(),
 false );

insert into "vd_user" (
  "id",
  "login"        ,
  "email"        ,
  "phoneNumber" ,
  "password"     ,
  "name"         ,
  "userType"    ,
  "lastLoginDate" ,
  "createdDate"    ,
  "updatedDate"    ,
  "deleted"
) values (
 100,
 'cc01',
 'Chornobyl@vdoma.com.ua',
 '+380630683088',
 'cc01',
 'Default Chornobyl Courier',
 'COURIER',
 now(),
 now(),
 now(),
 false );
insert into "vd_user" (
  "id",
  "login"        ,
  "email"        ,
  "phoneNumber" ,
  "password"     ,
  "name"         ,
  "userType"    ,
  "lastLoginDate" ,
  "createdDate"    ,
  "updatedDate"    ,
  "deleted"
) values (
 101,
 'rc01',
 'office@vdoma.com.ua',
 '+380630683088',
 'rc01',
 'Default Chornobyl Restaurant holder',
 'RESTAURANT',
 now(),
 now(),
 now(),
 false );


--//@UNDO
-- SQL to undo the change goes here.
# --- !Downs
delete from vd_user where id = 43 ;
delete from vd_user where id = 100 ;
delete from vd_user where id = 101 ;