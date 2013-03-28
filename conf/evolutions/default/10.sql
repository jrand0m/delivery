--//
# --- !Ups
insert into vd_user (
  id          ,
  login        ,
  email       ,
  phoneNumber ,
  password     ,
  name         ,
  userType    ,
  lastLoginDate ,
  createdDate    ,
  updatedDate    ,
  deleted
) values (
 '{92a9c641-e108-4b37-9d05-363893b68281}',
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

insert into vd_user (
  id,
  login        ,
  email        ,
  phoneNumber ,
  password     ,
  name         ,
  userType    ,
  lastLoginDate ,
  createdDate    ,
  updatedDate    ,
  deleted
) values (
 '{15ca4044-fe79-4e70-ab20-06b8c9508c7c}',
 'cc01',
 'Chornobyl@vdoma.com.ua',
 '+380630683087',
 'cc01',
 'Default Chornobyl Courier',
 'COURIER',
 now(),
 now(),
 now(),
 false );
insert into vd_user (
  id,
  login        ,
  email        ,
  phoneNumber ,
  password     ,
  name         ,
  userType    ,
  lastLoginDate ,
  createdDate    ,
  updatedDate    ,
  deleted
) values (
 '{38bab275-cdea-40f9-a659-275b72572a29}',
 'rc01',
 'office@vdoma.com.ua',
 '+380630683086',
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
delete from vd_user where id = '{92a9c641-e108-4b37-9d05-363893b68281}' ;
delete from vd_user where id = '{15ca4044-fe79-4e70-ab20-06b8c9508c7c}' ;
delete from vd_user where id = '{38bab275-cdea-40f9-a659-275b72572a29}' ;