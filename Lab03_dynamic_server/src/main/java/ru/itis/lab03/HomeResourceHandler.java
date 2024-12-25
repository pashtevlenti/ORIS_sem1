package ru.itis.lab03;

import java.util.Map;

public class HomeResourceHandler implements IResourceHandler {
    @Override
    public ResponceContent handle(Map<String, String> params) {
        ResponceContent responceContent = new ResponceContent();
        responceContent.setMimeType("text/html; charset=utf-8");

        String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
                "        \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\"/>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n" +
                "    <title>Home page</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<h1>Домашняя HTML страница</h1>\n" +
                "</body>\n" +
                "</html>";

        responceContent.setContent(content.getBytes());

        return responceContent;
    }
}
