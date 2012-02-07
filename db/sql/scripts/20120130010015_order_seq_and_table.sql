 CREATE SEQUENCE "orders_seq"
 INCREMENT 1
 START 1;

CREATE TABLE "vd_order" (
  "id"               int8 NOT NULL DEFAULT nextval('orders_seq') PRIMARY KEY,
  "decline_message"  varchar (255),
  "deleted"          bool not null,
  "delivery_price_currency" varchar(3)  not null default 'UAH',
  "delivery_price"   money,
  "total_menu_price_currency" varchar(3)  not null default 'UAH',
  "total_menu_price" money,
  "order_accepted"   timestamp,
  "order_closed"     timestamp,
  "order_cooked"     timestamp,
  "order_confirmed"  timestamp,
  "order_created"    timestamp,
  "order_delivered"  timestamp,
  "order_taken"      timestamp,
  "updated_at"       timestamp not null default now,
  "order_planed_cooked" interval,
  "order_planed_delivery_time" interval,
  "order_status"    varchar(100) not null default 'OPEN',
  "payment_status"  varchar(100) not null default 'NOT_PAID',

  "delivery_address_id" int8 not null,
  "order_owner_id"  int8 not null,
  "restaurant_id"   int4 not null,
  "confirmed_courier_id" int8 not null,

  FOREIGN KEY ( "delivery_address_id" ) REFERENCES vd_address ("address_id"),
  FOREIGN KEY ( "order_owner_id" ) REFERENCES vd_user ("id"),
  FOREIGN KEY ( "restaurant_id" ) REFERENCES vd_restaurant ("id"),
  FOREIGN KEY ( "confirmed_courier_id" ) REFERENCES vd_user ("id")
);

--//@UNDO

DROP TABLE vd_order;
DROP SEQUENCE orders_seq;