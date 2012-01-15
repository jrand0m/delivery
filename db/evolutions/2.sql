# --- !Ups
-- ----------------------------
-- Table structure for "address"
-- ----------------------------
CREATE TABLE "address" (
"id" int8 NOT NULL,
"buldingnumber" varchar(10), -- renamed from 'buildingnuber', varchar len changed from 255
"deleted" bool NOT NULL,
"street_id" int8,
"city_id" int8 -- added field (must be a foreign key)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "anonymousenduser"
-- ----------------------------
-- CREATE TABLE "anonymousenduser" ( -- deleted now users distinquish by special field
-- "usid" varchar(255),
--  "id" int8 NOT NULL
-- )
-- WITH (OIDS=FALSE)
-- ;

-- ----------------------------
-- Table structure for "city"
-- ----------------------------
CREATE TABLE "city" (
"id" int8 NOT NULL,
"citynamekey" varchar(255), -- (how to rename city if ) rename from citynameen (we store only city key)
-- "citynameru" varchar(255), -- deleted
-- "citynameua" varchar(255), -- deleted
"display" bool NOT NULL -- ,
-- "zipend" int4, -- deleted
-- "zipstart" int4 -- deleted
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "comment"
-- ----------------------------
CREATE TABLE "comment" (
"id" int8 NOT NULL,
"commonrating" int4,
"comment_date" date, -- changed type from timestamp(6), renamed from 'date'
"showasanonymous" bool NOT NULL,
"status" varchar(255), -- TODO restrict to values
"text" varchar(255), -- comment on order
"order_id" varchar(255),
"restaurant_id" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "coordinates"
-- ----------------------------
CREATE TABLE "coordinates" (
"id" int8 NOT NULL,
"latitude" float8, --TODO change to point
"longitude" float8 --TODO change to point
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "courier"
-- ----------------------------
--CREATE TABLE "courier" (
--"id" int8 NOT NULL,
--"device" bytea
--)
--WITH (OIDS=FALSE)
--
--;

-- ----------------------------
-- deleted Table structure for "courierlogs"
-- ----------------------------
--CREATE TABLE "courierlogs" (
--"id" int8 NOT NULL,
--"actiontype" varchar(255),
--"courier" bytea,
--"date" timestamp(6),
--"info" varchar(255),
--"level" varchar(255)
--)
--WITH (OIDS=FALSE)
--
--;

-- ----------------------------
-- deleted Table structure for "couriersettings"
-- ----------------------------
--CREATE TABLE "couriersettings" (
--"id" int8 NOT NULL,
--"courier" bytea,
--"enddate" timestamp(6),
--"isdefault" bool NOT NULL,
--"startdate" timestamp(6),
--"_stg_key" varchar(255),
--"_stg_value" varchar(255)
--)
--WITH (OIDS=FALSE)
--
--;

-- ----------------------------
-- now is in customeruser Table structure for "courieruser"
-- ----------------------------
CREATE TABLE "courieruser" (
"gaspayment" int4,
"sallary" int4,
"id" int8 NOT NULL,
"city_id" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- deleted Table structure for "devicelogs"
-- ----------------------------


-- ----------------------------
-- deleted Table structure for "enduser" use customeruser
-- ----------------------------

-- ----------------------------
-- deleted Table structure for "genericdevice"
-- ----------------------------

-- ----------------------------
-- Table structure for "ip_geo_data"
-- ----------------------------
CREATE TABLE "ip_geo_data" (
"id" int8 NOT NULL,
"ip" varchar(255),
"lastupdate" timestamp(6),
"city_id" int8,
"coordinates_id" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "menuitem"
-- ----------------------------
CREATE TABLE "menuitem" (
"id" int8 NOT NULL,
"avaliable" bool NOT NULL,
"deleted" bool NOT NULL,
"description" varchar(255),
"menuitemcreated" timestamp(6),
"name" varchar(255),
"price" int4,
"showcomponents" bool NOT NULL,
"menuitemgroup_id" int8,
"restaurant_id" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "menuitem_menuitemcomponent"
-- ----------------------------
CREATE TABLE "menuitem_menuitemcomponent" (
"menuitem_id" int8 NOT NULL,
"components_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "menuitemcomponent"
-- ----------------------------
CREATE TABLE "menuitemcomponent" (
"id" int8 NOT NULL,
"itm_avaliable" bool NOT NULL,
"itm_desc" varchar(255),
"itm_name" varchar(255),
"itm_price" int4,
"itm_root_id" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "menuitemcomponent_orderitem"
-- ----------------------------
CREATE TABLE "menuitemcomponent_orderitem" (
"menuitemcomponent_id" int8 NOT NULL,
"usedin_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "menuitemgroup"
-- ----------------------------
CREATE TABLE "menuitemgroup" (
"id" int8 NOT NULL,
"deleted" bool NOT NULL,
"description" varchar(255),
"name" varchar(255),
"restaurant_id" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "not_compatible_map"
-- ----------------------------
CREATE TABLE "not_compatible_map" (
"menuitemcomponent_id" int8 NOT NULL,
"notcompatible_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "orderitem"
-- ----------------------------
CREATE TABLE "orderitem" (
"id" int8 NOT NULL,
"count" int4,
"deleted" bool NOT NULL,
"orderitemprice" int4,
"menuitem_id" int8,
"order_id" varchar(255)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "orders"
-- ----------------------------
CREATE TABLE "orders" (
"id" varchar(255) NOT NULL,
"declinemessage" varchar(255),
"deleted" bool NOT NULL,
"deliveryprice" int4,
"orderaccepted" timestamp(6),
"orderclosed" timestamp(6),
"orderconfirmed" timestamp(6),
"ordercooked" timestamp(6),
"ordercreated" timestamp(6),
"orderdelivered" timestamp(6),
"orderplanedcooked" timestamp(6),
"orderplaneddeliverytime" timestamp(6),
"orderstatus" varchar(255),
"ordertaken" timestamp(6),
"paymentstatus" varchar(255),
"restaurantdiscount" float4,
"shorthandid" varchar(255),
"totalmenuprice" int4,
"updated" timestamp(6),
"confirmedcourier_id" int8,
"deliveryaddress_id" int8,
"orderowner_id" int8,
"restaurant_id" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "required_map"
-- ----------------------------
CREATE TABLE "required_map" (
"menuitemcomponent_id" int8 NOT NULL,
"required_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "restaurant"
-- ----------------------------
CREATE TABLE "restaurant" (
"dtype" varchar(31) NOT NULL,
"id" int8 NOT NULL,
"cityzip" int4,
"contactperson" varchar(255),
"contactphone" varchar(255),
"deleted" bool NOT NULL,
"restaurant_descr" varchar(255),
"discount" float8,
"logo" varchar(255),
"raiting" int4,
"salt" varchar(255),
"showonindex" bool NOT NULL,
"title" varchar(255),
"twoletters" varchar(255),
"address_id" int8,
"category_id" int8,
"city_id" int8,
"device_id" int8,
"workhours_id" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "restaurant_restaurant" W00t?
-- ----------------------------
CREATE TABLE "restaurant_restaurant" (
"restaurant_id" int8 NOT NULL,
"restoraunts_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "restaurant_restaurantuser" DELETE
-- ----------------------------
CREATE TABLE "restaurant_restaurantuser" (
"restaurant_id" int8 NOT NULL,
"users_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "restaurant_workhours"
-- ----------------------------
CREATE TABLE "restaurant_workhours" (
"id" int8 NOT NULL,
"fri_end" time(6),
"fri_start" time(6),
"mon_end" time(6),
"mon_start" time(6),
"sat_end" time(6),
"sat_start" time(6),
"sun_end" time(6),
"sun_start" time(6),
"thu_end" time(6),
"thu_start" time(6),
"tue_end" time(6),
"tue_start" time(6),
"wed_end" time(6),
"wed_start" bytea
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "restaurantadministration"
-- ----------------------------

-- ----------------------------
-- Table structure for "restaurantbarman"
-- ----------------------------

-- ----------------------------
-- Table structure for "restaurantcategory"
-- ----------------------------
CREATE TABLE "restaurantcategory" (
"id" int8 NOT NULL, --- if i ll find a way do it via messages
"categorydisplaynameen" varchar(255),
"categorydisplaynameru" varchar(255),
"categorydisplaynameua" varchar(255)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "restaurantdescription"
-- ----------------------------
CREATE TABLE "restaurantdescription" (
"id" int8 NOT NULL,
"description" varchar(255),
"lang" varchar(255),
"restaurant_id" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "restaurantuser"
-- ----------------------------


-- ----------------------------
-- Table structure for "selected_orderitems_components"
-- ----------------------------
CREATE TABLE "selected_orderitems_components" (
"orderitem_id" int8 NOT NULL,
"selectedcomponents_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "street"
-- ----------------------------
CREATE TABLE "street" (
"id" int8 NOT NULL,
"title_key" varchar(255), -- renamed from title_en
-- "title_ua" varchar(255), -- deleted
"display" bool NOT NULL, -- renamed from use
"city_id" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "systemadministrator"
-- ----------------------------

-- ----------------------------
-- Table structure for "systemlogs"
-- ----------------------------
/*CREATE TABLE "systemlogs" (
"id" int8 NOT NULL,
"actiontype" varchar(255),
"date" timestamp(6),
"info" varchar(255),
"level" varchar(255)
)
WITH (OIDS=FALSE)

;*/

-- ----------------------------
-- Table structure for "systemsettings"
-- ----------------------------
CREATE TABLE "systemsettings" (
"id" int8 NOT NULL,
"enddate" timestamp(6),
"isdefaultsetting" bool NOT NULL,
"startdate" timestamp(6),
"_stg_key" varchar(255),
"_stg_value" varchar(255)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "useraddress"
-- ----------------------------
CREATE TABLE "useraddress" (
"additionalinfo" varchar(255),
"appartamentsnumber" varchar(255),
"doorcode" varchar(255),
"id" int8 NOT NULL,
"user_id" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "userlogs"
-- ----------------------------


-- ----------------------------
-- Table structure for "usersettings"
-- ----------------------------


-- ----------------------------
-- Table structure for "uzer"
-- ----------------------------
CREATE TABLE "users" ( -- renamed from uzer
"id" int8 NOT NULL,
"deleted" bool NOT NULL,
"email" varchar(255),
"joindate" timestamp(6),
"lastlogin" timestamp(6), -- renamed from lastlogindate
"login" varchar(255),
"passwd" varchar(255), -- renamed from password, TODO  must be md5, mb there is special field or trigger
"phonenumber" varchar(255),
"usr_name" varchar(255), -- user name and surname
--"usr_surname" varchar(255) -- deleted
"registered" boolean not null default FALSE
)
WITH (OIDS=FALSE)

;
# --- !Downs

DROP TABLE IF EXISTS "address" ;
DROP TABLE IF EXISTS "city" ;
DROP TABLE IF EXISTS "comment" ;
DROP TABLE IF EXISTS "coordinates" ;
DROP TABLE IF EXISTS "courieruser" ;
DROP TABLE IF EXISTS "ip_geo_data" ;
DROP TABLE IF EXISTS "menuitem" ;
DROP TABLE IF EXISTS "menuitem_menuitemcomponent" ;
DROP TABLE IF EXISTS "menuitemcomponent" ;
DROP TABLE IF EXISTS "menuitemcomponent_orderitem" ;
DROP TABLE IF EXISTS "menuitemgroup" ;
DROP TABLE IF EXISTS "not_compatible_map" ;
DROP TABLE IF EXISTS "orderitem" ;
DROP TABLE IF EXISTS "orders" ;
DROP TABLE IF EXISTS "required_map" ;
DROP TABLE IF EXISTS "restaurant" ;
DROP TABLE IF EXISTS "restaurant_restaurant" ;
DROP TABLE IF EXISTS "restaurant_restaurantuser" ;
DROP TABLE IF EXISTS "restaurant_workhours" ;
DROP TABLE IF EXISTS "restaurantcategory" ;
DROP TABLE IF EXISTS "restaurantdescription" ;
DROP TABLE IF EXISTS "selected_orderitems_components";
DROP TABLE IF EXISTS "street" ;
DROP TABLE IF EXISTS "systemsettings" ;
DROP TABLE IF EXISTS "useraddress";
DROP TABLE IF EXISTS "users" ;

