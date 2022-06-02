#include "customers.h"

#include <string.h>
#include <stdio.h>

extern char* strdup(const char*);
extern int snprintf(char *, size_t, const char*,...);

#define LISTP 2
#define FIND 1

/*Functions for fetching and paging results*/

/*@null@*/ static char **fetch_and_store(SQLHSTMT stmt, int *count, void **p, int *error, int functsel){
    char **results=NULL;
    int size=0, i=0, flag=0, ret2=0;
    char line[600];
    SQLRETURN ret=0;
    
    if(!count||!p||!error){
        return NULL;
    }

    (*error) = 0;
    (*count) = 0;
    
    while (SQL_SUCCEEDED(ret = SQLFetch(stmt))) {
        flag=1;
        if(size==0){
            if ((results = (char **)calloc(10, sizeof(char *))) == NULL){
                (*error) = 1;
                break;
            }
            size=10;
        }else if(size==(*count)){
            size*=4;
            if((results=(char**)realloc(results, size*sizeof(char*)))==NULL){
                (*error) = 1;
                break;
            }
        }
        if(functsel==LISTP){ /*format of CustomersListProducts*/
            ret2=snprintf(line, 600, "%s %d", (char*)p[0], *((int*)p[1]));
        }else{/*format of CustomersFind*/
            ret2=snprintf(line, 600, "%s %s %s %s", (char*)p[3], (char*)p[0], (char*)p[1], (char*)p[2]); /*Following the format in the .sh*/
        }
        if(ret2==-1){
            /*somethig went wrong with sprintf*/
            (*error) = 1;
            break;
        }
        if(results!=NULL)/*this check is for splint (we could not solve it any other way)*/
            results[*count]=strdup(line);/*allocates memory and dupilcates the string*/

        (*count)++;
    }
    /*if there is a error or there are no results*/
    if((*error)==1 || flag==0){
        if(results!=NULL){
            for(i=0;i<(*count);i++){
                free(results[*count]);
            }
            free(results);
        }
        (*count)=0;
        return NULL;
    }
    else{
        return results;
    }
}

static void page_results(char **results, int count){
    int page=0, npages=0, i=0, flag=0;
    char c='\0';
    
    for(i=0;i<10 && i<count;i++){
        printf("%s\n", results[i]);
        page=0;
    }
    if(count >10){ /*there is more than one page (if there is not, no paging is used)*/
        flag=1;

        npages = count / 10;
        if (count % 10 != 0)
            npages++;

        printf("Page %i of %i",page+1,npages);
        printf("\nUse '>' for next results and '<' for the previous, and q for quitting: ");
    }

    if(flag==1){ /*More than one page to show, flag used as a check*/
        while(1==1){ /*this ugly syntax is for splint*/
            
            (void)scanf("%c", &c);
            if(c=='q'){
                break;
            }else if(c=='>' && page<npages-1){
                page++;
            }else if(c=='<' && page>0){
                page--;
            }

            printf("%c[2J",27); /*Clean screen*/
            printf("%c[1;1H",27); /*Positionates cursor in postition 1:1*/

            for(i=0;i<10 && (page*10+i)<count;i++){
                printf("%s\n", results[page*10+i]); /*Prints the page*/
            }
            printf("\nPage %i of %i", page + 1, npages);
            printf("\nUse '>' for next results and '<' for the previous, and q for quitting: ");
                    
        }      
    }
}

/*customer.h functions: */

