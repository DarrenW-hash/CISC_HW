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

	@Override
	public Account getCopy() {
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
		double preTransaction = getbalance();
		Calendar currentDate = Calendar.getInstance();
		Calendar sixMonthsAgo = (Calendar)currentDate.clone();
		sixMonthsAgo.add(Calendar.MONTH, -6);
		Check check = new Check(ticket.getAccountnumber(), ticket.getTransactionAmount(), checkDate);	
		if(getStatus().equals("Closed")) {
			String reason = "Error: Account Number : " + ticket.getAccountnumber()+ " is CLOSED";
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate, getStatus(),getaccountType());
			addtransactionReceipt(receipt); 
			return receipt;
		}
		else {
			int curMonth = currentDate.get(Calendar.MONTH) + 1;
		    int curDay = currentDate.get(Calendar.DAY_OF_MONTH);
		    int curYear = currentDate.get(Calendar.YEAR);
		    int checkMonth = check.getDateofCheck().get(Calendar.MONTH) + 1;
		    int checkDay = check.getDateofCheck().get(Calendar.DAY_OF_MONTH);
		    int checkYear = check.getDateofCheck().get(Calendar.YEAR);
		    int staleMonth = sixMonthsAgo.get(Calendar.MONTH) + 1;
		    int staleDay = sixMonthsAgo.get(Calendar.DAY_OF_MONTH);
		    int staleYear = sixMonthsAgo.get(Calendar.YEAR);      
		    if(check.getDateofCheck().after(currentDate)) {
		    	String reason = "Error:Check not cleared - Post-dated check: " + checkMonth + "/" + checkDay + "/" + checkYear;
			  	TransactionReceipt receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate,getStatus(),getaccountType());
			    addtransactionReceipt(receipt); 
			    return receipt;
		    }
		    //Check if the Check is Stale (older than six months)
		    else if (check.getDateofCheck().before(sixMonthsAgo)) {
		    	String reason = "Error: Cannot clear stale check (older than 6 months). "
                        + "Check Date: " + checkMonth + "/" + checkDay + "/" + checkYear + ". "
                        + "Oldest Acceptable Date: " + staleMonth + "/" + staleDay + "/" + staleYear;
				      TransactionReceipt receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate,getStatus(),getaccountType());
				      addtransactionReceipt(receipt); 
				      return receipt;
		    }
		    else if(preTransaction < check.getCheckAmount()) {
		    	// Check for insufficient funds
				double newBalance = preTransaction - 2.50;
				setbalance(newBalance);
				String reason = "Error: Insufficient funds. $2.50 service fee applied for bounced check - " 
						+ "Old Balance : " + preTransaction+" | " + "New Balance : " + newBalance;
			    TransactionReceipt receipt = new TransactionReceipt(ticket, false, reason, preTransaction, newBalance, currentDate, getStatus(),getaccountType());
			    addtransactionReceipt(receipt);
			    return receipt;
		    }else {
		    	double newBalance = preTransaction - check.getCheckAmount();
				setbalance(newBalance);
				TransactionReceipt receipt = new TransactionReceipt(ticket, true, preTransaction, newBalance, currentDate, getStatus(),getaccountType());
				addtransactionReceipt(receipt);
				return receipt;
		    }
		}
	}

}
