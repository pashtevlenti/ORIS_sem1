package ru.itis.lab01.lab1_hw;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    public static final int SERVER_PORT = 50000;

    public static void main(String[] args) {
        try{
            ServerSocket server = new ServerSocket(SERVER_PORT);
            System.out.println("Start server");

            while (true){
                Socket clientSocket = server.accept();

                InputStream inputStream = clientSocket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String message = bufferedReader.readLine();
                System.out.println(message);

                OutputStream outputStream = clientSocket.getOutputStream();
                switch (message.toLowerCase()) {
                    case "привет": outputStream.write("Привет, что бы ты хотел задать?\n".getBytes(StandardCharsets.UTF_8)); break;
                    case "как дела?": outputStream.write("Отлично\n".getBytes(StandardCharsets.UTF_8)); break;
                    case "как погода": outputStream.write("Солнечная и очень тепло\n".getBytes(StandardCharsets.UTF_8)); break;
                    case "2+2": outputStream.write("4\n".getBytes(StandardCharsets.UTF_8)); break;
                    case "какой твой любимый цвет?": outputStream.write("Голубой!\n".getBytes(StandardCharsets.UTF_8)); break;
                    case "exit": outputStream.write("exit\n".getBytes(StandardCharsets.UTF_8)); break;
                    default: outputStream.write("У меня нет ответа на этот вопрос\n".getBytes(StandardCharsets.UTF_8));
                }
                clientSocket.close();
                if (message.equals("exit")){
                    System.out.println("Stop server");
                    server.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
