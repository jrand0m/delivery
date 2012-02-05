--// create default shadow city
# --- !Ups
insert into vd_city (city_name_key, city_alias_name, display) values ('city.names.Chernobyl', 'Chernobyl',false);


--//@UNDO
# --- !Downs
delete from vd_city where city_name_key='city.names.Chernobyl' and display = false;

