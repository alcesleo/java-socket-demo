import java.util.*;
import java.net.*;
import java.io.*;

public class Server implements MessageReceiver {
    private int port;
    private ServerSocket serverSocket;
    private ArrayList<Connection> connections;

    public static void main(String[] args) {
        Server server = new Server(5555);

        System.out.println("Starting server...");
        server.run();
    }

    public Server(int port) {
        this.port = port;
        this.connections = new ArrayList<Connection>();
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
                Connection client = new Connection(socket, this);
                connections.add(client);
                client.listenForMessages();
            } catch (IOException e) {
                // Ignore failed incoming connection
                continue;
            }
        }
    }

    public void messageReceived(String message) {
        System.out.println("Broadcasting message: " + message);
        broadcast(message);
    }

    private void broadcast(String message) {
        Iterator<Connection> itr = connections.iterator();

        while (itr.hasNext()) {
            Connection client = itr.next();

            // Remove the connection and move on if it has disconnected
            if (client.disconnected()) {
                itr.remove();
                continue;
            }

            client.sendMessage(message);
        }
    }
}
