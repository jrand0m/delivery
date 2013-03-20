# --- !Ups
CREATE SEQUENCE order_items_seq
 INCREMENT 1
 START 10;

CREATE TABLE vd_order_items (
    id               int8 NOT NULL DEFAULT nextval('order_items_seq') PRIMARY KEY,
    count            int4 not null default 1,
    deleted          bool not null default false,
    totalOrderItemPrice decimal(15),
    totalOrderItemPrice_currency varchar(3) not null default 'UAH',
    menu_item_id     int8 not null,
    order_id         int8 not null,

    FOREIGN KEY ( menu_item_id ) REFERENCES vd_menu_items (id),
    FOREIGN KEY ( order_id ) REFERENCES vd_order (id)
);

--//@UNDO
# --- !Downs
DROP TABLE vd_order_items;
DROP SEQUENCE order_items_seq;