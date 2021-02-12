package com.example.ServeurClavardage;

import app.insa.clav.Messages.MessageInit;
import app.insa.clav.Messages.MessageSrvTCP;
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
            System.out.println(resp.toString());
        }
        MessageSrvTCP msgSrv = gson.fromJson(resp.toString(), MessageSrvTCP.class);
        response.setContentType("application/json");
        this.sh.addMsgInit(msgSrv.getUserId(), (MessageInit) msgSrv.getMessage());
    }

    public void destroy() {
    }
}

