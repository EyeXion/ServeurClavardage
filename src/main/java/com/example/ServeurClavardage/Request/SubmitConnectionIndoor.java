package com.example.ServeurClavardage.Request;

import java.io.*;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import app.insa.clav.Core.Utilisateurs;
import com.example.ServeurClavardage.Support.SharedInformation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@WebServlet(name = "submitConnectionIndoor", value = "/submitConnectionIndoor")
public class SubmitConnectionIndoor extends HttpServlet {
    private String message;
    private SharedInformation sh;

    public void init() {
        message = "Hello World!";
        this.sh = SharedInformation.getInstance();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        StringBuilder resp = new StringBuilder();
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                resp.append(responseLine.trim());
            }
        }
        Utilisateurs newUser = gson.fromJson(resp.toString(), Utilisateurs.class);
        //System.out.println("Submit connection Indoor avec " + newUser);
        newUser.update();
        this.sh.addIndoorUser(newUser);
    }

    public void destroy() {
    }
}