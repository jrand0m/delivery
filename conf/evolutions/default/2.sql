--// create default shadow city
# --- !Ups
insert into vd_city ("city_id","cityNameKey", "cityAliasName", "display") values (1,'city.names.Chernobyl', 'Chernobyl',false);


--//@UNDO
# --- !Downs
delete from vd_city where city_id = 1 ;

