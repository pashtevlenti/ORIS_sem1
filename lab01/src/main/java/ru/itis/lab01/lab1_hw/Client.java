package ru.itis.lab01.lab1_hw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try{
            System.out.println("Pls enter a message");
            while (true){
                Socket clientSocket = new Socket("127.0.0.1",50000);
                Scanner in = new Scanner(System.in);
                String message = in.nextLine() + "\n";

                OutputStream outputStream = clientSocket.getOutputStream();
                outputStream.write(message.getBytes(StandardCharsets.UTF_8));

                InputStream inputStream = clientSocket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String messageNew = bufferedReader.readLine();

                clientSocket.close();


                if (messageNew.equals("exit")){
                    System.out.println("Пока,пока!");
                    break;
                }
                System.out.println(messageNew);

            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



}
