package Exceptions;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(double requested, double balance) {
    	super(String.format(
                "Error: Insufficient funds. Transaction Amount: %.2f | Current Balance: %.2f",
                requested, balance
            ));
    }
}
