package com.example.ServeurClavardage.Request;

import app.insa.clav.Messages.Message;
import app.insa.clav.Messages.MessageRetourSrvTCP;
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

@WebServlet(name = "GetMessageChat", value = "/GetMessageChat")
public class GetMessageChat extends HttpServlet {
    private String message;
    private SharedInformation sh;

    public void init() {
        message = "Récupération des chat message";
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
        MessageRetourSrvTCP msgs = this.sh.getMessageList(msgSrv.getUserId(), msgSrv.getId());
        //System.out.println("Demande des messages avec " + msgSrv + " : renvoyé -> " + msgs);
        String param = gson.toJson(msgs);
        PrintWriter out = response.getWriter();
        out.print(param);
    }

    public void destroy() {
    }
}