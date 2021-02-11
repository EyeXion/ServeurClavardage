package com.example.ServeurClavardage;

import app.insa.clav.Core.Utilisateurs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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