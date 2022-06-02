'''
    Fichero con funciones y macros utilizadas en sb_funct y sb_files
'''
import requests
import os
from sb_exceptions import *

DIR_PATH = os.path.dirname(__file__) + '/'


def write_file(file_name, msg):
    '''Escribe bytes (msg) en un fichero (file_name), 
       sobreescribiéndolo si existe.'''
    with open(file_name, 'wb') as file:
        file.write(msg)


def read_file(file_name):
    '''Devuelve el contenido binario del fichero'''
    try:
        with open(file_name, 'rb') as file:
            return file.read()
    except IOError as e:
        raise FileException(e)


TOKEN_FILE = DIR_PATH + '../appfiles/keys/token.txt'

URL_ROOT = 'http://vega.ii.uam.es:8080/api'


# Intenta cargar el TOKEN de acceso a securebox desde el archivo que lo contiene
try:
    TOKEN = read_file(TOKEN_FILE).decode('utf-8').strip('\n')
except SecureboxException:
    TOKEN = 'error'
    print('No se pudo inicializar el token desde su fichero, seguramente '
          + 'se tenga que generar de nuevo:\n\tutilice --generate_token.')


# Headers por defecto para las peticiones post a securebox
HEADERS_DEFAULT = {'Authorization': 'Bearer ' + TOKEN,
                   'Content-Type': 'application/json'}


def process_post(
        end_point,
        headers=HEADERS_DEFAULT,
        json=None,
        files=None,
        params=None
):
    ''' Realiza el post al servidor y comprueba que no haya error. 
        Devuelve la respuesta.'''

    try:
        response = requests.post(
            url=URL_ROOT+end_point,
            headers=headers,
            json=json,
            files=files,
            params=params,
        )
    except requests.exceptions.RequestException as e:
        raise RequestException(exception=e)

    if not response.ok:
        raise RequestException(json=response.json())

    return response


def print_elems(elems, str_funct):
    '''Imprime los elementos del iterable elems
        con el formato descrito por str_funct'''
    i = 0
    for elem in elems:
        print(f'[{i}] ' + str_funct(elem))
        i += 1


def get_file_name(headers):
    '''Toma los headers de la respuesta a un download 
       y devuelve el nombre del fichero descargado'''
    s = headers['Content-Disposition']
    i1 = s.index('\"')+1
    i2 = s[i1:].index('\"')
    return s[i1:][:i2]
