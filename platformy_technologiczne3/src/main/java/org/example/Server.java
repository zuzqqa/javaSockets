package org.example;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import org.example.*;
import org.example.patterns.*;

public class Server {
    private static final int PORT = 3999;
    private static final Map<Integer, ClientInfo> clients = new ConcurrentHashMap<>();
    private static int nextClientId = 1;
    private static List<PatternMatcher> patternMatchers = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        patternMatchers.add(new PutPatternMatcher());
        patternMatchers.add(new GetPatternMatcher());
        patternMatchers.add(new OptionsPatternMatcher());
        patternMatchers.add(new HeadPatternMatcher());
        patternMatchers.add(new DeletePatternMatcher());
        patternMatchers.add(new PostPatternMatcher());

        // pula watkow wykonawczych, ktore automatycznie zwiekszaja i zmniejszaja swoja liczbe
        ExecutorService clientHandlingPool = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("ServerSocket awaiting connections...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connection from: " + socket + "!");

                int clientId = nextClientId++;
                ClientInfo clientInfo = new ClientInfo(socket);
                clients.put(clientId, clientInfo);

                ObjectInputStream objectInputStream = clientInfo.getObjectInputStream();
                ObjectOutputStream objectOutputStream = clientInfo.getObjectOutputStream();

                clientHandlingPool.execute(() ->
                        handleClient(clientId, objectInputStream, objectOutputStream));
            }
        } catch (EOFException e) {
            System.out.println("Server has been closed.");
        }
    }

    private static void handleClient(int clientId,
                                     ObjectInputStream objectInputStream,
                                     ObjectOutputStream objectOutputStream) {
        ClientInfo clientInfo = clients.get(clientId);
        try {
            while (!clientInfo.isClosed()) {
                Message receivedMessage = (Message) objectInputStream.readObject();
                System.out.println("Client " + clientId + ": " + receivedMessage.getMessage());

                if (receivedMessage.getMessage().equalsIgnoreCase("exit")) {
                    closingClient(clientId);
                    break;
                }

                sendMessageToClient(clientId, new Message(receivedMessage.getMessage()));
            }
        } catch (IOException | ClassNotFoundException e) { }
    }

    private static void sendMessageToClient(int clientId, Message message) {
        ClientInfo clientInfo = clients.get(clientId);
        if (clientInfo != null) {
            try {
                for (PatternMatcher matcher : patternMatchers) {
                    if (matcher.matches(message.getMessage())) {
                        String[] tokens = message.getMessage().split("\\s+");
                        if (matcher instanceof PutPatternMatcher) {
                            message.PUT(tokens[tokens.length - 1]);
                        } else if (matcher instanceof GetPatternMatcher) {
                            message.GET(tokens[tokens.length - 1]);
                        } else if (matcher instanceof OptionsPatternMatcher) {
                            message.OPTIONS(tokens[tokens.length - 1]);
                        } else if (matcher instanceof HeadPatternMatcher) {
                            message.HEAD(tokens[tokens.length - 1]);
                        } else if (matcher instanceof DeletePatternMatcher) {
                            message.DELETE(tokens[tokens.length - 1]);
                        } else if (matcher instanceof PostPatternMatcher) {
                            message.POST(tokens[tokens.length - 1]);
                        }
                        clientInfo.getObjectOutputStream().writeObject(message);
                        clientInfo.getObjectOutputStream().flush();
                        return;
                    }
                }

                message.BADREQUEST();
                clientInfo.getObjectOutputStream().writeObject(message);
                clientInfo.getObjectOutputStream().flush();
            } catch (IOException e) { }
        }
    }


    private static void closingClient(int clientId) {
        System.out.println("Closing client " + clientId + "...");

        ClientInfo clientInfo = clients.remove(clientId);
        if (clientInfo != null) {
            try {
                clientInfo.getObjectOutputStream().close();
                clientInfo.getObjectInputStream().close();
                clientInfo.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
