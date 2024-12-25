package ru.itis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.crud.model.GroupSportsman;
import ru.itis.crud.model.User;
import ru.itis.crud.service.CoachService;
import ru.itis.crud.service.GroupSportsmanService;
import ru.itis.crud.service.SportsmanService;

import java.io.IOException;
import java.util.List;

@WebServlet("/addSportsmanToGroup")
public class AddSportsmanToGroupServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        SportsmanService sportsmanService = (SportsmanService) request.getServletContext().getAttribute("sportsmanService");
        User user = (User) request.getSession().getAttribute("user");

        List<User> users = sportsmanService.getSportsmenByCoachAndGroupId(
                user.getCoach().getId(),
                Long.parseLong(request.getParameter("groupId")));

        request.setAttribute("groupName", request.getParameter("groupName"));
        request.setAttribute("athletes", users);
        request.setAttribute("groupId", request.getParameter("groupId"));
        try {
            request.getRequestDispatcher("/jsps/add_sportsman.jsp").forward(request,response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        GroupSportsmanService groupSportsmanService = (GroupSportsmanService) request.getServletContext().getAttribute("groupSportsmanService");
        String[] selectedAthleteIds = request.getParameterValues("athleteIds");

        if (selectedAthleteIds != null) {
            for (String athleteId : selectedAthleteIds) {
                groupSportsmanService.createGroupSportsmanToSportsman(
                        Long.parseLong(athleteId),
                        Long.parseLong(request.getParameter("groupId")));
            }
        }
        try {
            request.getRequestDispatcher("/groups").forward(request,response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

}
