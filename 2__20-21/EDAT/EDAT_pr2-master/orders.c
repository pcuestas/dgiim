#include "orders.h"
#include "string.h"

/*Checks if an array has the format YYYY-MM-DD*/
static int check_date(char *c){
    int i=0, num[8]={0,1,2,3,5,6,8,9}, len=0;
    
    if(c==NULL)
        return -1;

    len=(int)strlen(c);
    if(len!=10){
        return -1;
    }
        
    for(i=0;i<8 && i<len;i++){
        if(c[num[i]]<'0' || c[num[i]]>'9'){
            return -1;
        }
    }

    if(c[4]!='-' || c[7]!='-')
        return -1;

    return 0;
}

int OrdersOpen(){
    SQLHENV env=NULL;
    SQLHDBC dbc=NULL;
    SQLHSTMT stmt=NULL;
    SQLRETURN ret=0; 
    SQLINTEGER x=0;
    static char buf[512];
    int flag=0;

    ret = (SQLRETURN)odbc_connect(&env, &dbc);

    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        return EXIT_FAILURE;
    }

    ret=SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }

    ret=SQLExecDirect(stmt, (SQLCHAR *)"SELECT ordernumber FROM orders WHERE shippeddate is NULL ORDER by ordernumber;", SQL_NTS);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }
    /*No need to prepare the query as the user does not enter any data. No risk of injection*/

    ret=SQLDescribeCol(stmt, 1, (SQLCHAR*)buf, (SQLSMALLINT)sizeof(buf), NULL, NULL, NULL, NULL, NULL);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }
    ret=SQLBindCol(stmt, 1, SQL_C_SLONG, &x, (SQLLEN)sizeof(SQLINTEGER), NULL);
    

    while (SQL_SUCCEEDED(ret = SQLFetch(stmt))){
       
        if(flag==0){
            printf("Orders that have not been sent yet: \n");
            printf("%s \n", buf);
            flag=1;
        }
        printf("%d\n", x);
    }
    if(flag==0){
        printf("\nAll of the ordes have been sent.\n");
    }

    printf("\n");

    ret = SQLFreeHandle(SQL_HANDLE_STMT, stmt);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }

    ret = (SQLRETURN)odbc_disconnect(env, dbc);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
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
    int flag=0, ret2=0;
    SQLLEN bind_col=0;
    

    ret = (SQLRETURN)odbc_connect(&env, &dbc);

    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        return EXIT_FAILURE;
    }
    
    ret=SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }
    ret=SQLPrepare(stmt, (SQLCHAR *)"SELECT ordernumber, orderdate, shippeddate FROM orders WHERE orderdate>=? and orderdate<=? ORDER by ordernumber;", SQL_NTS);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }
    printf("Enter dates (YYYY-MM-DD - YYYY-MM-DD) > ");
    ret2=scanf("%s - %s",date1,date2);
    
    /*check if the strings read have format of dates (although they may make no sense)*/
    while (check_date(date1) == -1 || check_date(date2) == -1){
        printf("Please enter a valid date.\n");
        printf("Enter dates (YYYY-MM-DD - YYYY-MM-DD) > ");
        ret2=scanf("%s - %s",date1,date2);
    }

    if (ret2 == 2){
        ret=SQLBindParameter(stmt, 1, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_DATE, 0, 0, date1, 0, NULL);
        if (!SQL_SUCCEEDED(ret)){
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLBindParameter(stmt, 2, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_DATE, 0, 0, date2, 0, NULL);
        if (!SQL_SUCCEEDED(ret)){
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }

        ret=SQLExecute(stmt);
        if (!SQL_SUCCEEDED(ret)){
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }

        ret=SQLBindCol(stmt, 1, SQL_C_CHAR, c1, (SQLLEN)sizeof(c1), NULL); /*Column ordernumber*/
        if (!SQL_SUCCEEDED(ret)){
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLBindCol(stmt, 2, SQL_C_CHAR, c2, (SQLLEN)sizeof(c2), NULL); /*Column orderdate*/
        if (!SQL_SUCCEEDED(ret)){
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLBindCol(stmt, 3, SQL_C_CHAR, c3, (SQLLEN)sizeof(c3), &bind_col); /** Column shippeddate, could be NULL, 
                                                                                  * so we use the bind_col parameter to later check*/
        if (!SQL_SUCCEEDED(ret)){
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }

        ret=SQLDescribeCol(stmt, 1, (SQLCHAR*)buf1, (SQLSMALLINT)sizeof(buf1), NULL, NULL, NULL, NULL, NULL);
        if (!SQL_SUCCEEDED(ret)){
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLDescribeCol(stmt, 2, (SQLCHAR*)buf2, (SQLSMALLINT)sizeof(buf2), NULL, NULL, NULL, NULL, NULL);
        if (!SQL_SUCCEEDED(ret)){
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLDescribeCol(stmt, 3, (SQLCHAR*)buf3, (SQLSMALLINT)sizeof(buf3), NULL, NULL, NULL, NULL, NULL);
        if (!SQL_SUCCEEDED(ret)){
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        

        while (SQL_SUCCEEDED(ret = SQLFetch(stmt))){
            if(flag==0){
                printf("%s \t %s \t %s \n\n", buf1, buf2, buf3); /*Print types of the results*/
                flag=1;
            }
            printf("%s %s %s \n", c1, c2, bind_col!=SQL_NULL_DATA?c3:"-not shipped-"); /** Checking that shippeddate has no NULL value. 
                                                                                         * The other attributes have constraint NOT NULL
                                                                                         * so they cannot be null
                                                                                         **/
        }

        if (flag == 0)
            printf("No products found\n");
    }

    printf("\n");

    ret = SQLFreeHandle(SQL_HANDLE_STMT, stmt);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }

    ret = (SQLRETURN)odbc_disconnect(env, dbc);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}

int OrdersDetails(){
    SQLHENV env=NULL;
    SQLHDBC dbc=NULL;
    SQLHSTMT stmt=NULL, stmt1=NULL;
    SQLRETURN ret=0; 
    static char c1[512], c2[512], c3[512],c4[512],c5[512];
    int flag = 0,i=0,x=0;
    SQLDOUBLE sum=0;


    ret = (SQLRETURN)odbc_connect(&env, &dbc);

    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        return EXIT_FAILURE;
    }

    ret=SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }
    ret=SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt1);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }

    ret=SQLPrepare(stmt1, (SQLCHAR*)"SELECT Sum(price) AS sum \
                                    FROM   (SELECT ( priceeach * quantityordered ) AS price \
                                            FROM   orderdetails \
                                                natural JOIN orders \
                                            WHERE  ordernumber = ? \
                                            ORDER  BY orderlinenumber) AS x;", SQL_NTS);

    ret=SQLPrepare(stmt, (SQLCHAR *)"SELECT status, orderdate, productcode, quantityordered, priceeach  \
                                    FROM    orderdetails natural JOIN orders \
                                    WHERE  ordernumber = ?  \
                                    ORDER  BY orderlinenumber;", SQL_NTS);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }

    printf("Enter ordernumber > ");
    (void)fflush(stdout);


    if(scanf("%d",&x)==1){
        
        ret=SQLBindParameter(stmt, 1, SQL_PARAM_INPUT, SQL_C_SLONG, SQL_INTEGER, 0, 0, &x, 0, NULL);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLBindParameter(stmt1, 1, SQL_PARAM_INPUT, SQL_C_SLONG, SQL_INTEGER, 0, 0, &x, 0, NULL);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }

        ret=SQLExecute(stmt);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLExecute(stmt1);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }

        ret=SQLBindCol(stmt, 1, SQL_C_CHAR, c1, (SQLLEN)sizeof(c1), NULL); /*Column status*/
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLBindCol(stmt, 2, SQL_C_CHAR, c2, (SQLLEN)sizeof(c2), NULL); /*Column orderdate*/
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLBindCol(stmt, 3, SQL_C_CHAR, c3, (SQLLEN)sizeof(c3), NULL); /*Column productocde*/
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLBindCol(stmt, 4, SQL_C_CHAR, c4, (SQLLEN)sizeof(c4), NULL); /*Column quantityordered*/
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLBindCol(stmt, 5, SQL_C_CHAR, c5, (SQLLEN)sizeof(c5), NULL); /*Column priceeach*/
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLBindCol(stmt1, 1, SQL_C_DOUBLE, &sum, (SQLLEN)sizeof(SQLDOUBLE), NULL); /*Column sum*/
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }

        if (!SQL_SUCCEEDED(ret = SQLFetch(stmt1))){
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        else{
                while (SQL_SUCCEEDED(ret = SQLFetch(stmt))){   
                    if(i==0){
                        printf("Order Date=%s - status=%s \n",c2,c1); /*Print orderdate and staus (only once)*/
                        printf("Total sum = %f\n", sum);
                        i++;
                        flag = 1;
                    }

                    printf("%s %s %s \n", c3, c4, c5); 
                }
        }

        if (flag == 0)
            printf("\nNo products found\n");
    }
    printf("\n");

    ret = SQLFreeHandle(SQL_HANDLE_STMT, stmt);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }
    ret = SQLFreeHandle(SQL_HANDLE_STMT, stmt1);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }

    ret = (SQLRETURN)odbc_disconnect(env, dbc);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}
