import java.net.*;
import java.io.*;

public class Client implements Runnable {
    private String host;
    private int port;
    BufferedReader keyboard;
    Socket socket;

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 5555);
        client.listenForIncomingCommunications();
        client.transmitUserCommunications();
    }

    public Client(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        keyboard = new BufferedReader(new InputStreamReader(System.in));

        socket   = new Socket(host, port);
        System.out.println("Connected to " + host + ":" + port);
        System.out.println();
    }

    public void listenForIncomingCommunications() {
        new Thread(this).start();
    }

    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());

            while (true) {
                String message = "<<< " + in.readUTF();
                System.out.println(message);
            }
        } catch (IOException e) {
            System.out.println("Connection was interrupted or failed.");
        }
    }

    public void transmitUserCommunications() {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            while (true) {
                String keyboardInput = keyboard.readLine();
                out.writeUTF(keyboardInput);
            }
        } catch (IOException e) {
            System.out.println("Connection was interrupted or failed.");
        }
    }

}
