package com.example.ServeurClavardage;

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.example.ServeurClavardage.Messages.MessagePseudo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@WebServlet(name = "getAllUsers", value = "/getAllUsers")
public class GetAllUsers extends HttpServlet {
    private String message;
    private SharedInformation sh;

    public void init() {
        message = "Hello World!";
        this.sh = SharedInformation.getInstance();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        ArrayList<Utilisateurs> users = new ArrayList<>(this.sh.getOutdoorUsersList());
        users.addAll(this.sh.getIndoorUsersList());
        String param = gson.toJson(users);
        PrintWriter out = response.getWriter();
        out.print(param);
    }

    public void destroy() {
    }
}