package edu.upc.dsa;

import edu.upc.dsa.exceptions.GameIDDoesNotExistException;
import edu.upc.dsa.exceptions.UserIDDoesNotExistException;
import edu.upc.dsa.exceptions.UserIsCurrentlyInMatchException;
import edu.upc.dsa.exceptions.UserIsNotInMatchException;
import edu.upc.dsa.models.*;
import edu.upc.dsa.models.auxiliarclasses.MatchStats;

import java.util.ArrayList;
import java.util.List;

public interface GameManager {
    public int numberOfUsers();
    public int numberOfGames();
    public Game getGame(String gameID);
    public User getUser(String userID);
    public Match getMatch(String userID);
    public void createUser(String userID);
    public int size();
    public void createGame(String gameID, String gameDescription, int numberOfLevels);
    public void createMatch(String gameID, String userID)throws GameIDDoesNotExistException, UserIDDoesNotExistException, UserIsCurrentlyInMatchException;
    public int getLevelFromMatch(String userID) throws UserIDDoesNotExistException, UserIsNotInMatchException;
    public int getMatchPoints(String userID) throws UserIDDoesNotExistException, UserIsNotInMatchException;
    public void NextLevel(String userID, int points, String date)throws UserIDDoesNotExistException, UserIsNotInMatchException;
    public void EndMatch(String userID)throws UserIDDoesNotExistException, UserIsNotInMatchException;
    public List<User> getUserHistoryForGame(String gameID) throws GameIDDoesNotExistException;
    public List<Match> getUserMatches(String userID) throws UserIDDoesNotExistException;
    public MatchStats getStatsFromUserPerGame(String gameID, String userID) throws GameIDDoesNotExistException, UserIDDoesNotExistException;
}
