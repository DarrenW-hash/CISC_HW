import java.util.Calendar;

public class TransactionTicket {
	private int accountNumber;
	private Calendar dateofTransaction;
	private String typeofTransaction;
	private double amountofTransaction;
	private int termofCD;
	
	public TransactionTicket(int accountNumber, Calendar dateofTransaction, String typeofTransaction, double amountofTransaction, int termofCD) {
		this.accountNumber = accountNumber;
		this.dateofTransaction = dateofTransaction;
		this.typeofTransaction = typeofTransaction;
		this.amountofTransaction = amountofTransaction;
		this.termofCD = termofCD;
	}
	//copy constructor 
	public TransactionTicket(TransactionTicket other) {
	    this.accountNumber = other.accountNumber;
	    this.dateofTransaction = (Calendar) other.dateofTransaction.clone();
	    this.typeofTransaction = other.typeofTransaction;
	    this.amountofTransaction = other.amountofTransaction;
	    this.termofCD = other.termofCD;
	}
	
	public Calendar getDateofTransaction() {
		return dateofTransaction;
	}
	public String getTransaction() {
		return typeofTransaction;
	}
	public int gettermofCD() {
		return termofCD;
	}
	public int getAccountnumber() {
		return accountNumber;
	}
	public double getTransactionAmount() {
		return amountofTransaction;
	}
	//toString
	@Override
	public String toString()	{
		StringBuilder sb = new StringBuilder();
		switch(typeofTransaction) {
			case("WITHDRAWAL"):
				sb.append(String.format("Account Number : %d%nWithdrawal Amount : %.2f%n", 
						getAccountnumber(),
						getTransactionAmount()));
				break;
			case("DEPOSIT"):
				sb.append(String.format("Account Number : %d%nDeposit Amount : %.2f%n", 
						getAccountnumber(),
						getTransactionAmount()));
				break;
			case("Clear Check"):
				sb.append(String.format("Account Number : %d%nDeposit Amount : %.2f%n", 
						getAccountnumber(),
						getTransactionAmount()));
				break;
			case("Close Account"):
				sb.append(String.format("Account Number : %d%n", 
						getAccountnumber()));
				break;
			case("Open Account"):
				sb.append(String.format("Account Number : %d%n", 
						getAccountnumber()));
				break;
			case("New Account"):
				sb.append(String.format("Maturity Date : %tD%n",getDateofTransaction() ));
				break;
		}
		return sb.toString();	
	}
}
