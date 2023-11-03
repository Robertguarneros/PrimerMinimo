package edu.upc.dsa.models;

public class Game {
    String gameID;
    String gameDescription;
    int numberOfLevels;

    public Game(String gameID, String gameDescription, int numberOfLevels) {
        this.gameID = gameID;
        this.gameDescription = gameDescription;
        this.numberOfLevels = numberOfLevels;
    }

    //Empty constructor for Jersy and Swagger
    public Game(){}
    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public int getNumberOfLevels() {
        return numberOfLevels;
    }

    public void setNumberOfLevels(int numberOfLevels) {
        this.numberOfLevels = numberOfLevels;
    }
}
