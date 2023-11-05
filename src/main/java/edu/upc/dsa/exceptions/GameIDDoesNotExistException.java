package edu.upc.dsa.exceptions;


//Structure found in https://www.webucator.com/article/how-to-create-an-exception-class-in-java/

/**
 * Class for when a game id does not exist exception
 */
public class GameIDDoesNotExistException extends Exception{
    public GameIDDoesNotExistException(String message){
        super(message);
    }
}
