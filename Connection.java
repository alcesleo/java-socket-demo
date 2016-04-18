import java.net.*;
import java.io.*;

// Handles a single connection from a client to the server
public class Connection implements Runnable {
    private Socket socket;
    private MessageReceiver receiver;

    public Connection(Socket socket, MessageReceiver receiver) {
        this.socket = socket;
        this.receiver = receiver;
    }

    public void listenForMessages() {
        new Thread(this).start();
    }

    public boolean disconnected() {
        return !socket.isConnected();
    }

    public boolean sendMessage(String message) {
        try {
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            if (disconnected()) {
                return false;
            }
            output.writeUTF(message);
        }
        catch (SocketException e) {
            // Fuck you Java
        }
        catch (IOException e) {
            System.out.println(e);
        }
        return true;
    }

    public void run() {
        try {
            DataInputStream input = new DataInputStream(socket.getInputStream());
            while (socket.isConnected()) {
                receiver.messageReceived(input.readUTF());
            }
        } catch (EOFException e) {
            System.out.println("A connection has been dropped.");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
