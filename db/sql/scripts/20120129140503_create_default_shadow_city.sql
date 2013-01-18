--// create default shadow city

insert into vd_city ("city_id","cityNameKey", "cityAliasName", "display") values (1,'city.names.Chernobyl', 'Chernobyl',false);


--//@UNDO

delete from vd_city where city_id = 1 ;

