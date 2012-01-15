# --- Alter talbes

# --- !Ups

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

# --- !Downs

-- ----------------------------
-- Primary Key structure for table "address"
-- ----------------------------
ALTER TABLE "address" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "anonymousenduser"
-- ----------------------------
ALTER TABLE "anonymousenduser" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "city"
-- ----------------------------
ALTER TABLE "city" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "comment"
-- ----------------------------
ALTER TABLE "comment" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "coordinates"
-- ----------------------------
ALTER TABLE "coordinates" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "courier"
-- ----------------------------
ALTER TABLE "courier" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "courierlogs"
-- ----------------------------
ALTER TABLE "courierlogs" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "couriersettings"
-- ----------------------------
ALTER TABLE "couriersettings" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "courieruser"
-- ----------------------------
ALTER TABLE "courieruser" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "devicelogs"
-- ----------------------------
ALTER TABLE "devicelogs" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "enduser"
-- ----------------------------
ALTER TABLE "enduser" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "genericdevice"
-- ----------------------------
ALTER TABLE "genericdevice" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "ip_geo_data"
-- ----------------------------
ALTER TABLE "ip_geo_data" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "menuitem"
-- ----------------------------
ALTER TABLE "menuitem" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table "menuitem_menuitemcomponent"
-- ----------------------------
ALTER TABLE "menuitem_menuitemcomponent" DROP UNIQUE ("components_id");

-- ----------------------------
-- Primary Key structure for table "menuitemcomponent"
-- ----------------------------
ALTER TABLE "menuitemcomponent" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "menuitemcomponent_orderitem"
-- ----------------------------
ALTER TABLE "menuitemcomponent_orderitem" DROP PRIMARY KEY ("menuitemcomponent_id", "usedin_id");

-- ----------------------------
-- Primary Key structure for table "menuitemgroup"
-- ----------------------------
ALTER TABLE "menuitemgroup" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "orderitem"
-- ----------------------------
ALTER TABLE "orderitem" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "orders"
-- ----------------------------
ALTER TABLE "orders" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "restaurant"
-- ----------------------------
ALTER TABLE "restaurant" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table "restaurant_restaurant"
-- ----------------------------
ALTER TABLE "restaurant_restaurant" DROP UNIQUE ("restoraunts_id");

-- ----------------------------
-- Uniques structure for table "restaurant_restaurantuser"
-- ----------------------------
ALTER TABLE "restaurant_restaurantuser" DROP UNIQUE ("users_id");

-- ----------------------------
-- Primary Key structure for table "restaurant_workhours"
-- ----------------------------
ALTER TABLE "restaurant_workhours" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "restaurantadministration"
-- ----------------------------
ALTER TABLE "restaurantadministration" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "restaurantbarman"
-- ----------------------------
ALTER TABLE "restaurantbarman" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "restaurantcategory"
-- ----------------------------
ALTER TABLE "restaurantcategory" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "restaurantdescription"
-- ----------------------------
ALTER TABLE "restaurantdescription" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "restaurantlogs"
-- ----------------------------
ALTER TABLE "restaurantlogs" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "restaurantsettings"
-- ----------------------------
ALTER TABLE "restaurantsettings" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "restaurantuser"
-- ----------------------------
ALTER TABLE "restaurantuser" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "selected_orderitems_components"
-- ----------------------------
ALTER TABLE "selected_orderitems_components" DROP PRIMARY KEY ("orderitem_id", "selectedcomponents_id");

-- ----------------------------
-- Primary Key structure for table "street"
-- ----------------------------
ALTER TABLE "street" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "systemadministrator"
-- ----------------------------
ALTER TABLE "systemadministrator" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "systemlogs"
-- ----------------------------
ALTER TABLE "systemlogs" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "systemsettings"
-- ----------------------------
ALTER TABLE "systemsettings" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "useraddress"
-- ----------------------------
ALTER TABLE "useraddress" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "userlogs"
-- ----------------------------
ALTER TABLE "userlogs" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "usersettings"
-- ----------------------------
ALTER TABLE "usersettings" DROP PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table "uzer"
-- ----------------------------
ALTER TABLE "uzer" DROP PRIMARY KEY ("id");