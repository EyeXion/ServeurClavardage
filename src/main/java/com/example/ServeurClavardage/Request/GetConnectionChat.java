package com.example.ServeurClavardage.Request;

import app.insa.clav.Messages.MessageInit;
import app.insa.clav.Messages.MessageSrvTCP;
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
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@WebServlet(name = "GetConnectionChat", value = "/GetConnectionChat")
public class GetConnectionChat extends HttpServlet {
    private String message;
    private SharedInformation sh;

    public void init() {
        message = "Récupération des chat connexion";
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
        MessageSrvTCP msgSrv = gson.fromJson(resp.toString(), MessageSrvTCP.class);
        response.setContentType("application/json");
        ArrayList<MessageInit> msgs = this.sh.getCoList(msgSrv.getUserId());
        //System.out.println("Demande des connection avec " + msgSrv + " : renvoyé -> " + msgs);
        String param = gson.toJson(msgs);
        PrintWriter out = response.getWriter();
        out.print(param);
    }

    public void destroy() {
    }
}
