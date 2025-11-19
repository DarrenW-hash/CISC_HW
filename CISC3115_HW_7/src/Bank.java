import java.util.ArrayList;
import java.util.Calendar;
import java.io.File;
import java.util.Scanner;


public class Bank {

	private ArrayList<Account>bankAccounts = new ArrayList<>();
	private int index;
	private int Size;
	private static double totalAmountInSavingAccts;
	private static double totalAmountInCheckingAccts;
	private static double totalAmountInCDAccts;
	private static double totalAmountInAllAccts;
	
	
	
	Bank(){
		bankAccounts = new ArrayList<>();
	}
	
	//getter 
	public Account getbankAccounts(int index) {
		if(index < 0 || index >= bankAccounts.size()) {
			return null;
		}
		
		Account originalAccount = bankAccounts.get(index);
		if (originalAccount == null) {
	        return null; 
	    }
	    
	    // 3. If the account is valid, return a safe deep copy.
	    return originalAccount.getCopy();
	}
	
	//getter
	public static double getTotalSavings() {
		return totalAmountInSavingAccts;
	}
	public static double getTotalChecking() {
		return totalAmountInCheckingAccts;
	}
	public static double getTotalCDAccts() {
		return totalAmountInCDAccts;
	}
	public static double getTotalAll() {
		return totalAmountInAllAccts;
	}
	public int getindex() {
		return index;
	}
	public int getbankSize() {
		return bankAccounts.size();
	}
	
	public void setindex(int i)	{
		index = i;
	}
	private int findAcct(int acctNumber) {
		int index = 0;
		for(int i = 0; i < getbankSize(); i ++) {
			Account currentAccount = bankAccounts.get(i);
			 if (currentAccount != null) {  
				 if (currentAccount.getAccountNumber() == acctNumber) {
		           return i; // Found the index
				 }
			 }
		}
		index = -1;
		return index;
	}
	
	public void addAccounts(Account Acc)	{
		//System.out.println("Added Account");
		bankAccounts.add(Acc);
	}
	
