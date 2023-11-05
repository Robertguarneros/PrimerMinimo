package edu.upc.dsa.exceptions;

/**
 * Class for when user is in a match exception
 */
public class UserIsCurrentlyInMatchException extends Exception{
    public UserIsCurrentlyInMatchException(String message){
        super(message);
    }
}
