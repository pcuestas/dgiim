#include "queries.h"

#define Q_SIZE 1000

/** Receives from the keyboard a product 
 * identfier and returns the number of 
 * items there are of that product.*/
int ProductsStock(){
    SQLHENV env;
    SQLHDBC dbc;
    SQLHSTMT stmt;

    SQLRETURN ret;       /* ODBC API return status */
    SQLINTEGER i;
    char x[512], query[Q_SIZE];
    int flag=0;

    /* Get the productcode */
    printf("\nEnter productcode > ");
    if(!fgets(x, sizeof(x), stdin)){
        return EXIT_FAILURE;
    }
    x[strlen(x)-1]=0;

    /* CONNECT */
    ret = odbc_connect(&env, &dbc);
    if (!SQL_SUCCEEDED(ret)) {
        return EXIT_FAILURE;
    }
    
    /* Allocate a statement handle */
    SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);

    /* Query: */
    sprintf(query, "SELECT quantityinstock FROM products WHERE productcode = '%s';",x);

    /* Make query*/
    SQLExecDirect(stmt, (SQLCHAR*) query, SQL_NTS);

    if (SQL_SUCCEEDED(ret = SQLFetch(stmt))) {
        ret = SQLGetData(stmt, 1, SQL_C_SLONG, &i, sizeof(SQLINTEGER), NULL);
        printf("Stock of product %s: %d\n\n", x, i);
    }else{
        printf("There are no products with the entered productcode.\n\n");
    }
    
    /* free up statement handle */
    SQLFreeHandle(SQL_HANDLE_STMT, stmt);

    /* DISCONNECT */
    ret = odbc_disconnect(env, dbc);
    if (!SQL_SUCCEEDED(ret)) {
        return EXIT_FAILURE;
    }

    return flag==1?EXIT_FAILURE:EXIT_SUCCESS;
}

int ProductsFind()
{
    SQLHENV env;
    SQLHDBC dbc;
    SQLHSTMT stmt;
    SQLRETURN ret;       /* ODBC API return status */
    SQLCHAR c1[512], c2[512], buf1[512],buf2[512];
    char q1[512], q2[512];
    char product[512];
    int flag = 0;

    ret = odbc_connect(&env, &dbc);
    if (!SQL_SUCCEEDED(ret))
    {
        return EXIT_FAILURE;
    }

    SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);

    printf("Enter productname > ");
    fflush(stdout);

    strcpy(q1, "select productcode, productname from products where productname like '%");
    strcpy(q2, "%' order by productcode");

    if (fgets(product, sizeof(product), stdin) != NULL)
    {
        product[strlen(product) - 1] = 0; /*Eliminate the \n from product*/
        strcat(q1, product);
        strcat(q1, q2);

        SQLExecDirect(stmt, (SQLCHAR *)q1, SQL_NTS);
        SQLBindCol(stmt, 1, SQL_C_CHAR, c1, sizeof(c1), NULL); /*Column productcode*/
        SQLBindCol(stmt, 2, SQL_C_CHAR, c2, sizeof(c2), NULL); /*Column productname*/

        SQLDescribeCol(stmt, 1, buf1, sizeof(buf1), NULL, NULL, NULL, NULL, NULL);
        SQLDescribeCol(stmt, 2, buf2, sizeof(buf2), NULL, NULL, NULL, NULL, NULL);
        printf("%s \t %s \n\n", buf1,buf2);
        while (SQL_SUCCEEDED(ret = SQLFetch(stmt)))
        {
            flag = 1;
            printf("%s \t %s\n", c1, c2);
        }

        if (flag == 0)
            printf("No products found");
    }

    printf("\n");

    SQLFreeHandle(SQL_HANDLE_STMT, stmt);

    ret = odbc_disconnect(env, dbc);
    if (!SQL_SUCCEEDED(ret))
        return EXIT_FAILURE;

    return EXIT_SUCCESS;
}

