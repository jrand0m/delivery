# --- !Ups
INSERT INTO public.vd_menu_items_groups (id, name, description, deleted, restaurant_id) VALUES (1, 'Перші старви', 'Смачні супи', false, 1);
INSERT INTO public.vd_menu_items_groups (id, name, description, deleted, restaurant_id) VALUES (2, 'Другі старви', 'Смачне мясне та гарніри', false, 1);
# --- !Downs
delete from public.vd_menu_items_groups where id = 1;
delete from public.vd_menu_items_groups where id = 2;

