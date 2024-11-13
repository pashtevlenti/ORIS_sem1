package ru.itis.lab02_httpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class Server {
    public static final int SERVER_PORT = 5000;
    public static final String ROOT_DIRECTORY = "html/";

    public static void main (String[] args){
        try {
            ServerSocket server = new ServerSocket(SERVER_PORT);
            System.out.println("start server");

            Socket clientSocket = server.accept();
            System.out.println("connected " + clientSocket.getInetAddress() +
                    ":" + clientSocket.getPort());

            ClientHandler handler = new ClientHandler(clientSocket);
            new Thread(handler).start();

            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
