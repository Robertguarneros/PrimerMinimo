package edu.upc.dsa.exceptions;

/**
 * Class for when the is in a match exception
 */
public class UserIsNotInMatchException extends Exception{
    public UserIsNotInMatchException(String message){
        super(message);
    }
}
