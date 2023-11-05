package edu.upc.dsa;

import edu.upc.dsa.exceptions.GameIDDoesNotExistException;
import edu.upc.dsa.exceptions.UserIDDoesNotExistException;
import edu.upc.dsa.exceptions.UserIsCurrentlyInMatchException;
import edu.upc.dsa.exceptions.UserIsNotInMatchException;
import edu.upc.dsa.models.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.models.auxiliarclasses.MatchStats;
import org.apache.log4j.Logger;

/**
 * Class Game Manager Implementation. Here I decided to use HashMaps in order to make the search for items more efficient.
 */
public class GameManagerImpl implements GameManager {
    //In order to facilitate searches, we can implement Hashmaps in Java
    private List<Game> games;
    private List<User> users;
    private List<Match> matches;
    private Map<String, Match> userMatches; // Use a Map to associate matches directly with users

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
        this.games = new ArrayList<>();
        this.users = new ArrayList<>();
        this.matches = new ArrayList<>();
    }
    /**
     * Method to calculate the amount of users
     * @return amount of users
     */
    @Override
    public int size() {
        return this.users.size();
    }
    /**
     * Method to get amount of users
     * @return number of users
     */
    @Override
    public int numberOfUsers() {
        return this.users.size();
    }

    /**
     * Method to get number of games
     * @return number of games
     */
    @Override
    public int numberOfGames() {
        return this.games.size();
    }

    /**
     * Method to get a game
     * @param gameID
     * @return Game Object
     */
    public Game getGame(String gameID) {
        for (Game game : games) {
            if (game.getGameID().equals(gameID)) {
                return game;
            }
        }
        return null;
    }

    /**
     * Method to get user
     * @param userID
     * @return User Object
     */
    public User getUser(String userID) {
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Method to get a match
     * @param userID
     * @return Match Object
     */
    public Match getMatch(String userID) {
        for (Match match : matches) {
            if (match.getUserID().equals(userID)) {
                return match;
            }
        }
        return null;
    }

    /**
     * Method to create a new user given a userID
     * @param userID
     */
    @Override
    public void createUser(String userID) {
        logger.info("Create user with ID= " + userID);
        User user = new User(userID);
        users.add(user);
        Match match = new Match();
        matches.add(match);
        logger.info("User successfully created");
    }

    /**
     * Method to add a game to the available games
     * @param gameID
     * @param gameDescription
     * @param numberOfLevels
     */
    @Override
    public void createGame(String gameID, String gameDescription, int numberOfLevels) {
        logger.info("Create game with gameID: " + gameID + "\n Description: " + gameDescription + "\n Number of Levels: " + numberOfLevels);
        Game game = new Game(gameID, gameDescription, numberOfLevels);
        games.add(game);
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
    public void createMatch(String gameID, String userID) throws GameIDDoesNotExistException, UserIDDoesNotExistException, UserIsCurrentlyInMatchException {
        logger.info("Create match for user " + userID + " in " + gameID);

        Game game = getGame(gameID);
        User user = getUser(userID);

        if (game == null) {
            logger.warn("Game does not exist");
            throw new GameIDDoesNotExistException("Game does not exist");
        } else if (user == null) {
            logger.warn("User does not exist");
            throw new UserIDDoesNotExistException("User does not exist");
        } else {
            if (matches != null) {
                for (Match match : matches) {
                    if (match != null && match.getUserID() != null && match.getUserID().equals(userID) && match.isCurrentMatch()) {
                        logger.warn("User is currently in a match");
                        throw new UserIsCurrentlyInMatchException("User is in a match");
                    }
                }
            }

            Match newMatch = new Match(gameID, userID);

            if (matches == null) {
                matches = new ArrayList<>(); // Initialize matches if it's null
            }

            matches.add(newMatch);
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

        User user = getUser(userID);
        if (user == null) {
            logger.warn("User does not exist");
            throw new UserIDDoesNotExistException("User does not exist");
        }

        Match userMatch = null;
        for (Match match : matches) {
            if (match.getUserID().equals(userID)) {
                userMatch = match;
                break;
            }
        }

        if (userMatch == null || !userMatch.isCurrentMatch()) {
            logger.warn(userID + " is not in a match");
            throw new UserIsNotInMatchException("User is not in match");
        } else {
            int level = userMatch.getCurrentLevel();
            logger.info("Current level: " + level);
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
    public int getMatchPoints(String userID) throws UserIDDoesNotExistException, UserIsNotInMatchException {
        logger.info("Show points from current match");

        User user = getUser(userID);
        if (user == null) {
            logger.warn("User does not exist");
            throw new UserIDDoesNotExistException("User does not exist");
        }

        Match userMatch = null;
        for (Match match : matches) {
            if (match.getUserID().equals(userID)) {
                userMatch = match;
                break;
            }
        }

        if (userMatch == null || !userMatch.isCurrentMatch()) {
            logger.warn(userID + " is not in a match");
            throw new UserIsNotInMatchException("User is not in match");
        } else {
            int points = userMatch.getTotalPoints();
            logger.info("Total current points: " + points);
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
    public void NextLevel(String userID, int points, String date) throws UserIDDoesNotExistException, UserIsNotInMatchException {
        logger.info("Save user " + userID + " goes to the next level with " + points + " on " + date);

        User user = getUser(userID);
        if (user == null) {
            logger.warn("User does not exist");
            throw new UserIDDoesNotExistException("User does not exist");
        }

        Match userMatch = null;
        for (Match match : matches) {
            if (match.getUserID().equals(userID)) {
                userMatch = match;
                break;
            }
        }

        if (userMatch == null || !userMatch.isCurrentMatch()) {
            logger.warn(userID + " is not in a match");
            throw new UserIsNotInMatchException("User is not in match");
        } else {
            if (userMatch.getCurrentLevel() < getGame(userMatch.getGameID()).getNumberOfLevels()) {
                userMatch.nextLevel(date, points);
                logger.info("User has changed to the next level");
            } else {
                // User was in the last level
                userMatch.endMatch(date, points);
                Match m = userMatch;
                user.newFinishedMatch(m);
                logger.info("User has finished the match, all levels passed");
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
    public void EndMatch(String userID) throws UserIDDoesNotExistException, UserIsNotInMatchException {
        logger.info("Ending the current match for user " + userID);

        User user = getUser(userID);
        if (user == null) {
            logger.warn("User does not exist");
            throw new UserIDDoesNotExistException("User does not exist");
        }

        Match userMatch = null;
        for (Match match : matches) {
            if (match.getUserID().equals(userID)) {
                userMatch = match;
                break;
            }
        }

        if (userMatch == null || !userMatch.isCurrentMatch()) {
            logger.warn(userID + " is not in a match");
            throw new UserIsNotInMatchException("User is not in match");
        } else {
            userMatch.endMatchNow();
            Match m = userMatch;
            user.newFinishedMatch(m);
            logger.info("User has ended the match");
        }
    }


    /**
     * Method for getting the users that have played a game and sorting them by points in descending order
     * @param gameID
     * @return a list with the users sorted by points
     * @throws GameIDDoesNotExistException
     */
    @Override
    public List<User> getUserHistoryForGame(String gameID) throws GameIDDoesNotExistException {
        logger.info("Show users that have played " + gameID);

        Game game = getGame(gameID);
        if (game == null) {
            logger.warn("Game does not exist");
            throw new GameIDDoesNotExistException("Game does not exist");
        }

        List<User> usersHavePlayedGame = new ArrayList<>();
        for (User user : users) {
            for (Match match : user.getMatchesPlayed()) {
                if (match.getGameID().equals(gameID)) {
                    usersHavePlayedGame.add(user);
                    break;
                }
            }
        }

        usersHavePlayedGame.sort((a, b) -> b.getTotalPointsPerGame(gameID) - a.getTotalPointsPerGame(gameID));
        logger.info("Sorted list of players that have played " + gameID);
        return usersHavePlayedGame;
    }


    /**
     * Method to get the matches a user has played
     * @param userID
     * @return list with the matches played by user
     * @throws UserIDDoesNotExistException
     */
    @Override
    public List<Match> getUserMatches(String userID) throws UserIDDoesNotExistException {
        logger.info("Show matches played by user " + userID);

        User user = getUser(userID);
        if (user == null) {
            logger.warn("User does not exist");
            throw new UserIDDoesNotExistException("User does not exist");
        }

        return user.getMatchesPlayed();
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
    public MatchStats getStatsFromUserPerGame(String gameID, String userID) throws GameIDDoesNotExistException, UserIDDoesNotExistException {
        logger.info("Show stats from last match for user " + userID + " in game " + gameID);

        Game game = getGame(gameID);
        if (game == null) {
            logger.warn("Game does not exist");
            throw new GameIDDoesNotExistException("Game does not exist");
        }

        User user = getUser(userID);
        if (user == null) {
            logger.warn("User does not exist");
            throw new UserIDDoesNotExistException("User does not exist");
        }

        return user.getMatchStatsPerUser(gameID);
    }

}
