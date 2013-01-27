#Setup Application
install postgres 9.x
install play 2.0.x
enter postgresql
run
CREATE USER vdoma_usr WITH PASSWORD '271828183';
CREATE DATABASE vdoma_db;
GRANT ALL PRIVILEGES ON DATABASE vdoma_db to vdoma_usr;
