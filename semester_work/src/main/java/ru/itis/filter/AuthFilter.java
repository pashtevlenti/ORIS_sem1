package ru.itis.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.crud.model.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        String requestURI = httpRequest.getServletPath();

        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        List<String> excludedUrls = Arrays.asList("/check-register","/check-login","/index","/register","/login", "/logout", "/resources/","/static/");
        List<String> sportsmanUrls = Arrays.asList("/sportsman","/updateProfile","/trainings","/groups","/deleteCoach","/assignCoach","/coaches");
        List<String> coachUrls = Arrays.asList("/coach","/updateProfile","/trainings","/groups","/addGroup","/addTrainingToGroup","/deleteGroup","/addSportsmanToGroup","/deleteSportsmanFromGroup","/deleteTrainingFromGroup");

        boolean isExcluded = excludedUrls.stream()
                .anyMatch(pattern -> requestURI.startsWith(pattern));
        boolean isSportsman = sportsmanUrls.stream()
                .anyMatch(pattern -> requestURI.startsWith(pattern));
        boolean isCoach = coachUrls.stream()
                .anyMatch(pattern -> requestURI.startsWith(pattern));

        if (isExcluded) {
            chain.doFilter(request, response);
        }
        else if (isLoggedIn && !httpRequest.getServletPath().equals("/")) {
            User user = (User) session.getAttribute("user");
            if ((user.getSportsman() != null) &&
                    (isSportsman)) {
                chain.doFilter(request, response);
            } else if ((user.getCoach() != null) &&
                    (isCoach)) {
                chain.doFilter(request, response);
            } else if ((user.getWorker() != null) &&
                    ((httpRequest.getServletPath().startsWith("/worker")) ||
                            (httpRequest.getServletPath().startsWith("/updateProfile")))){
                chain.doFilter(request, response);
            }
            else {
                httpResponse.sendRedirect("/semester-work/login");
            }
        }
        else{
            httpResponse.sendRedirect("/semester-work/index");
        }

    }


}


