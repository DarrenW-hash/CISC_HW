import java.util.Calendar;
import java.util.Scanner;

public class SavingAccounts extends Account{

	private final double WITHDRAWAL_FEE = 1.50;
	private Calendar date; 
	
	public SavingAccounts(Depositor depositor, int AccountNumber, String AccountType, double balance, String accountStatus) {
		super(depositor, AccountNumber, AccountType, balance, accountStatus);
		this.date = Calendar.getInstance();
	}
	public SavingAccounts(Depositor depositor, int AccountNumber, String AccountType, double balance, String accountStatus, Calendar date ) {
		super(depositor, AccountNumber, AccountType, balance, accountStatus, date);
	}
	//Copy Constructor
	public SavingAccounts(SavingAccounts other) {
		super(other);
	}

	@Override
	public Account getCopy() {
		return new SavingAccounts(this);
	}

	@Override
	public TransactionReceipt makeWithDrawal(TransactionTicket ticket, Scanner userinput) {
		double preTransaction = getbalance();
		Calendar currentDate = Calendar.getInstance();
		if(getStatus().equals("Closed")) {
			String reason = "Error: Account Number " + ticket.getAccountnumber() + " is CLOSED";
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate, getStatus(),getaccountType());
			addtransactionReceipt(receipt);
			return receipt;
		}else {
			if(ticket.getTransactionAmount() < 0) {
				String reason = String.format("Error: Invalid Withdrawal input: %.2f",ticket.getTransactionAmount());
				TransactionReceipt receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate,getStatus(), getaccountType());
				addtransactionReceipt(receipt);
				return receipt;
			}else if(preTransaction < ticket.getTransactionAmount()) {
				String reason = String.format("Error: Insufficient funds. Withdrawal Amount: %.2f. Current Balance: %.2f", ticket.getTransactionAmount(), getbalance());
				TransactionReceipt receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate,getStatus(), getaccountType());
				addtransactionReceipt(receipt);
				return receipt;
			}else {
				//successful withdrawal
				double newBalance = preTransaction - ticket.getTransactionAmount();
				setbalance(newBalance);
				TransactionReceipt receipt =  new TransactionReceipt(ticket, true, preTransaction,newBalance,currentDate,getStatus(), getaccountType());
				addtransactionReceipt(receipt);
				return receipt;
			}
		}
	}

	@Override
	public TransactionReceipt makedeposit(TransactionTicket ticket, Scanner userinput) {
		double preTransaction = getbalance();
		Calendar currentDate = Calendar.getInstance();
		if(getStatus().equals("Closed")) {
			String reason = "Error: Account Number : " + ticket.getAccountnumber()+ " is CLOSED";
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate, getStatus(),getaccountType());
			addtransactionReceipt(receipt); 
			return receipt;
		}else {
		// Handle standard accounts (Checking, Savings)
			if(ticket.getTransactionAmount() < 0) {
			String reason = String.format(
		       	    "Error: Invalid Deposit input: %.2f",
		       	    ticket.getTransactionAmount()
		       	);
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate,getStatus(),getaccountType());
			addtransactionReceipt(receipt); 
		    return receipt;
		}else {
			// Successful deposit
			double newBalance = preTransaction + ticket.getTransactionAmount();
			setbalance(newBalance);
			TransactionReceipt receipt = new TransactionReceipt(ticket, true, preTransaction, newBalance, currentDate, getStatus(),getaccountType());
			addtransactionReceipt(receipt); 
			return receipt;
			}	
		}
	}

	//Clearing a Savings Account is not Allowed
	@Override
	public TransactionReceipt clearCheck(TransactionTicket ticket, Calendar checkDate) {
		double preTransactionBalance = getbalance();
		String reason = "Error only Clear Checking Accounts. " + "Account Type : " + getaccountType();
		TransactionReceipt receipt = new TransactionReceipt(
				ticket,false,reason,preTransactionBalance,0.0,date,getStatus(),getaccountType());
		addtransactionReceipt(receipt); 
		return receipt;
	}

}
