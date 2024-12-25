package ru.itis.lab03;

import java.util.Map;

public class GetResourceHandler implements IResourceHandler{
    @Override
    public ResponceContent handle(Map<String, String> params) {
        ResponceContent responceContent = new ResponceContent();
        responceContent.setMimeType("text/html; charset=utf-8");

//        String content = "<form>\n"+
//                "<label>Введите имя\n"+
//                "<input type=\"text\">\n"+
//                "</label>\n"+
//                "<button type=\"submit\">Войти</button\n"+
//                "</form>\n";
        String content = "<form action=\"/name\" method=\"get\">\n"+
                        "<input type=\"text\"  name=\"name\" placeholder=\"Поиск...\">\n"+
                        "<button type=\"submit\">Искать</button>\n"+
                        "</form>";


        content = out("name",params,content);
        content = out("last_name",params,content);

        responceContent.setContent(content.getBytes());

        return responceContent;
    }
    private static String out(String str, Map<String,String> params,String content) {
        if ((params != null) && (params.containsKey(str))) {
            content = content  +
                    "<body>\n" +
                    "<h1>"+ params.get(str) +"</h1>\n" +
                    "</body>\n" ;
        }
        return content;
    }
}
