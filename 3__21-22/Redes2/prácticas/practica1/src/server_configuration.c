#include "../includes/server_configuration.h"
#include <confuse.h>
#include <stdlib.h>
#include <syslog.h>

/***************************************************************/
/* Variables globales de la configuración del servidor         */
extern char *server_name;
extern char *server_root;
extern long int nthrds;             /*num. de threads*/
/***************************************************************/

void scfg_configure(long int *max_clients, long int *listen_port)
{ 
    cfg_opt_t opts[] = {
		CFG_SIMPLE_STR("server", &server_name),
        CFG_SIMPLE_INT("max_clients", max_clients),
        CFG_SIMPLE_INT("listen_port", listen_port),
        CFG_SIMPLE_STR("server_root", &server_root),
        CFG_SIMPLE_INT("num_threads", &nthrds),
		CFG_END()
	};
	cfg_t *cfg;

    cfg = cfg_init(opts, 0);
	cfg_parse(cfg, CONF_FILE);
    cfg_free(cfg);

    if(*listen_port < 1024)
    {
        syslog(LOG_ERR, "Port cannot be lower than 1024 - using default port %d", DEFAULT_PORT);
        *listen_port = DEFAULT_PORT;
    }
}

void scfg_free()
{
    free(server_name);
    free(server_root);
}