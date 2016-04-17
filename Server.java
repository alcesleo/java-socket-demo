import java.net.*;
import java.io.*;

public class Server {
    private int port;
    private ServerSocket serverSocket;

    public static void main(String[] args) {
        Server server = new Server(5555);

        System.out.println("Starting server...");
        server.run();
    }

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(5000);

            // Wait and connect to a client
            Socket socket = serverSocket.accept();
            DataInputStream input = new DataInputStream(socket.getInputStream());

            System.out.println("Incoming connection from " + socket.getInetAddress());
            System.out.println();

            while (socket.isConnected()) {
                System.out.println("<<< " + input.readUTF());
            }

            // Shut down
            System.out.println();
            System.out.println("Server shutting down...");
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Connection was interrupted.");
        }
    }
}
