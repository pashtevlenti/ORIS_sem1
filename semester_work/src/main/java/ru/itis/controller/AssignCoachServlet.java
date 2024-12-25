package ru.itis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.crud.service.CoachService;

import java.io.IOException;

@WebServlet("/assignCoach")
public class AssignCoachServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        CoachService coachService = (CoachService) request.getServletContext().getAttribute("coachService");
        Long sportsmanId = Long.parseLong(request.getParameter("sportsmanId"));
        String[] selectedCoachIds = request.getParameterValues("coachIds");

        if (selectedCoachIds != null) {
            for (String coachId : selectedCoachIds) {
                coachService.assignCoachToSportsman(sportsmanId, Long.parseLong(coachId));
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
