package com.example.ServeurClavardage;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import app.insa.clav.Core.Utilisateurs;
import app.insa.clav.Messages.MessagePseudo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@WebServlet(name = "submitConnectionOutdoor", value = "/submitConnectionOutdoor")
public class SubmitConnectionOutdoor extends HttpServlet {
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
            System.out.println(resp.toString());
        }
        Utilisateurs newUser = gson.fromJson(resp.toString(), Utilisateurs.class);
        newUser.update();
        this.sh.addOutdoorUser(newUser);

    }

    public void destroy() {
    }
}