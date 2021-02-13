package com.example.ServeurClavardage.Request;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import app.insa.clav.Core.Utilisateurs;
import com.example.ServeurClavardage.Support.SharedInformation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@WebServlet(name = "getOutdoorUsers", value = "/getOutdoorUsers")
public class GetOutdoorUsers extends HttpServlet {
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
        ArrayList<Utilisateurs> outdoorArrayList = this.sh.getOutdoorUsersList();
        //System.out.println("Demande des outdoorUser : renvoyé -> " + outdoorArrayList);
        String param = gson.toJson(outdoorArrayList);
        PrintWriter out = response.getWriter();
        out.print(param);
    }

    public void destroy() {
    }
}