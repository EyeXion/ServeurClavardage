package com.example.ServeurClavardage.Support;

import app.insa.clav.Core.Utilisateurs;

import java.util.ArrayList;

public class ListMessageTxtParUser {
    private Utilisateurs user;
    private ArrayList<ListMessageTxtParCo> listMsgsTxtParCo;

    public ListMessageTxtParUser(Utilisateurs user) {
        this.user = user;
        this.listMsgsTxtParCo = new ArrayList<ListMessageTxtParCo>();
    }

    public Utilisateurs getUser() {
        return user;
    }

    public void addCo(int remoteId) {
        this.listMsgsTxtParCo.add(new ListMessageTxtParCo(remoteId));
    }

    public ArrayList<ListMessageTxtParCo> getlistMsgsTxtParCo() {
        return listMsgsTxtParCo;
    }
}
