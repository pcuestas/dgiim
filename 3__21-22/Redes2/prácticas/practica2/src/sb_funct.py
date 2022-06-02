'''
    Funciones principales de securebox_client
'''

from sb_files import *
import os
from sb_util import *


def generate_token(nia, nick):
    '''Solicita un nuevo token al servidor y lo guarda 
       en el fichero correspondiente'''

    end_point = '/users/getToken'
    args = {'nia': nia, 'email': nick}

    print('Solicitando nuevo token...', end='')

    r = process_post(end_point, headers=None, params=args)
    print('OK\n-> Guardando token...OK')
    # extraemos token de la respuesta y lo guardamos en el fichero:
    s = r.text
    i1 = s.find('Nuevo token:')
    i1 = s[i1:].index(':')+2+i1
    i2 = s[i1:].index('\n')
    token = s[i1:][:i2+1]
    write_file(TOKEN_FILE, bytes(token, 'utf-8'))


def create_id(name, email, alias=None):
    ''' Solicita la creación de un usuario en el servidor '''

    end_point = '/users/register'
    key = generate_RSA_key()
    args = {
        'nombre': name,
        'email': email,
        'publicKey': key.public_key().export_key('PEM').decode('utf-8'),
    }
    if alias:
        args['alias'] = alias

    print('Creando un cliente con nombre: \'{}\', email: \'{}\'...'
          .format(name, email), end='')

    rjson = process_post(end_point, json=args).json()
    print('OK\nIdentidad con ID {} creada correctamente'
          .format(rjson['userID']))


def delete_id(id):
    ''' Borra el usuario con identificador id del servidor '''

    end_point = '/users/delete'
    args = {'userID': id}

    print('Solicitando borrado de la identidad con ID {}...'
          .format(id), end='')

    rjson = process_post(end_point, json=args).json()
    print('OK\nIdentidad con ID {} borrada correctamente'
          .format(rjson['userID']))


def search_id(data_search):
    '''Busca la cadena data_search en los usuarios del servidor'''

    end_point = '/users/search'
    args = {'data_search': data_search}

    print('Buscando usuario \'{}\' en el servidor...'
          .format(data_search), end='')

    rjson = process_post(end_point, json=args).json()

    print('OK\n{} usuario(s) encontrado(s):'.format(len(rjson)))
    print_elems(elems=rjson,
                str_funct=lambda user:
                '{}, {}, ID: {}\n{}'
                    .format(
                        user['nombre'],
                        user['email'],
                        user['userID'],
                        user['publicKey']
                    )
                )


def upload(file_name, dest_id):
    '''Firma, cifra y sube un fichero al servidor'''

    print('Solicitando envío de fichero a SecureBox')

    # firma y cifrado:
    file_full_name = FILES_DIR+'/'+file_name
    signed_file_name = sign_file(file_full_name)
    cyph_file_name = encrypt_file(signed_file_name, dest_id)

    # cambiar nombres de ficheros
    file_tmp_name = FILES_DIR+'/'+'tmp.bin'
    os.rename(file_full_name, file_tmp_name)
    os.rename(cyph_file_name, file_full_name)

    print('-> Subiendo fichero al servidor...', end='')
    end_point = '/files/upload'
    try:
        with open(file_full_name, 'rb') as file:
            args = {'ufile': file}  # se envía el cifrado

            #reestablecer nombres
            os.rename(file_full_name, cyph_file_name)
            os.rename(file_tmp_name, file_full_name)

            rjson = process_post(
                end_point=end_point,
                headers={'Authorization': 'Bearer ' + TOKEN},
                files=args
            ).json()
            print('OK\nSubida realizada correctamente, ID del fichero: {}'
                  .format(rjson['file_id']))

    except IOError as e:
        raise FileException(e)


def delete_file(file_id):
    '''Solicita el borrado del fichero con id file_id del servidor'''
    end_point='/files/delete'
    args={'file_id': file_id}
    print('Solicitando borrado del fichero con id {}...'.format(file_id), end='')
    
    rjson = process_post(end_point, json=args).json()
    print('OK\nSe ha borrado con éxito el fichero con id {}'.format(rjson['file_id']))


def list_files():
    ''' Imprime la lista de ficheros en el servidor 
        (accesibles por el usuario)'''
    end_point='/files/list'
    print('Solicitando ficheros del usuario...', end='')
    
    rjson = process_post(end_point).json()
    print('OK\nHay un total de {} fichero(s)'.format(rjson['num_files']))
    
    print_elems(
        rjson['files_list'], 
        str_funct = lambda file : 
                        ' File ID: {}, name: \'{}\''
                            .format(file['fileID'],file['fileName'])  
    )


def download(file_id, source_id):
    '''Descarga de un fichero subido por source_id'''

    end_point='/files/download'
    args={'file_id': file_id}

    print('Descargando fichero de SecureBox...'.format(file_id), end='')
    r = process_post(end_point, json=args)
    print('OK\n-> {} bytes descargados correctamente'.format(len(r.content)))

    downloaded_file_name = DOWNLOADS_FILES_DIR+'/'+get_file_name(r.headers)
    dec_content = decrypt_content(r.content)
    verify_signature_and_create_file(
        content=dec_content,
        file_name=downloaded_file_name,
        source_id=source_id,
    )

    print('Fichero descargado y verificado correctamente')
    print('Fichero descifrado en {}'.format(downloaded_file_name))



def encrypt(file_name, dest_id):
    '''Cifra un fichero'''

    file_full_name=FILES_DIR+'/'+file_name
    # cifrado:
    cyph_file_name = encrypt_file(file_full_name, dest_id) 

    print('Fichero cifrado en {}'.format(cyph_file_name))


def decrypt(file_name):
    '''Desifra un fichero'''

    file_full_name=FILES_DIR+'/'+file_name
    dec_file_name = append_to_file_name(file_full_name, '_descifrado')
    # descifrado:
    enc_content = read_file(file_full_name)
    dec_content = decrypt_content(enc_content)

    write_file(dec_file_name, dec_content)

    print('Fichero descifrado en {}'.format(dec_file_name))


def sign(file_name):
    '''Firma un fichero'''

    file_full_name=FILES_DIR+'/'+file_name
    # firma:
    signed_file_name = sign_file(file_full_name)

    print('Fichero firmado en {}'.format(signed_file_name))


def enc_sign(file_name, dest_id):
    '''Firma y cifra un fichero'''

    file_full_name=FILES_DIR+'/'+file_name
    # firma y cifrado:
    signed_file_name = sign_file(file_full_name)
    cyph_file_name = encrypt_file(signed_file_name, dest_id) 

    print('Fichero firmado y cifrado en {}'.format(cyph_file_name))


def verify_sign(file_name, source_id):
    '''Verifica la firma de un fichero'''

    file_full_name = FILES_DIR+'/'+file_name
    content = read_file(file_full_name)

    # comprobación de la firma:
    verified_file = verify_signature_and_create_file(
        content,
        append_to_file_name(file_full_name, '_verificado'),
        source_id,
    )
    print('Fichero verificado en {}'.format(verified_file))


def verify_enc_sign(file_name, source_id):
    '''Descifra y comprueba la firma de un fichero un fichero'''

    file_full_name=FILES_DIR+'/'+file_name
    # descifrado:
    enc_content = read_file(file_full_name)
    dec_content = decrypt_content(enc_content)

    # comprobación de la firma:
    verified_file = verify_signature_and_create_file(
        dec_content,
        append_to_file_name(file_full_name, '_descifrado_verificado'),
        source_id,
    )
    print('Fichero verificado en {}'.format(verified_file))
