#include "customers.h"

int CustomersFind(){
    SQLHENV env=NULL;
    SQLHDBC dbc=NULL;
    SQLHSTMT stmt=NULL;

    SQLRETURN ret=0; /* ODBC API return status */
    static char c1[512], c2[512], c3[512], c4[512];
    char name[512];
    int flag = 0;

    ret = (SQLRETURN)odbc_connect(&env, &dbc);

    if (!SQL_SUCCEEDED(ret))
        return EXIT_FAILURE;

    ret=SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);
    ret=SQLPrepare(stmt, (SQLCHAR *)"select customername, contactfirstname,contactlastname, customernumber \
                                from customers \
                                where contactfirstname like '%'||?||'%' \
                                    or contactlastname like '%'||?||'%' \
                                order by customernumber ", SQL_NTS);
    printf("Enter customer name > ");
    (void)fflush(stdout);

    if (scanf("%s", name)!=0){

        ret=SQLBindParameter(stmt, 1, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_CHAR, 0, 0, name, 0, NULL);
        ret=SQLBindParameter(stmt, 2, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_CHAR, 0, 0, name, 0, NULL);
  
        ret=SQLExecute(stmt);

        ret=SQLBindCol(stmt, 1, SQL_C_CHAR, c1, (SQLLEN)sizeof(c1), NULL); /*Column customername*/
        ret=SQLBindCol(stmt, 2, SQL_C_CHAR, c2, (SQLLEN)sizeof(c2), NULL); /*Column contactfirstname*/
        ret=SQLBindCol(stmt, 3, SQL_C_CHAR, c3, (SQLLEN)sizeof(c3), NULL); /*Column contactlastname*/
        ret=SQLBindCol(stmt, 4, SQL_C_CHAR, c4, (SQLLEN)sizeof(c4), NULL); /*Column customernumber*/

        while (SQL_SUCCEEDED(ret = SQLFetch(stmt))){

            flag = 1;
            printf("%s %s %s %s \n", c4, c1, c2, c3); /*Following the format in the .sh*/
        }

        if (flag == 0)
            printf("No clients found found\n");
    }

    printf("\n");

    ret=SQLFreeHandle(SQL_HANDLE_STMT, stmt);

    ret = (SQLRETURN)odbc_disconnect(env, dbc);
    if (!SQL_SUCCEEDED(ret))
        return EXIT_FAILURE;

    return EXIT_SUCCESS;
}



/**
 * This function makes the following query, 
 * using the customernumber entered by the user
 * 
 * SELECT productname, sum(quantityordered)
 * FROM (((orders natural join customers)
 *      natural join orderdetails)
 *      natural join products)
 * WHERE customernumber = ?
 * GROUP BY productcode, productname
 * ORDER BY productcode
*/
int CustomersListProducts(){
    SQLHENV env=NULL;
    SQLHDBC dbc=NULL;
    SQLHSTMT stmt=NULL;
    SQLRETURN ret=0; 
    SQLINTEGER x=0;
    static char y[512];
    int flag = 0;
    static char name1[100], name2[100];
    
    ret = (SQLRETURN)odbc_connect(&env, &dbc);
    if (!SQL_SUCCEEDED(ret)) {
        return EXIT_FAILURE;
    }
    
    ret=SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);

    ret=SQLPrepare(stmt, (SQLCHAR*)"SELECT productname, sum(quantityordered) as total_number_of_units \
                                FROM (((orders natural join customers) \
                                        natural join orderdetails) \
                                        natural join products) \
                                WHERE customernumber = ? \
                                GROUP BY productcode, productname \
                                ORDER BY productcode;", SQL_NTS);

    printf("Enter customer number > ");
    (void)fflush(stdout);
    if(scanf("%d", &x)!=0){

        ret=SQLBindParameter(stmt, 1, SQL_PARAM_INPUT, SQL_C_SLONG, SQL_INTEGER, 0, 0, &x, 0, NULL);    
	    
        /*execute the query*/
        ret=SQLExecute(stmt);

        ret=SQLDescribeCol(stmt, 1, (SQLCHAR*)name1, (SQLSMALLINT)sizeof(name1), NULL, NULL, NULL, NULL, NULL);
        ret=SQLDescribeCol(stmt, 2, (SQLCHAR*)name2, (SQLSMALLINT)sizeof(name2), NULL, NULL, NULL, NULL, NULL);

        ret=SQLBindCol(stmt, 1, SQL_C_CHAR, y, (SQLLEN)sizeof(y), NULL);
        ret=SQLBindCol(stmt, 2, SQL_C_SLONG, &x, (SQLLEN)sizeof(SQLINTEGER), NULL);
        
        /*print the column names*/
        printf("%s\t%s\n", name1, name2);

        /*print the results*/
        while (SQL_SUCCEEDED(ret = SQLFetch(stmt))) {
		flag=1;
		printf("%s %d\n", y, x);
        }
        if(flag==0){
		printf("No customer found.\n");
        }

        printf("\n");
    } 

    /*free the handle*/
    ret=SQLFreeHandle(SQL_HANDLE_STMT, stmt);

    /*disconnect*/
    ret = (SQLRETURN)odbc_disconnect(env, dbc);

    if (!SQL_SUCCEEDED(ret)) {
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}


