package com.example.ServeurClavardage.Support;

import app.insa.clav.Core.Utilisateurs;
import app.insa.clav.Messages.Message;
import app.insa.clav.Messages.MessageInit;
import app.insa.clav.Messages.MessageRetourSrvTCP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class SharedInformation {
    private ArrayList<Utilisateurs> outdoorUsersList;
    private ArrayList<Utilisateurs> indoorUsersList;
    private ArrayList<ListMessagesInitParUser> listMsgInit;
    private ArrayList<ListMessageTxtParUser> listMessageTxt;
    private ArrayList<Connexion> connexions;
    private static SharedInformation instance = null;

    public SharedInformation(){
        this.outdoorUsersList = new ArrayList<>();
        this.indoorUsersList = new ArrayList<>();
        this.listMsgInit = new ArrayList<>();
        this.listMessageTxt = new ArrayList<>();
        this.connexions = new ArrayList<>();
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
                msgs = new ArrayList<>(list.getMsgs());
                //System.out.println("TROUVEEE DANS LE GETTTTTT " + user + " avec : " + msgs);
                list.clearMsgs();
                //System.out.println("TROUVEEE BIS DANS LE GETTTTTT " + user + " avec : " + msgs);
                break;
            }
        }
        //System.out.println("Co list getted : " + msgs);
        return msgs;
    }

    public synchronized MessageRetourSrvTCP getMessageList(int user, int remoteId){
        MessageRetourSrvTCP msgs = null;
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

    private void createUserInLists(Utilisateurs user) {
        boolean trouve = false;
        for (Iterator<ListMessagesInitParUser> iter = listMsgInit.iterator(); iter.hasNext(); ) {
            ListMessagesInitParUser list = iter.next();
            if (user.getId() == list.getUser().getId()) {
                trouve = true;
                break;
            }
        }
        if (!trouve) {
            listMsgInit.add(new ListMessagesInitParUser(user));
        }

        trouve = false;
        for (Iterator<ListMessageTxtParUser> iter1 = listMessageTxt.iterator(); iter1.hasNext(); ) {
            ListMessageTxtParUser list1 = iter1.next();
            if (list1.getUser().getId() == user.getId()) {
                trouve = true;
                break;
            }
        }
        if (!trouve) {
            listMessageTxt.add(new ListMessageTxtParUser(user));
        }
    }

    private void createCoInList(int user, int remoteId) {
        boolean trouve = false;
        for (Iterator<ListMessageTxtParUser> iter1 = listMessageTxt.iterator(); iter1.hasNext(); ) {
            ListMessageTxtParUser list1 = iter1.next();
            if (list1.getUser().getId() == user) {
                for (Iterator<ListMessageTxtParCo> iter2 = list1.getlistMsgsTxtParCo().iterator(); iter2.hasNext(); ) {
                    ListMessageTxtParCo list2 = iter2.next();
                    if (remoteId == list2.getRemoteId()) {
                        trouve = true;
                        break;
                    }
                }
                if (!trouve) {
                    list1.addCo(remoteId);
                }
                break;
            }
        }
    }

    public synchronized void addMsgInit(int user, int remoteId, MessageInit msg){
        this.addConnexion(new Connexion(user, remoteId));
        for (Iterator<ListMessagesInitParUser> iter = listMsgInit.iterator(); iter.hasNext(); ) {
            ListMessagesInitParUser list = iter.next();
            if (user == list.getUser().getId()) {
                //System.out.println("UTILISATEUR TROOUVE");
                list.addMsg(msg);
                break;
            }
        }
        createCoInList(user, remoteId);
        createCoInList(remoteId, user);
        printLists();
    }

    public synchronized void addMsgTxt(int user, int remoteId, MessageRetourSrvTCP msg) {
        if (msg.getMessage() != null) {
            this.removeConnexion(user, remoteId);
        }
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
        printLists();
    }

    public synchronized void addIndoorUser(Utilisateurs newUser){
        this.indoorUsersList.remove(newUser);
        this.indoorUsersList.add(newUser);
        Collections.sort(this.indoorUsersList);
        createUserInLists(newUser);
    }

    public synchronized void addOutdoorUser(Utilisateurs newUser){
        this.outdoorUsersList.remove(newUser);
        this.outdoorUsersList.add(newUser);
        Collections.sort(this.outdoorUsersList);
        createUserInLists(newUser);
    }

    public synchronized void removeIndoorUser(Utilisateurs user){
        this.indoorUsersList.remove(user);
        this.handleDeconnexion(user.getId());
    }

    public synchronized void removeOutdoorUser(Utilisateurs user){
        this.outdoorUsersList.remove(user);
        this.handleDeconnexion(user.getId());
    }

    private void addConnexion(Connexion con) {
        this.connexions.add(con);
    }

    private void removeConnexion(int id1, int id2) {
        Connexion c = null;
        for (Iterator<Connexion> iter = this.connexions.iterator(); iter.hasNext();) {
            c = iter.next();
            if ((c.getId1() == id1 && c.getId2() == id2) || (c.getId1() == id2 && c.getId2() == id1)) {
                break;
            }
        }
        if (c != null) {
            this.connexions.remove(c);
        }
    }

    private void handleDeconnexion(int user) {
        for (Iterator<Connexion> iter = this.connexions.iterator(); iter.hasNext();) {
            Connexion c = iter.next();
            if (user == c.getId1()) {
                this.addMsgTxt(c.getId2(), user, new MessageRetourSrvTCP(new Message(8, null)));
                this.removeConnexion(user, c.getId2());
            }
            if (user == c.getId2()) {
                this.addMsgTxt(c.getId1(), user, new MessageRetourSrvTCP(new Message(8, null)));
                this.removeConnexion(user, c.getId1());
            }
        }
    }
    public synchronized boolean existConnexion(Connexion con) {
        boolean trouve = false;
        for (Iterator<Connexion> iter = this.connexions.iterator(); iter.hasNext();) {
            Connexion c = iter.next();
            if (c.equals(con)) {
                trouve = true;
                break;
            }
        }
        return trouve;
    }

    private void printLists() {
        System.out.println("Affichage de la liste des connexions :");
        for (Iterator<ListMessagesInitParUser> iter = listMsgInit.iterator(); iter.hasNext(); ) {
            ListMessagesInitParUser list = iter.next();
            System.out.println("   {" + list.getUser() + ", " + list.getMsgs() + "}");
        }
        System.out.println("Affichage de la liste des messages :");
        for (Iterator<ListMessageTxtParUser> iter1 = listMessageTxt.iterator(); iter1.hasNext(); ) {
            ListMessageTxtParUser list1 = iter1.next();
            System.out.println("   {" + list1.getUser() + ", ");
            for (Iterator<ListMessageTxtParCo> iter2 = list1.getlistMsgsTxtParCo().iterator(); iter2.hasNext(); ) {
                ListMessageTxtParCo list2 = iter2.next();
                System.out.println("      {" + list2.getRemoteId() + ", " + list2.getMsgsList() + "}");
            }
            System.out.println("   }");
        }
    }
}