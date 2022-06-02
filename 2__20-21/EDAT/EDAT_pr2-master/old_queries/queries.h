#ifndef QUERIES_H
#define QUERIES_H

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sql.h>
#include <sqlext.h>
#include "odbc.h"

int ProductsStock();
int ProductsFind();

int OrdersOpen();
int OrdersRange();
int OrdersDetails();

int CustomersFind();
int CustomersListProducts();
int CustomersBalance();


#endif