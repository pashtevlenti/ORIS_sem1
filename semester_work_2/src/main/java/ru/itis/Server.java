package ru.itis;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static final Map<String, GameRoom> rooms = new HashMap<>();
    
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running...");
            while (true) {
                new Thread(new ClientHandler(serverSocket.accept(),rooms)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





