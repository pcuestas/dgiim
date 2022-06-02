#include "utils.h"
#include <string.h>

#define STR_SIZE 512

#define MIN_TITLE 1
#define NO_NEXT_DEL -1


/**
 * following function copies content of fileName
 * into indexName replacing the first appeareance
 * of ".*" by ".idx" (we make the assumption that
 * fileName will only contain the '.' 
 * character for the extensions ".dat")
 * The content of indexName will be lost after 
 * calling this function  
 */
void replaceExtensionByIdx(const char *fileName, char * indexName) {
    char *b = indexName;

    /* copy the fileName in indexName until a '.' (or EoS)
     * is found in the first*/
    while( *fileName != '.' && *fileName != 0)
        *(b++) = *(fileName++);
    
    /*write EoS*/
    *b = '\0';

    /*append the .idx extension*/
    strncat(indexName, ".idx", STR_SIZE);

    return;
}

bool createTable(const char * tableName) {

    FILE *pf=NULL;
    char indexName[STR_SIZE] = "";
    int init_value = NO_DELETED_REGISTERS;

    if(!tableName){
        return false;
    }

    if((pf=fopen(tableName,"rb+"))==NULL){
        /*if it does not exist, we create it*/
        pf=fopen(tableName,"wb+");
        /*and now initialize the header*/
        fwrite(&init_value, sizeof(int), 1, pf);
    }

    fclose(pf);

    /**store in indexName the '.idx' name of 
     * the file correspondig to that table */
    replaceExtensionByIdx(tableName, indexName);

    return createIndex(indexName);
}

bool createIndex(const char *indexName) {
    FILE *pf=NULL;
    /**init values to be printed: (no nodes) and 
     * (no deleted registers)
     * 
     * (this is the header of a new index file)
     * */
    int init_values[2] = {-1, NO_DELETED_REGISTERS};

    if(!indexName){
        return false;
    }

    if((pf=fopen(indexName,"rb+"))==NULL){
        /*if it does not exist, we create it*/
        pf=fopen(indexName,"wb+");
        /*and now initialize the header*/
        fwrite(&init_values, sizeof(int), 2, pf);
    }
    fclose(pf);

    return true;
}

/**prints a node in level: level and its successive 
 * children, until level: _level is reached
 * 
 * parameter side is 'l' (left) or 'r' (right) or
 * 'n' (none-for the root) 
 **/
void printnode(size_t _level, size_t level,
               FILE * indexFileHandler, int node_id, char side) {
    Node node;
    int i = 0;
    /*base case*/
    if(node_id == -1 || level > _level)
        return;

    /*seek the node in the file*/
    fseek(indexFileHandler, INDEX_HEADER_SIZE+node_id*sizeof(Node), SEEK_SET);
    /*read the info of the node*/
    fread(&node, sizeof(Node), 1, indexFileHandler);

    /*print the indentation:*/
    for(i = 0; i < (int)level; i++)
        printf("\t");
    /*print the side, if it is not the root*/
    if(side != 'n')
        printf("%c ", side);
    /*print info of node*/
    for(i=0;i<PK_SIZE;i++)
        printf("%c", node.book_id[i]);
    printf(" (%d): %d\n", node_id, node.offset); 

    /*print left tree*/
    printnode(_level, level+1, indexFileHandler, (node.left), 'l');
    /*print right tree*/
    printnode(_level, level+1, indexFileHandler, (node.right), 'r');

    return;
}

void printTree(size_t level, const char * indexName){   
    FILE *pf = NULL;
    int root_id;

    if(indexName==NULL)
        return;
    if((pf=fopen(indexName, "rb"))==NULL)
        return;
    /*read the root's id*/
    fread(&root_id, sizeof(int), 1, pf);
    
    /*call recursive function*/
    printnode(level, 0, pf, root_id, 'n');

    fclose(pf);
    return;
}

bool findKey(const char * book_id, const char *indexName, int * nodeIDOrDataOffset){
    FILE *pf=NULL;
    int pos, comp;
    Node node;
    bool found = false;
    char b_id[5], search[5];

    if(!indexName)
        return false;

    if((pf=fopen(indexName,"rb"))==NULL)
        return false;

    memcpy(search, book_id, PK_SIZE);
    search[PK_SIZE] = '\0';
    /*read the root's position*/
    fread(&pos, sizeof(int), 1, pf);

    if(pos==-1){/*empty*/
        *nodeIDOrDataOffset=-1;
    }

    while(pos != -1 && found == false){
        /*find the node's offset in the file*/
        fseek(pf, INDEX_HEADER_SIZE+pos*sizeof(Node), SEEK_SET);
        /*read the node*/
        fread(&node, sizeof(Node), 1, pf);
        memcpy(b_id, node.book_id, PK_SIZE);
        b_id[PK_SIZE] = '\0';
        /*compare the primary key*/
        comp=strcmp(search, b_id);
        
        if(comp < 0){
            if(node.left==-1)/*not found*/
                (*nodeIDOrDataOffset) = pos;
            pos = node.left;            
        }
        else if(comp > 0){
            if(node.right==-1)/*not found*/
                (*nodeIDOrDataOffset) = pos;
            pos = node.right;            
        }
        else{/*found*/
            (*nodeIDOrDataOffset) = node.offset;
            found = true;
        }
    }
    fclose(pf);
    return found;
}