	/* Processes a withdrawal request for a specific account.
	 * Finds the account by account number, and if found, delegates the withdrawal
	 * to the Account object. If the account is not found, returns a failure receipt.
	 */
	public TransactionReceipt makeWithdrawal(TransactionTicket ticket, int accountNumber, Scanner userinput) {
		int index = findAcct(accountNumber);
		if(index == -1) {
			String ReasonForFailure  = "Error Account Number:" + accountNumber +" not found.";
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonForFailure, 0.0, 0.0, Calendar.getInstance(),null, null);
			return Receipt;
		}else {
			System.out.println("Debug: " + getbankAccounts(index).getaccountType() + " " + ticket.getTransactionAmount());
			return bankAccounts.get(index).makeWithDrawal(ticket, userinput);
		}
	}
	/* Processes a deposit request for a specific account.
	 * Finds the account by account number, and if found, delegates the deposit
	 * to the Account object. If the account is not found, returns a failure receipt.	
	 */
	public TransactionReceipt makedeposit(TransactionTicket ticket, int accountNumber, Scanner userinput) {
		int index = findAcct(accountNumber);
		if(index == -1) {
			String ReasonforFailure = "Error Account Number " + accountNumber + " not found.";
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonforFailure, 0.0, 0.0, Calendar.getInstance(),null,null);
			return Receipt;
		}else {
			System.out.println("Debug: " + getbankAccounts(index).getaccountType() + " " + ticket.getTransactionAmount());
			return bankAccounts.get(index).makedeposit(ticket ,userinput );
		}
	}
	/* Retrieves the current balance of a specific account.
	 * Finds the account by account number, and if found, delegates the balance
	 * retrieval to the Account object. If the account is not found, returns a failure receipt.	
	 */
	public TransactionReceipt getBalance(TransactionTicket ticket, int accountNumber) {
		int index = findAcct(accountNumber);
		if(index == -1) {
			String ReasonforFailure = "Error Account Number " + accountNumber + " not found.";
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonforFailure, 0.0, 0.0, Calendar.getInstance(), null,null);
			return Receipt;
		}else {
			return bankAccounts.get(index).getBalance(ticket);
		}
	}
	/* Processes a check clearing transaction for a specific account.
	 * Finds the account by account number, and if found, delegates the check clearing
	 * to the Account object. If the account is not found, returns a failure receipt.
	 */
	public TransactionReceipt clearCheck(TransactionTicket ticket, int accountNumber, Calendar checkDate) {
		int index = findAcct(accountNumber);
		if(index == -1) {
			String ReasonforFailure = "Error Account Number" + accountNumber + " not found.";
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonforFailure, 0.0, 0.0, Calendar.getInstance(),getbankAccounts(index).getStatus(),getbankAccounts(index).getaccountType());
			return Receipt;
		}else {
			System.out.println("Debug: " + getbankAccounts(index).getaccountType() + " " + ticket.getTransactionAmount());
			return bankAccounts.get(index).clearCheck(ticket,checkDate);
		}
	}
	/* Creates a new account in the bank if it passes all validations.
	 * Performs checks for duplicate account numbers, valid account types,
	 * valid SSN, and account number ranges. Returns a TransactionReceipt
	 * indicating success or failure.
	 */	
	public TransactionReceipt makeNewAcct(Account account) {
		int accountNumber = account.getAccountNumber();
		Calendar time = account.getDate();
		int index = findAcct(accountNumber);
		int SSnumber = Integer.parseInt(account.getdepositor().getSSnumber());
		int maxSize = getbankSize();
			
		// Check if account number already exists
		if(index != -1) {
			String ReasonforFailure = "Error Account Number " + accountNumber + " Exist.";
			TransactionTicket ticket = new TransactionTicket(accountNumber,time,"New Account",0.0,0);
			TransactionReceipt Receipt = new TransactionReceipt(ticket,false,ReasonforFailure,0.0,0.0,Calendar.getInstance(),getbankAccounts(index).getStatus(), getbankAccounts(index).getaccountType());
			return Receipt;
		}else {
			// Validate account type
			if(account.getaccountType().equals("Saving") || account.getaccountType().equals("Checking") || account.getaccountType().equals("CD"))  {
				// Validate account number range (6-digit numbers)
				if(account.getAccountNumber() >= 100000 && account.getAccountNumber() <= 999999) {
					// Validate SSN (9-digit numbers)
					if(SSnumber >= 100000000 && SSnumber <= 999999999) {
						bankAccounts.add(account);
						TransactionTicket ticket = new TransactionTicket(accountNumber, time,"New Account", 0,0) ;
						TransactionReceipt Receipt = new TransactionReceipt(ticket, true, 0.0,0.0,time,getbankAccounts(maxSize -1).getStatus(), getbankAccounts(maxSize -1).getaccountType());
						return Receipt;
					}else {
						// Invalid SSN
						String ReasonforFailure = "Error INVALID SSN : " + account.getdepositor().getSSnumber();
						TransactionTicket ticket = new TransactionTicket(accountNumber,time,"New Account",0.0,0);
						TransactionReceipt Receipt = new TransactionReceipt(ticket,false,ReasonforFailure,0.0,0.0,Calendar.getInstance(),getbankAccounts(index).getStatus(), getbankAccounts(index).getaccountType());
						return Receipt;
					}
				}else {
					// Invalid account number range
					String ReasonforFailure = "Error INVALID ACCOUNT NUMBER RANGE : " + account.getAccountNumber();
					TransactionTicket ticket = new TransactionTicket(accountNumber,time,"New Account",0.0,0);
					TransactionReceipt Receipt = new TransactionReceipt(ticket,false,ReasonforFailure,0.0,0.0,Calendar.getInstance(),getbankAccounts(index).getStatus(), getbankAccounts(index).getaccountType());
					return Receipt;
				}
						
			}else {
				// Invalid account type
				String ReasonforFailure = "Error INVALID ACCOUNT TYPE of : " + account.getaccountType();
				TransactionTicket ticket = new TransactionTicket(accountNumber,time,"New Account",0.0,0);
				TransactionReceipt Receipt = new TransactionReceipt(ticket,false,ReasonforFailure,0.0,0.0,Calendar.getInstance(),getbankAccounts(index).getStatus(), getbankAccounts(index).getaccountType());
				return Receipt;
			}
		}
	}
	/* Closes a specific bank account based on the account number provided.
	 * Finds the account by account number, and if found, delegates the closing
	 * operation to the Account object. If the account is not found, returns a failure receipt.
	 */
	public TransactionReceipt closeAcct(TransactionTicket ticket, int accountNumber) {
		int index = findAcct(accountNumber);
		if(index == -1) {
			String ReasonforFailure = "Error Account Number " + accountNumber + " not found.";
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonforFailure, 0.0, 0.0, Calendar.getInstance(),null,null);
			return Receipt;
				}else {
					return bankAccounts.get(index).closeAcct(ticket);
		}
	}
	/* Reopens a previously closed bank account based on the account number provided.
	 * Finds the account by account number, and if found, delegates the reopening
	 * operation to the Account object. If the account is not found, returns a failure receipt.
	 */
	public TransactionReceipt openAcct(TransactionTicket ticket, int accountNumber) {
		int index = findAcct(accountNumber);
		if(index == -1) {
			String ReasonforFailure = "Error Account Number " + accountNumber + " not found.";
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonforFailure, 0.0, 0.0, Calendar.getInstance(),null,null);
			return Receipt;
				}else {
					return bankAccounts.get(index).openAcct(ticket);
			}	
		}
	/* Deletes a bank account if it exists and has a zero balance.
	 * Finds the account by account number, validates balance, and removes it from the bank's list.
	 * Returns a TransactionReceipt indicating success or failure.
	 */
	public TransactionReceipt deleteAcct(TransactionTicket ticket, int accountNumber) {
		int index = findAcct(accountNumber);
		if(index == -1) {
			String ReasonforFailure = "Error Account Number " + accountNumber + " not found.";
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonforFailure, 0, 0.0, Calendar.getInstance(),null, null);
			return Receipt;
			}else {
				if(bankAccounts.get(index).getbalance() > 0) {
					String ReasonforFailure = "Error Account Number " + accountNumber + " has a balance of " + bankAccounts.get(index).getbalance();
					TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonforFailure, 0, 0.0, Calendar.getInstance(),getbankAccounts(index).getStatus(), getbankAccounts(index).getaccountType());
					return Receipt;
				}else {
					bankAccounts.remove(index);
					TransactionReceipt Receipt = new TransactionReceipt(ticket, true, 0.0,0.0,Calendar.getInstance(),getbankAccounts(index).getStatus(), getbankAccounts(index).getaccountType());
					return Receipt;
			}
		}
	}
	
	public void recalcTotals() {
		for(int i = 0; i < getbankSize(); i++) {
			Account acc = getbankAccounts(i);
			if (acc != null) {
	            double balance = acc.getbalance();
	            String type = acc.getaccountType();

	            switch(type) {
	                case "Savings": 
	                    totalAmountInSavingAccts += balance;
	                    break;
	                case "Checking":
	                    totalAmountInCheckingAccts += balance;
	                    break;
	                case "CD":
	                    totalAmountInCDAccts += balance;
	                    break;
	            }
	            totalAmountInAllAccts += balance;
	        }
		}
	}	
}
