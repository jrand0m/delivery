# --- !Ups
insert into vd_attachments values ('{a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11}', 'text', '2001-01-01', 'png', 'png');
update vd_restaurant SET logo_id = '{a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11}' where id = 1;
SET intervalstyle = iso_8601;
--//@UNDO
# --- !Downs
SET intervalstyle = postgres;
update vd_restaurant SET logo_id = null where id = 1;
delete from  vd_attachments where id = '{a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11}';