#Setup Application

- Install [postgres 9.x](http://www.postgresql.org/download/)
- Install [play 2.1.x](http://www.playframework.com/download)
- Enter postgresql:
> psql -P localhost
- Create database:
> CREATE USER vdoma_usr WITH PASSWORD '271828183';<br/>
> CREATE DATABASE vdoma_db;<br/>
> GRANT ALL PRIVILEGES ON DATABASE vdoma_db to vdoma_usr;<br/>

- Run play:
> `$ play ~run`

    or
> `> start.bat`

#Tips and Tricks
Here are some essential tips and howto's for working with components
##PostgresSQL
Yup, it's not mysql
###Navigating throug metadata of tables and databases
- `\l` List databases
- `\c database-name`  Connect to db (like MYSQL's `use <database-name>`)
- `\d` List tables in database
- `\d table-name` Describe table

###Cleaning database without droping database
Drop `public` schema and then recreate it
> DROP SCHEMA public CASCADE;<br/>
> CREATE SCHEMA public AUTHORIZATION bob;<br/>
> GRANT ALL ON SCHEMA public TO bob;<br/>

#Troubleshooting

##PostgreSQL related stuff

### org.postgresql.util.PSQLException: ERROR: relation "play_evolutions" does not exist
Most probably you have forgot to create `public` schema or grant permissions for your user to write-access it.
####Fix
> CREATE SCHEMA public AUTHORIZATION vdoma_usr;

or
> GRANT ALL ON SCHEMA public TO vdoma_usr;

