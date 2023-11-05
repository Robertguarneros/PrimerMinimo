package edu.upc.dsa.exceptions;

/**
 * Class for when a user id does not exist exception
 */
public class UserIDDoesNotExistException extends Exception{
    public UserIDDoesNotExistException(String message){
        super(message);
    }
}
