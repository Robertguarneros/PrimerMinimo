package edu.upc.dsa.models;


import edu.upc.dsa.models.auxiliarclasses.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class User
 */
public class User {
    String userID;
    List<Match> matchesPlayed;

    /**
     * Class User
     * @param userID
     */
    public User (String userID){
        this.userID = userID;
        this.matchesPlayed = new ArrayList<>();
    }

    public User(){}

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<Match> getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(List<Match> matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    /**
     * Method to get the match stats for a user for a game
     * @param gameID
     * @return
     */
    public MatchStats getMatchStatsPerUser(String gameID){
        List<LevelStats> detailsPerLevel = new ArrayList<>();
        Match lastMatch = new Match();
        for (int i=0; i<this.matchesPlayed.size(); i++){
            if (Objects.equals(this.matchesPlayed.get(i).getGameID(),gameID)){
                lastMatch = this.matchesPlayed.get(i);
            }
        }
        int levelsPlayed = lastMatch.getPointsPerLevel().size();
        for (int j=0;j<levelsPlayed;j++){
            LevelStats levelStats = new LevelStats(j+1, lastMatch.getPointsPerLevel().get(j),lastMatch.getLevelDate().get(j));
            detailsPerLevel.add(levelStats);
        }
        return new MatchStats(gameID, this.userID, detailsPerLevel);
    }

    /**
     * Method to create a finished match to the list of matches played
     * @param m
     */
    public void newFinishedMatch(Match m){this.matchesPlayed.add(m);}

    /**
     * Method to get the total amount of points in a game
     * @param gameID
     * @return
     */
    public int getTotalPointsPerGame(String gameID){
        int points = 0;
        for (int i=0; i<this.matchesPlayed.size();i++){
            if (Objects.equals(this.matchesPlayed.get(i).getGameID(),gameID)){
                points = points + this.matchesPlayed.get(i).getTotalPoints();
            }
        }
        return points;
    }

}
