import java.util.*;
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
        } catch (IOException e) {
            System.out.println("Connection was interrupted.");
            System.exit(1);
        }

        // Wait and connect to a clients indefinitely
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("<-- Incoming connection from " + socket.getInetAddress());
                new Thread(new Connection(socket)).start();
            } catch (IOException e) {
                // Ignore failed incoming connection
                continue;
            }
        }
    }
}
