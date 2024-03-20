package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientInfo implements AutoCloseable{
    private final Socket socket;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;
    private volatile boolean closed = false;

    public ClientInfo(Socket socket) throws IOException {
        this.socket = socket;
        this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());
    }

    public Socket getSocket(){
        return socket;
    }

    public ObjectOutputStream getObjectOutputStream(){
        return objectOutputStream;
    }

    public ObjectInputStream getObjectInputStream(){
        return objectInputStream;
    }

    @Override
    public void close() throws Exception { }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isClosed() {
        return closed;
    }

}
