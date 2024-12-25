package ru.itis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.crud.model.GroupSportsman;
import ru.itis.crud.model.ScheduleTraining;
import ru.itis.crud.model.User;
import ru.itis.crud.service.GroupSportsmanService;
import ru.itis.crud.service.ScheduleTrainingService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/addTrainingToGroup")
public class AddTrainingToGroupServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        GroupSportsmanService groupSportsmanService = (GroupSportsmanService) request.getServletContext().getAttribute("groupSportsmanService");
        try {

            List<Optional<GroupSportsman>> groupSportsmen = groupSportsmanService.getByCoachId(user.getCoach().getId());
            request.setAttribute("groups", groupSportsmen);
            request.getRequestDispatcher("/jsps/add_trainings.jsp").forward(request,response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        ScheduleTrainingService scheduleTrainingService = (ScheduleTrainingService) request.getServletContext().getAttribute("trainingService");

        ScheduleTraining scheduleTraining = new ScheduleTraining();
        scheduleTraining.setDayOfWeek(request.getParameter("day"));
        scheduleTraining.setTime(request.getParameter("time"));
        scheduleTraining.setCoachId(user.getCoach().getId());


        Long trainingId = scheduleTrainingService.getByDayOfWeekAndTimeAndCoachId(request.getParameter("day"),
                request.getParameter("time"),
                user.getCoach().getId());

        if (trainingId == -1)  {
            trainingId = scheduleTrainingService.create(scheduleTraining);
        }

        String[] selectedGroupIds = request.getParameterValues("groupIds");

        if (selectedGroupIds != null) {
            for (String groupId : selectedGroupIds) {
                boolean scheduleTrainingGroupSportsman = scheduleTrainingService.getByTrainingIdAndGroupId(trainingId,Long.parseLong(groupId));
                if (!scheduleTrainingGroupSportsman){
                    scheduleTrainingService.createScheduleTrainingGroupSportsman(trainingId,Long.parseLong(groupId));
                }
            }
        }
        try {
            request.getRequestDispatcher("/trainings").forward(request,response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

}
