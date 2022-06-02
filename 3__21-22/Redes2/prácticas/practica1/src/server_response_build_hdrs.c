#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <unistd.h>
#include <syslog.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <time.h>

#include "../includes/server_response_build.h"
#include "../includes/server_sck_interaction.h"
#include "../includes/server_configuration.h"

#define TYPE_LEN 30

/***************************************************************/
/* Variables globales de la configuración del servidor         */
extern char *server_name;
/***************************************************************/
/******************AUX FUNCTIONS********************************/

int cmp_type(char *type, char* dot)
{
    return strncmp(type, dot, strlen(type));
}

int srb_hdr_add_date(struct srb_headers *headers)
{    
    char buf[1000];
    time_t now = time(0);
    struct tm tm = *gmtime(&now);
    strftime(buf, sizeof(buf), "%a, %d %b %Y %H:%M:%S %Z", &tm);

    return srb_hdr_add(headers,"Date",buf);
}

int srb_hdr_add_contentlen(struct srb_headers *headers, int len)
{    
    char str[1000];
    sprintf(str, "%d", len);
    return srb_hdr_add(headers,"Content-Length",str);
}


/****************.H FUNCTIONS***************************************/



void srb_hdr_init(struct srb_headers *h)
{
    h->_max = MAX_HEADERS;
    h->num = 0;

    srb_hdr_add(h, "Server", server_name);
    srb_hdr_add_date(h);
    srb_hdr_add(h, "Connection", "close");
}


int srb_hdr_add(struct srb_headers * headers, char *name, char*value)
{
    int i;

    if(!headers || !name || !value )
        return -1;
        
    i = headers->num;

    if(i >= headers->_max)
        return -1;
    
    snprintf(headers->headers[i].name, 
            strlen(name) + 1, "%s", name);
    
    snprintf(headers->headers[i].value,
            strlen(value) + 1, "%s", value);
    
    headers->num++;
    
    return 1;
}

/**
 * @brief añade el header de content type, rellena las flags siguientes, en caso de no ser null
 * @param path path del fichero
 * @param content_type pointer to an already alocated string 
 * @param allowed para OPTIONS, métodos permitidos
 * @param script para el tipo de script a ejecutar
 */
int srb_hdr_process_content(char *path, char *content_type, int *allowed, int *script)
{
    char *dot = strrchr(path, '.');
    char s[TYPE_LEN], *ctype;
    int aux;
    int *methods_allow, *script_type;

    /*para permitir llamadas a esta función con punteros nulos*/
    methods_allow = (allowed) ? allowed : (&aux);
    ctype = (content_type) ? content_type : (s);
    script_type = (script) ? script : &aux;

    if (!dot)
        return -1;

    (*script_type) = ST_NONE;

    if (!(cmp_type(".html", dot)) || !(cmp_type(".htm", dot)))
    {
        sprintf(ctype, "%s", "text/html");
        (*methods_allow) = ALLOW_GET;
    }
    else if (!(cmp_type(".jpg", dot)) || !(cmp_type(".jpeg", dot)))
    {
        sprintf(ctype, "%s", "image/jpeg");
        (*methods_allow) = ALLOW_GET;
    }
    else if (!(cmp_type(".mpg", dot)) || !(cmp_type(".mpeg", dot)))
    {
        sprintf(ctype, "%s", "video/mpeg");
        (*methods_allow) = ALLOW_GET;
    }
    else if (!(cmp_type(".doc", dot)) || !(cmp_type(".docx", dot)))
    {
        sprintf(ctype, "%s", "application/msword");
        (*methods_allow) = ALLOW_GET;
    }
    else if (!(cmp_type(".pdf", dot)))
    {
        sprintf(ctype, "%s", "application/pdf");
        (*methods_allow) = ALLOW_GET;
    }
    else if (!(cmp_type(".gif", dot)))
    {
        sprintf(ctype, "%s", "image/gif");
        (*methods_allow) = ALLOW_GET;
    }
    else if (!(cmp_type(".py", dot)))
    {
        sprintf(ctype, "%s", "text/plain");
        (*script_type) = ST_PYTHON;
        (*methods_allow) = ALLOW_POST;
    }
    else if (!(cmp_type(".php", dot)))
    {
        sprintf(ctype, "%s", "text/plain");
        (*script_type) = ST_PHP;
        (*methods_allow) = ALLOW_POST;
    }
    else
    {
        sprintf(ctype, "%s", "text/plain");
        (*methods_allow) = ALLOW_GET;
    }

    return 0;
}

int srb_hdr_add_contenttype(char *path, struct srb_headers *headers, int *script_type)
{
    char type[TYPE_LEN];

    srb_hdr_process_content(path, type, NULL, script_type);

    srb_hdr_add(headers, "Content-Type", type);
    return 1;
}


int srb_hdr_add_lastmodified(struct srb_headers *headers, struct srb_body *body){
    struct stat attr;
    char str[BUF_LEN];
    struct tm *tm;

    fstat(body->fdesc, &attr);
    tm=gmtime(&attr.st_mtime);
    strftime(str, sizeof(str), "%a, %d %b %Y %H:%M:%S %Z", tm);

    body->fsize = attr.st_size;

    return srb_hdr_add(headers, "Last-Modified", str);
}

int srb_hdr_add_file_info(struct srb_headers *headers, struct srb_body *body)
{
    int ret;

    if (body->fpoin == NULL)
    {   

        ret=srb_hdr_add_lastmodified(headers,body);

        if(ret==-1)
            return -1;
    }
    else
    {
        fseek(body->fpoin, 0, SEEK_END);
        body->fsize = ftell(body->fpoin);
        fseek(body->fpoin, 0, SEEK_SET);
    }

    return srb_hdr_add_contentlen(headers, body->fsize);
}

int srb_hdr_add_location(struct srb_headers *headers, char *new_path)
{
    return srb_hdr_add(headers, "Location", new_path);
}

int srb_hdr_add_allow(struct srb_headers *headers, int n, ...)
{
    char methods[MAX_PARAMS_QUERY];
    va_list ptr;

    va_start(ptr, n);

    for (int i = 0, l = 0; i < n; i++)
    {
        l += snprintf(methods + l, MAX_PARAMS_QUERY - l, "%s, ", va_arg(ptr, char *));
    }

    *(strrchr(methods, ',')) = 0; /*Clear the last comma and blank space*/

    va_end(ptr);

    return srb_hdr_add(headers, "Allow", methods);
}