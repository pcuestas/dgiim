## Postgres installation (Linux / WSL)

In the terminal, type:
`sudo apt install postgresql postgresql-contrib`

This will install both the server (where the database resides) and the command line client (`psql`) with which you access the server, in addition to some other utilities described below.

After installation, you can check the version with `psql --version` 

Check that the server is running with `sudo service postgresql status`

You can also use `sudo service postgresql start` to start the service (which runs the server); the options `stop`, `restart` and `reload` are also available with this command.

## Install pgAdmin4 (Linux / Windows) 
https://www.pgadmin.org/download/

pgAdmin4 and dBeaver are graphical *clients*, and you need to tell them to connect to the *server*. For that you need to provide a connection *name* (whichever you want), indicate which *drivers* to use (postgresql), and then the *host* (`localhost` whenever server and client are in the same machine) and *port* (just use the default), the user (`alumnodb` for this class) and the user's password (`alumnodb`).

Before doing that, you need to configure postgresql and create the user alumnodb, as discussed next.
## Postgresql terminal commands

After installing postgres, you have a number of terminal commands available: *psql* (the postgres client command line shell), *createdb*, *dropdb*, *pg_dump*, and *pg_restore*. All of these take a number of optional arguments. For example:

`psql -U alumnodb classicmodels`

will connect to the server with user alumnodb and database classicmodels.

When these options are not provided in the command line, postgres will use certain values for these options - localhost with the default port, the linux user as database user, and a database with the name of the user. You can provide some of these values with environment variables (PGUSER and PGPASSWORD, for example), which can be specified directly in the Linux shell, in your .bashprofile, or in a shell script or Makefile.


