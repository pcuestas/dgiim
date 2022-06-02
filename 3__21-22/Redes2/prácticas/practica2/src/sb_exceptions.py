'''
    Excepciones de securebox_client
'''


class SecureboxException(Exception):
    def __init__(self, msg=None):
        super().__init__()
        if msg:
            self.securebox_exception_msg = msg+'\n'
        else:
            self.securebox_exception_msg = ''

    def __str__(self) -> str:
        return "ERROR\n" + self.securebox_exception_msg


class RequestException(SecureboxException):
    def __init__(self, json=None, exception=None):
        if json:
            self.json_str = '\t{} {}: {}'.format(
                json['http_error_code'],
                json['error_code'],
                json['description']
            )
        else:
            self.json_str = None
        self.exception = exception
        super().__init__()

    def __str__(self) -> str:
        if self.json_str:
            return super().__str__() + self.json_str
        else:
            return super().__str__() \
                + 'Error comunicando con el servidor:\n\t'\
                + str(self.exception)


class InvalidSignature(SecureboxException):
    def __init__(self, error):
        super().__init__('Error al verificar la firma: ' + str(error))


class InvalidDecryption(SecureboxException):
    def __init__(self, error):
        super().__init__(
            'Error al desencriptar: ' + str(error)
        )


class FileException(SecureboxException):
    def __init__(self, ioerror):
        super().__init__('Error de fichero:\n\t' + str(ioerror))
