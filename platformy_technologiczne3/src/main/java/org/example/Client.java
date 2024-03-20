package org.example;

import java.io.*;
import java.net.Socket;

public class Client{
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 3999);
             ClientInfo client = new ClientInfo(socket);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected!");

            Thread receivingThread = new Thread(() -> {
                try {
                    while (true) {
                        Message receivedMessage = (Message) client.getObjectInputStream().readObject();
                        System.out.println("Server: " + receivedMessage.getMessage());
                    }
                } catch (IOException | ClassNotFoundException e) { }
            });

            receivingThread.start();

            String consoleInput;

            while (true) {
                consoleInput = consoleReader.readLine();

                if(consoleInput.equalsIgnoreCase("exit")){
                    closingSocket(client);
                    break;
                }
                client.getObjectOutputStream().writeObject(new Message(consoleInput));
                client.getObjectOutputStream().flush();
            }

        } catch (Exception e) { }
    }

    public static void closingSocket(ClientInfo client) throws IOException {
        client.setClosed(true);
        client.getObjectOutputStream().close();
        client.getObjectInputStream().close();
        client.getSocket().close();
    }
}