int OrdersOpen()
{
    SQLHENV env;
    SQLHDBC dbc;
    SQLHSTMT stmt;

    SQLRETURN ret;       /* ODBC API return status */
    SQLINTEGER x;
    SQLCHAR buf[512];

    ret = odbc_connect(&env, &dbc);
    if (!SQL_SUCCEEDED(ret))
    {
        return EXIT_FAILURE;
    }

    SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);

    SQLExecDirect(stmt, (SQLCHAR *)"select ordernumber from ORDERS where shippeddate is NULL order by ordernumber", SQL_NTS);

    SQLDescribeCol(stmt, 1, buf, sizeof(buf), NULL, NULL, NULL, NULL, NULL);
    printf("Orders that have not been sent yet: \n\n");
    printf("%s \n\n", buf);

    printf("\n");

    while (SQL_SUCCEEDED(ret = SQLFetch(stmt)))
    {
        ret = SQLGetData(stmt, 1, SQL_C_SLONG, &x, sizeof(SQLINTEGER), NULL);
        printf("%d\n", x);
    }

    printf("\n");

    ret = odbc_disconnect(env, dbc);
    if (!SQL_SUCCEEDED(ret))
    {
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}

int OrdersRange(){

    SQLHENV env;
    SQLHDBC dbc;
    SQLHSTMT stmt;

    SQLRETURN ret; /* ODBC API return status */
    SQLCHAR buf1[512],buf2[512],buf3[512],c1[512],c2[512],c3[512];
    char date[40],date1[11],date2[20],query[512],*aux;
    int flag=0;

    ret = odbc_connect(&env, &dbc);

    if (!SQL_SUCCEEDED(ret))
        return EXIT_FAILURE;
    
    SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);

    printf("Enter dates (YYYY-MM-DD - YYYY-MM-DD) > ");
    fflush(stdout);

    if (fgets(date, sizeof(date), stdin) != NULL){
        strncpy(date1,date,10);
        date1[10]=0; /*sin esta linea se copiaba mal date 1! no deberia*/
        aux=date+13; /*begining of second date*/
        strncpy(date2,aux,10);
        sprintf(query,"select ordernumber, orderdate, shippeddate from orders where orderdate>='%s' and orderdate<='%s' order by ordernumber",date1,date2);

        SQLExecDirect(stmt, (SQLCHAR *)query, SQL_NTS);
        SQLBindCol(stmt, 1, SQL_C_CHAR, c1, sizeof(c1), NULL); /*Column ordernumber*/
        SQLBindCol(stmt, 2, SQL_C_CHAR, c2, sizeof(c2), NULL); /*Column orderdate*/
        SQLBindCol(stmt, 3, SQL_C_CHAR, c3, sizeof(c3), NULL); /*Column shippeddate*/

        SQLDescribeCol(stmt, 1, buf1, sizeof(buf1), NULL, NULL, NULL, NULL, NULL);
        SQLDescribeCol(stmt, 2, buf2, sizeof(buf2), NULL, NULL, NULL, NULL, NULL);
        SQLDescribeCol(stmt, 3, buf3, sizeof(buf3), NULL, NULL, NULL, NULL, NULL);
        printf("%s \t %s \t %s \n\n", buf1, buf2,buf3); /*Print types of the results*/

        while (SQL_SUCCEEDED(ret = SQLFetch(stmt)))
        {
            flag = 1;
            printf("%s %s %s \n", c1, c2, c3);
        }

        if (flag == 0)
            printf("No products found\n");
    }

    printf("\n");

    SQLFreeHandle(SQL_HANDLE_STMT, stmt);

    ret = odbc_disconnect(env, dbc);
    if (!SQL_SUCCEEDED(ret))
        return EXIT_FAILURE;

    return EXIT_SUCCESS;
}

