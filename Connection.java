import java.net.*;
import java.io.*;

// Handles a single connection from a client to the server
public class Connection implements Runnable {
    private Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            DataInputStream input = new DataInputStream(socket.getInputStream());
            while (socket.isConnected()) {
                System.out.println("<<< " + input.readUTF());
            }
        } catch (IOException e) {
            System.out.println("A connection was dropped");
        }
    }
}
