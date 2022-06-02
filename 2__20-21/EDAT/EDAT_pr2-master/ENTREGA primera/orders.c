#include "orders.h"

int OrdersOpen()
{
    SQLHENV env=NULL;
    SQLHDBC dbc=NULL;
    SQLHSTMT stmt=NULL;
    SQLRETURN ret=0; 
    SQLINTEGER x=0;
    static char buf[512];

    ret = (SQLRETURN)odbc_connect(&env, &dbc);

    if (!SQL_SUCCEEDED(ret))
         return EXIT_FAILURE;
    

    ret=SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);

    ret=SQLExecDirect(stmt, (SQLCHAR *)"select ordernumber from ORDERS where shippeddate is NULL order by ordernumber", SQL_NTS);
    /*No need to prepare the query as the user does not enter any data. No risk of injection*/

    ret=SQLDescribeCol(stmt, 1, (SQLCHAR*)buf, (SQLSMALLINT)sizeof(buf), NULL, NULL, NULL, NULL, NULL);
    printf("Orders that have not been sent yet: \n\n");
    printf("%s \n\n", buf);

    printf("\n");

    while (SQL_SUCCEEDED(ret = SQLFetch(stmt)))
    {
        ret = SQLGetData(stmt, 1, SQL_C_SLONG, &x, (SQLLEN)sizeof(SQLINTEGER), NULL);
        printf("%d\n", x);
    }

    printf("\n");

    ret = (SQLRETURN)odbc_disconnect(env, dbc);
    if (!SQL_SUCCEEDED(ret))
    {
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}

int OrdersRange(){
    SQLHENV env=NULL;
    SQLHDBC dbc=NULL;
    SQLHSTMT stmt=NULL;
    SQLRETURN ret=0; 
    static char buf1[512],buf2[512],buf3[512],c1[512],c2[512],c3[512];
    static char date1[10], date2[10];
    int flag=0;

    ret = (SQLRETURN)odbc_connect(&env, &dbc);

    if (!SQL_SUCCEEDED(ret))
        return EXIT_FAILURE;
    
    ret=SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);
    ret=SQLPrepare(stmt, (SQLCHAR *)"select ordernumber, orderdate, shippeddate from orders where orderdate>=? and orderdate<=? order by ordernumber", SQL_NTS);

    printf("Enter dates (YYYY-MM-DD - YYYY-MM-DD) > ");
    (void)fflush(stdout);

    if (scanf("%s - %s",date1,date2) == 2){
        ret=SQLBindParameter(stmt, 1, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_DATE, 0, 0, date1, 0, NULL);
        ret=SQLBindParameter(stmt, 2, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_DATE, 0, 0, date2, 0, NULL);

        ret=SQLExecute(stmt);

        ret=SQLBindCol(stmt, 1, SQL_C_CHAR, c1, (SQLLEN)sizeof(c1), NULL); /*Column ordernumber*/
        ret=SQLBindCol(stmt, 2, SQL_C_CHAR, c2, (SQLLEN)sizeof(c2), NULL); /*Column orderdate*/
        ret=SQLBindCol(stmt, 3, SQL_C_CHAR, c3, (SQLLEN)sizeof(c3), NULL); /*Column shippeddate*/

        ret=SQLDescribeCol(stmt, 1, (SQLCHAR*)buf1, (SQLSMALLINT)sizeof(buf1), NULL, NULL, NULL, NULL, NULL);
        ret=SQLDescribeCol(stmt, 2, (SQLCHAR*)buf2, (SQLSMALLINT)sizeof(buf2), NULL, NULL, NULL, NULL, NULL);
        ret=SQLDescribeCol(stmt, 3, (SQLCHAR*)buf3, (SQLSMALLINT)sizeof(buf3), NULL, NULL, NULL, NULL, NULL);
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

    ret = SQLFreeHandle(SQL_HANDLE_STMT, stmt);

    ret = (SQLRETURN)odbc_disconnect(env, dbc);
    if (!SQL_SUCCEEDED(ret))
        return EXIT_FAILURE;

    return EXIT_SUCCESS;
}

int OrdersDetails(){
    SQLHENV env=NULL;
    SQLHDBC dbc=NULL;
    SQLHSTMT stmt=NULL;
    SQLRETURN ret=0; 
    static char c1[512], c2[512], c3[512],c4[512],c5[512];
    int flag = 0,i=0,x=0;

    ret = (SQLRETURN)odbc_connect(&env, &dbc);

    if (!SQL_SUCCEEDED(ret))
        return EXIT_FAILURE;

    ret=SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);
    ret=SQLPrepare(stmt, (SQLCHAR *)"select status, orderdate, productcode,quantityordered,priceeach from orderdetails natural join orders where ordernumber=? order by orderlinenumber", SQL_NTS);

    printf("Enter ordernumber > ");
    (void)fflush(stdout);

    
    /*if (fgets(onumber, sizeof(onumber), stdin) != NULL){*/
    if(scanf("%d",&x)==1){
        
        ret=SQLBindParameter(stmt, 1, SQL_PARAM_INPUT, SQL_C_SLONG, SQL_INTEGER, 0, 0, &x, 0, NULL);
        ret=SQLExecute(stmt);

        /*onumber includes a \n but id doesnt affect the query
        sprintf(query, "select status, orderdate, productcode,quantityordered,priceeach from orderdetails natural join orders where ordernumber=%s order by orderlinenumber", onumber);*/
        /*SQLExecDirect(stmt, (SQLCHAR *)query, SQL_NTS);*/

       
        ret=SQLBindCol(stmt, 1, SQL_C_CHAR, c1, (SQLLEN)sizeof(c1), NULL); /*Column status*/
        ret=SQLBindCol(stmt, 2, SQL_C_CHAR, c2, (SQLLEN)sizeof(c2), NULL); /*Column orderdate*/
        ret=SQLBindCol(stmt, 3, SQL_C_CHAR, c3, (SQLLEN)sizeof(c3), NULL); /*Column productocde*/
        ret=SQLBindCol(stmt, 4, SQL_C_CHAR, c4, (SQLLEN)sizeof(c4), NULL); /*Column quantityordered*/
        ret=SQLBindCol(stmt, 5, SQL_C_CHAR, c5, (SQLLEN)sizeof(c5), NULL); /*Column priceeach*/

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

    ret = SQLFreeHandle(SQL_HANDLE_STMT, stmt);

    ret = (SQLRETURN)odbc_disconnect(env, dbc);
    if (!SQL_SUCCEEDED(ret))
        return EXIT_FAILURE;

    return EXIT_SUCCESS;
}