/**
 * Makes the following query, 
 * using the customernumber entered by the user
SELECT ( y.paid - x.total ) AS Balance
FROM   (SELECT Sum(priceeach * quantityordered) AS total 
        FROM   (orders 
                natural JOIN orderdetails) 
        WHERE  customernumber = ?) AS x, 
       (SELECT Sum(amount) AS paid 
        FROM   payments 
        WHERE  customernumber = ?) AS y 
*/

int CustomersBalance(){
    SQLHENV env=NULL;
    SQLHDBC dbc=NULL;
    SQLHSTMT stmt=NULL;
    SQLRETURN ret=0; 
    SQLINTEGER x=0;
    SQLDOUBLE balance=0;

    int flag = 0;
    
    ret = (SQLRETURN)odbc_connect(&env, &dbc);
    if (!SQL_SUCCEEDED(ret)) {
        return EXIT_FAILURE;
    }

    ret=SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);

    ret=SQLPrepare(stmt, (SQLCHAR*)"SELECT ( y.paid - x.total ) AS Balance \
                                FROM   (SELECT Sum(priceeach * quantityordered) AS total \
                                        FROM   (orders NATURAL JOIN orderdetails) \
                                        WHERE  customernumber = ?) AS x, \
                                    (SELECT Sum(amount) AS paid \
                                        FROM   payments \
                                        WHERE  customernumber = ?) AS y ", ret=SQL_NTS);

    printf("Enter customer number > ");
    (void)fflush(stdout);
    if(scanf("%d", &x)!=0){

        ret=SQLBindParameter(stmt, 1, SQL_PARAM_INPUT, SQL_C_SLONG, SQL_INTEGER, 0, 0, &x, 0, NULL);   
        ret=SQLBindParameter(stmt, 2, SQL_PARAM_INPUT, SQL_C_SLONG, SQL_INTEGER, 0, 0, &x, 0, NULL);    
 
    
         /*execute the query*/
        ret=SQLExecute(stmt);

        /*print the results*/
        if (SQL_SUCCEEDED(ret = SQLFetch(stmt))) {
            flag = 1;
            ret = SQLGetData(stmt, 1, SQL_C_DOUBLE, &balance, (SQLLEN)sizeof(SQLDOUBLE), NULL);
            printf("Balance = %f\n", balance);
        }
        if(flag==0){
            printf("No customer found.\n");
        }

        printf("\n");
    }

    /*free the handle*/
    ret=SQLFreeHandle(SQL_HANDLE_STMT, stmt);

    /*disconnect*/
    ret = (SQLRETURN)odbc_disconnect(env, dbc);

    if (!SQL_SUCCEEDED(ret)) {
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}