int OrdersDetails(){

    SQLHENV env;
    SQLHDBC dbc;
    SQLHSTMT stmt;

    SQLRETURN ret; /* ODBC API return status */
    SQLCHAR c1[512], c2[512], c3[512],c4[512],c5[512];
    char onumber[40],query[512];
    int flag = 0,i=0;

    ret = odbc_connect(&env, &dbc);

    if (!SQL_SUCCEEDED(ret))
        return EXIT_FAILURE;

    SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);
    /*SQLPrepare(stmt, (SQLCHAR *)"select status, orderdate, productcode,quantityordered,priceeach from orderdetails natural join orders where ordernumber=? order by orderlinenumber", SQL_NTS);*/

    printf("Enter ordernumber > ");
    fflush(stdout);

    
    if (fgets(onumber, sizeof(onumber), stdin) != NULL){
    /*if(scanf("%i",&x)!=EOF){*/
        
        /*SQLBindParameter(stmt, 1, SQL_PARAM_INPUT, SQL_C_SLONG, SQL_INTEGER, 0, 0, &x, 0, NULL);*/
        /*SQLExecute(stmt);*/

        /*onumber includes a \n but id doesnt affect the query*/
        sprintf(query, "select status, orderdate, productcode,quantityordered,priceeach from orderdetails natural join orders where ordernumber=%s order by orderlinenumber", onumber);
        SQLExecDirect(stmt, (SQLCHAR *)query, SQL_NTS);

       
        SQLBindCol(stmt, 1, SQL_C_CHAR, c1, sizeof(c1), NULL); /*Column status*/
        SQLBindCol(stmt, 2, SQL_C_CHAR, c2, sizeof(c2), NULL); /*Column orderdate*/
        SQLBindCol(stmt, 3, SQL_C_CHAR, c3, sizeof(c3), NULL); /*Column productocde*/
        SQLBindCol(stmt, 4, SQL_C_CHAR, c4, sizeof(c4), NULL); /*Column quantityordered*/
        SQLBindCol(stmt, 5, SQL_C_CHAR, c5, sizeof(c5), NULL); /*Column priceeach*/

        while (SQL_SUCCEEDED(ret = SQLFetch(stmt)))
        {   
            if(i==0){
                printf("%s %s \n",c2,c1); /*Print orderdate and staus (only once)*/
                i++;
            }

            flag = 1;
            printf("%s %s %s \n", c3, c4, c5); 
        }

        if (flag == 0)
            printf("No products found\n");
    }
    printf("\n");

    SQLFreeHandle(SQL_HANDLE_STMT, stmt);

    ret = odbc_disconnect(env, dbc);
    if (!SQL_SUCCEEDED(ret))
        return EXIT_FAILURE;

    return EXIT_SUCCESS;
}

