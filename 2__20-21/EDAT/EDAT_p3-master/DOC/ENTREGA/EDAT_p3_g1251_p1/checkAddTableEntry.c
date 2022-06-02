#include "tester.h"

#include <string.h>

#define STR_MAX_SIZE 512

/*extern char *strndup(const char *, size_t);*/

void checkAddTableEntry(const char *tableName, const char *indexName)
{
    bool result;
    char bookId[PK_SIZE], *booktitle = "Rose", *booktitle2 = "Mary", *booktitle3 = "F. Mercury", *title, *booktitle4="P" ;
    Book book;
    FILE *fp;
    int l, length,i;
    int none=-1,eof_data,r, eof_index, nnode, offset, header;
    int del_index,del_data,next_del_data, del_rec_offset;

    printf("\n* checkAddTableEntry: \n");

    createTestFiles(tableName,indexName); /*create .dat and .idx*/

    /*1) Add existing node*/
    printf("\tExpected warning from function addTableEntry: ");
    fflush(stdout);
    result=addTableEntry(&b[0],tableName,indexName);

    if(result){
        fprintf(stderr, "Error in addTableEntry, insertred exisintg book %s",b[0].book_id);
        exit(EXIT_FAILURE);
    }

    printf("\tCorrect behaviour when trying to insert and existing book.\n");
    fflush(stdout);
    /*2) Insert if they are no deleted records*/

    /*Modify headers of .dat and .inx to specify there is no record deleted*/
    fp=fopen(indexName,"rb+");
    fseek(fp,4,SEEK_SET);
    fread(&del_index,sizeof(int),1,fp);
    fseek(fp,4,SEEK_SET);
    fwrite(&none,sizeof(int),1,fp);
    fseek(fp,0,SEEK_END);
    eof_index=ftell(fp);/*store the offset of the end of the index file*/
    fclose(fp);

    fp = fopen(tableName, "rb+");
    fseek(fp, 0, SEEK_SET);
    fread(&del_data, sizeof(int), 1, fp);
    fseek(fp, 0, SEEK_SET);
    fwrite(&none, sizeof(int), 1, fp);
    fseek(fp,0,SEEK_END);
    eof_data=ftell(fp);/*store the offset of the end of the data file*/
    fseek(fp,del_data,SEEK_SET);
    fread(&next_del_data,sizeof(int),1,fp);
    fclose(fp);
    
    
    /*data of a new node*/
    memcpy(book.book_id,"NEW1",4);
    book.title_len = strlen(booktitle);
    length = book.title_len;
    book.title = (char *)malloc(length);
    title=(char*)malloc(length);
    memcpy(book.title, booktitle, length);

    /*Add the node. Should be inserted at the end*/
    result=addTableEntry(&book,tableName,indexName);

    /*Check if the info has been written at the end*/
    fp = fopen(tableName, "rb+");
    fseek(fp,eof_data,SEEK_SET);

    if(fread(bookId,sizeof(char),PK_SIZE,fp)!=4){
       fprintf(stderr,"(At the end)Primary Key not inserted.");
       exit(EXIT_FAILURE); 
    }

    if(strcmp(bookId,"NEW1")!=0){
        fprintf(stderr,"(At the end)Error wirtign PK. Expected %s but read %s.\n", "NEW1", bookId);
        exit(EXIT_FAILURE);
    }

    if (fread(&l, sizeof(int), 1, fp)!=1){
        fprintf(stderr, "(At the end)Title length not inserted.");
        exit(EXIT_FAILURE);
    }

    if(l!=length){
        fprintf(stderr, "(At the end)Error writing length. Expected %i but read %i.\n", length, l);
        exit(EXIT_FAILURE);
    }

    if (fread(title, sizeof(char), length, fp) != (unsigned)(length)){
        fprintf(stderr, "(At the end)Title not inserted.\n");
        exit(EXIT_FAILURE);
    }


    for(i=0;i<length;i++){
        if(title[i]!=booktitle[i]){
            fprintf(stderr, "(At the end)Error writing title.\n");
            exit(EXIT_FAILURE);
        }
    }

    /*Check if the binary files ends here*/
    l=ftell(fp);
    fseek(fp,0,SEEK_END);
    r=ftell(fp);
    if(l!=r){
        printf("Error writing book at the end of the file. More info wirtten after the book.\n");
    }
    fclose(fp);

    free(book.title);
    free(title);

    /*CHECK THE INDEX (should be inserted at the end)*/

    /*Check if the node has been written at the end*/
    fp = fopen(indexName, "rb+");
    fseek(fp,eof_index,SEEK_SET);

    fread(bookId, sizeof(char), PK_SIZE, fp);
    if(strncmp(bookId,"NEW1", PK_SIZE)!=0){
        fprintf(stderr,"(At the end)Error wirtign PK in index file. Expected %s but read %s.\n", "NEW1", bookId);
        exit(EXIT_FAILURE);
    }
    
    /*left field*/
    fread(&nnode, sizeof(int), 1, fp);
    if(nnode!=-1){
        fprintf(stderr,"(At the end)Error writing left child of node. Expected %d but read %d.\n", -1, nnode);
        exit(EXIT_FAILURE);
    }
    /*right field*/
    fread(&nnode, sizeof(int), 1, fp);
    if(nnode!=-1){
        fprintf(stderr,"(At the end)Error writing right child of node. Expected %d but read %d.\n", -1, nnode);
        exit(EXIT_FAILURE);
    }
    /*parent*/
    fread(&nnode, sizeof(int), 1, fp);
    if(nnode!=7){
        fprintf(stderr,"(At the end)Error writing parent of node. Expected %d but read %d.\n", 7, nnode);
        exit(EXIT_FAILURE);
    }
    /*offset*/
    fread(&nnode, sizeof(int), 1, fp);
    if(nnode!=eof_data){
        fprintf(stderr,"(At the end)Error left child of node. Expected %d but read %d.\n", eof_data, nnode);
        exit(EXIT_FAILURE);
    }
  

    /*Check if the binary files ends here*/
    l=ftell(fp);
    fseek(fp,0,SEEK_END);
    r=ftell(fp);
    if(l!=r){
        printf("(At the end) More nodes wirtten after the last node in index file.\n");
    }

    /*rewrite the original pointers to deleted records*/
    fp = fopen(tableName, "rb+");
    fseek(fp, 0, SEEK_SET);
    fwrite(&del_data, sizeof(int), 1, fp);
    fclose(fp);

    fp = fopen(indexName, "rb+");
    fseek(fp, 4, SEEK_SET);
    fwrite(&del_index, sizeof(int), 1, fp);
    fclose(fp);

    printf("\tCorrect entry in Table (and in index file) when inserted at the end.\n");


    /* 3) WRITE IN 1st DELETED RECORD (it fits)*/ 

    /* What to check
        The heather is changed, the first deleted record offset has been updated and now is 88
        the record is inserted correctly
        the next deleted node has field next -1 (there are no more deleted records. At the beggining there were two
        : the one in which we insert and the next)
    */

   /*Info of node2*/
    memcpy(book.book_id, "NEW2", 4);
    book.title_len = strlen(booktitle2);
    length = book.title_len;
    book.title = (char *)malloc(length);
    title = (char *)malloc(length);
    memcpy(book.title, booktitle2, length);

    /*Insert node 2*/
    
    result = addTableEntry(&book, tableName, indexName);
    fp = fopen(tableName, "rb+");
    fseek(fp, del_data, SEEK_SET);
  

    if (fread(bookId, sizeof(char), PK_SIZE, fp) != 4){
        fprintf(stderr, "(1st del record)Primary Key not inserted.\n");
        exit(EXIT_FAILURE);
    }

    if (strcmp(bookId, "NEW2") != 0){
        fprintf(stderr, "(1st del record)Error writing PK. Expected %s but read %s.\n", "NEW2", bookId);
        exit(EXIT_FAILURE);
    }

    if (fread(&l, sizeof(int), 1, fp) != 1){
        fprintf(stderr, "(1st del record)Title length not inserted.\n");
        exit(EXIT_FAILURE);
    }

    if (l != length){
        fprintf(stderr, "(1st del record)Error writing length. Expected %i but read %i.\n", length, l);
        exit(EXIT_FAILURE);
    }

    if (fread(title, sizeof(char), length, fp) != (unsigned)(length))
    {
        fprintf(stderr, "(1st del record)Title not inserted.\n");
        exit(EXIT_FAILURE);
    }

    for (i = 0; i < length; i++){
        if (title[i] != booktitle2[i])
        {
            fprintf(stderr, "(1st del record)Error writing title.\n");
            exit(EXIT_FAILURE);
        }
    }


    /*Check that the header has been modified correctly*/
    fseek(fp, 0, SEEK_SET);
    fread(&l, sizeof(int), 1, fp);
    if(l!=next_del_data){
        fprintf(stderr,"Expected first deleted record %i but read %i.\n",next_del_data,l);
        exit(EXIT_FAILURE);
    }

    /*Check that next deleted record is -1. There are no more*/
    fseek(fp,next_del_data,SEEK_SET);
    fread(&l,sizeof(int),1,fp);
    if (l != -1){
        fprintf(stderr, "Expected next deleted record %i but read %i.\n", -1, l);
        exit(EXIT_FAILURE);
    }
    fclose(fp);

    /*check the index file by searching using findKey and cheching the Book that offset points to*/

    if(findKey("NEW2",indexName,&offset)==false){
        fprintf(stderr, "(1st del record)Key not found in the index file.\n");
        exit(EXIT_FAILURE);
    }

    fp=fopen(tableName, "rb");
    fseek(fp, offset, SEEK_SET);
    fread(&(book.book_id), sizeof(char), PK_SIZE, fp);
    fread(&(book.title_len), sizeof(int), 1, fp);
    fread(book.title, sizeof(char), book.title_len, fp);

    if(strncmp(book.book_id, "NEW2", PK_SIZE)!=0){
        fprintf(stderr, "(1st del record)Error in the index: PK.\n");
        exit(EXIT_FAILURE);
    }
    if(book.title_len!=(size_t)length){
        fprintf(stderr, "(1st del record)Error in the index: length.\n");
        exit(EXIT_FAILURE);
    }
    if(strncmp(book.title, booktitle2, length)!=0){
        fprintf(stderr, "(1st del record)Error in the index: title.\n");
        exit(EXIT_FAILURE);
    }

    printf("\tCorrect entry in Table (and in index file) when inserted in 1st deleted record.\n");
    free(book.title);
    free(title);

    memcpy(book.book_id, "NEW4", 4);
    book.title_len = strlen(booktitle4);
    length = book.title_len;
    book.title = (char *)malloc(length);
    title = (char *)malloc(length);
    memcpy(book.title, booktitle4, length);

    fseek(fp, 0, SEEK_SET);
    /*read the header*/
    fread(&header, sizeof(int), 1, fp);
    /*after insertion, header should contain this*/
    del_rec_offset=header+8+strlen(booktitle4);
    fclose(fp);
    
    result = addTableEntry(&book, tableName, indexName);

    fp=fopen(tableName, "rb");
    
    fseek(fp, 0, SEEK_SET);
    /*read the header*/
    fread(&header, sizeof(int), 1, fp);
    if(header!=del_rec_offset){
        fprintf(stderr, "Header not correct when reusing extra space. Read: %d, expected: %d.\n", header, next_del_data);
        exit(EXIT_FAILURE);
    }

    fclose(fp);

    
    /* 4) WRITE IN fitting record*/
    /*book should be inserted in second deleted record, in the first one 
    does not fit*/

    /*What to check
    inserted in 2nd deleted record register
    first deleted record has next field -1 (there is no more deleted records apart
    from it after insertion)*/

    /*reset table*/
    remove(tableName);
    remove(indexName);
    createTestFiles(tableName, indexName);

    memcpy(book.book_id, "NEW3", 4);
    book.title_len = strlen(booktitle3);
    length = book.title_len;
    book.title = (char *)malloc(length);
    title = (char *)malloc(length);
    memcpy(book.title, booktitle3, length);

    result = addTableEntry(&book, tableName, indexName);

    fp = fopen(tableName, "rb+");
    fseek(fp,next_del_data,SEEK_SET);

    if (fread(bookId, sizeof(char), 4, fp) != 4){
        fprintf(stderr, "(fitting del. record)Primary Key not inserted.\n");
        exit(EXIT_FAILURE);
    }

    if (strcmp(bookId, "NEW3") != 0){
        fprintf(stderr, "(fitting del. record)Error writing PK. Expected %s but read %s.\n", "NEW2", bookId);
        exit(EXIT_FAILURE);
    }

    if (fread(&l, sizeof(int), 1, fp) != 1){
        fprintf(stderr, "(fitting del. record)Title length not inserted.\n");
        exit(EXIT_FAILURE);
    }

    if (l != length){
        fprintf(stderr, "(fitting del. record)Error writing length. Expected %i but read %i.\n", length, l);
        exit(EXIT_FAILURE);
    }

    if (fread(title, sizeof(char), length, fp) != (unsigned)(length))
    {
        fprintf(stderr, "(fitting del. record)Title not inserted.\n");
        exit(EXIT_FAILURE);
    }

    for (i = 0; i < length; i++){
        if (title[i] != booktitle3[i]){
            fprintf(stderr, "(fitting del. record)Error writing title.\n");
            exit(EXIT_FAILURE);
        }
    }

    /*Check that next deleted record field has been updated from previous deleted record*/
    fseek(fp,del_data,SEEK_SET);
    fread(&l, sizeof(int), 1, fp);
    if (l != -1){
        fprintf(stderr, "Expected next deleted record %i but read %i.\n", -1, l);
        exit(EXIT_FAILURE);
    }
    fclose(fp);

    /*check the index file by searching using findKey and cheching the Book that offset points to*/
    if(findKey("NEW3",indexName,&offset)==false){
        fprintf(stderr, "(fitting del. record)Key not found in the index file.\n");
        exit(EXIT_FAILURE);
    }

    fp=fopen(tableName, "rb");
    fseek(fp, offset, SEEK_SET);
    fread(&(book.book_id), sizeof(char), PK_SIZE, fp);
    fread(&(book.title_len), sizeof(int), 1, fp);
    fread(book.title, sizeof(char), book.title_len, fp);

    if(strncmp(book.book_id, "NEW3", PK_SIZE)!=0){
        fprintf(stderr, "(fitting del. record)Error in the index: PK.\n");
        exit(EXIT_FAILURE);
    }
    if(book.title_len!=(size_t)length){
        fprintf(stderr, "(fitting del. record)Error in the index: length.\n");
        exit(EXIT_FAILURE);
    }
    if(strncmp(book.title, booktitle3, length)!=0){
        fprintf(stderr, "(fitting del. record)Error in the index: title.\n");
        exit(EXIT_FAILURE);
    }

    printf("\tCorrect entry in Table (and in index file) when inserted in fitting deleted record.\n\t...OK\n");
    free(book.title);
    free(title);
    fclose(fp);

}