package ru.itis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.crud.service.GroupSportsmanService;
import ru.itis.crud.service.ScheduleTrainingService;

import java.io.IOException;

@WebServlet("/deleteTrainingFromGroup")
public class DeleteTrainingFromGroupServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response){

        ScheduleTrainingService scheduleTrainingService = (ScheduleTrainingService) request.getServletContext().getAttribute("trainingService");
        Long groupId = Long.parseLong(request.getParameter("groupId"));
        Long trainingId = Long.parseLong(request.getParameter("trainingId"));

        scheduleTrainingService.deleteTrainingFromGroup(trainingId,groupId);

        try {
            request.getRequestDispatcher("/trainings").forward(request,response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

}
