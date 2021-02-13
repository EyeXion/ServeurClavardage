package com.example.ServeurClavardage.Support;



import app.insa.clav.Messages.Message;
import app.insa.clav.Messages.MessageRetourSrvTCP;

import java.util.ArrayList;

public class ListMessageTxtParCo {
    private int remoteId;
    private ArrayList<MessageRetourSrvTCP> msgs;

    public ListMessageTxtParCo(int remoteId) {
        this.remoteId = remoteId;
        this.msgs = new ArrayList<MessageRetourSrvTCP>();
    }

    public int getRemoteId() {
        return remoteId;
    }

    public void addMsg(MessageRetourSrvTCP msg) {
        this.msgs.add(msg);
    }

    public ArrayList<MessageRetourSrvTCP> getMsgsList() {
        return msgs;
    }

    public MessageRetourSrvTCP getMsgs() {
        if (msgs.isEmpty()) {
            return null;
        } else {
            MessageRetourSrvTCP msg = msgs.get(0);
            msgs.remove(0);
            return msg;
        }
    }

}
