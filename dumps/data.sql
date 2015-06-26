--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- Name: address_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('address_seq', 29, true);


--
-- Data for Name: auth_group; Type: TABLE DATA; Schema: public; Owner: anderson
--



--
-- Name: auth_group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('auth_group_id_seq', 1, false);


--
-- Data for Name: django_content_type; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO django_content_type VALUES (1, 'admin', 'logentry');
INSERT INTO django_content_type VALUES (2, 'auth', 'permission');
INSERT INTO django_content_type VALUES (3, 'auth', 'group');
INSERT INTO django_content_type VALUES (4, 'auth', 'user');
INSERT INTO django_content_type VALUES (5, 'contenttypes', 'contenttype');
INSERT INTO django_content_type VALUES (6, 'sessions', 'session');
INSERT INTO django_content_type VALUES (7, 'sites', 'site');


--
-- Data for Name: auth_permission; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO auth_permission VALUES (1, 'Can add log entry', 1, 'add_logentry');
INSERT INTO auth_permission VALUES (2, 'Can change log entry', 1, 'change_logentry');
INSERT INTO auth_permission VALUES (3, 'Can delete log entry', 1, 'delete_logentry');
INSERT INTO auth_permission VALUES (4, 'Can add permission', 2, 'add_permission');
INSERT INTO auth_permission VALUES (5, 'Can change permission', 2, 'change_permission');
INSERT INTO auth_permission VALUES (6, 'Can delete permission', 2, 'delete_permission');
INSERT INTO auth_permission VALUES (7, 'Can add group', 3, 'add_group');
INSERT INTO auth_permission VALUES (8, 'Can change group', 3, 'change_group');
INSERT INTO auth_permission VALUES (9, 'Can delete group', 3, 'delete_group');
INSERT INTO auth_permission VALUES (10, 'Can add user', 4, 'add_user');
INSERT INTO auth_permission VALUES (11, 'Can change user', 4, 'change_user');
INSERT INTO auth_permission VALUES (12, 'Can delete user', 4, 'delete_user');
INSERT INTO auth_permission VALUES (13, 'Can add content type', 5, 'add_contenttype');
INSERT INTO auth_permission VALUES (14, 'Can change content type', 5, 'change_contenttype');
INSERT INTO auth_permission VALUES (15, 'Can delete content type', 5, 'delete_contenttype');
INSERT INTO auth_permission VALUES (16, 'Can add session', 6, 'add_session');
INSERT INTO auth_permission VALUES (17, 'Can change session', 6, 'change_session');
INSERT INTO auth_permission VALUES (18, 'Can delete session', 6, 'delete_session');
INSERT INTO auth_permission VALUES (19, 'Can add site', 7, 'add_site');
INSERT INTO auth_permission VALUES (20, 'Can change site', 7, 'change_site');
INSERT INTO auth_permission VALUES (21, 'Can delete site', 7, 'delete_site');


--
-- Data for Name: auth_group_permissions; Type: TABLE DATA; Schema: public; Owner: anderson
--



--
-- Name: auth_group_permissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('auth_group_permissions_id_seq', 1, false);


--
-- Name: auth_permission_id_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('auth_permission_id_seq', 21, true);


--
-- Data for Name: auth_user; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO auth_user VALUES (1, 'pbkdf2_sha256$20000$Bi8oJArD82FB$brRIwUKPTM0rvyV0O6nowVNsFNF6kk8hGgGdEZqP8Ss=', '2015-06-26 18:07:57.803389+03', true, 'anderson', '', '', 'andrew@dun.ai', true, true, '2015-06-26 17:51:24.186699+03');


--
-- Data for Name: auth_user_groups; Type: TABLE DATA; Schema: public; Owner: anderson
--



--
-- Name: auth_user_groups_id_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('auth_user_groups_id_seq', 1, false);


--
-- Name: auth_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('auth_user_id_seq', 1, true);


--
-- Data for Name: auth_user_user_permissions; Type: TABLE DATA; Schema: public; Owner: anderson
--



--
-- Name: auth_user_user_permissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('auth_user_user_permissions_id_seq', 1, false);


--
-- Name: city_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('city_seq', 10, false);


--
-- Name: comments_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('comments_seq', 10, false);


--
-- Data for Name: django_admin_log; Type: TABLE DATA; Schema: public; Owner: anderson
--



--
-- Name: django_admin_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('django_admin_log_id_seq', 1, false);


--
-- Name: django_content_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('django_content_type_id_seq', 7, true);


