package com.example.ServeurClavardage;

import app.insa.clav.Core.Utilisateurs;
import app.insa.clav.Messages.MessageInit;

import java.util.ArrayList;

public class ListMessagesInitParUser {

    private Utilisateurs user;
    private ArrayList<MessageInit> msgs;

    public ListMessagesInitParUser(Utilisateurs user) {
        this.user = user;
        this.msgs = new ArrayList<MessageInit>();
    }

    public Utilisateurs getUser() {
        return user;
    }

    public void addMsg(MessageInit msg) {
        this.msgs.add(msg);
    }

    public ArrayList<MessageInit> getMsgs() {
        return msgs;
    }

    public void clearMsgs() {
        this.msgs.clear();
    }
}
