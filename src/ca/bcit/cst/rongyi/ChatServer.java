package ca.bcit.cst.rongyi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * The server of the chat.
 * 
 * @author Rongyi Chen
 * @version 0.2
 */
public class ChatServer {

    private ServerSocket serverSocket;
    List<ClientHandler> clientSockets = new ArrayList<>();

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is up and running.");
            while (true) {
                ClientHandler ch = new ClientHandler(serverSocket.accept());
                ch.start();
                clientSockets.add(ch);
                System.out.println("New connection established");
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            for (ClientHandler ch : clientSockets) {
                ch.stopConnection();
            }
            serverSocket.close();
            System.out.println("Server is compeletly shut down.");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    static class ClientHandler extends Thread {

        private Socket clientSocket;
        private PrintStream out;
        private BufferedReader in;
        private String inputLine = "New connection";

        public ClientHandler(Socket socket) {
            clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                out = new PrintStream(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while ((inputLine = in.readLine()) != null) {
                    out.println(inputLine);
                }
                stopConnection();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        public void stopConnection() {
            try {
                clientSocket.close();
                out.close();
                in.close();
                System.out.println("Connection closed.");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        public String toString() {
            return inputLine;
        }

    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.start(6666);
        server.stop();
    }

}