bool addIndexEntry(char * book_id,  int bookOffset, char const * indexName){
    
    int header[2]={0,0},first_deleted,parent,greater,inserted;
    FILE *pf;
    Node node,del_node;
    char parent_name[5], book_id_aux[5];

    if(book_id==NULL||indexName==NULL)
        return false;

    if((pf=fopen(indexName, "rb+"))==NULL)
        return false;

    if(findKey(book_id, indexName, &parent)==true){
        return false;
    }


    fread(&header, INDEX_HEADER_SIZE,1,pf); /*check if there are deleted nodes*/
    if(header[1]!=-1){
        fseek(pf,INDEX_HEADER_SIZE+header[1]*sizeof(Node),SEEK_SET); /*go to the first deleted node*/
        fread(&del_node,sizeof(Node),1,pf);
        first_deleted=del_node.left; /*next deleted record. Assume it is in the left field*/
        fseek(pf,sizeof(int),SEEK_SET);
        fwrite(&first_deleted,sizeof(int),1,pf); /*Update new first deleted record*/
        inserted=header[1];
        fseek(pf,INDEX_HEADER_SIZE+inserted*sizeof(Node),SEEK_SET);
    }
    else
    { /*add it at the end*/
        fseek(pf, 0, SEEK_END);
        inserted=(ftell(pf)-INDEX_HEADER_SIZE)/sizeof(Node);
    }

    /*Write the info of the node inserted*/
    memcpy(node.book_id, book_id, PK_SIZE);
    node.left = -1;
    node.right = -1;
    node.parent = parent;
    node.offset = bookOffset;
    fwrite(&node, sizeof(Node), 1, pf); /*write node*/

    if(parent==-1){/*this means we are inserting the root*/
        fseek(pf, 0, SEEK_SET);
        fwrite(&inserted , sizeof(int), 1, pf);
    }else{
        fseek(pf, INDEX_HEADER_SIZE + sizeof(Node) * parent, SEEK_SET); /*Go to the parent*/

        fread(parent_name, sizeof(char), 4, pf);
        memcpy(book_id_aux, book_id, PK_SIZE);
        parent_name[PK_SIZE]='\0';
        book_id_aux[PK_SIZE]='\0';


        greater=strcmp(parent_name,book_id_aux);
        if(greater>0){
            /*Left child*/
            fwrite(&inserted,sizeof(int),1,pf);
        }else{
            /*Right child*/
            fseek(pf, sizeof(int), SEEK_CUR);
            fwrite(&inserted, sizeof(int), 1, pf);
        }
    }
    fclose(pf);
    return true;
}


bool addTableEntry(Book *book, const char *dataName,
                   const char *indexName){
    int nodeIDOrDataOffset=0, header=0, previous=-1, next=0, offset=0, title_extra_space, new_next, avail;
    FILE *pf = NULL;
    Deleted_record rec;

    if(book==NULL||dataName==NULL||indexName==NULL)
        return false;

    if(findKey(book->book_id, indexName, &nodeIDOrDataOffset)==true){
        fprintf(stderr,"Error in addTableEntry, trying to insert an existing key\n");
        return false;
    }

    if((pf=fopen(dataName, "rb+"))==NULL){
        fprintf(stderr,"Error in addTableEntry, data file could not be opened\n");
        return false;
    }
    /*read the header to see if there are any deleted records*/
    fread(&header, DATA_HEADER_SIZE, 1, pf);
    
    next = header; 
    /*find the offset of where the new record can be inserted*/
    while(next != NO_NEXT_DEL){
        /*go to the record*/
        fseek(pf, next, SEEK_SET);
        /*read the deleted record*/
        fread(&rec, sizeof(Deleted_record), 1, pf);
        if((avail=(rec.title_len)-(int)(book->title_len))>=0){
            offset = next;
            if((title_extra_space=(avail-(2*sizeof(int))))>=MIN_TITLE){/*Reuse available space*/
                /*the difference is enough to save the remaining part of the record as deleted*/
                fseek(pf, book->title_len, SEEK_CUR);
                /*keep the offset to write it in the previous deleted node*/
                new_next=ftell(pf);
                fwrite(&(rec.next), sizeof(int), 1, pf);
                fwrite(&(title_extra_space), sizeof(int), 1, pf);
            }else{
                new_next=rec.next;
            }
            if(previous!=-1){/*this means this is NOT the first deleted record*/
                /*go to the previous record, 'next' field*/
                fseek(pf, previous, SEEK_SET);
                /*assign the next of the record that is going to be used to the previous's next*/
                fwrite(&(new_next), sizeof(int), 1, pf);
            }else{/*this means this is the first deleted record*/
                /*go to the header*/
                fseek(pf, 0, SEEK_SET);
                /*write rec.next, as there are no deleted records anymore*/
                header=new_next;
                fwrite(&header, sizeof(header), 1, pf);
            }
            break;/*spot already found*/
        }
        /*update values*/
        previous=next;
        next=rec.next;
    }

    if(offset == 0){
        /*then no (fitting) deleted record has been found*/
        fseek(pf, 0, SEEK_END);
    }else{
        fseek(pf, offset, SEEK_SET);
    }

    offset = ftell(pf);
    
    /*write the book in the found position*/
    fwrite(book->book_id, sizeof(char), PK_SIZE, pf);
    fwrite(&(book->title_len), sizeof(int), 1, pf);
    fwrite(book->title, sizeof(char), book->title_len, pf);

    fclose(pf);

    return addIndexEntry((book->book_id), offset, indexName);
}