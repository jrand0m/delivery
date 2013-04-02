# --- !Ups
CREATE SEQUENCE orders_seq
 INCREMENT 1
 START 10;

CREATE TABLE vd_order (
  id               int8 NOT NULL DEFAULT nextval('orders_seq') PRIMARY KEY,
  declineMessage  varchar (255),
  deleted          bool not null,
  deliveryPrice_currency varchar(3)  not null default 'UAH',
  deliveryPrice   numeric(16,0),
  totalMenuPrice_currency varchar(3)  not null default 'UAH',
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
  orderStatus    varchar(100) not null default 'OPEN',
  paymentStatus  varchar(100) not null default 'NOT_PAID',

  delivery_address_id int8,
  confirmed_courier_id varchar(38),
  order_owner_id  varchar(38) not null,
  restaurant_id   int4 not null,

  FOREIGN KEY ( delivery_address_id ) REFERENCES vd_address (address_id),
  FOREIGN KEY ( order_owner_id ) REFERENCES vd_user (id),
  FOREIGN KEY ( restaurant_id ) REFERENCES vd_restaurant (id),
  FOREIGN KEY ( confirmed_courier_id ) REFERENCES vd_user (id)
);

--//@UNDO
# --- !Downs
DROP TABLE vd_order cascade;
DROP SEQUENCE orders_seq;