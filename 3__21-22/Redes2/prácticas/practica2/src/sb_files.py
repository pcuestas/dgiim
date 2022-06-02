'''
    Cifrados y firmas de ficheros 
'''

from Crypto.Hash import SHA256
from Crypto.Signature import pkcs1_15
from Crypto.Random import get_random_bytes 
from Crypto.Cipher import AES, PKCS1_OAEP
from Crypto.PublicKey import RSA
from Crypto.Util.Padding import pad, unpad
from sb_exceptions import InvalidSignature
from sb_util import *
import os, re

# tamaño en bytes de la clave simétrica
SYMMETRIC_KEY_LEN = 256 >> 3

# longitud en bits de las claves rsa
RSA_KEY_LEN_BITS = 2048

# longitud de la clave simétrica cifrada con rsa
RSA_SYMMETRIC_KEY_LEN_BYTES = 256

# longitud del vector de inicialización en bytes
IV_LEN_BYTES = 16

# longitud de la firma 
SIGN_LEN_BYTES = 256


# las sustitución en el path con regex es para que no 
# aparezcan paths como: practica2/str/../appfiles/etc,
# que es equivalente a  practica2/appfiles/etc.
APPFILES_DIR         = re.sub('/\w*/\.\.','', DIR_PATH + '../appfiles')
FILES_DIR            = APPFILES_DIR + '/files'
DOWNLOADS_FILES_DIR  = FILES_DIR + '/downloads'
KEYS_DIR             = APPFILES_DIR + '/keys'
PRIVATE_KEY_FILE     = KEYS_DIR + '/private_key.pem'

# crear directorios si no existen
if not os.path.exists(FILES_DIR):
    os.makedirs(FILES_DIR)
if not os.path.exists(DOWNLOADS_FILES_DIR):
    os.makedirs(DOWNLOADS_FILES_DIR)
if not os.path.exists(KEYS_DIR):
    os.makedirs(KEYS_DIR)


def append_to_file_name(file_name, sufix):
    ''' si file_name="file.name.ext" y sufix="_sufix", entonces
        la fución devuelve: "file.name_sufix.ext" '''
    index = len(file_name) - 1 - file_name[::-1].index('.')
    return file_name[:index] + sufix + file_name[index:]


def generate_RSA_key(bits=RSA_KEY_LEN_BITS):
    '''Genera par de claves y guarda la privada en un fichero privado'''

    print('Generando par de claves RSA de {} bits...'.format(bits), end='')
    
    key = RSA.generate(bits)
    write_file(PRIVATE_KEY_FILE, key.export_key('PEM'))

    print('OK')
    return key


def get_private_key():
    '''Devuelve la clave privada del (único) fichero que la contiene'''
    return RSA.import_key(read_file(PRIVATE_KEY_FILE))


def get_public_key(user_id):
    '''Devuelve la clave pública del usuario con id user_id'''

    end_point = '/users/getPublicKey'
    args = {'userID': user_id}

    print('-> Recuperando la clave pública de ID {}...'.format(user_id), end='')
    resp = process_post(end_point, json=args).json()
    
    if resp['publicKey']: 
        print('OK')
        return RSA.import_key(resp['publicKey'])
    else:
        raise SecureboxException(
            'La clave pública en el servidor es None. Posiblemente no se haya creado un usuario con este id.'
        )


def encrypt_file(file_name, dest_id):
    '''Cifra un fichero para que lo pueda descifrar dest_id 
       y devuelve el nombre del nuevo fichero encriptado'''

    print('-> Cifrando fichero...')

    msg = read_file(file_name)
    public_key = get_public_key(dest_id)
    symmetric_key = get_random_bytes(SYMMETRIC_KEY_LEN)

    # cifrado del mensaje
    cipher_aes = AES.new(symmetric_key, AES.MODE_CBC)
    aes_msg = cipher_aes.encrypt(pad(msg, AES.block_size))
    iv = cipher_aes.iv # AES genera el iv de 16 bytes por defecto

    # cifrado de la clave simétrica 
    public_rsa = PKCS1_OAEP.new(public_key)
    rsa_symmetric_key = public_rsa.encrypt(symmetric_key) # tiene longitud 256Bytes

    # mensaje cifrado
    msg_ciphered = iv + rsa_symmetric_key + aes_msg

    ciph_file_name = append_to_file_name(file_name, '_cifrado')
    write_file(ciph_file_name, msg_ciphered)

    print('--> Fichero cifrado...OK')

    return ciph_file_name


def decrypt_content(content):
    '''Recibe el contenido binario cifrado y devuelve 
       el contenido binario descifrado'''

    print('-> Descifrando contenido...', end='')

    # elementos para el descifrado
    private_key = get_private_key()
    iv = content[:IV_LEN_BYTES]
    rsa_symmetric_key = content[IV_LEN_BYTES: IV_LEN_BYTES+RSA_SYMMETRIC_KEY_LEN_BYTES]
    aes_msg = content[IV_LEN_BYTES+RSA_SYMMETRIC_KEY_LEN_BYTES:]

    try: 
        # descifrado de la clave simétrica
        private_rsa = PKCS1_OAEP.new(private_key)
        symmetric_key = private_rsa.decrypt(rsa_symmetric_key)

        # descifrado del contenido
        cipher_aes = AES.new(symmetric_key, AES.MODE_CBC, iv)
        msg = unpad(cipher_aes.decrypt(aes_msg), AES.block_size)
        
    except (ValueError, KeyError) as e:
        raise InvalidDecryption(e)

    print('OK')
    
    return msg


def sign_file(file_name):
    '''Firma un fichero y devuelve el nombre del fichero firmado'''

    print('-> Firmando fichero...', end='')

    file_content = read_file(file_name)
    hash = SHA256.new(file_content)
    private_key = get_private_key()
    signature = pkcs1_15.new(private_key).sign(hash)

    signed_file_name = append_to_file_name(file_name, '_firmado')
    write_file(signed_file_name, signature + file_content)

    print('OK')
    return signed_file_name


def verify_signature_and_create_file(content, file_name, source_id):
    '''Toma el contenido binario de un fichero y si la firma es correcta, 
       lo escribe en el fichero con nombre file_name. 
       Si la firma no es correcta, lanza la excepción InvalidSignature '''

    print('-> Verificando firma...',)

    signature = content[:SIGN_LEN_BYTES]
    file_content = content[SIGN_LEN_BYTES:]
    public_key = get_public_key(source_id)
    hash = SHA256.new(file_content)
    try:
        pkcs1_15.new(public_key).verify(hash, signature)
    except (ValueError, TypeError) as e:
        raise InvalidSignature(e)

    write_file(file_name, file_content)

    print('--> Firma verificada...OK')
    return file_name
