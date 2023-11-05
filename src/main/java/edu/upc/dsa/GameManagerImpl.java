package edu.upc.dsa;

import edu.upc.dsa.exceptions.GameIDDoesNotExistException;
import edu.upc.dsa.exceptions.UserIDDoesNotExistException;
import edu.upc.dsa.exceptions.UserIsCurrentlyInMatchException;
import edu.upc.dsa.exceptions.UserIsNotInMatchException;
import edu.upc.dsa.models.*;
import java.util.*;

import edu.upc.dsa.models.auxiliarclasses.MatchStats;
import org.apache.log4j.Logger;

/**
 * Class Game Manager Implementation. Here I decided to use HashMaps in order to make the search for items more efficient.
 */
public class GameManagerImpl implements GameManager {
    //In order to facilitate searches, we can implement Hashmaps in Java
    HashMap<String,Game> games; //Key = gameID
    HashMap<String,User> users; //Key = userID
    HashMap<String,Match> matches; // Key = userID

    private static GameManager instance;

    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    /**
     * method to get Instance of GameManager
     * @return instance
     */
    public static GameManager getInstance(){
        if (instance==null) instance = new GameManagerImpl();
        return instance;
    }

    /**
     * Assign HashMaps to games,users and matches
     */
    public GameManagerImpl(){
        this.games = new HashMap<>();
        this.users = new HashMap<>();
        this.matches = new HashMap<>();
    }
    /**
     * Method to calculate the amount of users
     * @return amount of users
     */
    @Override
    public int size(){
        int ret = this.users.size();
        logger.info("Size: " + ret);
        return ret;
    }
    /**
     * Method to get amount of users
     * @return number of users
     */
    @Override
    public int numberOfUsers(){
        return this.users.size();
    }

    /**
     * Method to get number of games
     * @return number of games
     */
    @Override
    public int numberOfGames(){
        return this.games.size();
    }

    /**
     * Method to get a game
     * @param gameID
     * @return Game Object
     */
    public Game getGame(String gameID){
        return this.games.get(gameID);
    }

    /**
     * Method to get user
     * @param userID
     * @return User Object
     */
    public User getUser(String userID){
        return this.users.get(userID);
    }

    /**
     * Method to get a match
     * @param userID
     * @return Match Object
     */
    public Match getMatch(String userID){
        return this.matches.get(userID);
    }

    /**
     * Method to create a new user given a userID
     * @param userID
     */
    @Override
    public void createUser(String userID){
        logger.info("Create user with ID= "+userID);
        User user = new User(userID);
        users.put(userID, user);
        Match match = new Match();
        matches.put(userID,match);
        logger.info("User successfully created");
    }

    /**
     * Method to add a game to the available games
     * @param gameID
     * @param gameDescription
     * @param numberOfLevels
     */
    @Override
    public void createGame(String gameID, String gameDescription, int numberOfLevels){
        logger.info("Create game with gameID: "+gameID+"\n Description: "+gameDescription+"\n Number of Levels: "+numberOfLevels);
        Game g = new Game(gameID, gameDescription, numberOfLevels);
        games.put(gameID,g);
        logger.info("Game successfully created");
    }

    /**
     * Method to create a new match for a game and a user
     * @param gameID
     * @param userID
     * @throws GameIDDoesNotExistException
     * @throws UserIDDoesNotExistException
     * @throws UserIsCurrentlyInMatchException
     */
    @Override
    public void createMatch(String gameID, String userID)throws GameIDDoesNotExistException, UserIDDoesNotExistException, UserIsCurrentlyInMatchException{
        logger.info("Create match for user "+userID+" in "+gameID);
        if (!this.games.containsKey(gameID)){
            logger.warn("Game does not exist");
            throw new GameIDDoesNotExistException("Game does not exist");
        } else if (!this.users.containsKey(userID)){
            logger.warn("User does not exist");
            throw new UserIDDoesNotExistException("User does not exist");
        }else if (matches.get(userID).isCurrentMatch()){
            logger.warn("User is currently in match");
            throw new UserIsCurrentlyInMatchException("User is in match");
        }else {
            Match m = new Match(gameID, userID);
            matches.put(userID, m);
            logger.info("Match created");
        }
    }

    /**
     * Method to get the current level from the match a user is currently in
     * @param userID
     * @return an integer with the level
     * @throws UserIDDoesNotExistException
     * @throws UserIsNotInMatchException
     */
    @Override
    public int getLevelFromMatch(String userID) throws UserIDDoesNotExistException, UserIsNotInMatchException {
        logger.info("Show level from current match");
        if (!this.users.containsKey(userID)){
            logger.warn("User does not exist");
            throw new UserIDDoesNotExistException("User does not exist");
        }else if(!matches.get(userID).isCurrentMatch()){
            logger.warn(userID+"is not in a match");
            throw new UserIsNotInMatchException("User is not in match");
        } else {
            int level = matches.get(userID).getCurrentLevel();
            logger.info("Current level: "+level);
            return level;
        }
    }

