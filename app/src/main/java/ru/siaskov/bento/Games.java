package ru.siaskov.bento;

import java.util.ArrayList;

public class Games {

    private ArrayList<Game> games;

    public Games() {

    }

    public Games(ArrayList<Game> games) {
        this.games = games;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        return "Games{" +
                "games=" + games +
                '}';
    }
}
