package ca.bcit.cst.rongyi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * The client of the chat.
 * 
 * @author Rongyi Chen
 * @version 0.1
 *
 */
public class ChatClient {

    Socket clientSocket;
    PrintStream out;
    BufferedReader in;
    
    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Client is up and running.");
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        System.out.println("Message Sent");
        String response = in.readLine();
        System.out.println("Response revecived");
        return response;
    }
    
    public void stopConnection() throws IOException {
        clientSocket.close();
        out.close();
        in.close();
    }
    
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        ChatClient client = new ChatClient();
        client.startConnection("127.0.0.1", 6666);        
        try {
            String sendMsg;
            while (!(sendMsg = scan.nextLine()).equals("")) {
                client.sendMessage(sendMsg);
            }
            client.sendMessage("");
            client.stopConnection();
            scan.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
