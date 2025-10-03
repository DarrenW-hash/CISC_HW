package pg_004_CISC3315_hw4;
import java.util.Calendar;

public class TransactionTicket {

	private int accountNumber;
	private Calendar dateofTransaction;
	private String typeofTransaction;
	private double amountofTransaction;
	private int termofCD;
	
	public TransactionTicket(int _accountNumber, Calendar _dateofTransaction, String _typeofTransaction, double _amountofTransaction, int _termofCD ) {
		accountNumber = _accountNumber;
		dateofTransaction = _dateofTransaction;
		typeofTransaction = _typeofTransaction;
		amountofTransaction = _amountofTransaction;
		termofCD = _termofCD;
	}
	
	public Calendar getDateofTransaction() {
		return dateofTransaction;
	}
	public String getTransaction() {
		return typeofTransaction;
	}
	public double getTransactionAmount() {
		return amountofTransaction;
	}
	public int gettermOfCD() {
		return termofCD;
	}
	public int getAccountnumber() {
		return accountNumber;
	}
}
