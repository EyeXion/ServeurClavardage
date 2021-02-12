package com.example.ServeurClavardage;

import app.insa.clav.Core.Utilisateurs;
import app.insa.clav.Messages.Message;
import app.insa.clav.Messages.MessageInit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class SharedInformation {
    private ArrayList<Utilisateurs> outdoorUsersList;
    private ArrayList<Utilisateurs> indoorUsersList;
    private ArrayList<ListMessagesInitParUser> listMsgInit;
    private ArrayList<ListMessageTxtParUser> listMessageTxt;
    private static SharedInformation instance = null;

    public SharedInformation(){
        this.outdoorUsersList = new ArrayList<>();
        this.indoorUsersList = new ArrayList<>();
        this.listMsgInit = new ArrayList<>();
        this.listMessageTxt = new ArrayList<>();
    }

    public static SharedInformation getInstance(){
        synchronized(SharedInformation.class){
            if (instance == null) {
                instance = new SharedInformation();
            }
        }
        return instance;
    }

    public synchronized ArrayList<Utilisateurs> getOutdoorUsersList(){
        return outdoorUsersList;
    }

    public synchronized  ArrayList<Utilisateurs> getIndoorUsersList(){
        return indoorUsersList;
    }

    public synchronized ArrayList<MessageInit> getCoList(int user){
        ArrayList<MessageInit> msgs = new ArrayList<>();
        for (Iterator<ListMessagesInitParUser> iter = listMsgInit.iterator(); iter.hasNext(); ) {
            ListMessagesInitParUser list = iter.next();
            if (user == list.getUser().getId()) {
                msgs = list.getMsgs();
                list.clearMsgs();
                break;
            }
        }
        return msgs;
    }

    public synchronized Message getMessageList(int user, int remoteId){
        Message msgs = null;
        for (Iterator<ListMessageTxtParUser> iter1 = listMessageTxt.iterator(); iter1.hasNext(); ) {
            ListMessageTxtParUser list1 = iter1.next();
            if (list1.getUser().getId() == user) {
                for (Iterator<ListMessageTxtParCo> iter2 = list1.getlistMsgsTxtParCo().iterator(); iter2.hasNext(); ) {
                    ListMessageTxtParCo list2 = iter2.next();
                    if (remoteId == list2.getRemoteId()) {
                        msgs = list2.getMsgs();
                        break;
                    }
                }
            break;
            }
        }
        return msgs;
    }

    public synchronized void addMsgInit(int user, MessageInit msg){
        for (Iterator<ListMessagesInitParUser> iter = listMsgInit.iterator(); iter.hasNext(); ) {
            ListMessagesInitParUser list = iter.next();
            if (user == list.getUser().getId()) {
                list.addMsg(msg);
                break;
            }
        }
    }

    public synchronized void addMsgTxt(int user, int remoteId, Message msg) {
        for (Iterator<ListMessageTxtParUser> iter1 = listMessageTxt.iterator(); iter1.hasNext(); ) {
            ListMessageTxtParUser list1 = iter1.next();
            if (list1.getUser().getId() == user) {
                for (Iterator<ListMessageTxtParCo> iter2 = list1.getlistMsgsTxtParCo().iterator(); iter2.hasNext(); ) {
                    ListMessageTxtParCo list2 = iter2.next();
                    if (remoteId == list2.getRemoteId()) {
                        list2.addMsg(msg);
                        break;
                    }
                }
                break;
            }
        }
    }

    public synchronized void addIndoorUser(Utilisateurs newUser){
        this.indoorUsersList.remove(newUser);
        this.indoorUsersList.add(newUser);
        Collections.sort(this.indoorUsersList);
    }

    public synchronized void addOutdoorUser(Utilisateurs newUser){
        this.outdoorUsersList.remove(newUser);
        this.outdoorUsersList.add(newUser);
        Collections.sort(this.outdoorUsersList);
    }

    public synchronized void removeIndoorUser(Utilisateurs user){
        this.indoorUsersList.remove(user);
    }

    public synchronized void removeOutdoorUser(Utilisateurs user){
        this.outdoorUsersList.remove(user);
    }
}
