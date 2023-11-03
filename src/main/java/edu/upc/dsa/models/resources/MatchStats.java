package edu.upc.dsa.models.resources;

import java.util.List;
public class MatchStats {
    String gameID;
    String userID;
    List<LevelStats> levelDetails;

    public MatchStats(String gameID, String userID, List<LevelStats> levelDetails) {
        this.gameID = gameID;
        this.userID = userID;
        this.levelDetails = levelDetails;
    }

    public MatchStats(){}

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<LevelStats> getLevelDetails() {
        return levelDetails;
    }

    public void setLevelDetails(List<LevelStats> levelDetails) {
        this.levelDetails = levelDetails;
    }
}
