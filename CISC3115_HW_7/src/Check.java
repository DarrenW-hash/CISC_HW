import java.util.Calendar;

public class Check {
	private int accountNumber;
	private double checkAmount;
	private Calendar dateofcheck;
	
	public Check(int accountNumber, double checkAmount, Calendar dateofcheck){
		this.accountNumber = accountNumber;
		this.checkAmount = checkAmount;
		this.dateofcheck = dateofcheck;
	}
	
	//copy constructor 
	public Check(Check other)	{
		this.accountNumber = other.accountNumber;
		this.checkAmount = other.checkAmount;
		this.dateofcheck = other.dateofcheck;
	}
	
	//getters
	public int getAccountNumber() {
		return accountNumber;
	}
	public double getCheckAmount() {
		return checkAmount;
	}
	public Calendar getDateofCheck() {
		return dateofcheck;
	}
	
	//toString()
	@Override
	public String toString()	{
		return accountNumber + " " + checkAmount + " " + dateofcheck;
	}
}
