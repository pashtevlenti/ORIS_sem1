package ru.itis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itis.crud.model.*;
import ru.itis.crud.service.*;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/updateProfile")
public class UpdateProfileServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(UpdateProfileServlet.class);

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        UserService userService = (UserService) request.getServletContext().getAttribute("userService");
        CoachService coachService = (CoachService) request.getServletContext().getAttribute("coachService");
        WorkerService workerService = (WorkerService) request.getServletContext().getAttribute("workerService");
        SportsmanService sportsmanService = (SportsmanService) request.getServletContext().getAttribute("sportsmanService");

        try {
            User user = (User) request.getSession().getAttribute("user");
            user.setName(request.getParameter("name"));
            user.setAddress(request.getParameter("address"));
            user.setPhone(request.getParameter("phone"));
            user.setAge(Integer.parseInt(request.getParameter("age")));

            if (user.getSportsman() != null){
                Sportsman sportsman = sportsmanService.getByUserId(user.getId());
                sportsman.setRank(request.getParameter("rank"));

                sportsmanService.update(user.getId(),sportsman);
                user.setSportsman(sportsman);
                userService.update(user);

                request.getSession().setAttribute("user", user);
                request.getRequestDispatcher("/sportsman").forward(request,response);
            }
            else if (user.getCoach() != null){
                Coach coach = coachService.getByUserId(user.getId());
                coach.setRank(request.getParameter("rank"));

                coachService.update(user.getId(),coach,request.getParameter("sport"));
                user.setCoach(coachService.getByUserId(user.getId()));
                userService.update(user);

                request.getSession().setAttribute("user", user);
                request.getRequestDispatcher("/coach").forward(request,response);
            }
            else if (user.getWorker() != null){
                Worker worker = workerService.getByUserId(user.getId());
                worker.setPost(request.getParameter("post"));

                workerService.update(user.getId(),worker);
                user.setWorker(worker);
                userService.update(user);

                request.getSession().setAttribute("user", user);
                request.getRequestDispatcher("/worker").forward(request,response);
            }


}       catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        SportService sportService = (SportService) request.getServletContext().getAttribute("sportService");
        try{
            User user = (User) request.getSession().getAttribute("user");
            logger.info(user);
            if (user.getCoach() != null){

                List<Optional<Sport>> sports = sportService.getAll();
                if (user.getCoach().getSportId() != 0){
                    Optional<Sport> sportReplace = sports.get((int) (user.getCoach().getSportId() - 1));
                    Optional<Sport> sport0 = sports.get(0);
                    sports.set(0, sportReplace);
                    sports.set((int) (user.getCoach().getSportId() - 1), sport0);
                }

                request.setAttribute("sports", sports);
                request.getRequestDispatcher("/jsps/editProfileCoach.jsp").forward(request,response);
            }
            else if (user.getWorker() != null){
                request.getRequestDispatcher("/jsps/editProfileWorker.jsp").forward(request,response);
            }
            else if (user.getSportsman() != null){
                request.getRequestDispatcher("/jsps/editProfileSportsman.jsp").forward(request,response);
            }
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