int CustomersFind(){
    SQLHENV env;
    SQLHDBC dbc;
    SQLHSTMT stmt;

    SQLRETURN ret; /* ODBC API return status */
    SQLCHAR c1[512], c2[512], c3[512], c4[512];
    char name[40], query[512];
    int flag = 0;

    ret = odbc_connect(&env, &dbc);

    if (!SQL_SUCCEEDED(ret))
        return EXIT_FAILURE;

    SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);
    SQLPrepare(stmt, (SQLCHAR *)"select customername, contactfirstname,contactlastname, customernumber from customers where contactfirstname like '%?%' or contactlastname like '%?%' order by customernumber ", SQL_NTS);
    printf("Enter customer name > ");
    fflush(stdout);
    query[0]=0;

    if (fgets(name, sizeof(name), stdin) != NULL){

        /*Write the query*/
        name[strlen(name) - 1] = 0; /*Elminate the enter*/
        strcat(query, "select customername, contactfirstname,contactlastname, customernumber from customers where contactfirstname like '%");
        strcat(query, name);
        strcat(query, "%' or contactlastname like '%");
        strcat(query, name);
        strcat(query, "%' order by customernumber");

        SQLExecDirect(stmt, (SQLCHAR *)query, SQL_NTS);

        SQLBindCol(stmt, 1, SQL_C_CHAR, c1, sizeof(c1), NULL); /*Column customername*/
        SQLBindCol(stmt, 2, SQL_C_CHAR, c2, sizeof(c2), NULL); /*Column contactfirstname*/
        SQLBindCol(stmt, 3, SQL_C_CHAR, c3, sizeof(c3), NULL); /*Column contactlastname*/
        SQLBindCol(stmt, 4, SQL_C_CHAR, c4, sizeof(c4), NULL); /*Column customernumber*/

        while (SQL_SUCCEEDED(ret = SQLFetch(stmt))){

            flag = 1;
            printf("%s %s %s %s \n", c4, c1, c2, c3); /*Following the format in the .sh*/
        }

        if (flag == 0)
            printf("No clients found found\n");
    }

    printf("\n");

    SQLFreeHandle(SQL_HANDLE_STMT, stmt);

    ret = odbc_disconnect(env, dbc);
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
    SQLHENV env;
    SQLHDBC dbc;
    SQLHSTMT stmt;
    SQLRETURN ret; 
    SQLINTEGER x;
    SQLCHAR y[512];
    int flag = 0;
    

    
    ret = odbc_connect(&env, &dbc);
    if (!SQL_SUCCEEDED(ret)) {
        return EXIT_FAILURE;
    }

    
    SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);

    SQLPrepare(stmt, (SQLCHAR*)"SELECT productname, sum(quantityordered) \
                                FROM (((orders natural join customers) \
                                        natural join orderdetails) \
                                        natural join products) \
                                WHERE customernumber = ? \
                                GROUP BY productcode, productname \
                                ORDER BY productcode", SQL_NTS);

    printf("Enter customer number > ");
    fflush(stdout);
    scanf("%d", &x);

    SQLBindParameter(stmt, 1, SQL_PARAM_INPUT, SQL_C_SLONG, SQL_INTEGER, 0, 0, &x, 0, NULL);    
    
    /*execute the query*/
    SQLExecute(stmt);

    SQLBindCol(stmt, 1, SQL_C_CHAR, y, sizeof(y), NULL);
    SQLBindCol(stmt, 2, SQL_C_SLONG, &x, sizeof(SQLINTEGER), NULL);
    
    /*print the results*/
    while (SQL_SUCCEEDED(ret = SQLFetch(stmt))) {
        flag=1;
        printf("%s %d\n", y, x);
    }
    if(!flag){
        printf("No customer found.\n");
    }

    printf("\n");

    /*free the handle*/
    SQLFreeHandle(SQL_HANDLE_STMT, stmt);

    /*disconnect*/
    ret = odbc_disconnect(env, dbc);

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
    SQLHENV env;
    SQLHDBC dbc;
    SQLHSTMT stmt;
    SQLRETURN ret; 
    SQLINTEGER x;
    SQLDOUBLE balance;

    int flag = 0;
    
    ret = odbc_connect(&env, &dbc);
    if (!SQL_SUCCEEDED(ret)) {
        return EXIT_FAILURE;
    }

    SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);

    SQLPrepare(stmt, (SQLCHAR*)"SELECT ( y.paid - x.total ) AS Balance \
                                FROM   (SELECT Sum(priceeach * quantityordered) AS total \
                                        FROM   (orders \
                                                NATURAL JOIN orderdetails) \
                                        WHERE  customernumber = ?) AS x, \
                                    (SELECT Sum(amount) AS paid \
                                        FROM   payments \
                                        WHERE  customernumber = ?) AS y ", SQL_NTS);

    printf("Enter customer number > ");
    fflush(stdout);
    scanf("%d", &x);

    SQLBindParameter(stmt, 1, SQL_PARAM_INPUT, SQL_C_SLONG, SQL_INTEGER, 0, 0, &x, 0, NULL);   
    SQLBindParameter(stmt, 2, SQL_PARAM_INPUT, SQL_C_SLONG, SQL_INTEGER, 0, 0, &x, 0, NULL);    
 
    
    /*execute the query*/
    SQLExecute(stmt);

    /*SQLBindCol(stmt, 1, SQL_C_NUMERIC, &balance, sizeof(SQLNUMERIC), NULL);*/
    
    /*print the results*/
    if (SQL_SUCCEEDED(ret = SQLFetch(stmt))) {
        flag = 1;
        ret = SQLGetData(stmt, 1, SQL_C_DOUBLE, &balance, sizeof(SQLDOUBLE), NULL);
        printf("Balance = %f\n", balance);
    }
    if(!flag){
        printf("No customer found.\n");
    }

    printf("\n");

    /*free the handle*/
    SQLFreeHandle(SQL_HANDLE_STMT, stmt);

    /*disconnect*/
    ret = odbc_disconnect(env, dbc);

    if (!SQL_SUCCEEDED(ret)) {
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}

