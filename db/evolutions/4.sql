# --- !Ups

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
-- ----------------------------
-- Foreign Key structure for table "accountingtransaction"
-- ----------------------------
ALTER TABLE "accountingtransaction" DROP FOREIGN KEY ("target_id") ;
ALTER TABLE "accountingtransaction" DROP FOREIGN KEY ("group_id") ;

-- ----------------------------
-- Foreign Key structure for table "address"
-- ----------------------------
ALTER TABLE "address" DROP FOREIGN KEY ("street_id");

-- ----------------------------
-- Foreign Key structure for table "anonymousenduser"
-- ----------------------------
ALTER TABLE "anonymousenduser" DROP FOREIGN KEY ("id") ;

-- ----------------------------
-- Foreign Key structure for table "comment"
-- ----------------------------
ALTER TABLE "comment" DROP FOREIGN KEY ("restaurant_id");
ALTER TABLE "comment" DROP FOREIGN KEY ("order_id") ;

-- ----------------------------
-- Foreign Key structure for table "courieruser"
-- ----------------------------
ALTER TABLE "courieruser" DROP FOREIGN KEY ("id") ;
ALTER TABLE "courieruser" DROP FOREIGN KEY ("city_id") ;

-- ----------------------------
-- Foreign Key structure for table "enduser"
-- ----------------------------
ALTER TABLE "enduser" DROP FOREIGN KEY ("id") ;

-- ----------------------------
-- Foreign Key structure for table "ip_geo_data"
-- ----------------------------
ALTER TABLE "ip_geo_data" DROP FOREIGN KEY ("city_id") ;
ALTER TABLE "ip_geo_data" DROP FOREIGN KEY ("coordinates_id");

-- ----------------------------
-- Foreign Key structure for table "menuitem"
-- ----------------------------
ALTER TABLE "menuitem" DROP FOREIGN KEY ("menuitemgroup_id");
ALTER TABLE "menuitem" DROP FOREIGN KEY ("restaurant_id") ;

-- ----------------------------
-- Foreign Key structure for table "menuitem_menuitemcomponent"
-- ----------------------------
ALTER TABLE "menuitem_menuitemcomponent" DROP FOREIGN KEY ("menuitem_id") ;
ALTER TABLE "menuitem_menuitemcomponent" DROP FOREIGN KEY ("components_id") ;

-- ----------------------------
-- Foreign Key structure for table "menuitemcomponent"
-- ----------------------------
ALTER TABLE "menuitemcomponent" DROP FOREIGN KEY ("itm_root_id") ;

-- ----------------------------
-- Foreign Key structure for table "menuitemcomponent_orderitem"
-- ----------------------------
ALTER TABLE "menuitemcomponent_orderitem" DROP FOREIGN KEY ("menuitemcomponent_id");
ALTER TABLE "menuitemcomponent_orderitem" DROP FOREIGN KEY ("usedin_id") ;

-- ----------------------------
-- Foreign Key structure for table "menuitemgroup"
-- ----------------------------
ALTER TABLE "menuitemgroup" DROP FOREIGN KEY ("restaurant_id") ;

-- ----------------------------
-- Foreign Key structure for table "not_compatible_map"
-- ----------------------------
ALTER TABLE "not_compatible_map" DROP FOREIGN KEY ("notcompatible_id");
ALTER TABLE "not_compatible_map" DROP FOREIGN KEY ("menuitemcomponent_id") ;

-- ----------------------------
-- Foreign Key structure for table "orderitem"
-- ----------------------------
ALTER TABLE "orderitem" DROP FOREIGN KEY ("order_id") ;
ALTER TABLE "orderitem" DROP FOREIGN KEY ("menuitem_id") ;

-- ----------------------------
-- Foreign Key structure for table "orders"
-- ----------------------------
ALTER TABLE "orders" DROP FOREIGN KEY ("orderowner_id");
ALTER TABLE "orders" DROP FOREIGN KEY ("deliveryaddress_id");
ALTER TABLE "orders" DROP FOREIGN KEY ("confirmedcourier_id") ;
ALTER TABLE "orders" DROP FOREIGN KEY ("restaurant_id") ;

-- ----------------------------
-- Foreign Key structure for table "required_map"
-- ----------------------------
ALTER TABLE "required_map" DROP FOREIGN KEY ("required_id") ;
ALTER TABLE "required_map" DROP FOREIGN KEY ("menuitemcomponent_id") ;

-- ----------------------------
-- Foreign Key structure for table "restaurant"
-- ----------------------------
ALTER TABLE "restaurant" DROP FOREIGN KEY ("address_id") ;
ALTER TABLE "restaurant" DROP FOREIGN KEY ("city_id") ;
ALTER TABLE "restaurant" DROP FOREIGN KEY ("category_id") ;
ALTER TABLE "restaurant" DROP FOREIGN KEY ("workhours_id") ;
ALTER TABLE "restaurant" DROP FOREIGN KEY ("device_id");

-- ----------------------------
-- Foreign Key structure for table "restaurant_restaurant"
-- ----------------------------
ALTER TABLE "restaurant_restaurant" DROP FOREIGN KEY ("restaurant_id");
ALTER TABLE "restaurant_restaurant" DROP FOREIGN KEY ("restoraunts_id") ;

-- ----------------------------
-- Foreign Key structure for table "restaurant_restaurantuser"
-- ----------------------------
ALTER TABLE "restaurant_restaurantuser" DROP FOREIGN KEY ("users_id");
ALTER TABLE "restaurant_restaurantuser" DROP FOREIGN KEY ("restaurant_id");

-- ----------------------------
-- Foreign Key structure for table "restaurantadministration"
-- ----------------------------
ALTER TABLE "restaurantadministration" DROP FOREIGN KEY ("id");

-- ----------------------------
-- Foreign Key structure for table "restaurantbarman"
-- ----------------------------
ALTER TABLE "restaurantbarman" DROP FOREIGN KEY ("id");

-- ----------------------------
-- Foreign Key structure for table "restaurantdescription"
-- ----------------------------
ALTER TABLE "restaurantdescription" DROP FOREIGN KEY ("restaurant_id");

-- ----------------------------
-- Foreign Key structure for table "restaurantuser"
-- ----------------------------
ALTER TABLE "restaurantuser" DROP FOREIGN KEY ("id");
ALTER TABLE "restaurantuser" DROP FOREIGN KEY ("restaurant_id") ;

-- ----------------------------
-- Foreign Key structure for table "selected_orderitems_components"
-- ----------------------------
ALTER TABLE "selected_orderitems_components" DROP FOREIGN KEY ("orderitem_id");
ALTER TABLE "selected_orderitems_components" DROP FOREIGN KEY ("selectedcomponents_id") ;

-- ----------------------------
-- Foreign Key structure for table "street"
-- ----------------------------
ALTER TABLE "street" DROP FOREIGN KEY ("city_id") ;

-- ----------------------------
-- Foreign Key structure for table "systemadministrator"
-- ----------------------------
ALTER TABLE "systemadministrator" DROP FOREIGN KEY ("id") ;

-- ----------------------------
-- Foreign Key structure for table "useraddress"
-- ----------------------------
ALTER TABLE "useraddress" DROP FOREIGN KEY ("user_id") ;
ALTER TABLE "useraddress" DROP FOREIGN KEY ("id") ;
ALTER TABLE "useraddress" DROP FOREIGN KEY ("user_id") ;
