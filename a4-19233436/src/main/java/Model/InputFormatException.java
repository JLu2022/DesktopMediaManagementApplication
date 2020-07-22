package Model;

/**
 * @author: Junlin Lu
 * @date: 20/04/2020
 * @version: 1.0.0
 * @description: The exception subclass which is thrown when the input String does not fit the format
 */
public class InputFormatException extends Exception{
    public static final String INCORRECT_YEAR="Only pure number is allowed for 'year' ",
            EMPTY_INPUT="Empty fields are not allowed to create new Item";

    public InputFormatException(String s){
        super(s);
    }

}//end class
