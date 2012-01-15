
# --- !Ups

-- ----------------------------
-- Sequence structure for "addresss_sequence"
-- ----------------------------
CREATE SEQUENCE "address_sequence"
 INCREMENT 1
 START 1;

-- ----------------------------
-- Sequence structure for "city_sequence"
-- ----------------------------
CREATE SEQUENCE "city_sequence"
 INCREMENT 1
 START 1;

-- ----------------------------
-- Sequence structure for "comment_sequence"
-- ----------------------------
CREATE SEQUENCE "comment_sequence"
 INCREMENT 1
 START 1;

-- ----------------------------
-- Sequence structure for "coordinates_sequence"
-- ----------------------------
CREATE SEQUENCE "coordinates_sequence"
 INCREMENT 1
 START 1;

-- ----------------------------
-- Sequence structure for "ip_geo_data_sequence"
-- ----------------------------
CREATE SEQUENCE "ip_geo_data_sequence"
 INCREMENT 1
 START 1;

-- ----------------------------
-- Sequence structure for "menuitem_sequence"
-- ----------------------------
CREATE SEQUENCE "menuitem_sequence"
 INCREMENT 1
 START 1;

-- ----------------------------
-- Sequence structure for "menuitemcomponent_sequence"
-- ----------------------------
CREATE SEQUENCE "menuitemcomponent_sequence"
 INCREMENT 1
 START 1;

-- ----------------------------
-- Sequence structure for "menuitemgroup_sequence"
-- ----------------------------
CREATE SEQUENCE "menuitemgroup_sequence"
 INCREMENT 1
 START 1;
 -- ----------------------------
-- Sequence structure for "orderitem_sequence"
-- ----------------------------
CREATE SEQUENCE "orderitem_sequence"
 INCREMENT 1
 START 1;
 -- ----------------------------
-- Sequence structure for "orders_sequence"
-- ----------------------------
CREATE SEQUENCE "orders_sequence"
 INCREMENT 1
 START 1;
 -- ----------------------------
-- Sequence structure for "restaurant_sequence"
-- ----------------------------
CREATE SEQUENCE "restaurant_sequence"
 INCREMENT 1
 START 1;
 -- ----------------------------
-- Sequence structure for "restaurant_workhours_sequence"
-- ----------------------------
CREATE SEQUENCE "restaurant_workhours_sequence"
 INCREMENT 1
 START 1;
 -- ----------------------------
-- Sequence structure for "restaurantcategory_sequence"
-- ----------------------------
CREATE SEQUENCE "restaurantcategory_sequence"
 INCREMENT 1
 START 1;
 -- ----------------------------
-- Sequence structure for "restaurantdescription_sequence"
-- ----------------------------
CREATE SEQUENCE "restaurantdescription_sequence"
 INCREMENT 1
 START 1;

-- ----------------------------
-- Sequence structure for "street_sequence"
-- ----------------------------
CREATE SEQUENCE "street_sequence"
 INCREMENT 1
 START 1;


-- ----------------------------
-- Sequence structure for "users_sequence"
-- ----------------------------
CREATE SEQUENCE "users_sequence"
 INCREMENT 1
 START 1;

 
-- ----------------------------
-- Sequence structure for "workhours_sequence"
-- ----------------------------
CREATE SEQUENCE "workhours_sequence"
 INCREMENT 1
 START 1;

# --- !Downs

-- ----------------------------
-- Sequence structure for "addresss_sequence"
-- ----------------------------
DROP SEQUENCE IF EXISTS "address_sequence" CASCADE;
-- ----------------------------
-- Sequence structure for "city_sequence"
-- ----------------------------
DROP SEQUENCE IF EXISTS "city_sequence" CASCADE;
-- ----------------------------
-- Sequence structure for "comment_sequence"
-- ----------------------------
DROP SEQUENCE IF EXISTS "comment_sequence" CASCADE;
-- ----------------------------
-- Sequence structure for "coordinates_sequence"
-- ----------------------------
DROP SEQUENCE IF EXISTS "coordinates_sequence" CASCADE;
-- ----------------------------
-- Sequence structure for "ip_geo_data_sequence"
-- ----------------------------
DROP SEQUENCE IF EXISTS "ip_geo_data_sequence" CASCADE;
-- ----------------------------
-- Sequence structure for "menuitem_sequence"
-- ----------------------------
DROP SEQUENCE IF EXISTS "menuitem_sequence" CASCADE;
-- ----------------------------
-- Sequence structure for "menuitemcomponent_sequence"
-- ----------------------------
DROP SEQUENCE IF EXISTS "menuitemcomponent_sequence" CASCADE;
-- ----------------------------
-- Sequence structure for "menuitemgroup_sequence"
-- ----------------------------
DROP SEQUENCE IF EXISTS "menuitemgroup_sequence" CASCADE;
 -- ----------------------------
-- Sequence structure for "orderitem_sequence"
-- ----------------------------
DROP SEQUENCE IF EXISTS "orderitem_sequence" CASCADE;
 -- ----------------------------
-- Sequence structure for "orders_sequence"
-- ----------------------------
DROP SEQUENCE IF EXISTS "orders_sequence" CASCADE;
 -- ----------------------------
-- Sequence structure for "restaurant_sequence"
-- ----------------------------
DROP SEQUENCE IF EXISTS "restaurant_sequence" CASCADE;
 -- ----------------------------
-- Sequence structure for "restaurant_workhours_sequence"
-- ----------------------------
DROP SEQUENCE IF EXISTS "restaurant_workhours_sequence" CASCADE;
 -- ----------------------------
-- Sequence structure for "restaurantcategory_sequence"
-- ----------------------------
DROP SEQUENCE IF EXISTS "restaurantcategory_sequence" CASCADE;
 -- ----------------------------
-- Sequence structure for "restaurantdescription_sequence"
-- ----------------------------
DROP SEQUENCE IF EXISTS "restaurantdescription_sequence" CASCADE;
-- ----------------------------
-- Sequence structure for "workhours_seq"
-- ----------------------------
DROP SEQUENCE IF EXISTS "workhours_seq" CASCADE;