--// create default shadow city

insert into vd_city (city_name_key, display) values ('city.names.Chernobyl', false);


--//@UNDO

delete from vd_city where city_name_key='city.names.Chernobyl' and display = false;

