package com.example.ServeurClavardage;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;


//Classe qui permet d'identifier un utilisateur

public class Utilisateurs implements Comparable{
    private String pseudo;
    private InetAddress inetAddress;
    private int id;
    private int port;
    private String login;
    private boolean isOutdoor;

    public Date getLatestUpdate() {
        return latestUpdate;
    }

    public void setLatestUpdate(Date latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    private Date latestUpdate;

    public Utilisateurs(String pseudo, InetAddress inetAddress, int id, int port, boolean isOutdoor) {
        this.pseudo = pseudo;
        this.inetAddress = inetAddress;
        this.id = id;
        this.port = port;
        this.isOutdoor = isOutdoor;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPseudo() {
        return pseudo;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public int getId() {
        return id;
    }

    public int getPort() {
        return port;
    }


    public boolean isOutdoor() {
        return isOutdoor;
    }

    public void setOutdoor(boolean outdoor) {
        isOutdoor = outdoor;
    }


    @Override
    public String toString(){
        return Integer.toString(this.id) + "|" + this.pseudo + "|" + this.inetAddress.toString() + "|" + Integer.toString(this.port);
    }


    @Override
    public boolean equals(Object obj) {
        Utilisateurs aux = (Utilisateurs) obj;
        return this.id == aux.id;
    }

    @Override
    public int compareTo(Object o) {
        Utilisateurs u = (Utilisateurs)  o;
        return this.pseudo.compareTo(u.pseudo);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
