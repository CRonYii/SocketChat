package ca.bcit.cst.rongyi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The server of the chat.
 * 
 * @author Rongyi Chen
 * @version 0.1
 */
public class ChatServer {

    ServerSocket serverSocket;
    Socket clientSocket;
    PrintStream out;
    BufferedReader in;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is up and running.");
            clientSocket = serverSocket.accept();
            out = new PrintStream(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("New connection is established.");
            String msg;
            while (!(msg = in.readLine()).equals("")) {
                System.out.println(msg);
                out.println("Message Received");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            serverSocket.close();
            clientSocket.close();
            out.close();
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Drives the program.
     * 
     * @param args
     *            unused
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println("Starting test");
        ChatServer server = new ChatServer();
        server.start(6666);
        server.stop();
    }

}
