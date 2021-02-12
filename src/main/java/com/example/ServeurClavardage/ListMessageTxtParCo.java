package com.example.ServeurClavardage;



import app.insa.clav.Messages.Message;

import java.util.ArrayList;

public class ListMessageTxtParCo {
    private int remoteId;
    private ArrayList<Message> msgs;

    public ListMessageTxtParCo(int remoteId) {
        this.remoteId = remoteId;
        this.msgs = new ArrayList<Message>();
    }

    public int getRemoteId() {
        return remoteId;
    }

    public void addMsg(Message msg) {
        this.msgs.add(msg);
    }

    public Message getMsgs() {
        Message msg = msgs.get(0);
        msgs.remove(0);
        return msg;
    }

}
