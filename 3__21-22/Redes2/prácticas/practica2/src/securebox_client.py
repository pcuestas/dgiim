import argparse 
import sys
import pdb 

from sb_funct import *

def parse_args():
    '''Parsea los argumentos de la aplicación'''
    parser = argparse.ArgumentParser(description='Securebox_client command parser')
    
    # Gestión de usuarios e identidades
    parser.add_argument('--create_id', 
                        nargs='+',
                        metavar=('NOMBRE EMAIL', 'ALIAS'),
                        help="Crea una nueva identidad (par de claves pública y privada) para un usuario con nombre nombre y correo email, y la registra en SecureBox, para que pueda ser encontrada por otros usuarios. Alias es una cadena identificativa opcional.",
                    )
    parser.add_argument('--search_id',
                        metavar='CADENA',
                        help="Busca un usuario cuyo nombre o correo electrónico contenga cadena en el repositorio de identidades de SecureBox, y devuelve su ID.",
                    )
    parser.add_argument('--delete_id',
                        metavar='ID',
                        help="Borra la identidad con ID id registrada en el sistema. Obviamente, sólo se pueden borrar aquellas identidades creadas por el usuario que realiza la llamada."
                    )
    parser.add_argument('--generate_token',
                        nargs=2,
                        metavar=('NIA','EMAIL[sin @...]'),
                        help="Genera un token y lo guarda en un fichero. Utilizar en caso de que se reciba del servidor el error de tipo TOK."
                    )

    # Subida y descarga de ficheros
    parser.add_argument('--upload',
                        metavar='FICHERO',
                        help="Envia un fichero a otro usuario, cuyo ID es especificado con la opción --dest_id. Por defecto, el archivo se subirá a SecureBox firmado y cifrado con las claves adecuadas para que pueda ser recuperado y verificado por el destinatario.",
                    )
    parser.add_argument('--source_id',
                        metavar='ID',
                        help="ID del emisor del fichero.",
                    )
    parser.add_argument('--dest_id',
                        metavar='ID',
                        help="ID del receptor del fichero.",
                    )
    parser.add_argument('--list_files',
                        action='store_true',
                        help="Lista todos los ficheros pertenecientes al usuario.",
                    )    
    parser.add_argument('--download',  
                        metavar='ID_FICHERO',
                        help="Recupera un fichero con ID id_fichero del sistema (este ID se genera en la llamada a upload, y debe ser comunicado al receptor). Tras ser descargado, debe ser verificada la firma y, después, descifrado el contenido.", 
                    )
    parser.add_argument('--delete_file',
                        metavar='ID_FICHERO',
                        help="Borra un fichero del sistema.",
                    )

    # Cifrado y firma de ficheros local
    parser.add_argument('--encrypt',
                        metavar='FICHERO',
                        help="	Cifra un fichero, de forma que puede ser descifrado por otro usuario, cuyo ID es especificado con la opción --dest_id.",
                    )
    parser.add_argument('--sign',
                        metavar='FICHERO',
                        help="Firma un fichero.",
                    )
    parser.add_argument('--enc_sign',
                        metavar='FICHERO',
                        help="Cifra y firma un fichero, combinando funcionalmente las dos opciones anteriores.",
                    )
    parser.add_argument('--verify_sign',
                        metavar='FICHERO',
                        help="Verifica la firma de un fichero, firmado por otro usuario, cuyo ID es especificado con la opción --source_id.",
                    )
    parser.add_argument('--verify_enc_sign',
                        metavar='FICHERO',
                        help="Descifra y comprueba la la firma de un fichero, firmado por otro usuario, cuyo ID es especificado con la opción --source_id.",
                    )
    parser.add_argument('--decrypt',
                        metavar='FICHERO',
                        help="Descifra un fichero, cifrado por otro usuario.",
                    )

    # parseo de argumentos
    argsret = parser.parse_args()

    # comprobación de que los requisitos se cumplen
    if len(sys.argv) < 2:
        parser.error("Introduce algún argumento")

    if argsret.create_id and len(argsret.create_id) not in [2, 3]:
        parser.error('--create_id tiene 2 ó 3 valores')

    if (not argsret.dest_id) \
        and (argsret.upload or argsret.encrypt or argsret.enc_sign):
        parser.error('Es necesario el campo --dest_id')

    if (not argsret.source_id) \
        and (argsret.download or argsret.verify_sign or argsret.verify_enc_sign):
        parser.error('Es necesario el campo --source_id')

    return argsret

def securebox_client_main():
    args = parse_args()

    if args.create_id:
        if len(args.create_id) == 2: 
            create_id(
                name=args.create_id[0], 
                email=args.create_id[1],
            )
        else:
            create_id(
                name=args.create_id[0],
                email=args.create_id[1], 
                alias=args.create_id[2],
            )

    elif args.search_id:
        search_id(data_search = args.search_id)

    elif args.delete_id:
        delete_id(id = args.delete_id)

    elif args.upload:
        upload(
            file_name = args.upload,
            dest_id   = args.dest_id
        )

    elif args.delete_file:
        delete_file(file_id = args.delete_file)

    elif args.list_files:
        list_files()

    elif args.download:
        download(
            file_id   = args.download,
            source_id = args.source_id
        )

    elif args.encrypt:
        encrypt(
            file_name = args.encrypt, 
            dest_id = args.dest_id
        )

    elif args.sign:
        sign(file_name = args.sign)

    elif args.enc_sign:
        enc_sign(
            file_name = args.enc_sign, 
            dest_id   = args.dest_id
        )

    elif args.verify_sign:
        verify_sign(
            file_name = args.verify_sign, 
            source_id = args.source_id
        )

    elif args.verify_enc_sign:
        verify_enc_sign(
            file_name = args.verify_enc_sign, 
            source_id = args.source_id
        )

    elif args.decrypt:
        decrypt(file_name = args.decrypt)
        
    elif args.generate_token:
        generate_token(
            nia=args.generate_token[0],
            nick=args.generate_token[1],
        )


if __name__ == "__main__":
    try:
        securebox_client_main()
    except SecureboxException as e:
        print(e)
