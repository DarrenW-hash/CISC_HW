package pg_004_CISC3315_hw4;

import java.util.ArrayList;
import java.util.Calendar;

public class Account {

	private Depositor depositor;
	private int AccountNumber;
	private String accountType;
	private boolean accountStatus;
	private double balance;
	private ArrayList<TransactionReceipt> transactionReceipts= new ArrayList<TransactionReceipt>();
	private Check check;
	private Calendar date;
	
	public Account(Depositor _depositor, int _AccountNumber, Double _Balance, String _accountType) {
		depositor = _depositor;
		AccountNumber = _AccountNumber;
		balance = _Balance;
		accountType = _accountType;
		//transactionReceipts = _transactionRecipt;
	}
	
	//contrustor including checks
	public Account(Depositor _depositor, int _AccountNumber, Double _Balance, String _accountType, Calendar _date) {
		depositor = _depositor;
		AccountNumber = _AccountNumber;
		balance = _Balance;
		accountType = _accountType;
		//transactionReceipts = _transactionRecipt;
		date = _date;
		
	}
	
	//getters
	public Depositor getdepositor() {
		return depositor;
	}
	public int getAccountNumber() {
		return AccountNumber;
	}
	public String getaccountType() {
		return accountType;
	}
	public boolean getAccountStatus() {
		return accountStatus;
	}
	public ArrayList gettransactionRecipts() {
		return transactionReceipts;
	}
	public Check getcheck() {
		return check;
	}
	public double getbalance() {
		return balance;
	}
	public Calendar getdate() {
		return date;
	}
	
	//setters 
	private void setAccountStatus(boolean _accountStatus) {
		accountStatus = _accountStatus;
	}
	private void setbalance(double _Balance) {
		balance = _Balance;
	}
	private void addtransactionRecipt( TransactionReceipt receipt) {
		 transactionReceipts.add(receipt);
	}
	private void setcheck(Check _check) {
		check = _check;	
	}
	private void setCalendar(Calendar _date) {
		date = _date;
	}
}
