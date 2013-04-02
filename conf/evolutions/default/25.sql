# ---!Ups
INSERT INTO public.vd_city (city_id, citynamekey, cityaliasname, display) VALUES (-1, '', '', false);
INSERT INTO public.vd_street (street_id, title_ua, title_en, title_ru, display, city_id) VALUES (-1, '', '', '', false, -1);
# ---!Downs
delete from public.vd_street where city_id = -1;
delete from public.vd_city where city_id = -1;
