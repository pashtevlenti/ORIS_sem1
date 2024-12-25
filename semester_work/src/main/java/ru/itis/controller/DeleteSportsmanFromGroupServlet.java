package ru.itis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.crud.service.GroupSportsmanService;

import java.io.IOException;

@WebServlet("/deleteSportsmanFromGroup")
public class DeleteSportsmanFromGroupServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response){

        GroupSportsmanService groupSportsmanService = (GroupSportsmanService) request.getServletContext().getAttribute("groupSportsmanService");
        Long groupId = Long.parseLong(request.getParameter("groupId"));
        Long sportsmanId = Long.parseLong(request.getParameter("sportsmanId"));

        groupSportsmanService.deleteSportsmanFromGroup(groupId,sportsmanId);

        try {
            request.getRequestDispatcher("/groups").forward(request,response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

}
