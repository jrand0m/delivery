--//
# --- !Ups
insert into "vd_user" (
  "id"          ,
  "vd_login"        ,
  "vd_email"       ,
  "vd_phone_number" ,
  "vd_password"     ,
  "vd_name"         ,
  "vd_user_type"    ,
  "last_login_date" ,
  "created_date"    ,
  "updated_date"    ,
  "deleted"
) values (
 43,
 'mickey123',
 'jays.demons@gmail.com',
 '+380630683088',
 'password31415',
 'Mickey The Mouse',
 'VD_ADMIN',
 now(),
 now(),
 now(),
 false );

insert into "vd_user" (
  "id",
  "vd_login"        ,
  "vd_email"        ,
  "vd_phone_number" ,
  "vd_password"     ,
  "vd_name"         ,
  "vd_user_type"    ,
  "last_login_date" ,
  "created_date"    ,
  "updated_date"    ,
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
  "vd_login"        ,
  "vd_email"        ,
  "vd_phone_number" ,
  "vd_password"     ,
  "vd_name"         ,
  "vd_user_type"    ,
  "last_login_date" ,
  "created_date"    ,
  "updated_date"    ,
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