# Version 1.0
# Por lo general no se requiere modificar el fichero

export PGDATABASE:=classicmodels
export PGUSER :=alumnodb
export PGPASSWORD :=alumnodb
export PGCLIENTENCODING:=LATIN9
export PGHOST:=localhost

DBNAME =$(PGDATABASE)
PSQL = psql
CREATEDB = createdb
DROPDB = dropdb --if-exists
PG_DUMP = pg_dump
PG_RESTORE = pg_restore

all: dropdb createdb restore shell
newdatabase: dropdb createdb restorenewdatabase shell
restorenewdatabase:
	@echo restore the modified data base
	@cat newdatabase.sql | $(PSQL) 

createdb:
	@echo Creando BBDD
	@$(CREATEDB)
dropdb:
	@echo Eliminando BBDD
	@$(DROPDB) $(DBNAME)
	rm -f *.log
dump:
	@echo creando dumpfile
	@$(PG_DUMP) > $(DBNAME).sql
restore:
	@echo restore data base
	@cat $(DBNAME).sql | $(PSQL)  
psql: shell
shell:
	@echo create psql shell
	@$(PSQL)  

allqueries: query1 query2 query3 query4 query5 query6
	@cat query?.log > all_queries.log

query1:
	@echo query-1: "Total amount of money paid by the customers that have ordered the “1940 Ford Pickup Truck”. Ordered by descending order." | tee query1.log
	@cat query1.sql | $(PSQL) | tee -a query1.log
query2:
	@echo query-2: "Average time (measured in days) between order date and ship date for each product line." | tee query2.log
	@cat query2.sql | $(PSQL) | tee -a query2.log
query3:
	@echo query3: "Employees who report to those employees who report to the director." | tee query3.log
	@cat query3.sql | $(PSQL) | tee -a query3.log
query4:
	@echo query4: "Office that sold most products (number of items)." | tee query4.log
	@cat query4.sql | $(PSQL) | tee -a query4.log
query5:
	@echo query5: "Countries with one (or more) offices that did not sell a single item during the year 2003." | tee query5.log
	@cat query5.sql | $(PSQL) | tee -a query5.log
query6:
	@echo query6: "Pairs of products that have been purchased together (in the same order) and amount of times this has happened for each pair." | tee query6.log
	@cat query6.sql | $(PSQL) | tee -a query6.log