--
-- Data for Name: django_migrations; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO django_migrations VALUES (1, 'contenttypes', '0001_initial', '2015-06-26 17:49:05.310313+03');
INSERT INTO django_migrations VALUES (2, 'auth', '0001_initial', '2015-06-26 17:49:05.405096+03');
INSERT INTO django_migrations VALUES (3, 'admin', '0001_initial', '2015-06-26 17:49:05.442139+03');
INSERT INTO django_migrations VALUES (4, 'contenttypes', '0002_remove_content_type_name', '2015-06-26 17:49:05.49418+03');
INSERT INTO django_migrations VALUES (5, 'auth', '0002_alter_permission_name_max_length', '2015-06-26 17:49:05.509989+03');
INSERT INTO django_migrations VALUES (6, 'auth', '0003_alter_user_email_max_length', '2015-06-26 17:49:05.541151+03');
INSERT INTO django_migrations VALUES (7, 'auth', '0004_alter_user_username_opts', '2015-06-26 17:49:05.562421+03');
INSERT INTO django_migrations VALUES (8, 'auth', '0005_alter_user_last_login_null', '2015-06-26 17:49:05.582471+03');
INSERT INTO django_migrations VALUES (9, 'auth', '0006_require_contenttypes_0002', '2015-06-26 17:49:05.584979+03');
INSERT INTO django_migrations VALUES (10, 'sessions', '0001_initial', '2015-06-26 17:49:05.60305+03');
INSERT INTO django_migrations VALUES (11, 'sites', '0001_initial', '2015-06-26 17:52:01.545998+03');


--
-- Name: django_migrations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('django_migrations_id_seq', 11, true);


--
-- Data for Name: django_session; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO django_session VALUES ('e92xfruwnbvbncvkzowwgg7kwcdz54sg', 'NzA4N2QwYTE4MjE5MjYxZTcwOTcwNzNjZTIzMWQ1NDhlZGEzZTRmZDp7Il9hdXRoX3VzZXJfaGFzaCI6ImE4NDY0MDg4NWJlYjY2Y2M4OWRiN2JhNWVmMWJjOGVkNjIyZWE0MmEiLCJfYXV0aF91c2VyX2JhY2tlbmQiOiJkamFuZ28uY29udHJpYi5hdXRoLmJhY2tlbmRzLk1vZGVsQmFja2VuZCIsIl9hdXRoX3VzZXJfaWQiOiIxIn0=', '2015-07-10 17:51:27.080818+03');
INSERT INTO django_session VALUES ('7rgt26s73e409qr1rye5ith4xvupbz8g', 'NzA4N2QwYTE4MjE5MjYxZTcwOTcwNzNjZTIzMWQ1NDhlZGEzZTRmZDp7Il9hdXRoX3VzZXJfaGFzaCI6ImE4NDY0MDg4NWJlYjY2Y2M4OWRiN2JhNWVmMWJjOGVkNjIyZWE0MmEiLCJfYXV0aF91c2VyX2JhY2tlbmQiOiJkamFuZ28uY29udHJpYi5hdXRoLmJhY2tlbmRzLk1vZGVsQmFja2VuZCIsIl9hdXRoX3VzZXJfaWQiOiIxIn0=', '2015-07-10 18:03:56.82407+03');
INSERT INTO django_session VALUES ('fmpg2qoturdt1pxqnp3vypsaio6d5bh3', 'NzA4N2QwYTE4MjE5MjYxZTcwOTcwNzNjZTIzMWQ1NDhlZGEzZTRmZDp7Il9hdXRoX3VzZXJfaGFzaCI6ImE4NDY0MDg4NWJlYjY2Y2M4OWRiN2JhNWVmMWJjOGVkNjIyZWE0MmEiLCJfYXV0aF91c2VyX2JhY2tlbmQiOiJkamFuZ28uY29udHJpYi5hdXRoLmJhY2tlbmRzLk1vZGVsQmFja2VuZCIsIl9hdXRoX3VzZXJfaWQiOiIxIn0=', '2015-07-10 18:07:57.805687+03');


--
-- Data for Name: django_site; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO django_site VALUES (1, 'example.com', 'example.com');


--
-- Name: django_site_id_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('django_site_id_seq', 1, true);


--
-- Name: menu_item_component_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('menu_item_component_seq', 10, false);


--
-- Name: menu_items_groups_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('menu_items_groups_seq', 10, false);


--
-- Name: menu_items_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('menu_items_seq', 10, false);


--
-- Name: order_items_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('order_items_seq', 10, false);


--
-- Name: orders_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('orders_seq', 29, true);


