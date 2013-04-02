# --- !Ups
insert into vd_address (
  address_id,
  city_id,
  street_id,
  additionalInfo,
  apartmentsNumber,
  buildingNumber,
  deleted
) values (
  1,
  1,
  1,
  '--',
  '--',
  '5',
  false
);

insert into vd_restaurants_categories (
   categoryDisplayNameEN,
   categoryDisplayNameRU,
   categoryDisplayNameUA
) values (
   'Phantoms',
   'Фантомы',
   'Фантоми'
);
insert into vd_restaurant_workhours (
  id,
  mon_end,
  mon_start,
  tue_end,
  tue_start,
  wed_end,
  wed_start,
  thu_end,
  thu_start,
  fri_end,
  fri_start,
  sat_end,
  sat_start,
  sun_end,
  sun_start

)
values
(
  1,
    '22:00',
    '08:00',
  '22:00',
  '08:00',
    '22:00',
    '08:00',
  '22:00',
  '08:00',
    '22:00',
    '08:00',
  '22:00',
  '08:00',
    '22:00',
    '08:00'
);
insert into vd_restaurant (
  id,
  title           ,
  deleted         ,
  showOnIndex   ,
  raiting         ,
  deviceLogin    ,
  devicePassword ,
  lastPing       ,
  discount        ,
  twoLetters      ,
  city_id         ,
  address_id      ,
  category_id     ,
  workhours_id    ,
  user_id

) values (
  1,
  'Припять'           ,
  false         ,
  true   ,
  0         ,
  'pr01'    ,
  '3214' ,
  now()       ,
  0        ,
  'PC'      ,
  1, -- city_id         ,
  1, -- address_id      ,
  1, -- category_id     ,
  1, -- workhours_id    ,
  '38bab275-cdea-40f9-a659-275b72572a29'  -- user_id
);



--//@UNDO
-- SQL to undo the change goes here.
# --- !Downs
TRUNCATE TABLE vd_restaurant cascade;
TRUNCATE TABLE vd_address cascade ;
TRUNCATE TABLE vd_restaurants_categories cascade ;
TRUNCATE TABLE vd_restaurant_workhours cascade ;
