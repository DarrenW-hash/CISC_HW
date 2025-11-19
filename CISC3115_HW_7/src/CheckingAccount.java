import java.util.Calendar;
import java.util.Scanner;

public class CheckingAccount extends Account{
	
	public CheckingAccount(Depositor depositor, int AccountNumber, String accountType, double balance, String accountStatus) {
		super(depositor, AccountNumber, accountType, balance, accountStatus);
	 }
	
	public CheckingAccount(Depositor depositor, int AccountNumber, String accountType, double balance, String accountStatus, Calendar date) {
		super(depositor, AccountNumber, accountType, balance, accountStatus, date);
	}
	
	public CheckingAccount(CheckingAccount other) {
        super(other);
    }

	public CheckingAccount(Account other) {
		super(other);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Account getCopy() {
		System.out.println("In Checking Account getCopy()");
		return new CheckingAccount(this);
	}

	@Override
	public TransactionReceipt makeWithDrawal(TransactionTicket ticket, Scanner userinput) {
		double preTransaction = getbalance();
		Calendar currentDate = Calendar.getInstance();
		if(getStatus().equals("Closed")) {
			String reason = "Error: Account Number : " + ticket.getAccountnumber()+ " is CLOSED";
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

	@Override
	public TransactionReceipt clearCheck(TransactionTicket ticket, Calendar checkDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
