package ru.itis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.crud.model.GroupSportsman;
import ru.itis.crud.service.CoachService;
import ru.itis.crud.service.GroupSportsmanService;

import java.io.IOException;
import java.util.List;

@WebServlet("/deleteCoach")
public class DeleteCoachServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        CoachService coachService = (CoachService) request.getServletContext().getAttribute("coachService");
        GroupSportsmanService groupService = (GroupSportsmanService) request.getServletContext().getAttribute("groupSportsmanService");
        Long sportsmanId = Long.parseLong(request.getParameter("sportsmanId"));
        String[] selectedCoachIds = request.getParameterValues("coachIds");


        if (selectedCoachIds != null) {
            for (String coachId : selectedCoachIds) {
                List<GroupSportsman> groupSportsmen = coachService.getGroupByCoachId(Long.parseLong(coachId));
                coachService.deleteCoachToSportsman(sportsmanId, Long.parseLong(coachId));
                for (GroupSportsman groupSportsman : groupSportsmen) {
                    groupService.deleteSportsmanFromGroup(groupSportsman.getId(),sportsmanId);
                }
            }
        }
        try {
            request.getRequestDispatcher("/coaches").forward(request,response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

}
