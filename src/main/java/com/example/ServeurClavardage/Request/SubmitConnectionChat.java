package com.example.ServeurClavardage.Request;

import app.insa.clav.Messages.MessageInit;
import app.insa.clav.Messages.MessageSrvTCP;
import com.example.ServeurClavardage.Support.Connexion;
import com.example.ServeurClavardage.Support.SharedInformation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "SubmitConnectionChat", value = "/SubmitConnectionChat")
public class SubmitConnectionChat extends HttpServlet {
    private String message;
    private SharedInformation sh;

    public void init() {
        message = "Ajout d'une co";
        this.sh = SharedInformation.getInstance();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        StringBuilder resp = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                resp.append(responseLine.trim());
            }
        }
        MessageSrvTCP msgSrv = gson.fromJson(resp.toString(), MessageSrvTCP.class);
        response.setContentType("application/json");
        System.out.println("Submit connection Chat avec " + msgSrv);
        if (this.sh.existConnexion(new Connexion(msgSrv.getUserId(), msgSrv.getId())))  {
            response.setStatus(201);
        } else {
            this.sh.addMsgInit(msgSrv.getUserId(), msgSrv.getId(), msgSrv.getMessageInit());
        }
        //System.out.println("Etat après le dépot de connexion : " + this.sh.getCoList(msgSrv.getUserId()));
    }

    public void destroy() {
    }
}

