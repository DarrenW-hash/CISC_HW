import java.util.Calendar;

public class TransactionReceipt {
	private TransactionTicket ticket;
	private boolean TransactionSuccessIndicatorFlag;
	private String ReasonForFailure;
	private double PreTransactionBalance;
	private double PostTransactionBalance;
	private Calendar postTransactionMaturityDate;
	private String accountStatus;
	private String accountType;
	
	public TransactionReceipt(TransactionTicket ticket, boolean TranactionSuccessIndicatorFlag, double PreTransactionBalance, double PostTransactionBalance, Calendar postTransactionMaturityDate, String accountStatus, String accountType ) {
		this.ticket = ticket;
		this.TransactionSuccessIndicatorFlag = TranactionSuccessIndicatorFlag;
		this.PreTransactionBalance = PreTransactionBalance;
		this.PostTransactionBalance = PostTransactionBalance;
		this.postTransactionMaturityDate = postTransactionMaturityDate;
		this.accountStatus = accountStatus;
		this.accountType = accountType;
	
	}
	
	public TransactionReceipt(TransactionTicket ticket, boolean TranactionSuccessIndicatorFlag,String ReasonForFailure, double PreTransactionBalance, double PostTransactionBalance, Calendar postTransactionMaturityDate, String accountStatus, String accountType) {
		this.ticket = ticket;
		this.TransactionSuccessIndicatorFlag = TranactionSuccessIndicatorFlag;
		this.PreTransactionBalance = PreTransactionBalance;
		this.PostTransactionBalance = PostTransactionBalance;
		this.ReasonForFailure = ReasonForFailure;
		this.postTransactionMaturityDate = postTransactionMaturityDate;
		this.accountStatus = accountStatus;
		this.accountType = accountType;
	}
	
	
	//copy constructor
	public TransactionReceipt(TransactionReceipt other) {
	    this.ticket = new TransactionTicket(other.ticket); 
	    this.TransactionSuccessIndicatorFlag = other.TransactionSuccessIndicatorFlag;
	    this.ReasonForFailure = other.ReasonForFailure;
	    this.PreTransactionBalance = other.PreTransactionBalance;
	    this.PostTransactionBalance = other.PostTransactionBalance;
	    this.postTransactionMaturityDate = (Calendar) other.postTransactionMaturityDate.clone();
	    this.accountStatus = other.accountStatus;
	    this.accountType = other.accountType;
	}
	//getters 
	public TransactionTicket getTransactionTicket() {
		return ticket;
	}
	public boolean getTransactionSuccessIndicatorFlag() {
		return TransactionSuccessIndicatorFlag;
	}
	public String getReasonForFailure() {
		return ReasonForFailure;
	}
	public double getPostTransactionBalance() {
		return PostTransactionBalance;
	}
	public double getPreTransactionBalance() {
		return PreTransactionBalance;
	}
	public Calendar getPostTransactionMaturityDate() {
		return postTransactionMaturityDate;
	}
	public String getAccStatus() {
		return accountStatus;
	}
	public String getAccType()	{
		return accountType;
	}
	
	//toString 
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if(TransactionSuccessIndicatorFlag == false) {
			sb.append("Transaction Failed \n");
			sb.append("Account Number " + ticket.getAccountnumber() + "\n");
			sb.append("Reason : " + ReasonForFailure + "\n");
			sb.append("------------------------ \n");
	
		}else {
			switch(ticket.getTransaction()) {
				case("WITHDRAWAL"):
					sb.append(ticket.toString());
					sb.append(String.format("Old Balance : %.2f%nNew Balance : %.2f%n",
							getPreTransactionBalance(),
							getPostTransactionBalance()));
					sb.append("Account Status :" + getAccStatus() + "\n");
					if(getAccType().equals("CD")) {
						sb.append(String.format("CD New Maturity Date : %tD%n",  getPostTransactionMaturityDate()));
					}
					break;
				case("DEPOSIT"):
					sb.append(ticket.toString());
					sb.append(String.format("Old Balance : %.2f%nNew Balance : %.2f%n",
							getPreTransactionBalance(),
							getPostTransactionBalance()));
					sb.append("Account Status :" + getAccStatus() + "\n");
					if(getAccType().equals("CD")) {
						sb.append(String.format("CD New Maturity Date : %tD%n", getPostTransactionMaturityDate()));
					}
					break;
				case("Clear Check"):
					sb.append(ticket.toString());
					sb.append(String.format("Old Balance : %.2f%nNew Balance : %.2f%n",
							getPreTransactionBalance(),
							getPostTransactionBalance()));
					sb.append("Account Status : " + getAccStatus() + "\n");
					break;
				case("Close Account"):
					sb.append(ticket.toString());
					sb.append("Account Status : Now Closed \n");
					break;
				case("Open Account"):
					sb.append(ticket.toString());
					sb.append("Account Status : Now Open \n");
					break;
				case("Delete Account"):
					sb.append("Account Number " +ticket.getAccountnumber() + " has been DELETED \n");
					break;
				case("BALANCE"):
					sb.append("Account Number : "+ticket.getAccountnumber() + "\n");
					sb.append("Account Type : " + getAccType() +"\n");
					sb.append("Balance : " + getPreTransactionBalance() + "\n");
					break;
				case("New Account"):
					sb.append("Account Number :" + ticket.getAccountnumber() + " has been CREATED \n");
					sb.append(String.format("Account Type : %s%nAccount Status : %s%n",
							getAccType(),
							getAccStatus()));
					if(accountType.equalsIgnoreCase("CD") && postTransactionMaturityDate != null) {
					    sb.append(String.format("Maturity Date: %tD%n", postTransactionMaturityDate));
					}
					break;
					
			}
		}
		return sb.toString();
	}
}
