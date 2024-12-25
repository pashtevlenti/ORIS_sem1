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

@WebServlet("/addGroup")
public class AddGroupServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response){

        GroupSportsmanService groupSportsmanService = (GroupSportsmanService) request.getServletContext().getAttribute("groupSportsmanService");
        User user = (User) request.getSession().getAttribute("user");
        String[] selectedAthleteIds = request.getParameterValues("athleteIds");

        GroupSportsman groupSportsman = new GroupSportsman();
        groupSportsman.setGroupName(request.getParameter("groupName"));
        groupSportsman.setCoachId(user.getCoach().getId());

        Long id = groupSportsmanService.create(groupSportsman);

        if (selectedAthleteIds != null) {
            for (String athleteId : selectedAthleteIds) {
                groupSportsmanService.createGroupSportsmanToSportsman(Long.parseLong(athleteId), id);
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
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        SportsmanService sportsmanService = (SportsmanService) request.getServletContext().getAttribute("sportsmanService");
        User user = (User) request.getSession().getAttribute("user");

        List<User> users = sportsmanService.getSportsmenByCoach(user.getCoach().getId());
        request.setAttribute("athletes", users);
        try {
            request.getRequestDispatcher("/jsps/add_groups.jsp").forward(request,response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
