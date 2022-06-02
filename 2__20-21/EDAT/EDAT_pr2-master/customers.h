#ifndef CUSTOMERS_H
#define CUSTOMERS_H

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sql.h>
#include <sqlext.h>
#include "odbc.h"


int CustomersFind();
int CustomersListProducts();
int CustomersBalance();


#endif