package Exceptions;

public class InvalidAmountException extends Exception {
    public InvalidAmountException(String s, double d) {
        super(String.format(
        		"Error: Invalid %s input: %.2f",s,d
            ));
    }
}