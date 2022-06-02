#ifndef ORDERS_H
#define ORDERS_H

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sql.h>
#include <sqlext.h>
#include "odbc.h"

int OrdersOpen();
int OrdersRange();
int OrdersDetails();


#endif