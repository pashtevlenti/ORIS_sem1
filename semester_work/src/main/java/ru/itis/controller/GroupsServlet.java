package ru.itis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itis.crud.model.GroupSportsman;
import ru.itis.crud.model.User;
import ru.itis.crud.service.GroupSportsmanService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/groups")
public class GroupsServlet extends HttpServlet {

    Logger logger = LogManager.getLogger(GroupsServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        GroupSportsmanService groupSportsmanService = (GroupSportsmanService) request.getServletContext().getAttribute("groupSportsmanService");
        List<Optional<GroupSportsman>> groups = null;
        if (user.getCoach() != null) {
            groups = groupSportsmanService.getByCoachId(user.getCoach().getId());

        }
        else if (user.getSportsman() != null){
            groups = groupSportsmanService.getBySportsmanId(user.getSportsman().getId());
        }

        try{
            logger.info(groups);
            request.setAttribute("groups", groups);
            request.getRequestDispatcher("jsps/groups.jsp").forward(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        doGet(request, response);
    }
}
