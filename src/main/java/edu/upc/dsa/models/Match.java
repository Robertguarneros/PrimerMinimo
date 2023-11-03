package edu.upc.dsa.models;

import java.util.List;
import java.util.ArrayList;
public class Match {
    String gameID;
    String userID;
    int currentLevel;
    List<Integer> pointsPerLevel;
    Integer totalPoints;
    List<String> levelDate;
    boolean currentMatch;

    public Match(String gameID, String userID){
        this.gameID = gameID;
        this.userID = userID;
        this.currentLevel = 1;//Always start at the first level
        this.pointsPerLevel= new ArrayList<>();
        this.totalPoints = 50; //Level always starts with 50 points as per the requirements
        this.levelDate = new ArrayList<>();
        this.currentMatch = true;
    }
    public Match(){this.currentMatch=false;}

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

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public List<Integer> getPointsPerLevel() {
        return pointsPerLevel;
    }

    public void setPointsPerLevel(List<Integer> pointsPerLevel) {
        this.pointsPerLevel = pointsPerLevel;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public List<String> getLevelDate() {
        return levelDate;
    }

    public void setLevelDate(List<String> levelDate) {
        this.levelDate = levelDate;
    }

    public boolean isCurrentMatch() {
        return currentMatch;
    }

    public void setCurrentMatch(boolean currentMatch) {
        this.currentMatch = currentMatch;
    }

    public void nextLevel(String date, int points){
        this.currentLevel = this.currentLevel + 1;
        this.totalPoints = this.totalPoints + points;
        pointsPerLevel.add(points);
        levelDate.add(date);
    }

    public void endMatch(String date, int points){
        this.totalPoints = this.totalPoints + points + 100;
        pointsPerLevel.add(points);
        levelDate.add(date);
        this.currentMatch = false;
    }


}
