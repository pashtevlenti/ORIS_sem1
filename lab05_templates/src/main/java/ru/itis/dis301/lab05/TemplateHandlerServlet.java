package ru.itis.dis301.lab05;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

@WebServlet("*.html")
public class TemplateHandlerServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("name", request.getParameter("name"));
        request.setAttribute("login", request.getParameter("login"));
        request.setAttribute("password", request.getParameter("password"));

//        String contextPath = request.getContextPath();
//        String pathInfo = request.getPathInfo();
//        String servletPath = request.getServletPath();
//
//        Iterator<String> iter = request.getAttributeNames().asIterator();
//        while (iter.hasNext()) {
//            String name = iter.next();
//            System.out.println(name);
//            Object value = request.getAttribute(name);
//            System.out.println(value);
//        }

        InputStream inputStream = TemplateHandlerServlet.class.getClassLoader().getResourceAsStream(request.getServletPath());

        try {
            byte[] content = inputStream.readAllBytes();

            String contentTemplate = new String(content);
            char flag = 0;
            StringBuilder stringBuilder = new StringBuilder();
            if (contentTemplate.contains("$")) {
                for (char c : contentTemplate.toCharArray()) {
                    System.out.println(c + " " + flag);
                    if (c == '$') {
                        flag = c;
                        continue;
                    }

                    if ((c == '{') && (flag == '$')) {
                        flag = c;
                        continue;
                    }

                    if ((flag == '{') && (c != '}')) {
                        stringBuilder.append(c);
                    }
                    else if ((flag == '{')) {
                        System.out.println("stringBuilder.toString() = " + stringBuilder.toString());
                        contentTemplate = contentTemplate.replace(
                                "${%s}".formatted(stringBuilder.toString()),
                                request.getAttribute(stringBuilder.toString()).toString());
                        stringBuilder = new StringBuilder();
                        flag = c;
                    }
                    else if (c != '{') flag = c;
                }
            }
            response.getWriter().write(contentTemplate);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request,response);
    }
}
