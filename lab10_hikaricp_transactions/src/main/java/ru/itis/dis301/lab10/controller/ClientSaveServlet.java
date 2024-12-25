package ru.itis.dis301.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itis.dis301.lab10.model.Client;
import ru.itis.dis301.lab10.model.ClientInfo;
import ru.itis.dis301.lab10.service.ClientService;

import java.io.IOException;

@WebServlet("/client/save")
public class ClientSaveServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(ClientSaveServlet.class);

    private ClientService service = new ClientService();

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try{

            ClientInfo clientInfo = new ClientInfo();
            clientInfo.setAddress(request.getParameter("address"));
            clientInfo.setPassport(request.getParameter("passport"));
            clientInfo.setPhone(request.getParameter("phone"));

            Client client = new Client();
            client.setClientInfo(clientInfo);
            client.setName(request.getParameter("name"));
            client.setEmail(request.getParameter("email"));

            service.save(client);

            request.setAttribute("clients", service.findAll());

            request.getRequestDispatcher("/index.ftl").forward(request, response);

        } catch (IOException e) {
            logger.error(e);
        } catch (ServletException e) {
            logger.error(e);
        }
    }
}
