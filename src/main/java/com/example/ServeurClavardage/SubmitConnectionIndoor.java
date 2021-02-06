package com.example.ServeurClavardage;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.example.ServeurClavardage.Messages.Message;
import com.example.ServeurClavardage.Messages.MessagePseudo;
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
            System.out.println(resp.toString());
        }
        Utilisateurs newUser = gson.fromJson(resp.toString(),Utilisateurs.class);
        System.out.println(newUser);
        for (Utilisateurs user : this.sh.getOutdoorUsersList()){
            DatagramSocket socket = new DatagramSocket();
            MessagePseudo msg = new MessagePseudo(4, newUser.getInetAddress(), newUser.getPort(), user.getInetAddress(), user.getPort(), user.getPseudo(),user.getId());
            try {
                //Envoi du pseudo sur le reseau local à l'adresse IP dest sur le port dest
                byte[] buffer = "".getBytes();
                ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream objectOutStream = new ObjectOutputStream(byteOutStream);
                    objectOutStream.writeObject(msg);
                    objectOutStream.close();
                    buffer = byteOutStream.toByteArray();
                } catch (IOException e1) {
                    System.out.println("Exception serialisation de l'objet envoi message");
                }
                //InetAddress broadcastAdress = InetAddress.getByAddress("255.255.255.255".getBytes());
                DatagramPacket packet = new DatagramPacket(buffer,buffer.length,msg.destIP,msg.destPort);
                socket.send(packet);
            }
            catch (UnknownHostException e){
                System.out.println("Unknown host dans broadcast address");
                e.printStackTrace();
            }
            catch (IOException e){
                System.out.println("IOException send pseudo");
                e.printStackTrace();
            }
        }
        this.sh.addIndoorUser(newUser);
    }

    public void destroy() {
    }
}