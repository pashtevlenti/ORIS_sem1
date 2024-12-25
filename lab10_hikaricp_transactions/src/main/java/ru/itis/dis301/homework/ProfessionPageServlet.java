package ru.itis.dis301.homework;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

@WebServlet("/profession")
public class ProfessionPageServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(ProfessionPageServlet.class);

    private static int count = 0;

    private ProfessionRepository repository = new ProfessionRepository();

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Profession> professions = repository.find25(request.getParameter("search"), count);
        try{
            if (request.getParameter("next") != null) {
                count += 25;
                professions = repository.find25(request.getParameter("next"), count);
                logger.info(request.getParameter("next"));
            }
            if ((request.getParameter("previous") != null) && (count != 0)){
                count -= 25;
                professions = repository.find25(request.getParameter("previous"),count);
                logger.info(request.getParameter("previous"));
            }

            request.setAttribute("professions", professions);


            request.getRequestDispatcher("profession.ftl").forward(request, response);
        } catch (IOException e) {
            logger.error(e);
        } catch (ServletException e) {
            logger.error(e);
        }


    }

}
