package ru.siaskov.bento;

import java.util.UUID;

public class Game {

    private String teamFirst;
    private String teamSecond;
    private int scoreFirstTeam;
    private int scoreSecondTeam;
    private String typeOfTheGame;
    private String league;
    private UUID Id;

    public Game(String teamFirst, String teamSecond, int scoreFirstTeam, int scoreSecondTeam, String typeOfTheGame, String league) {
        this.teamFirst = teamFirst;
        this.teamSecond = teamSecond;
        this.scoreFirstTeam = scoreFirstTeam;
        this.scoreSecondTeam = scoreSecondTeam;
        this.typeOfTheGame = typeOfTheGame;
        this.league = league;
        this.Id = UUID.randomUUID();
    }

    public String getTeamFirst() {
        return teamFirst;
    }

    public void setTeamFirst(String teamFirst) {
        this.teamFirst = teamFirst;
    }

    public String getTeamSecond() {
        return teamSecond;
    }

    public void setTeamSecond(String teamSecond) {
        this.teamSecond = teamSecond;
    }

    public String getTypeOfTheGame() {
        return typeOfTheGame;
    }

    public void setTypeOfTheGame(String typeOfTheGame) {
        this.typeOfTheGame = typeOfTheGame;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public int getScoreFirstTeam() {
        return scoreFirstTeam ;
    }

    public void setScoreFirstTeam(int scoreFirstTeam) {
        this.scoreFirstTeam = scoreFirstTeam;
    }

    public int getScoreSecondTeam() {
        return  scoreSecondTeam;
    }

    public void setScoreSecondTeam(int scoreSecondTeam) {
        this.scoreSecondTeam = scoreSecondTeam;
    }

    public UUID getId() {
        return Id;
    }


}
