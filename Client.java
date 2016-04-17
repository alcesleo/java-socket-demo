import java.net.*;
import java.io.*;

public class Client {
    private String host;
    private int port;
    BufferedReader keyboard;

    public static void main(String[] args) {
        Client client = new Client("localhost", 5555);
        client.run();
    }

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        keyboard = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() {
        try {
            Socket socket           = new Socket(host, port);
            DataOutputStream out    = new DataOutputStream(socket.getOutputStream());

            System.out.println("Connected to " + host + ":" + port);
            System.out.println();

            while (true) {
                // Get input from keyboard
                System.out.print(">>> ");
                String keyboardInput = keyboard.readLine();

                if (keyboardInput.equals("ZZ")) {
                    System.out.println("--> Disconnected");
                    break;
                }

                out.writeUTF(keyboardInput);
            }
            socket.close();
        } catch (IOException e) {
            System.out.println("Connection was interrupted or failed.");
        }
    }

}
