package edu.upc.dsa.models.resources;

import java.util.logging.Level;

public class LevelStats {
    int levelID;
    int pointsPerLevel;
    String levelDate;

    public LevelStats(int levelID, int pointsPerLevel, String levelDate) {
        this.levelID = levelID;
        this.pointsPerLevel = pointsPerLevel;
        this.levelDate = levelDate;
    }

    public LevelStats(){}

    public int getLevelID() {
        return levelID;
    }

    public void setLevelID(int levelID) {
        this.levelID = levelID;
    }

    public int getPointsPerLevel() {
        return pointsPerLevel;
    }

    public void setPointsPerLevel(int pointsPerLevel) {
        this.pointsPerLevel = pointsPerLevel;
    }

    public String getLevelDate() {
        return levelDate;
    }

    public void setLevelDate(String levelDate) {
        this.levelDate = levelDate;
    }
}
