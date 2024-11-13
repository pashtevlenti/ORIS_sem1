package ru.itis.lab01.lab1_01;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable {

    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        try {
            System.out.println("connected " + clientSocket.getInetAddress() +
                    ":" + clientSocket.getPort());

            InputStream inputStream = clientSocket.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String message = bufferedReader.readLine();

            // Ответ
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write("Был рад вопросу".getBytes(StandardCharsets.UTF_8));

            System.out.println(message);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
