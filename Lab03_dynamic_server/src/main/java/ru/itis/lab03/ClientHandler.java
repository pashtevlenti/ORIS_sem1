package ru.itis.lab03;

import java.io.*;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    public static final String ROOT_DIRECTORY = "files/";

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {

            Map<String, IResourceHandler> resources = new HashMap<>();
            resources.put("/", new HomeResourceHandler());
            resources.put("/name", new GetResourceHandler());

            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String headerLine = bufferedReader.readLine();

            String[] firstLine = headerLine.split("\\s+");

            String method = firstLine[0];
            String uri = firstLine[1];
            String httpVers = firstLine[2];


            System.out.println(method + " " + uri + " " + httpVers);
            while (headerLine != null && !headerLine.equals("")) {
                headerLine = bufferedReader.readLine();
                System.out.println(headerLine);
            }

            Map<String,String> params = null;
            String[] split = uri.split("\\?");;
            if (split.length > 1) {
                String[] paramSplit = split[1].split("&");
                params = new HashMap<>();
                for (String param : paramSplit) {
                    params.put(param.split("=")[0], param.split("=")[1]);
                }
            }

            ResponceContent responceContent = null;
            IResourceHandler handler = resources.get(split[0]);
            if (handler != null) {
                responceContent = handler.handle(params);
            } else {
                File file = new File(ROOT_DIRECTORY + uri);
                if (file.exists()) {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        responceContent = new ResponceContent();
                        responceContent.setMimeType(URLConnection.guessContentTypeFromName(file.getName()));
                        responceContent.setContent(Files.readAllBytes(file.toPath()));
                    }
                }
            }

            String[] response;
            if (responceContent != null){
                response = new String[]{
                        "HTTP/1.1 200 OK\r\n",
                        "Server: NewSuperServer\r\n",
                        "Content-Type: " + responceContent.getMimeType() + "\r\n",
                        "Content-Length: " + responceContent.getContent().length + "\r\n",
                        "\r\n"};
            } else {
                response = new String[]{
                        "HTTP/1.1 404 Not Found\r\n",
                        "Server: NewSuperServer\r\n",
                        "\r\n"};
            }


            for (String responseHeaderLine : response) {
                clientSocket.getOutputStream().write(responseHeaderLine.getBytes());
            }
            if (responceContent != null) clientSocket.getOutputStream().write(responceContent.getContent());

            clientSocket.close();

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}