package ru.itis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.crud.model.GroupSportsman;
import ru.itis.crud.model.User;
import ru.itis.crud.service.GroupSportsmanService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/trainings")
public class TrainingsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        GroupSportsmanService groupSportsmanService = (GroupSportsmanService) request.getServletContext().getAttribute("groupSportsmanService");
        List<Optional<GroupSportsman>> groups = null;
        if (user.getCoach() != null) {
            groups = groupSportsmanService.getByCoachId(user.getCoach().getId());
        }
        else if (user.getSportsman() != null){
            groups = groupSportsmanService.getBySportsmanId(user.getSportsman().getId());
        }
        try {
            request.setAttribute("groups", groups);
            request.getRequestDispatcher("/jsps/trainings.jsp").forward(request,response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        doGet(request, response);
    }
}