    /**
     * Method to get the points in a match being played by a user
     * @param userID
     * @return an integer containing the amount of points
     * @throws UserIDDoesNotExistException
     * @throws UserIsNotInMatchException
     */
    @Override
    public int getMatchPoints(String userID) throws UserIDDoesNotExistException, UserIsNotInMatchException{
        logger.info("Show points from current match");
        if (!this.users.containsKey(userID)){
            logger.warn("User does not exist");
            throw new UserIDDoesNotExistException("User does not exist");
        }else if(!matches.get(userID).isCurrentMatch()){
            logger.warn(userID+"is not in a match");
            throw new UserIsNotInMatchException("User is not in match");
        } else {
            int points = matches.get(userID).getTotalPoints();
            logger.info("Total current points: "+points);
            return points;
        }
    }

    /**
     * Method to change level to the next one, in the case that the level is the last one, it ends the match
     * @param userID
     * @param points
     * @param date
     * @throws UserIDDoesNotExistException
     * @throws UserIsNotInMatchException
     */
    @Override
    public void NextLevel(String userID, int points, String date)throws UserIDDoesNotExistException, UserIsNotInMatchException{
        logger.info("Save user "+userID+" goes to the next level with "+points+" on "+date);
        if (!this.users.containsKey(userID)){
            logger.warn("User does not exist");
            throw new UserIDDoesNotExistException("User does not exist");
        }else if(!matches.get(userID).isCurrentMatch()){
            logger.warn(userID+"is not in a match");
            throw new UserIsNotInMatchException("User is not in match");
        } else {
            if(matches.get(userID).getCurrentLevel()<games.get(matches.get(userID).getGameID()).getNumberOfLevels()){
                matches.get(userID).nextLevel(date, points);
                logger.info("User has changed to the next level");
            }else{
                //User was in last level
                matches.get(userID).endMatch(date,points);
                Match m = matches.get(userID);
                users.get(userID).newFinishedMatch(m);
                logger.info("User has finished match, all levels passed");
            }
        }
    }

    /**
     * Method to end the current match
     * @param userID
     * @throws UserIDDoesNotExistException
     * @throws UserIsNotInMatchException
     */
    @Override
    public void EndMatch(String userID)throws UserIDDoesNotExistException, UserIsNotInMatchException {
        if (!this.users.containsKey(userID)){
            logger.warn("User does not exist");
            throw new UserIDDoesNotExistException("User does not exist");
        }else if(!matches.get(userID).isCurrentMatch()){
            logger.warn(userID+"is not in a match");
            throw new UserIsNotInMatchException("User is not in match");
        } else {
            matches.get(userID).endMatchNow();
            Match m = matches.get(userID);
            users.get(userID).newFinishedMatch(m);
            logger.info("User has ended match");
        }
    }

    /**
     * Method for getting the users that have played a game and sorting them by points in descending order
     * @param gameID
     * @return a list with the users sorted by points
     * @throws GameIDDoesNotExistException
     */
    @Override
    public List<User> getUserHistoryForGame(String gameID) throws GameIDDoesNotExistException{
        logger.info("Show user that have played "+gameID);
        if (!this.games.containsKey(gameID)){
            logger.warn("Game does not exist");
            throw new GameIDDoesNotExistException("Game does not exist");
        } else{
            List<User> usersHavePlayedGame = new ArrayList<>();
            List<User> list = new ArrayList<>(this.users.values());
            for (int i=0; i<list.size(); i++){
                int matchesPlayed = list.get(i).getMatchesPlayed().size();
                for (int j=0; j < matchesPlayed; j++){
                    if (Objects.equals(list.get(i).getMatchesPlayed().get(j).getGameID(),gameID)){
                        usersHavePlayedGame.add(list.get(i));
                        j = matchesPlayed;
                    }
                }
            }
            usersHavePlayedGame.sort((User a,User b)->(b.getTotalPointsPerGame(gameID) - a.getTotalPointsPerGame(gameID) ));
            logger.info("Show sorted list of players that have played "+gameID);
            return usersHavePlayedGame;
        }
    }

    /**
     * Method to get the matches a user has played
     * @param userID
     * @return list with the matches played by user
     * @throws UserIDDoesNotExistException
     */
    @Override
    public List<Match> getUserMatches(String userID) throws UserIDDoesNotExistException{
        logger.info("Show matches played by user "+userID);
        if (!this.users.containsKey(userID)){
            logger.warn("User does not exist");
            throw new UserIDDoesNotExistException("User does not exist");
        }else{
            return this.users.get(userID).getMatchesPlayed();
        }
    }

    /**
     * Method to get the last played match stats from for a user in a game
     * @param gameID
     * @param userID
     * @return
     * @throws GameIDDoesNotExistException
     * @throws UserIDDoesNotExistException
     */
    @Override
    public MatchStats getStatsFromUserPerGame(String gameID, String userID) throws GameIDDoesNotExistException, UserIDDoesNotExistException{
        if (!this.games.containsKey(gameID)){
            logger.warn("Game does not exist");
            throw new GameIDDoesNotExistException("Game does not exist");
        } else if (!this.users.containsKey(userID)){
            logger.warn("User does not exist");
            throw new UserIDDoesNotExistException("User does not exist");
        }else {
            logger.info("Show stats from last match for user "+userID+" in game "+gameID);
            return this.users.get(userID).getMatchStatsPerUser(gameID);
        }
    }
}
