package edu.upc.dsa.models.resources;

public class NextLevel {
    String userID;
    int points;
    String nextLevelDate;

    public NextLevel(String userID, int points, String nextLevelDate) {
        this.userID = userID;
        this.points = points;
        this.nextLevelDate = nextLevelDate;
    }

    public NextLevel(){}

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getNextLevelDate() {
        return nextLevelDate;
    }

    public void setNextLevelDate(String nextLevelDate) {
        this.nextLevelDate = nextLevelDate;
    }
}
