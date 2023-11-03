package edu.upc.dsa.models;


import edu.upc.dsa.models.resources.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
public class User {
    String userID;
    List<Match> matchesPlayed;
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

    public void newFinishedMatch(Match m){this.matchesPlayed.add(m);}

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
