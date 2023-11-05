package edu.upc.dsa.services;

import edu.upc.dsa.*;
import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;
import edu.upc.dsa.models.auxiliarclasses.*;


import javax.ws.rs.*;
import io.swagger.annotations.*;
import org.eclipse.persistence.annotations.ReturnUpdate;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Api(value = "/game", description = "Endpoint to Game Service")
@Path("/game")

/**
 * Class to implement the REST
 */
public class GameService {
    private GameManager gm;

    /**
     * Here we create games and user to be able to test the methods in swagger
     */
    public GameService(){
        this.gm = GameManagerImpl.getInstance();
        if(gm.size()==0){
            this.gm.createGame("Spider-Man","Play the Spider-Man game",10);
            this.gm.createGame("Hogwarts Legacy","Experience being a wizard in Hogwarts", 5);
            this.gm.createGame("Stardew Valley", "Create your own farm and explore the region while making friends", 2);
            this.gm.createUser("Roberto");
            this.gm.createUser("User1");
            this.gm.createUser("User2");
        }
    }

    /**
     * Method to create a new game using http POST.
     * @param game
     * @return 201 when successful
     */
    @POST
    @ApiOperation(value = "Create a game", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Game created successfully")
    })
    @Path("/game/create_game")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createGame(Game game){
        this.gm.createGame(game.getGameID(),game.getGameDescription(),game.getNumberOfLevels());
        return Response.status(201).build();
    }

    /**
     * Method to create a new user using http POST.
     * @param user
     * @return 201 when successful
     */
    @POST
    @ApiOperation(value = "Create a user", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created successfully")
    })
    @Path("/game/create_user")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createUser(User user){
        this.gm.createUser(user.getUserID());
        return Response.status(201).build();
    }

    /**
     * Method to create a match using http PUT.
     * @param gameID
     * @param userID
     * @return 201 when successful, 40
     */
    @PUT
    @ApiOperation(value = "Create match", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Done"),
            @ApiResponse(code = 452, message = "Game does not exist"),
            @ApiResponse(code = 406, message = "User does not exist"),
            @ApiResponse(code = 407, message = "User is currently in match")
    })
    @Path("/match/create_match/{gameID}/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMatch(@PathParam("gameID") String gameID, @PathParam("userID") String userID){
        try {
            this.gm.createMatch(gameID, userID);
        } catch(GameIDDoesNotExistException e){
            return Response.status(405).build();
        }catch(UserIDDoesNotExistException e){
            return Response.status(406).build();
        }catch (UserIsCurrentlyInMatchException e){
            return Response.status(407).build();
        }
        return Response.status(201).build();
    }

    /**
     * Method used to get the current level from the current match a user is playing
     * @param userID
     * @return integer level
     */
    @GET
    @ApiOperation(value = "Get current level from match given a user", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Done", response = Integer.class),
            @ApiResponse(code = 406, message = "User does not exist"),
            @ApiResponse(code = 408, message = "User is not in a match")
    })
    @Path("/match/get_level/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLevel(@PathParam("userID") String userID){
        int level;
        try{
            level = this.gm.getLevelFromMatch(userID);
        }catch(UserIDDoesNotExistException e){
            return Response.status(406).build();
        }catch (UserIsNotInMatchException e){
            return Response.status(408).build();
        }

        return Response.status(201).entity(level).build();
    }

    /**
     * Method to get the current score from the current match from a user
     * @param userID
     * @return integer points from current match
     */
    @GET
    @ApiOperation(value = "Get current score from match of a given user", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Done",response = Integer.class),
            @ApiResponse(code = 406, message = "User does not exist"),
            @ApiResponse(code = 408, message = "User is not in a match")
    })
    @Path("/match/get_match_points/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatchPoints(@PathParam("userID") String userID){
        int points;
        try{
            points = this.gm.getMatchPoints(userID);
        }catch(UserIDDoesNotExistException e){
            return Response.status(406).build();
        }catch (UserIsNotInMatchException e){
            return Response.status(408).build();
        }
        return Response.status(201).entity(points).build();
    }

    /**
     * Method to change the level from the current match for a given user
     * @param nextLevel
     */
    @PUT
    @ApiOperation(value = "Next level", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Done"),
            @ApiResponse(code = 406, message = "User does not exist"),
            @ApiResponse(code = 408, message = "User is not in a match")
    })
    @Path("/match/next_level")
    @Produces(MediaType.APPLICATION_JSON)
    public Response NextLevel(NextLevel nextLevel){
        try {
            this.gm.NextLevel(nextLevel.getUserID(),nextLevel.getPoints(),nextLevel.getNextLevelDate());
        }catch(UserIDDoesNotExistException e){
            return Response.status(406).build();
        }catch (UserIsNotInMatchException e){
            return Response.status(408).build();
        }
        return Response.status(201).build();
    }

    /**
     * Method to end match
     * @param userID
     */
    @PUT
    @ApiOperation(value = "End match", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Done"),
            @ApiResponse(code = 406, message = "User does not exist"),
            @ApiResponse(code = 408, message = "User is not in a match")
    })
    @Path("/match/end_match/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response EndMatch(@PathParam("userID") String userID){
        try {
            this.gm.EndMatch(userID);
        }catch(UserIDDoesNotExistException e){
            return Response.status(406).build();
        }catch (UserIsNotInMatchException e){
            return Response.status(408).build();
        }
        return Response.status(201).build();
    }

    /**
     * Method to get a list of all the users that have played a game in descending order by points
     * @param gameID
     * @return list of players
     */
    @GET
    @ApiOperation(value = "Get users that have played a game in descending order", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Done", response = Integer.class),
            @ApiResponse(code = 405, message = "Game does not exist"),
    })
    @Path("/game/get_users_that_played_game/{gameID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserHistoryForGame(@PathParam("gameID") String gameID){
        List<User> userHistory;
        try{
            userHistory = this.gm.getUserHistoryForGame(gameID);
        }catch(GameIDDoesNotExistException e){
            return Response.status(405).build();
        }
        GenericEntity<List<User>> userH = new GenericEntity<List<User>>(userHistory){};
        return Response.status(201).entity(userH).build();
    }

    /**
     * Method to get the matches a user has played
     * @param userID
     * @return list of matches
     */
    @GET
    @ApiOperation(value = "Get matches user has played", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Done",response = Match.class, responseContainer = "List"),
            @ApiResponse(code = 406, message = "User does not exist"),
    })
    @Path("/match/get_user_matches/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserMatches(@PathParam("userID") String userID){
        List<Match> matches;
        try{
            matches = this.gm.getUserMatches(userID);
        }catch(UserIDDoesNotExistException e){
            return Response.status(406).build();
        }
        GenericEntity<List<Match>> matchList = new GenericEntity<List<Match>>(matches){};
        return Response.status(201).entity(matchList).build();
    }

    /**
     * Method to get the stats about a match for a user for a game
     * @param gameID
     * @param userID
     * @return Object Match
     */
    @GET
    @ApiOperation(value = "Get stats about match from user for a certain game", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Done",response = Match.class, responseContainer = "List"),
            @ApiResponse(code = 405, message = "Game does not exist"),
            @ApiResponse(code = 406, message = "User does not exist"),

    })
    @Path("/match/get_user_matches/{gameID}/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatsFromUserPerGame(@PathParam("gameID") String gameID, @PathParam("userID") String userID){
        MatchStats stats;
        try{
            stats = this.gm.getStatsFromUserPerGame(gameID,userID);
        }catch(GameIDDoesNotExistException e){
            return Response.status(405).build();
        }catch(UserIDDoesNotExistException e){
            return Response.status(406).build();
        }
        return Response.status(201).entity(stats).build();
    }
}