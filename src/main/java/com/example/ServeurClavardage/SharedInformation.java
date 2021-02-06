package com.example.ServeurClavardage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SharedInformation {
    private ArrayList<Utilisateurs> outdoorUsersList;
    private ArrayList<Utilisateurs> indoorUsersList;
    private static SharedInformation instance = null;

    public SharedInformation(){
        this.outdoorUsersList = new ArrayList<>();
        this.indoorUsersList = new ArrayList<>();
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
