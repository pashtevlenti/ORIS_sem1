package ru.itis.lab01.lab1_01;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Client {

    public static void main(String[] args) {

        try {
            Socket clientSocket = new Socket("127.0.0.1",50000);

            System.out.println("From client: connected");

            OutputStream outputStream = clientSocket.getOutputStream();

            outputStream.write("Hello!\n".getBytes(StandardCharsets.UTF_8));

            clientSocket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
