package com.example.ServeurClavardage.Support;

public class Connexion {

    private final int id1;
    private final int id2;

    public Connexion(int id1, int id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    public int getId1() {
        return id1;
    }

    public int getId2() {
        return id2;
    }

    public boolean equals(Connexion con) {
        return (this.id1 == con.getId1() && this.id2 == con.getId2()) || (this.id1 == con.getId2() && this.id2 == con.getId1());
    }
}
