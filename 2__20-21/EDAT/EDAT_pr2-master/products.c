#include "products.h"

#define Q_SIZE 1000

/** Receives from the keyboard a product 
 * identfier and returns the number of 
 * items there are of that product.*/

int ProductsStock(){
    SQLHENV env=NULL;
    SQLHDBC dbc=NULL;
    SQLHSTMT stmt=NULL;
    SQLRETURN ret=0;    /* ODBC API return status */
    static char buf1[512], c1[512], pc[512];    

    /* Get the productcode */
    printf("\nEnter productcode > ");
    (void)fflush(stdout);
    if(scanf("%s",pc)==0){
        return EXIT_FAILURE;
    }
    

    /* CONNECT */
    ret = (SQLRETURN)odbc_connect(&env, &dbc);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        return EXIT_FAILURE;
    }   

    ret=SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    } 
    ret=SQLPrepare(stmt, (SQLCHAR *)"SELECT quantityinstock FROM products WHERE productcode = ?;", SQL_NTS);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    } 
    ret=SQLBindParameter(stmt, 1, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_CHAR, 0, 0, pc, 0, NULL);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    } 
   
    ret=SQLExecute(stmt);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    } 

    ret=SQLBindCol(stmt, 1, SQL_C_CHAR, (SQLCHAR*)c1, (SQLLEN)sizeof(c1), NULL);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    } 
    ret=SQLDescribeCol(stmt, 1, (SQLCHAR*)buf1, (SQLSMALLINT)sizeof(buf1), NULL, NULL, NULL, NULL, NULL);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    } 
    
    
    if (SQL_SUCCEEDED(ret = SQLFetch(stmt))) {
        printf("%s \n",buf1);/*print the name of the column*/
        printf("%s \n",c1);/*print the value of the column (quantity in stock)*/
    }else{
        printf("\nThere are no products with the entered productcode.\n\n");
    }
    
    /* free up statement handle */
    ret=SQLFreeHandle(SQL_HANDLE_STMT, stmt);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    } 

    /* DISCONNECT */
    ret = (SQLRETURN)odbc_disconnect(env, dbc);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}

int ProductsFind(){
    SQLHENV env=NULL;
    SQLHDBC dbc=NULL;
    SQLHSTMT stmt=NULL;
    SQLRETURN ret=0;     /* ODBC API return status */
    static char c1[512], c2[512], buf1[512], buf2[512], product[512];
    int flag = 0;

    ret = (SQLRETURN)odbc_connect(&env, &dbc);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        return EXIT_FAILURE;
    }

    ret=SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }

    printf("Enter productname > ");
    (void)fflush(stdout);

    ret=SQLPrepare(stmt, (SQLCHAR *)"SELECT productcode, productname FROM products WHERE productname like '%'||?||'%';", SQL_NTS);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }

    if (scanf("%s", product)== 1){

        ret=SQLBindParameter(stmt, 1, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_CHAR, 0, 0, product, 0, NULL);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLExecute(stmt);
        ret=SQLBindCol(stmt, 1, SQL_C_CHAR, c1, (SQLLEN)sizeof(c1), NULL); /*Column productcode*/
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLBindCol(stmt, 2, SQL_C_CHAR, c2, (SQLLEN)sizeof(c2), NULL); /*Column productname*/
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }

        ret=SQLDescribeCol(stmt, 1, (SQLCHAR*)buf1, (SQLSMALLINT)sizeof(buf1), NULL, NULL, NULL, NULL, NULL);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLDescribeCol(stmt, 2, (SQLCHAR*)buf2, (SQLSMALLINT)sizeof(buf2), NULL, NULL, NULL, NULL, NULL);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
       
        while (SQL_SUCCEEDED(ret = SQLFetch(stmt))){
            if(flag==0){
                printf("%s \t %s \n\n", buf1,buf2);
                flag = 1;
            }
            printf("%s \t %s\n", c1, c2);
        }

        if (flag == 0)
            printf("\nNo products found.\n");
    }

    printf("\n");

    ret=SQLFreeHandle(SQL_HANDLE_STMT, stmt);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }

    ret = (SQLRETURN)odbc_disconnect(env, dbc);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}
