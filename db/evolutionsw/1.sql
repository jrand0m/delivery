
# --- !Ups
-- ----------------------------
-- Sequence structure for "hibernate_sequence"
-- ----------------------------
CREATE SEQUENCE "hibernate_sequence"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 3402
 CACHE 1;

-- ----------------------------
-- Sequence structure for "workhours_seq"
-- ----------------------------
CREATE SEQUENCE "workhours_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Table structure for "accountinggroup"
-- ----------------------------
CREATE TABLE "accountinggroup" (
"id" int8 NOT NULL,
"deleted" bool NOT NULL,
"description" varchar(255),
"name" varchar(255)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "accountingtransaction"
-- ----------------------------
CREATE TABLE "accountingtransaction" (
"id" int8 NOT NULL,
"amount" int4,
"deleted" bool NOT NULL,
"description" varchar(255),
"operationdate" timestamp(6),
"regulardayinmonthno" int4,
"state" varchar(255),
"group_id" int8,
"target_id" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "address"
-- ----------------------------
CREATE TABLE "address" (
"id" int8 NOT NULL,
"buldingnuber" varchar(255),
"deleted" bool NOT NULL,
"street_id" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "anonymousenduser"
-- ----------------------------
CREATE TABLE "anonymousenduser" (
"usid" varchar(255),
"id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "city"
-- ----------------------------
CREATE TABLE "city" (
"id" int8 NOT NULL,
"citynameen" varchar(255),
"citynameru" varchar(255),
"citynameua" varchar(255),
"display" bool NOT NULL,
"zipend" int4,
"zipstart" int4
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "comment"
-- ----------------------------
CREATE TABLE "comment" (
"id" int8 NOT NULL,
"commonrating" int4,
"date" timestamp(6),
"showasanonymous" bool NOT NULL,
"status" varchar(255),
"text" varchar(255),
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
"latitude" float8,
"longitude" float8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "courier"
-- ----------------------------
CREATE TABLE "courier" (
"id" int8 NOT NULL,
"device" bytea
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "courierlogs"
-- ----------------------------
CREATE TABLE "courierlogs" (
"id" int8 NOT NULL,
"actiontype" varchar(255),
"courier" bytea,
"date" timestamp(6),
"info" varchar(255),
"level" varchar(255)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "couriersettings"
-- ----------------------------
CREATE TABLE "couriersettings" (
"id" int8 NOT NULL,
"courier" bytea,
"enddate" timestamp(6),
"isdefault" bool NOT NULL,
"startdate" timestamp(6),
"_stg_key" varchar(255),
"_stg_value" varchar(255)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "courieruser"
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
-- Table structure for "devicelogs"
-- ----------------------------
CREATE TABLE "devicelogs" (
"id" int8 NOT NULL,
"actiontype" varchar(255),
"date" timestamp(6),
"device" bytea,
"info" varchar(255),
"level" varchar(255)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "enduser"
-- ----------------------------
CREATE TABLE "enduser" (
"id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "genericdevice"
-- ----------------------------
CREATE TABLE "genericdevice" (
"device_type" varchar(31) NOT NULL,
"id" int8 NOT NULL,
"deleted" bool NOT NULL,
"deviceactivateddate" timestamp(6),
"deviceversion" varchar(255),
"status" varchar(255),
"encriptionkey" varchar(255),
"lastping" timestamp(6)
)
WITH (OIDS=FALSE)

;

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
-- Table structure for "restaurant_restaurant"
-- ----------------------------
CREATE TABLE "restaurant_restaurant" (
"restaurant_id" int8 NOT NULL,
"restoraunts_id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "restaurant_restaurantuser"
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
CREATE TABLE "restaurantadministration" (
"id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "restaurantbarman"
-- ----------------------------
CREATE TABLE "restaurantbarman" (
"id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "restaurantcategory"
-- ----------------------------
CREATE TABLE "restaurantcategory" (
"id" int8 NOT NULL,
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
-- Table structure for "restaurantlogs"
-- ----------------------------
CREATE TABLE "restaurantlogs" (
"id" int8 NOT NULL,
"actiontype" varchar(255),
"date" timestamp(6),
"info" varchar(255),
"level" varchar(255),
"restaurant" bytea
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "restaurantsettings"
-- ----------------------------
CREATE TABLE "restaurantsettings" (
"id" int8 NOT NULL,
"enddate" timestamp(6),
"isdefault" bool NOT NULL,
"restaurant" bytea,
"startdate" timestamp(6),
"_stg_key" varchar(255),
"_stg_value" varchar(255)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "restaurantuser"
-- ----------------------------
CREATE TABLE "restaurantuser" (
"id" int8 NOT NULL,
"restaurant_id" int8
)
WITH (OIDS=FALSE)

;

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
"title_en" varchar(255),
"title_ua" varchar(255),
"use" bool NOT NULL,
"city_id" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "systemadministrator"
-- ----------------------------
CREATE TABLE "systemadministrator" (
"lastloginip" varchar(255),
"id" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "systemlogs"
-- ----------------------------
CREATE TABLE "systemlogs" (
"id" int8 NOT NULL,
"actiontype" varchar(255),
"date" timestamp(6),
"info" varchar(255),
"level" varchar(255)
)
WITH (OIDS=FALSE)

;

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
CREATE TABLE "userlogs" (
"id" int8 NOT NULL,
"actiontype" varchar(255),
"date" timestamp(6),
"info" varchar(255),
"level" varchar(255),
"loggedby" bytea
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "usersettings"
-- ----------------------------
CREATE TABLE "usersettings" (
"id" int8 NOT NULL,
"enddate" timestamp(6),
"isdefault" bool NOT NULL,
"startdate" timestamp(6),
"_stg_key" varchar(255),
"_stg_value" varchar(255),
"owner" bytea
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for "uzer"
-- ----------------------------
CREATE TABLE "uzer" (
"id" int8 NOT NULL,
"deleted" bool NOT NULL,
"email" varchar(255),
"joindate" timestamp(6),
"lastlogindate" timestamp(6),
"login" varchar(255),
"password" varchar(255),
"phonenumber" varchar(255),
"usr_name" varchar(255),
"usr_surname" varchar(255)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table "accountinggroup"
-- ----------------------------
ALTER TABLE "accountinggroup" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "accountingtransaction"
-- ----------------------------
ALTER TABLE "accountingtransaction" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "address"
-- ----------------------------
ALTER TABLE "address" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "anonymousenduser"
-- ----------------------------
ALTER TABLE "anonymousenduser" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "city"
-- ----------------------------
ALTER TABLE "city" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "comment"
-- ----------------------------
ALTER TABLE "comment" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "coordinates"
-- ----------------------------
ALTER TABLE "coordinates" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "courier"
-- ----------------------------
ALTER TABLE "courier" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "courierlogs"
-- ----------------------------
ALTER TABLE "courierlogs" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "couriersettings"
-- ----------------------------
ALTER TABLE "couriersettings" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "courieruser"
-- ----------------------------
ALTER TABLE "courieruser" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "devicelogs"
-- ----------------------------
ALTER TABLE "devicelogs" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "enduser"
-- ----------------------------
ALTER TABLE "enduser" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "genericdevice"
-- ----------------------------
ALTER TABLE "genericdevice" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "ip_geo_data"
-- ----------------------------
ALTER TABLE "ip_geo_data" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "menuitem"
-- ----------------------------
ALTER TABLE "menuitem" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table "menuitem_menuitemcomponent"
-- ----------------------------
ALTER TABLE "menuitem_menuitemcomponent" ADD UNIQUE ("components_id");

-- ----------------------------
-- Primary Key structure for table "menuitemcomponent"
-- ----------------------------
ALTER TABLE "menuitemcomponent" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "menuitemcomponent_orderitem"
-- ----------------------------
ALTER TABLE "menuitemcomponent_orderitem" ADD PRIMARY KEY ("menuitemcomponent_id", "usedin_id");

-- ----------------------------
-- Primary Key structure for table "menuitemgroup"
-- ----------------------------
ALTER TABLE "menuitemgroup" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "orderitem"
-- ----------------------------
ALTER TABLE "orderitem" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "orders"
-- ----------------------------
ALTER TABLE "orders" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "restaurant"
-- ----------------------------
ALTER TABLE "restaurant" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table "restaurant_restaurant"
-- ----------------------------
ALTER TABLE "restaurant_restaurant" ADD UNIQUE ("restoraunts_id");

-- ----------------------------
-- Uniques structure for table "restaurant_restaurantuser"
-- ----------------------------
ALTER TABLE "restaurant_restaurantuser" ADD UNIQUE ("users_id");

-- ----------------------------
-- Primary Key structure for table "restaurant_workhours"
-- ----------------------------
ALTER TABLE "restaurant_workhours" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "restaurantadministration"
-- ----------------------------
ALTER TABLE "restaurantadministration" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "restaurantbarman"
-- ----------------------------
ALTER TABLE "restaurantbarman" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "restaurantcategory"
-- ----------------------------
ALTER TABLE "restaurantcategory" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "restaurantdescription"
-- ----------------------------
ALTER TABLE "restaurantdescription" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "restaurantlogs"
-- ----------------------------
ALTER TABLE "restaurantlogs" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "restaurantsettings"
-- ----------------------------
ALTER TABLE "restaurantsettings" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "restaurantuser"
-- ----------------------------
ALTER TABLE "restaurantuser" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "selected_orderitems_components"
-- ----------------------------
ALTER TABLE "selected_orderitems_components" ADD PRIMARY KEY ("orderitem_id", "selectedcomponents_id");

-- ----------------------------
-- Primary Key structure for table "street"
-- ----------------------------
ALTER TABLE "street" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "systemadministrator"
-- ----------------------------
ALTER TABLE "systemadministrator" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "systemlogs"
-- ----------------------------
ALTER TABLE "systemlogs" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "systemsettings"
-- ----------------------------
ALTER TABLE "systemsettings" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "useraddress"
-- ----------------------------
ALTER TABLE "useraddress" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "userlogs"
-- ----------------------------
ALTER TABLE "userlogs" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "usersettings"
-- ----------------------------
ALTER TABLE "usersettings" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "uzer"
-- ----------------------------
ALTER TABLE "uzer" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Key structure for table "accountingtransaction"
-- ----------------------------
ALTER TABLE "accountingtransaction" ADD FOREIGN KEY ("target_id") REFERENCES "uzer" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "accountingtransaction" ADD FOREIGN KEY ("group_id") REFERENCES "accountinggroup" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "address"
-- ----------------------------
ALTER TABLE "address" ADD FOREIGN KEY ("street_id") REFERENCES "street" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "anonymousenduser"
-- ----------------------------
ALTER TABLE "anonymousenduser" ADD FOREIGN KEY ("id") REFERENCES "enduser" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "comment"
-- ----------------------------
ALTER TABLE "comment" ADD FOREIGN KEY ("restaurant_id") REFERENCES "restaurant" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "comment" ADD FOREIGN KEY ("order_id") REFERENCES "orders" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "courieruser"
-- ----------------------------
ALTER TABLE "courieruser" ADD FOREIGN KEY ("id") REFERENCES "uzer" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "courieruser" ADD FOREIGN KEY ("city_id") REFERENCES "city" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "enduser"
-- ----------------------------
ALTER TABLE "enduser" ADD FOREIGN KEY ("id") REFERENCES "uzer" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "ip_geo_data"
-- ----------------------------
ALTER TABLE "ip_geo_data" ADD FOREIGN KEY ("city_id") REFERENCES "city" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "ip_geo_data" ADD FOREIGN KEY ("coordinates_id") REFERENCES "coordinates" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "menuitem"
-- ----------------------------
ALTER TABLE "menuitem" ADD FOREIGN KEY ("menuitemgroup_id") REFERENCES "menuitemgroup" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "menuitem" ADD FOREIGN KEY ("restaurant_id") REFERENCES "restaurant" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "menuitem_menuitemcomponent"
-- ----------------------------
ALTER TABLE "menuitem_menuitemcomponent" ADD FOREIGN KEY ("menuitem_id") REFERENCES "menuitem" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "menuitem_menuitemcomponent" ADD FOREIGN KEY ("components_id") REFERENCES "menuitemcomponent" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "menuitemcomponent"
-- ----------------------------
ALTER TABLE "menuitemcomponent" ADD FOREIGN KEY ("itm_root_id") REFERENCES "menuitem" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "menuitemcomponent_orderitem"
-- ----------------------------
ALTER TABLE "menuitemcomponent_orderitem" ADD FOREIGN KEY ("menuitemcomponent_id") REFERENCES "menuitemcomponent" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "menuitemcomponent_orderitem" ADD FOREIGN KEY ("usedin_id") REFERENCES "orderitem" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "menuitemgroup"
-- ----------------------------
ALTER TABLE "menuitemgroup" ADD FOREIGN KEY ("restaurant_id") REFERENCES "restaurant" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "not_compatible_map"
-- ----------------------------
ALTER TABLE "not_compatible_map" ADD FOREIGN KEY ("notcompatible_id") REFERENCES "menuitemcomponent" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "not_compatible_map" ADD FOREIGN KEY ("menuitemcomponent_id") REFERENCES "menuitemcomponent" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "orderitem"
-- ----------------------------
ALTER TABLE "orderitem" ADD FOREIGN KEY ("order_id") REFERENCES "orders" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "orderitem" ADD FOREIGN KEY ("menuitem_id") REFERENCES "menuitem" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "orders"
-- ----------------------------
ALTER TABLE "orders" ADD FOREIGN KEY ("orderowner_id") REFERENCES "enduser" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "orders" ADD FOREIGN KEY ("deliveryaddress_id") REFERENCES "useraddress" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "orders" ADD FOREIGN KEY ("confirmedcourier_id") REFERENCES "courieruser" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "orders" ADD FOREIGN KEY ("restaurant_id") REFERENCES "restaurant" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "required_map"
-- ----------------------------
ALTER TABLE "required_map" ADD FOREIGN KEY ("required_id") REFERENCES "menuitemcomponent" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "required_map" ADD FOREIGN KEY ("menuitemcomponent_id") REFERENCES "menuitemcomponent" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "restaurant"
-- ----------------------------
ALTER TABLE "restaurant" ADD FOREIGN KEY ("address_id") REFERENCES "address" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "restaurant" ADD FOREIGN KEY ("city_id") REFERENCES "city" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "restaurant" ADD FOREIGN KEY ("category_id") REFERENCES "restaurantcategory" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "restaurant" ADD FOREIGN KEY ("workhours_id") REFERENCES "restaurant_workhours" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "restaurant" ADD FOREIGN KEY ("device_id") REFERENCES "genericdevice" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "restaurant_restaurant"
-- ----------------------------
ALTER TABLE "restaurant_restaurant" ADD FOREIGN KEY ("restaurant_id") REFERENCES "restaurant" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "restaurant_restaurant" ADD FOREIGN KEY ("restoraunts_id") REFERENCES "restaurant" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "restaurant_restaurantuser"
-- ----------------------------
ALTER TABLE "restaurant_restaurantuser" ADD FOREIGN KEY ("users_id") REFERENCES "restaurantuser" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "restaurant_restaurantuser" ADD FOREIGN KEY ("restaurant_id") REFERENCES "restaurant" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "restaurantadministration"
-- ----------------------------
ALTER TABLE "restaurantadministration" ADD FOREIGN KEY ("id") REFERENCES "restaurantuser" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "restaurantbarman"
-- ----------------------------
ALTER TABLE "restaurantbarman" ADD FOREIGN KEY ("id") REFERENCES "restaurantuser" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "restaurantdescription"
-- ----------------------------
ALTER TABLE "restaurantdescription" ADD FOREIGN KEY ("restaurant_id") REFERENCES "restaurant" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "restaurantuser"
-- ----------------------------
ALTER TABLE "restaurantuser" ADD FOREIGN KEY ("id") REFERENCES "uzer" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "restaurantuser" ADD FOREIGN KEY ("restaurant_id") REFERENCES "restaurant" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "selected_orderitems_components"
-- ----------------------------
ALTER TABLE "selected_orderitems_components" ADD FOREIGN KEY ("orderitem_id") REFERENCES "orderitem" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "selected_orderitems_components" ADD FOREIGN KEY ("selectedcomponents_id") REFERENCES "menuitemcomponent" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "street"
-- ----------------------------
ALTER TABLE "street" ADD FOREIGN KEY ("city_id") REFERENCES "city" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "systemadministrator"
-- ----------------------------
ALTER TABLE "systemadministrator" ADD FOREIGN KEY ("id") REFERENCES "uzer" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "useraddress"
-- ----------------------------
ALTER TABLE "useraddress" ADD FOREIGN KEY ("user_id") REFERENCES "uzer" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "useraddress" ADD FOREIGN KEY ("id") REFERENCES "address" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "useraddress" ADD FOREIGN KEY ("user_id") REFERENCES "enduser" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

# --- !Downs