int CustomersFind(){
    SQLHENV env=NULL;
    SQLHDBC dbc=NULL;
    SQLHSTMT stmt=NULL;

    SQLRETURN ret=0; /* ODBC API return status */
    static char c1[512], c2[512], c3[512], c4[512], name1[512], name2[512], name3[512], name4[512];
    char name[512], **results=NULL;
    int error=0, count=0, i=0;
    static void *p[4];

    ret = (SQLRETURN)odbc_connect(&env, &dbc);
    
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        return EXIT_FAILURE;
    }
    
    ret=SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }
    
    ret=SQLPrepare(stmt, (SQLCHAR *)"SELECT customername, contactfirstname, contactlastname, customernumber \
                                FROM customers \
                                WHERE contactfirstname like '%'||?||'%' \
                                    or contactlastname like '%'||?||'%' \
                                ORDER BY customernumber ;", SQL_NTS);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }
    printf("Enter customer name > ");
    (void)fflush(stdout);

    if (scanf("%s", name)!=0){

        ret=SQLBindParameter(stmt, 1, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_CHAR, 0, 0, name, 0, NULL);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLBindParameter(stmt, 2, SQL_PARAM_INPUT, SQL_C_CHAR, SQL_CHAR, 0, 0, name, 0, NULL);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLExecute(stmt);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }

        /*take the name of the columns*/
        ret=SQLDescribeCol(stmt, 1, (SQLCHAR*)name1, (SQLSMALLINT)sizeof(name1), NULL, NULL, NULL, NULL, NULL);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }    
        ret=SQLDescribeCol(stmt, 2, (SQLCHAR*)name2, (SQLSMALLINT)sizeof(name2), NULL, NULL, NULL, NULL, NULL);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }    
        ret=SQLDescribeCol(stmt, 3, (SQLCHAR*)name3, (SQLSMALLINT)sizeof(name1), NULL, NULL, NULL, NULL, NULL);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }    
        ret=SQLDescribeCol(stmt, 4, (SQLCHAR*)name4, (SQLSMALLINT)sizeof(name2), NULL, NULL, NULL, NULL, NULL);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }

        ret=SQLBindCol(stmt, 1, SQL_C_CHAR, c1, (SQLLEN)sizeof(c1), NULL); /*Column customername*/
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLBindCol(stmt, 2, SQL_C_CHAR, c2, (SQLLEN)sizeof(c2), NULL); /*Column contactfirstname*/
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLBindCol(stmt, 3, SQL_C_CHAR, c3, (SQLLEN)sizeof(c3), NULL); /*Column contactlastname*/
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        ret=SQLBindCol(stmt, 4, SQL_C_CHAR, c4, (SQLLEN)sizeof(c4), NULL); /*Column customernumber*/
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }

        p[0]=(void*)c1;
        p[1]=(void*)c2;
        p[2]=(void*)c3;
        p[3]=(void*)c4;

        /*fetch and store the results*/
        results=fetch_and_store(stmt, &count, p, &error, FIND);
        
        if(results==NULL && error==0){
            /*this means that there is no results, because there was not an error*/
		    printf("\nNo clients found.\n");
        }else if(error==0){
            /*print the column names*/
            printf("%s\t%s\t%s\t%s\n", name4, name1, name2, name3);
            /*page the results: */
            page_results(results, count);
        }
        printf("\n");
    }
    if(results!=NULL){
        for(i=0;i<count;i++){
            free(results[i]);
        }
        free(results);
    }
    /*free the handle*/
    ret=SQLFreeHandle(SQL_HANDLE_STMT, stmt);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }

    /*disconnect*/
    ret = (SQLRETURN)odbc_disconnect(env, dbc);
    if (!SQL_SUCCEEDED(ret)){
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        return EXIT_FAILURE;
    }

    if(error==1){
        /*this means there was an error in the (re)allocation of memory for the results*/
        return EXIT_FAILURE;
    }

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
    int error = 0, count = 0, i = 0;
    static char name1[100], name2[100];
    char **results=NULL;
    /*@null@*/ void *p[2];
    
    ret = (SQLRETURN)odbc_connect(&env, &dbc);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        return EXIT_FAILURE;
    }
    
    ret=SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }

    ret=SQLPrepare(stmt, (SQLCHAR*)"SELECT productname, sum(quantityordered) as total_number_of_units \
                                FROM    orders natural join customers \
                                        natural join orderdetails \
                                        natural join products \
                                WHERE customernumber = ? \
                                GROUP BY productcode, productname \
                                ORDER BY productcode;", SQL_NTS);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }

    printf("Enter customer number > ");
    (void)fflush(stdout);
    if(scanf("%d", &x)!=0){

        ret=SQLBindParameter(stmt, 1, SQL_PARAM_INPUT, SQL_C_SLONG, SQL_INTEGER, 0, 0, &x, 0, NULL);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }    
	    
        /*execute the query*/
        ret=SQLExecute(stmt);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }    

        ret=SQLDescribeCol(stmt, 1, (SQLCHAR*)name1, (SQLSMALLINT)sizeof(name1), NULL, NULL, NULL, NULL, NULL);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }    
        ret=SQLDescribeCol(stmt, 2, (SQLCHAR*)name2, (SQLSMALLINT)sizeof(name2), NULL, NULL, NULL, NULL, NULL);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }    

        ret=SQLBindCol(stmt, 1, SQL_C_CHAR, y, (SQLLEN)sizeof(y), NULL);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }    
        ret=SQLBindCol(stmt, 2, SQL_C_SLONG, &x, (SQLLEN)sizeof(SQLINTEGER), NULL);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }
        if(p!=NULL){/*this check is for splint (we could not solve it any other way)*/
            p[0]=(void*)y;
            p[1]=(void*)(&x);
        }
        /*fetch and store the results*/
        results=fetch_and_store(stmt, &count, p, &error, LISTP);
        
        if(results==NULL && error==0){
            /*this means that there is no results, because there was not an error*/
		    printf("\nNo customer found.\n");
        }else if(error==0){
            /*print the column names*/
            printf("%s\t%s\n", name1, name2);
            /*page the results: */
            page_results(results, count);
        }
        printf("\n");
    }

    for(i=0;i<count;i++){
        if(results!=NULL) /*This check is only for splint*/
            free(results[i]);
    }
    free(results);

    /*free the handle*/
    ret=SQLFreeHandle(SQL_HANDLE_STMT, stmt);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }    

    /*disconnect*/
    ret = (SQLRETURN)odbc_disconnect(env, dbc);

    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        return EXIT_FAILURE;
    }
    if(error==1){
        /*this means there was an error in the (re)allocation of memory for the results*/
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
        WHERE  customernumber = ? and orders.status<>'Cancelled') AS x, 
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
    SQLLEN bind_col=0;
    
    ret = (SQLRETURN)odbc_connect(&env, &dbc);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        return EXIT_FAILURE;
    }

    ret=SQLAllocHandle(SQL_HANDLE_STMT, dbc, &stmt);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }
    /*in this query, we do not count the price of the cancelled orders*/
    ret=SQLPrepare(stmt, (SQLCHAR*)"SELECT ( y.paid - x.total ) AS Balance \
                                FROM   (SELECT Sum(priceeach * quantityordered) AS total \
                                        FROM   (orders NATURAL JOIN orderdetails) \
                                        WHERE  customernumber = ? and orders.status<>'Cancelled') AS x, \
                                    (SELECT Sum(amount) AS paid \
                                        FROM   payments \
                                        WHERE  customernumber = ?) AS y ", ret=SQL_NTS);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }
    printf("Enter customer number > ");
    (void)fflush(stdout);
    if(scanf("%d", &x)!=0){

        ret=SQLBindParameter(stmt, 1, SQL_PARAM_INPUT, SQL_C_SLONG, SQL_INTEGER, 0, 0, &x, 0, NULL); 
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }  
        ret=SQLBindParameter(stmt, 2, SQL_PARAM_INPUT, SQL_C_SLONG, SQL_INTEGER, 0, 0, &x, 0, NULL);    
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }  
    
         /*execute the query*/
        ret=SQLExecute(stmt);
        if (!SQL_SUCCEEDED(ret)) {
            odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        }  

        /*print the results*/
        if (SQL_SUCCEEDED(ret = SQLFetch(stmt))) {
            ret = SQLGetData(stmt, 1, SQL_C_DOUBLE, &balance, (SQLLEN)sizeof(SQLDOUBLE), &bind_col);
            if(bind_col==-1){
                printf("\nNo customer found.\n");
            }else{
                printf("Balance = %f\n", balance);
            }
        }

        printf("\n");
    }

    /*free the handle*/
    ret=SQLFreeHandle(SQL_HANDLE_STMT, stmt);
    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
    }  

    /*disconnect*/
    ret = (SQLRETURN)odbc_disconnect(env, dbc);

    if (!SQL_SUCCEEDED(ret)) {
        odbc_extract_error("", stmt, SQL_HANDLE_ENV);
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}

