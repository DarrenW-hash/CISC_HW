package pg_004_CISC3315_hw4;
import java.util.Calendar;

public class Check {
	private int accountNumber;
	private double checkAmount;
	private Calendar dateofcheck;
	
	public Check(int _accountNumber, double _checkAmount, Calendar _dateofCheck ) {
		accountNumber = _accountNumber;
		checkAmount = _checkAmount;
		dateofcheck = _dateofCheck;
	}
	
	// Getters
    public int getAccountNumber() {
    	return accountNumber;
    	}
    public double getCheckAmount() { 
    	return checkAmount;
    	}
    public Calendar getDateOfCheck() {
    	return dateofcheck;
    	}
	
}
