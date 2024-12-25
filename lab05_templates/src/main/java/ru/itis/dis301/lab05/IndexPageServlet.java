package ru.itis.dis301.lab05;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("")
public class IndexPageServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        // Формирование параметров для шаблона


        try {
            // Перенаправляем запрос к сервлету-шаблонизатору
            // Указываем ресурс для обработки
            request.getRequestDispatcher("/template/name.html").forward(request, response);

        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
