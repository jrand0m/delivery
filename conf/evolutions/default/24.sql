# --- !Ups
INSERT INTO public.vd_menu_items (id, name, description, menuitemcreated, available, currency, price, deleted, restaurant_id, menu_item_group_id) VALUES (1, 'Борщ', 'Дуже смачний борщ', '2013-03-21 19:06:46', true, 'UAH', 100, false, 1, 1);
INSERT INTO public.vd_menu_items (id, name, description, menuitemcreated, available, currency, price, deleted, restaurant_id, menu_item_group_id) VALUES (2, 'Зупа', 'Дуже смачна зупа', '2013-03-21 19:06:46', true, 'UAH', 100, false, 1, 1);
INSERT INTO public.vd_menu_items (id, name, description, menuitemcreated, available, currency, price, deleted, restaurant_id, menu_item_group_id) VALUES (3, 'Картопля по селянськи', 'Дуже смачна картопля', '2013-03-21 19:06:46', true, 'UAH', 100, false, 1, 2);
INSERT INTO public.vd_menu_items (id, name, description, menuitemcreated, available, currency, price, deleted, restaurant_id, menu_item_group_id) VALUES (4, 'Гречка з маслом', 'Дуже гречка з маслом', '2013-03-21 19:06:46', true, 'UAH', 100, false, 1, 2);
# --- !Downs
delete from public.vd_menu_items where id =1;
delete from public.vd_menu_items where id =2;
delete from public.vd_menu_items where id =3;
delete from public.vd_menu_items where id =4;