--
-- Data for Name: play_evolutions; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO play_evolutions VALUES (1, 'da5488ea3e020da58a32441ac43bd6f2be56ef16', '2015-06-26 00:00:00', 'CREATE SEQUENCE city_seq
INCREMENT 1
START 10;

CREATE TABLE vd_city (
city_id int4 NOT NULL default nextval(''city_seq'') PRIMARY KEY ,
cityNameKey varchar(255) not null,
cityAliasName varchar(255) not null,
display bool NOT NULL
)
--WITH (OIDS=FALSE)
;

--//@UNDO', 'drop table vd_city cascade ;

drop SEQUENCE city_seq cascade ;', 'applied', '');
INSERT INTO play_evolutions VALUES (2, 'fb5e9c9f3df02f08f5a9f8d7d56c6131d993516b', '2015-06-26 00:00:00', 'insert into vd_city (city_id,cityNameKey, cityAliasName, display) values (1,''city.names.Chernobyl'', ''Chernobyl'',false);


--//@UNDO', 'delete from vd_city where city_id = 1 ;', 'applied', '');
INSERT INTO play_evolutions VALUES (3, 'c76a5cb85d2a548d72274cea1d8dc2fef48b0240', '2015-06-26 00:00:00', 'CREATE SEQUENCE street_seq
INCREMENT 1
START 1;


CREATE TABLE vd_street (
street_id int4 NOT NULL DEFAULT nextval(''street_seq'') PRIMARY KEY,
title_ua varchar(255),
title_en varchar(255),
title_ru varchar(255),
display bool NOT NULL,
city_id int4 not null,
FOREIGN KEY ( city_id ) REFERENCES vd_city (city_id)
)
--WITH (OIDS=FALSE)

;


--//@UNDO
-- SQL to undo the change goes here.', 'DROP TABLE vd_street CASCADE ;

DROP SEQUENCE  street_seq CASCADE ;', 'applied', '');
INSERT INTO play_evolutions VALUES (4, '18766dbbe84080b12a6643de6679a0f44cca29c0', '2015-06-26 00:00:00', 'insert into vd_street (street_id, title_ua,title_en,title_ru,display,city_id) values (1,''пр. Науки'', ''Science av.'', ''пр. Науки'', false, 1);


--//@UNDO', 'delete from vd_street where street_id = 1;', 'applied', '');
INSERT INTO play_evolutions VALUES (5, '6402492c28c70af3acde2c27d877e49618f6ee72', '2015-06-26 00:00:00', 'CREATE SEQUENCE address_seq
INCREMENT 1
START 10;

CREATE TABLE vd_address (
address_id int8 NOT NULL DEFAULT nextval(''address_seq'') PRIMARY KEY,
buildingNumber varchar(30),
apartmentsNumber varchar(20),
additionalInfo varchar(255),
deleted bool NOT NULL Default false,
city_id int4 not null,
street_id int4 not null,
FOREIGN KEY ( city_id ) REFERENCES vd_city (city_id),
FOREIGN KEY ( street_id ) REFERENCES vd_street (street_id)
)
--WITH (OIDS=FALSE)
;

--//@UNDO', 'DROP TABLE vd_address;

DROP  SEQUENCE address_seq;', 'applied', '');
INSERT INTO play_evolutions VALUES (6, '9d431f96ac3ce7b53b8f91febd588ac7b65f501e', '2015-06-26 00:00:00', 'CREATE SEQUENCE system_settings_seq
INCREMENT 1
START 1;


CREATE TABLE vd_system_settings (
id         int4 NOT NULL DEFAULT nextval(''system_settings_seq'') PRIMARY KEY,
_stg_key   varchar(32) not null,
_stg_value varchar(255) not null,
isDefault bool NOT NULL Default false,
startDate date,
endDate   date
)
--WITH (OIDS=FALSE)
;

--//@UNDO', 'DROP TABLE vd_system_settings;

DROP  SEQUENCE system_settings_seq;', 'applied', '');
INSERT INTO play_evolutions VALUES (7, '46481dae7a2c8c0eddb3fc441373a0eebaf43caa', '2015-06-26 00:00:00', 'CREATE SEQUENCE workhours_seq
INCREMENT 1
START 1;


CREATE TABLE vd_restaurant_workhours (
id         int4 NOT NULL DEFAULT nextval(''workhours_seq'') PRIMARY KEY,

mon_start  time  not null,
mon_end    time  not null,
tue_start  time  not null,
tue_end    time  not null,
wed_start  time  not null,
wed_end    time  not null,
thu_start  time  not null,
thu_end    time  not null,
fri_start  time  not null,
fri_end    time  not null,
sat_start  time  not null,
sat_end    time  not null,
sun_start  time  not null,
sun_end    time  not null
)
--WITH (OIDS=FALSE)
;

--//@UNDO', 'DROP TABLE vd_restaurant_workhours;

DROP  SEQUENCE workhours_seq;', 'applied', '');
INSERT INTO play_evolutions VALUES (8, '5cbf93b49757f5cf843392cb5621f87e07ac7454', '2015-06-26 00:00:00', 'CREATE SEQUENCE restaurants_categories_seq
INCREMENT 1
START 1;


CREATE TABLE vd_restaurants_categories (
id         int4 NOT NULL DEFAULT nextval(''restaurants_categories_seq'') PRIMARY KEY,
categoryDisplayNameEN varchar(255) not null,
categoryDisplayNameRU varchar(255),
categoryDisplayNameUA varchar(255) not null

)
--WITH (OIDS=FALSE)
;

--//@UNDO', 'DROP TABLE vd_restaurants_categories;

DROP  SEQUENCE restaurants_categories_seq;', 'applied', '');
INSERT INTO play_evolutions VALUES (9, '2fdf030055e4593ede670d4b1d75faf915100e87', '2015-06-26 00:00:00', 'CREATE TABLE vd_user (
id          varchar(38) NOT NULL PRIMARY KEY,
login       varchar(255)  not null,
email       varchar(255),
phoneNumber varchar(255)  not null,
password    varchar(255) not null,
name        varchar(255) not null default '' '',
userType    varchar(100)  not null default ''ANONYMOUS'',
lastLoginDate timestamp,
createdDate   timestamp  not null default localtimestamp,
updatedDate   timestamp  not null default localtimestamp,
deleted       bool      not null default false
)
--WITH (OIDS=FALSE)
;

--//@UNDO', 'DROP TABLE vd_user;', 'applied', '');
INSERT INTO play_evolutions VALUES (10, '99476edbcd646a7714efdfe2596bd6ce49e43cc8', '2015-06-26 00:00:00', 'insert into vd_user (
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
''92a9c641-e108-4b37-9d05-363893b68281'',
''mickey123'',
''jays.demons@gmail.com'',
''+380630683088'',
''Dl39PhnIg3Sqs7SUh39pEwFHUwE='', -- sha1 hash on password31415
''Mickey The Mouse'',
''VD_ADMIN'',
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
''15ca4044-fe79-4e70-ab20-06b8c9508c7c'',
''cc01'',
''Chornobyl@vdoma.com.ua'',
''+380630683087'',
''cc01'',
''Default Chornobyl Courier'',
''COURIER'',
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
''38bab275-cdea-40f9-a659-275b72572a29'',
''rc01'',
''office@vdoma.com.ua'',
''+380630683086'',
''rc01'',
''Default Chornobyl Restaurant holder'',
''RESTAURANT'',
now(),
now(),
now(),
false );


--//@UNDO
-- SQL to undo the change goes here.', 'delete from vd_user where id = ''92a9c641-e108-4b37-9d05-363893b68281'' ;
delete from vd_user where id = ''15ca4044-fe79-4e70-ab20-06b8c9508c7c'' ;
delete from vd_user where id = ''38bab275-cdea-40f9-a659-275b72572a29'' ;', 'applied', '');
INSERT INTO play_evolutions VALUES (11, '8090e67cb05cbf658566d0a4a80c716cae7b14a5', '2015-06-26 00:00:00', 'CREATE SEQUENCE restaurant_seq
INCREMENT 1
START 10;


CREATE TABLE vd_restaurant (
id              int4 NOT NULL DEFAULT nextval(''restaurant_seq'') PRIMARY KEY,
title           varchar(255)  not null,
deleted         bool not  null default false ,
showOnIndex   bool not  null default false,
raiting         integer default 0,
deviceLogin    varchar(255)  not null,
devicePassword varchar(100)  not null,
lastPing       timestamp     ,
discount        integer not null default 10,
twoLetters      varchar(2)    not null default ''__'',
city_id         int8 not null,
address_id      int8 not null,
category_id     int4 not null,
workhours_id    int4 not null,
user_id         varchar(38) not null,
FOREIGN KEY ( city_id ) REFERENCES vd_city (city_id),
FOREIGN KEY ( address_id ) REFERENCES vd_address (address_id),
FOREIGN KEY ( category_id ) REFERENCES vd_restaurants_categories (id),
FOREIGN KEY ( workhours_id ) REFERENCES vd_restaurant_workhours (id),
FOREIGN KEY ( user_id ) REFERENCES vd_user (id)
)
--WITH (OIDS=FALSE)
;

--//@UNDO', 'DROP TABLE vd_restaurant;

DROP SEQUENCE restaurant_seq;', 'applied', '');
INSERT INTO play_evolutions VALUES (12, '2b3aaf062fb93dd511b1a48c69f47c512a20400e', '2015-06-26 00:00:00', 'CREATE SEQUENCE restaurant_descriptions_seq
INCREMENT 1
START 10;


CREATE TABLE vd_restaurant_descriptions (
id              int4 NOT NULL DEFAULT nextval(''restaurant_descriptions_seq'') PRIMARY KEY,
lang            varchar(5) not null default ''en'',
description     varchar(255) not null,
restaurant_id   int4 not null,
FOREIGN KEY ( restaurant_id ) REFERENCES vd_restaurant (id)
)
--WITH (OIDS=FALSE)
;

--//@UNDO', 'DROP TABLE vd_restaurant_descriptions;

DROP SEQUENCE restaurant_descriptions_seq;', 'applied', '');
INSERT INTO play_evolutions VALUES (13, '51ac9fdcc084d6fe1cc7e27955d2bde26eab55c8', '2015-06-26 00:00:00', 'CREATE SEQUENCE menu_items_groups_seq
INCREMENT 1
START 10;


CREATE TABLE vd_menu_items_groups (
id              int4 NOT NULL DEFAULT nextval(''menu_items_groups_seq'') PRIMARY KEY,
name            varchar(255) not null,
description     varchar(255) not null,
deleted         bool not null,
restaurant_id   int4 not null,
FOREIGN KEY ( restaurant_id ) REFERENCES vd_restaurant (id)
);



--//@UNDO', 'DROP TABLE vd_menu_items_groups;
DROP SEQUENCE menu_items_groups_seq;', 'applied', '');
INSERT INTO play_evolutions VALUES (14, 'd6e2313259b5230e756f72a51c3f790e933cc2ad', '2015-06-26 00:00:00', 'CREATE SEQUENCE menu_items_seq
INCREMENT 1
START 10;


CREATE TABLE vd_menu_items (
id              int8 NOT NULL DEFAULT nextval(''menu_items_seq'') PRIMARY KEY,
name            varchar(255) not null,
description     varchar(255) not null,
menuItemCreated timestamp not null default now(),
available       bool not null default true,
currency        varchar(3)  not null default ''UAH'',
price           numeric(16,0),
deleted         bool not null,
restaurant_id   int4 not null,
menu_item_group_id   int4 not null,
FOREIGN KEY ( restaurant_id ) REFERENCES vd_restaurant (id),
FOREIGN KEY ( menu_item_group_id ) REFERENCES vd_menu_items_groups (id)
);



--//@UNDO', 'DROP TABLE vd_menu_items;
DROP SEQUENCE menu_items_seq;', 'applied', '');
INSERT INTO play_evolutions VALUES (15, '558772ef5d774424d7e8f2a94033984213fc516f', '2015-06-26 00:00:00', 'CREATE SEQUENCE orders_seq
INCREMENT 1
START 10;

CREATE TABLE vd_order (
id               int8 NOT NULL DEFAULT nextval(''orders_seq'') PRIMARY KEY,
declineMessage  varchar (255),
deleted          bool not null,
deliveryPrice_currency varchar(3)  not null default ''UAH'',
deliveryPrice   numeric(16,0),
totalMenuPrice_currency varchar(3)  not null default ''UAH'',
totalMenuPrice numeric(16,0),
orderAccepted   timestamp,
orderClosed     timestamp,
orderCooked     timestamp,
orderConfirmed  timestamp,
orderCreated    timestamp,
orderDelivered  timestamp,
orderTaken      timestamp,
updatedAt       timestamp not null default now(),
orderPlanedCooked interval,
orderPlanedDeliveryTime interval,
orderStatus    varchar(100) not null default ''OPEN'',
paymentStatus  varchar(100) not null default ''NOT_PAID'',

delivery_address_id int8,
confirmed_courier_id varchar(38),
order_owner_id  varchar(38) not null,
restaurant_id   int4 not null,

FOREIGN KEY ( delivery_address_id ) REFERENCES vd_address (address_id),
FOREIGN KEY ( order_owner_id ) REFERENCES vd_user (id),
FOREIGN KEY ( restaurant_id ) REFERENCES vd_restaurant (id),
FOREIGN KEY ( confirmed_courier_id ) REFERENCES vd_user (id)
);

--//@UNDO', 'DROP TABLE vd_order cascade;
DROP SEQUENCE orders_seq;', 'applied', '');
INSERT INTO play_evolutions VALUES (16, 'ebde087784c21b9698f5970753b1feb1e4e0c4ca', '2015-06-26 00:00:00', 'CREATE SEQUENCE order_items_seq
INCREMENT 1
START 10;

CREATE TABLE vd_order_items (
id               int8 NOT NULL DEFAULT nextval(''order_items_seq'') PRIMARY KEY,
count            int4 not null default 1,
deleted          bool not null default false,
totalOrderItemPrice numeric(16,0),
totalOrderItemPrice_currency varchar(3) not null default ''UAH'',
menu_item_id     int8 not null,
order_id         int8 not null,

FOREIGN KEY ( menu_item_id ) REFERENCES vd_menu_items (id),
FOREIGN KEY ( order_id ) REFERENCES vd_order (id)
);

--//@UNDO', 'DROP TABLE vd_order_items;
DROP SEQUENCE order_items_seq;', 'applied', '');
INSERT INTO play_evolutions VALUES (17, '84a8d267ad19c1514dd4e9d8c1fd60e16c774d2e', '2015-06-26 00:00:00', 'CREATE SEQUENCE menu_item_component_seq
INCREMENT 1
START 10;

CREATE TABLE vd_menu_item_components (
id                int8 NOT NULL DEFAULT nextval(''menu_item_component_seq'') PRIMARY KEY,
name              varchar (255) not null,
description       varchar(255) not null,

price             numeric(16,0),
price_currency    varchar(3) not null default ''UAH'',

deleted           bool not null default false,

requiredIds      int8 ARRAY,
notCompatibleIds int8 ARRAY,

menu_item_id     int8 not null,

FOREIGN KEY ( menu_item_id ) REFERENCES vd_menu_items (id)
);

--//@UNDO', 'DROP TABLE vd_menu_item_components;
DROP SEQUENCE menu_item_component_seq;', 'applied', '');
INSERT INTO play_evolutions VALUES (18, '42a2710147772811624586b443f47342fb27041e', '2015-06-26 00:00:00', 'CREATE SEQUENCE comments_seq
INCREMENT 1
START 10;

CREATE TABLE vd_comments (
id                int8 NOT NULL DEFAULT nextval(''comments_seq'') PRIMARY KEY,
commentText      varchar (255) not null,
commonRating     int4 not null default 0,
commentedAt      timestamp not null default now(),
status            varchar(100) not null default ''NOT_REVIEWED'',
showAsAnonymous bool default false,
order_id          int8 not null,
FOREIGN KEY ( order_id ) REFERENCES vd_order (id)
);

--//@UNDO', 'DROP TABLE vd_comments;
DROP SEQUENCE comments_seq;', 'applied', '');
INSERT INTO play_evolutions VALUES (19, 'ca11e4024f50f9abe3608f483bd43db7e3dd16da', '2015-06-26 00:00:00', 'insert into vd_address (
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
''--'',
''--'',
''5'',
false
);

insert into vd_restaurants_categories (
categoryDisplayNameEN,
categoryDisplayNameRU,
categoryDisplayNameUA
) values (
''Phantoms'',
''Фантомы'',
''Фантоми''
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
''22:00'',
''08:00'',
''22:00'',
''08:00'',
''22:00'',
''08:00'',
''22:00'',
''08:00'',
''22:00'',
''08:00'',
''22:00'',
''08:00'',
''22:00'',
''08:00''
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
''Припять''           ,
false         ,
true   ,
0         ,
''pr01''    ,
''3214'' ,
now()       ,
0        ,
''PC''      ,
1, -- city_id         ,
1, -- address_id      ,
1, -- category_id     ,
1, -- workhours_id    ,
''38bab275-cdea-40f9-a659-275b72572a29''  -- user_id
);



--//@UNDO
-- SQL to undo the change goes here.', 'TRUNCATE TABLE vd_restaurant cascade;
TRUNCATE TABLE vd_address cascade ;
TRUNCATE TABLE vd_restaurants_categories cascade ;
TRUNCATE TABLE vd_restaurant_workhours cascade ;', 'applied', '');
INSERT INTO play_evolutions VALUES (20, '27adead85afc5380f30c09e0360131644c530959', '2015-06-26 00:00:00', 'CREATE TABLE vd_attachments (
id             varchar(38) NOT NULL  PRIMARY KEY,
commentText    varchar (255) not null,
createdAt      timestamp not null default now(),
fileType       varchar(100) not null default ''UNKNOWN'',
fileExt        varchar(10) not null default ''''
);
ALTER TABLE vd_restaurant ADD COLUMN logo_id varchar(38);
ALTER TABLE vd_restaurant ADD foreign key (logo_id) references vd_attachments (id);
--//@UNDO', 'ALTER TABLE vd_restaurant drop constraint vd_restaurant_logo_id_fkey;
ALTER TABLE vd_restaurant drop COLUMN logo_id;
DROP TABLE vd_attachments;', 'applied', '');
INSERT INTO play_evolutions VALUES (21, '6b37d047970f91aeca1db2a60d0c9e9a7e25df14', '2015-06-26 00:00:00', 'insert into vd_attachments values (''{a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11}'', ''text'', ''2001-01-01'', ''png'', ''png'');
update vd_restaurant SET logo_id = ''{a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11}'' where id = 1;
SET intervalstyle = iso_8601;
--//@UNDO', 'SET intervalstyle = postgres;
update vd_restaurant SET logo_id = null where id = 1;
delete from  vd_attachments where id = ''{a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11}'';', 'applied', '');
INSERT INTO play_evolutions VALUES (22, '31bdb86b9bc9acefc655bb33a9ff6c5f89a9b838', '2015-06-26 00:00:00', 'INSERT INTO vd_restaurant_descriptions (id, lang, description, restaurant_id) VALUES (1, ''ua'', ''Test Description'', 1);
--//@UNDO', 'delete from vd_restaurant_descriptions where id = 1;', 'applied', '');
INSERT INTO play_evolutions VALUES (23, 'd67af58d7b85adebdc20a91e7c7632ce84328431', '2015-06-26 00:00:00', 'INSERT INTO public.vd_menu_items_groups (id, name, description, deleted, restaurant_id) VALUES (1, ''Перші старви'', ''Смачні супи'', false, 1);
INSERT INTO public.vd_menu_items_groups (id, name, description, deleted, restaurant_id) VALUES (2, ''Другі старви'', ''Смачне мясне та гарніри'', false, 1);', 'delete from public.vd_menu_items_groups where id = 1;
delete from public.vd_menu_items_groups where id = 2;', 'applied', '');
INSERT INTO play_evolutions VALUES (24, 'faecee886ed797ad8acf1dd3486e82cc45cefaeb', '2015-06-26 00:00:00', 'INSERT INTO public.vd_menu_items (id, name, description, menuitemcreated, available, currency, price, deleted, restaurant_id, menu_item_group_id) VALUES (1, ''Борщ'', ''Дуже смачний борщ'', ''2013-03-21 19:06:46'', true, ''UAH'', 100, false, 1, 1);
INSERT INTO public.vd_menu_items (id, name, description, menuitemcreated, available, currency, price, deleted, restaurant_id, menu_item_group_id) VALUES (2, ''Зупа'', ''Дуже смачна зупа'', ''2013-03-21 19:06:46'', true, ''UAH'', 100, false, 1, 1);
INSERT INTO public.vd_menu_items (id, name, description, menuitemcreated, available, currency, price, deleted, restaurant_id, menu_item_group_id) VALUES (3, ''Картопля по селянськи'', ''Дуже смачна картопля'', ''2013-03-21 19:06:46'', true, ''UAH'', 100, false, 1, 2);
INSERT INTO public.vd_menu_items (id, name, description, menuitemcreated, available, currency, price, deleted, restaurant_id, menu_item_group_id) VALUES (4, ''Гречка з маслом'', ''Дуже гречка з маслом'', ''2013-03-21 19:06:46'', true, ''UAH'', 100, false, 1, 2);', 'delete from public.vd_menu_items where id =1;
delete from public.vd_menu_items where id =2;
delete from public.vd_menu_items where id =3;
delete from public.vd_menu_items where id =4;', 'applied', '');
INSERT INTO play_evolutions VALUES (25, '61ba75d78cb1241d6792be7dec0d80b615723e69', '2015-06-26 00:00:00', 'INSERT INTO public.vd_city (city_id, citynamekey, cityaliasname, display) VALUES (-1, '''', '''', false);
INSERT INTO public.vd_street (street_id, title_ua, title_en, title_ru, display, city_id) VALUES (-1, '''', '''', '''', false, -1);', 'delete from public.vd_street where city_id = -1;
delete from public.vd_city where city_id = -1;', 'applied', '');


--
-- Name: restaurant_descriptions_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('restaurant_descriptions_seq', 10, false);


--
-- Name: restaurant_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('restaurant_seq', 10, false);


--
-- Name: restaurants_categories_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('restaurants_categories_seq', 1, true);


--
-- Name: street_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('street_seq', 1, false);


--
-- Name: system_settings_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('system_settings_seq', 1, false);


--
-- Data for Name: vd_city; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO vd_city VALUES (1, 'city.names.Chernobyl', 'Chernobyl', false);
INSERT INTO vd_city VALUES (-1, '', '', false);


--
-- Data for Name: vd_street; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO vd_street VALUES (1, 'пр. Науки', 'Science av.', 'пр. Науки', false, 1);
INSERT INTO vd_street VALUES (-1, '', '', '', false, -1);


--
-- Data for Name: vd_address; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO vd_address VALUES (1, '5', '--', '--', false, 1, 1);
INSERT INTO vd_address VALUES (10, NULL, NULL, NULL, false, -1, -1);


--
-- Data for Name: vd_attachments; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO vd_attachments VALUES ('{a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11}', 'text', '2001-01-01 00:00:00', 'png', 'png');


--
-- Data for Name: vd_restaurant_workhours; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO vd_restaurant_workhours VALUES (1, '08:00:00', '22:00:00', '08:00:00', '22:00:00', '08:00:00', '22:00:00', '08:00:00', '22:00:00', '08:00:00', '22:00:00', '08:00:00', '22:00:00', '08:00:00', '22:00:00');


--
-- Data for Name: vd_restaurants_categories; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO vd_restaurants_categories VALUES (1, 'Phantoms', 'Фантомы', 'Фантоми');


--
-- Data for Name: vd_user; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO vd_user VALUES ('92a9c641-e108-4b37-9d05-363893b68281', 'mickey123', 'jays.demons@gmail.com', '+380630683088', 'Dl39PhnIg3Sqs7SUh39pEwFHUwE=', 'Mickey The Mouse', 'VD_ADMIN', '2015-06-26 15:06:31.960127', '2015-06-26 15:06:31.960127', '2015-06-26 15:06:31.960127', false);
INSERT INTO vd_user VALUES ('15ca4044-fe79-4e70-ab20-06b8c9508c7c', 'cc01', 'Chornobyl@vdoma.com.ua', '+380630683087', 'cc01', 'Default Chornobyl Courier', 'COURIER', '2015-06-26 15:06:31.961248', '2015-06-26 15:06:31.961248', '2015-06-26 15:06:31.961248', false);
INSERT INTO vd_user VALUES ('38bab275-cdea-40f9-a659-275b72572a29', 'rc01', 'office@vdoma.com.ua', '+380630683086', 'rc01', 'Default Chornobyl Restaurant holder', 'RESTAURANT', '2015-06-26 15:06:31.962257', '2015-06-26 15:06:31.962257', '2015-06-26 15:06:31.962257', false);
INSERT INTO vd_user VALUES ('1c5539fd-fab5-4c15-b78e-f5fa748d4fe9', 'Anonymous_u6FUvnbQNA', '', 'Anonymous_u6FUvnbQNA', 'Ncwz4sQfYTRqZ1RoEcD5kAN9jSQ=', 'Anonymous_u6FUvnbQNA', 'ANONYMOUS', NULL, '2015-06-26 15:06:38.319865', '2015-06-26 15:06:38.305', false);
INSERT INTO vd_user VALUES ('ff95d21b-e734-4cb7-afef-0d83e1c7a332', 'Anonymous_qHRTnKWZAU', '', 'Anonymous_qHRTnKWZAU', 'ULBrPIdDPi5XbPmYm4zdxd0bxLA=', 'Anonymous_qHRTnKWZAU', 'ANONYMOUS', NULL, '2015-06-26 15:17:13.385294', '2015-06-26 15:17:13.375', false);


--
-- Data for Name: vd_restaurant; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO vd_restaurant VALUES (1, 'Припять', false, true, 0, 'pr01', '3214', '2015-06-26 15:06:32.043074', 0, 'PC', 1, 1, 1, 1, '38bab275-cdea-40f9-a659-275b72572a29', '{a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11}');


--
-- Data for Name: vd_order; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO vd_order VALUES (10, NULL, false, 'UAH', 0, 'UAH', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2015-06-26 15:06:54.301', NULL, NULL, 'OPEN', 'NOT_PAID', 10, NULL, '1c5539fd-fab5-4c15-b78e-f5fa748d4fe9', 1);


--
-- Data for Name: vd_comments; Type: TABLE DATA; Schema: public; Owner: anderson
--



--
-- Data for Name: vd_menu_items_groups; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO vd_menu_items_groups VALUES (1, 'Перші старви', 'Смачні супи', false, 1);
INSERT INTO vd_menu_items_groups VALUES (2, 'Другі старви', 'Смачне мясне та гарніри', false, 1);


--
-- Data for Name: vd_menu_items; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO vd_menu_items VALUES (1, 'Борщ', 'Дуже смачний борщ', '2013-03-21 19:06:46', true, 'UAH', 100, false, 1, 1);
INSERT INTO vd_menu_items VALUES (2, 'Зупа', 'Дуже смачна зупа', '2013-03-21 19:06:46', true, 'UAH', 100, false, 1, 1);
INSERT INTO vd_menu_items VALUES (3, 'Картопля по селянськи', 'Дуже смачна картопля', '2013-03-21 19:06:46', true, 'UAH', 100, false, 1, 2);
INSERT INTO vd_menu_items VALUES (4, 'Гречка з маслом', 'Дуже гречка з маслом', '2013-03-21 19:06:46', true, 'UAH', 100, false, 1, 2);


--
-- Data for Name: vd_menu_item_components; Type: TABLE DATA; Schema: public; Owner: anderson
--



--
-- Data for Name: vd_order_items; Type: TABLE DATA; Schema: public; Owner: anderson
--



--
-- Data for Name: vd_restaurant_descriptions; Type: TABLE DATA; Schema: public; Owner: anderson
--

INSERT INTO vd_restaurant_descriptions VALUES (1, 'ua', 'Test Description', 1);


--
-- Data for Name: vd_system_settings; Type: TABLE DATA; Schema: public; Owner: anderson
--



--
-- Name: workhours_seq; Type: SEQUENCE SET; Schema: public; Owner: anderson
--

SELECT pg_catalog.setval('workhours_seq', 1, false);


--
-- PostgreSQL database dump complete
--