Right after installation, you can only perform these operations with user `postgres` and one of the system databases. (That's simply because no other users or databases exist.) The goal of the next sections is to guide you through the steps needed to get to "normal usage".

## Postgresql config (Linux / WSL)

**Important note**: There is a *Linux* user called postgres, and there is a *postgresql database user* called postgres. 

The Linux `postgres` user owns the server process in your machine (just as you own the processes that you launch), and for the most part you can ignore it, except for the initial configuration (described below). 

The postgresql database user `postgres`, on the other hand, refers to a database user, not a Linux user. As this is the postgresql **super user**, you **should avoid** using it unless you know very well what you are doing, or you risk messing things up. See below for steps to create other users. 

By default, both the Linux and postgresql `postgres` users come with no password.


### Authentication methods to connect to the server
By default, postgresql uses *ident* as authentication method. This means that it will accept connections to the server only from the current Linux user. 

You want to change this to *password authentication*. In order to do this, locate the file `pg_hba.conf` (usually in /etc/postgres/\<version\>/main/pg_hba.conf) so that the authentication method is **md5** (meaning encrypted password)

```
# Database administrative login by Unix domain socket
local   all             postgres                                peer

# TYPE  DATABASE        USER            ADDRESS                 METHOD

# "local" is for Unix domain socket connections only
local   all             all                                     md5
# IPv4 local connections:
host    all             all             127.0.0.1/32            md5
# IPv6 local connections:
host    all             all             ::1/128                 md5
```

But leave it as *peer* (which corresponds to *ident*) for user `postgres`. This is needed for the server to work.

### Changing the password of the database user postgres 

In some (rare) cases, you want to be able to access the database with database user postgres, for example if you want to make a connection with that user in pgAdmin4, and for the initial configuration.

In the terminal, type

`> su - postgres` 

to become the (Linux) postgres user, and then

`> psql` 

will allow you to enter the postgresql shell without authentication (as the database user postgres, and connected to the database named postgres).

(Note: if this doesn't work, you might have changed the password of the Linux user 'postgres'; you can change it again by typing `sudo password postgres`)

Alternatively, 

`>sudo -u postgres psql`

also gets you into the psql shell as user postgres (after asking for your Linux user password).

You can change the password of the database user postgres from the psql shell using either `\password` or `ALTER USER postgres WITH PASSWORD 'mynewpassword';` 

**Important: in the shell, never forget to type the semicolon `;` to terminate a command.** If psql seems unresponsive, or the history mechanism (up and down arrows) are not working, the most likely cause is a missing semicolon in a previous command. Just type semicolons until it becomes responsive again.

Getting out of the psql shell: `\q` or `Ctrl-D` terminate the shell. If you know about background processes, you can also suspend it without quitting with `Ctrl-Z` to get back to the Linux shell; and resume the process later with `fg`.

### Creating a new user

In the psql shell type 
```
CREATE USER alumnodb WITH CREATEDB ENCRYPTED PASSWORD 'alumnodb';
``` 

You can also create a new user by going to the login/roles tab in pgAdmin4 or the roles tab in dbeaver, and right clicking to get the contextual menu. Make sure to choose the appropriate permissions for the new user, but do **not** make it a super user.

After creating the new user, typing `\l' inside psql should give you this output:
``` 
                    Lista de roles
 Nombre de rol |   Atributos                                                | Miembro de 
----------------------------------------------------------------------------------------
 alumnodb      | Crear BD                                                   | {}
 postgres      | Superusuario, Crear rol, Crear BD, Replicación, Ignora RLS | {}
```

(Alternatively, run `psql -c '\l'` from the Linux shell)

## Postgresql terminal commands

```
psql -U user database

createdb -U user database`

dropdb -U user database

pg_dump -U user database > dbdump.sql

psql -U user database < dbdump.sql
``` 

The third command creates a backup of database in file dbdump.sql (this is a text file with the SQL commands needed to restore the database). The fourth command restores the database dump created in the previous step, by simply running the commands in dbdump.sql.

You can use these environment vars to avoid entering connection options:

PGUSER=alumnodb

PGPASSWORD=alumnodb

PGDATABASE=classicmodels

You can set them with SET in Windows, export in Linux (either in the bash shell or in .bashprofile), or in bash scripts or Makefile vars.

## Running SQL code in psql

There are a number of ways to run postgresql code in the psql shell. One is to simply type it in the shell, like 
```
classicmodels=> select * from customers limit 5;
```

You can also use redirects, pipes, or command options from the Linux shell:

```
psql [OPTIONS] < foo.sql
cat foo.sql | psql [OPTIONS]
psql [OPTIONS] -f foo.sql
psql [OPTIONS] -c "select * from customers limit 5;"
``` 

The first three will run the code in foo.sql; the last one will simply run the given command. [OPTIONS] here refer mainly to username and database. You can also try it with psql internal commands (see below), e.g. `psql [OPTIONS] -c '\du'` to list users and permisions.

You can also type `\i foo.sql` command in psql to run the commands in "foo.sql" (the '\i' stands for 'insert file'). So for example you can restore a database dump "classicmodels.sql" with `\i classicmodels.sql`, or you can run a query in file "query1.sql" by typing `\i query1.sql`.

## PSQL internal commands

Some useful psql commands:

\q = quit

\\? = help

\\! shellcommand = run shellcommand in (Windows/Linux) terminal

\h sqlcommand = help about syntax of sqlcommand (e.g. "\h copy" or "\h with")

\l = list databases

\c databasename = connect to database 

\du = list users

\d list tables 

\dt describe tables

\d+ tablename = list info about table tablename, including **constraints**

\cd change current working dir 
(note in Windows \! cd gives pwd, but \cd somedir won't work)

\i filename.sql = insert (run) filename.sql

\o filename = send output to filename 

\copy 

\echo string 

\qecho string = like echo but sending to output set with \o

\password

## Using a Makefile

The goals defined in the provided Makefile (such as dropdb, createdb, restore, etc.) just run some of the commands describe above, with options set in the various PG... variables.

In particular, you need CREATEDB permissions for user alumnodb, and this user must have alumnodb as password.

Make sure you understand, therefore, what each of the goals defined in the Makefile do. For example, `make shell` is just calling `psql -U alumnodb classicmodels`, and passing it the password `alumnodb`. If `make shell` doesn't work, it's because that command doesn't work (just check for yourself), and you should make sure that command works.

But if things are properly configured, none of the commands provided in the Makefile should fail:

- `make` should create the database classicmodels, owned by user alumnodb (you can see the owner using `\l`)

- `make dropdb`, `make createdb`, `make shell`, `make restore` should work without problems - unless of course some obvious condition is not met, say the database doesn't exist for dropdb, or the database already exists for createdb.

So if you get an error such as "password authentication failed", just make sure you understand:

1) How users and permissions work, as described in this document
2) You have passed a wrong password to the corresponding program (that's what the message says, right? Right?)
3) You need to fix that, by making sure that the user exists with the given password

Other things to look for:
1) Is the database owned by alumnodb or by postgres? If owned by postgres, change its owner (with ALTER DATABASE classicmodels OWNER TO alumnodb, or with one of the graphical clients). You'll need permissions to do that, which basically means that only the postgres user can do it.