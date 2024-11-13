package ru.itis.lab02_httpserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String headerLine = bufferedReader.readLine();

            String[] firstLine = headerLine.split("\\s+");

            String method = firstLine[0];
            String uri = firstLine[1].substring(1);
            String httpVers = firstLine[2];

            System.out.println(method + " " + uri + " " + httpVers);
            while (headerLine != null && !headerLine.equals("")) {
                headerLine = bufferedReader.readLine();
                System.out.println(headerLine);
            }

            if (!httpVers.equals("HTTP/1.1")) {
                String[] response = {"HTTP/1.1 505 HTTP Version Not Supported\r\n", "Server: NewSuperServer\r\n", "\r\n"};

                for (String responseHeaderLine : response) {
                    clientSocket.getOutputStream().write(responseHeaderLine.getBytes());
                    clientSocket.getOutputStream().flush();
                }
                clientSocket.close();
            } else {
                File file = new File(Server.ROOT_DIRECTORY + uri);
                if (!file.exists()) {
                    String[] response = {"HTTP/1.1 404 Not Found\r\n", "Server: NewSuperServer\r\n", "\r\n"};

                    for (String responseHeaderLine : response) {
                        clientSocket.getOutputStream().write(responseHeaderLine.getBytes());
                        clientSocket.getOutputStream().flush();
                    }

                    clientSocket.close();
                } else {
                    try (FileInputStream fis = new FileInputStream(file)) {

                        byte[] buffer = Files.readAllBytes(file.toPath());
                        String[] response = {
                                "HTTP/1.1 200 OK\r\n",
                                "Server: NewSuperServer\r\n",
                                "Content-Type: text/html; charset=utf-8\r\n",
                                "Content-Length: " + buffer.length + "\r\n",
                                "\r\n"};

                        for (String responseHeaderLine : response) {
                            clientSocket.getOutputStream().write(responseHeaderLine.getBytes());
                            clientSocket.getOutputStream().flush();
                        }

                        clientSocket.getOutputStream().write(buffer);
                        clientSocket.getOutputStream().flush();

                        clientSocket.close();

                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}