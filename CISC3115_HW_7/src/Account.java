import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public abstract class Account {
	private Depositor depositor;
	private int AccountNumber;
	private String accountType;
	private String accountStatus;
	private double balance;
	private ArrayList<TransactionReceipt> transactionReceipts = new ArrayList<>();
	private Check check;
	private Calendar date;
	private TransactionTicket ticket;
	
	public Account(Depositor depositor, int AccountNumber, String accountType, double balance, String accountStatus)	{
		this.depositor = depositor;
		this.AccountNumber = AccountNumber;
		this.accountType = accountType;
		this.balance = balance;
		this.accountStatus = accountStatus;
		this.date = Calendar.getInstance();
	}
	
	public Account(Depositor depositor, int AccountNumber, String accountType, double balance, String accountStatus, Calendar date) {
		this.depositor = depositor;
		this.AccountNumber = AccountNumber;
		this.accountType = accountType;
		this.balance = balance;
		this.accountStatus = accountStatus;
		this.date = date;
		
	}
	//copy constructors
	public Account(Account other)	{
		this.depositor = new Depositor(other.depositor);
		this.AccountNumber = other.AccountNumber;
		this.balance = other.balance;
		this.accountStatus = other.accountStatus;
		this.accountType = other.accountType;
			if(other.date != null) {
				 this.date = (Calendar) other.date.clone();
			}else {
				this.date = null;
			}
			
			// deep copy transaction receipts
		    this.transactionReceipts = new ArrayList<>();
		    for (TransactionReceipt tr : other.transactionReceipts) {
		        this.transactionReceipts.add(new TransactionReceipt(tr)); 
		    }

		    // copy check if needed
		    if (other.check != null) {
		        this.check = new Check(other.check); 
		    }
		}
	//getters
	public Depositor getdepositor() {
		return new Depositor(depositor);
	}
	public int getAccountNumber() {
		return AccountNumber;
	}
	public String getaccountType() {
		return accountType;
	}
	public ArrayList<TransactionReceipt> gettransactionRecipts() {
		return new ArrayList<>(transactionReceipts);
	}
	public Check getCheck() {
		return new Check(check);
	}
	public double getbalance() {
		return balance;
	}
	public Calendar getDate() {
		if (this.date == null) {
			return Calendar.getInstance(); 
		    	}
	 return (Calendar) this.date.clone();
		
	}
	public String getStatus()	{
		return accountStatus;
	}	
	//setters
	private void setAccountStatus(String _accountStatus) {
		accountStatus = _accountStatus;
	}
	protected void setbalance(double _balance)	{
		balance = _balance;
	}
	//protected method
	protected void addtransactionReceipt(TransactionReceipt receipt)	{
		transactionReceipts.add(receipt);
		System.out.println("DEBUG: Added transaction to account " + getAccountNumber() + ", list size now: " + transactionReceipts.size());
	}
	private void setcheck(Check _check)	{
		check = _check;
	}
	protected void setCalendar(Calendar _date) {
		date = _date;
	}
	
	//toString()
	@Override
	public String toString()	{
		StringBuilder sb = new StringBuilder();
		if(getaccountType().equals("CD")) {
			sb.append(String.format("%-12s %-12s %-12s %-10d %-12s %-12s %-10.2f %12tD",
					getdepositor().getNames().getFirstName(),
					getdepositor().getNames().getLastName(),
					getdepositor().getSSnumber(),
					getAccountNumber(),
					getaccountType(),
					getStatus(),
					getbalance(),
					getDate()));
		}else {
			sb.append(String.format("%-12s %-12s %-12s %-10d %-12s %-12s %-10.2f", 
					getdepositor().getNames().getFirstName(),
					getdepositor().getNames().getLastName(),
					getdepositor().getSSnumber(),
					getAccountNumber(),
					getaccountType(),
					getStatus(),
					getbalance()));
		}
		return sb.toString();
	}
	
	//equals method
	@Override
	public boolean equals(Object obj)	{
		if(this == obj) {
			return true;
		}
		if(obj == null)	{
			return false;
		}
		Account other = (Account) obj;
		
		return  this.depositor.equals(other.depositor)
	            && this.AccountNumber == other.AccountNumber
	            && this.balance == other.balance;
	}
	/*Retrieves the current balance of the account.
	* Creates a TransactionReceipt to record the balance inquiry.
	*/
	public TransactionReceipt getBalance(TransactionTicket ticket){
		TransactionReceipt receipt;
		Calendar currentDate = Calendar.getInstance();
		receipt = new TransactionReceipt(ticket,true,getbalance(),0,currentDate, getStatus(), getaccountType());
		addtransactionReceipt(receipt);
		return receipt;
	}
	/* Closes the account if it is currently open.
	 * Validates the current account status and updates it to "Closed" if allowed.
	 * Records the transaction in the account’s transaction history.
	 */
	public TransactionReceipt closeAcct(TransactionTicket ticket) {
		TransactionReceipt receipt;
		Calendar currentDate = Calendar.getInstance();
			
		if(getStatus().equals("Closed")) {
			String reason = "Error: Account Number : " + ticket.getAccountnumber()+ " is Already CLOSED";
			receipt = new TransactionReceipt(ticket, false, reason, 0, 0, currentDate, getStatus(),getaccountType());
			addtransactionReceipt(receipt); 
			return receipt;
				}else {
					setAccountStatus("Closed");
					receipt = new TransactionReceipt(ticket, true,0,0, currentDate, getStatus(),getaccountType());
					addtransactionReceipt(receipt);
					return receipt;
			}
		}
	/* Reopens the account if it is currently closed.
	 * Validates the current account status and updates it to "Open" if allowed.
	 * Records the transaction in the account’s transaction history.
	 */
	public TransactionReceipt openAcct(TransactionTicket ticket) {
		TransactionReceipt receipt;
		Calendar currentDate = Calendar.getInstance();
			
		if(getStatus().equals("Open")) {
			String reason = "Error: Account Number : " + ticket.getAccountnumber()+ " is Already Open";
			receipt = new TransactionReceipt(ticket, false, reason, 0, 0, currentDate, getStatus(),getaccountType());
			addtransactionReceipt(receipt); 
			return receipt;
				}else {
					setAccountStatus("Open");
					receipt = new TransactionReceipt(ticket, true,0,0, currentDate, getStatus(),getaccountType());
					addtransactionReceipt(receipt);
					return receipt;
		}
	}
	//abstract method- to be implemented into CD, Checking, and Saving child Classes
		public abstract Account getCopy();
			
		public abstract TransactionReceipt makeWithDrawal(TransactionTicket ticket, Scanner userinput);
		
		public abstract TransactionReceipt makedeposit(TransactionTicket ticket, Scanner userinput);
		
		public abstract TransactionReceipt clearCheck(TransactionTicket ticket, Calendar checkDate);
}
