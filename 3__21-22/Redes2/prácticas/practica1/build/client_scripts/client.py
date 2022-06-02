import socket
import sys

'''
este cliente manda un mensaje que no corresponde a una petición http, 
por lo que el servidor debe, tras un tiempo de espera, cerrar el socket,
para impedir que nuestros hilos se bloqueen esperando a posibles
peticiones malintencionadas que puedan intentar bloquear el servidor
'''

SIZERCV = 4096

__message = b"GET / HTTP/1.1\r\n\r\n"

if __name__ == "__main__":
    # We initialice our variables
    serverIP = "127.0.0.1"
    try:
        serverPort = 11000 if len(sys.argv) <= 1 else int(sys.argv[1])
    except ValueError:
        serverPort = 11000

    # Create a TCP/IP socket
    print ("Creating our socket.")
    clientSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    print ("Connecting to \nIP:", serverIP, "\nPort:", serverPort)
    server_address = (serverIP, serverPort)
    clientSocket.connect(server_address)

    # Send data
    message = __message
    print ("Sending:", message)

    clientSocket.send(message)
    response = clientSocket.recv(SIZERCV)
    print ("Response:", response)
    print ("Closing socket.")
    clientSocket.close()