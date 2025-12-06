import java.util.ArrayList;
import java.util.Calendar;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Scanner;
//import custom 
import Exceptions.*;


public class Bank {

	private ArrayList<Account>bankAccounts = new ArrayList<>();
	private int index;
	private int Size;
	private static double totalAmountInSavingAccts;
	private static double totalAmountInCheckingAccts;
	private static double totalAmountInCDAccts;
	private static double totalAmountInAllAccts;
	private static final int ACCSIZE_LENGTH = 4;
	private static final int NAME_LENGTH = 30; // 30 chars * 2 bytes/char = 60 bytes
    private static final int SSN_LENGTH = 15;  // 15 chars * 2 bytes/char = 30 bytes
    private static final int TYPE_LENGTH = 10; // 10 chars * 2 bytes/char = 20 bytes
    private static final int STATUS_LENGTH = 10; // 10 chars * 2 bytes/char = 20 bytes
    private static final int RECEIPT_FILE_NAME_LENGTH = 20; // e.g., "TR_100001.dat" (20 chars * 2)
    private static final int DATE_LENGTH = 12; // "MM/DD/YYYY" (12 chars * 2)
    private static final int RECORD_SIZE = (NAME_LENGTH * 2) +  // lastName
    	    (NAME_LENGTH * 2) +  // firstName
    	    (SSN_LENGTH * 2) +   // SSN
    	    4 +                  // accountNumber (int)
    	    (TYPE_LENGTH * 2) +  // accountType
    	    (STATUS_LENGTH * 2) +// status
    	    8 +                  // balance (double)
    	    4 + 4 + 4;           // CD year, month, day (int); 
	
//	DataOutputStream outputStreamFile = new DataOutputStream(new FileOutputStream("C:\\Users\\dweng\\git\\CISC_HW\\CISC3115_HW_7\\src\\BankAccounts.dat"));

	
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
	
	public void WritetoBinaryFile() throws IOException {
		try(RandomAccessFile raf = new RandomAccessFile("C:\\Users\\dweng\\git\\CISC_HW\\CISC3115_HW_7\\src\\BankAccounts.dat","rw")){
			//writes the total account count first
			raf.seek(0);
			raf.writeInt(bankAccounts.size());
			
			for(int i = 0; i < bankAccounts.size(); i ++) {
				raf.seek(ACCSIZE_LENGTH + i *RECORD_SIZE);
				writeFixedAccountString(bankAccounts.get(i),raf);
			}
			
		}catch(FileNotFoundException e) {
			System.out.println("File not Found");
		}
	}
	
	private void writeFixedAccountString(Account acc, RandomAccessFile raf) throws IOException{
		writeFixedString(acc.getdepositor().getNames().getLastName(),NAME_LENGTH,raf);
		writeFixedString(acc.getdepositor().getNames().getFirstName(),NAME_LENGTH,raf);
		writeFixedString(acc.getdepositor().getSSnumber(), SSN_LENGTH, raf);
		raf.writeInt(acc.getAccountNumber());
		writeFixedString(acc.getaccountType(),TYPE_LENGTH, raf);
		writeFixedString(acc.getStatus(),STATUS_LENGTH,raf);
		raf.writeDouble(acc.getbalance());
		if(acc.getaccountType().equals("CD")) {
			Calendar maturityDate = acc.getDate();
			raf.writeInt(maturityDate.get(Calendar.YEAR));
			raf.writeInt(maturityDate.get(Calendar.MONTH));
			raf.writeInt(maturityDate.get(Calendar.DAY_OF_MONTH));
		}else {
	        raf.writeInt(0);
	        raf.writeInt(0);
	        raf.writeInt(0);
	    }
	}
	private void writeFixedString(String s, int size, RandomAccessFile raf)throws IOException {
		for(int i = 0; i < size; i++) {
			if(i <s.length()) {
				raf.writeChar(s.charAt(i));
			}else {
				raf.writeChar(' ');
			}
		}
	}
	
	public void appendAcc(Account newAcc) throws IOException {
		boolean AccountExist = false;
		try(RandomAccessFile raf  = new RandomAccessFile("C:\\Users\\dweng\\git\\CISC_HW\\CISC3115_HW_7\\src\\BankAccounts.dat","rw")){
			//get the current count
			raf.seek(0);
			int count = raf.readInt();
			for(int i = 0; i < count; i++) {
				raf.seek(ACCSIZE_LENGTH  + i * RECORD_SIZE + 2 * NAME_LENGTH * 2 + SSN_LENGTH * 2);
				int existingAcctNumber = raf.readInt();
				if(existingAcctNumber == newAcc.getAccountNumber()) {
					return;
				}
			}
			long pos = ACCSIZE_LENGTH  + (long)count * RECORD_SIZE;
			raf.seek(pos);
			
			writeFixedAccountString(newAcc, raf);
			raf.seek(0);
			raf.writeInt(count + 1);
		}
	}
	public void addAccounts(Account Acc) throws IOException {
		bankAccounts.add(Acc);
		WritetoBinaryFile();
	}
	
	public void readFromBinaryFile() throws IOException {
		File testFile = new File("C:\\Users\\dweng\\git\\CISC_HW\\CISC3115_HW_7\\src\\RCTestFile.txt");
		PrintWriter writer = new PrintWriter(testFile);
		File file = new File("C:\\Users\\dweng\\git\\CISC_HW\\CISC3115_HW_7\\src\\BankAccounts.dat");
		try(RandomAccessFile raf = new RandomAccessFile(file, "r")){
			raf.seek(0);
			int count = raf.readInt();
			
			for(int i  = 0; i < count; i++ ) {
				raf.seek(ACCSIZE_LENGTH + i*RECORD_SIZE);
				
				//readNames
				String lastName = readFixedString(raf, NAME_LENGTH).trim();
				String firstName = readFixedString(raf, NAME_LENGTH).trim();
	            String ssn = readFixedString(raf, SSN_LENGTH).trim();

	            int accountNumber = raf.readInt();
	            String accountType = readFixedString(raf, TYPE_LENGTH).trim();
	            String status = readFixedString(raf, STATUS_LENGTH).trim();
	            double balance = raf.readDouble();

	            Depositor depositor = new Depositor(new Name(firstName, lastName), ssn);
	            Account acc = null;

	            if (accountType.equalsIgnoreCase("CD")) {
	                // Read maturity date
	                int year = raf.readInt();
	                int month = raf.readInt();
	                int day = raf.readInt();
	                Calendar maturityDate = Calendar.getInstance();
	                maturityDate.set(year, month, day);
	                acc = new CDAccount(depositor, accountNumber, accountType, balance, status, maturityDate);
	                writer.println(lastName + firstName + ssn + " " + accountNumber + status + balance + "CD" + maturityDate);

	            } else if (accountType.equalsIgnoreCase("Savings")) {
	                acc = new SavingAccounts(depositor, accountNumber, accountType, balance, status);
	                writer.println(lastName + firstName + ssn + " " + accountNumber + status + balance + "Saving");
	            } else if (accountType.equalsIgnoreCase("Checking")) {
	                acc = new CheckingAccount(depositor, accountNumber, accountType, balance, status);
	                writer.println(lastName + firstName + ssn + " " + accountNumber + status + balance + "Checking");
	            }

	            // Add to bank
//	            bankAccounts.add(acc);
	        }
			writer.flush();
	    } catch (IOException e) {
	        System.out.println("Error reading file: " + e.getMessage());
	    }
	}
	
	private String readFixedString(RandomAccessFile raf, int size) throws IOException{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < size; i ++) {
			sb.append(raf.readChar());
		}
		return sb.toString();
	}
	public void deleteAccFromFile(int accountNumber) throws IOException {
	    try (RandomAccessFile raf = new RandomAccessFile("C:\\Users\\dweng\\git\\CISC_HW\\CISC3115_HW_7\\src\\BankAccounts.dat", "rw")) {
	        raf.seek(0);
	        int count = raf.readInt();
	        if (count == 0) return; // nothing to delete

	        int deleteIndex = -1;

	        // Find the index of the account to delete
	        for (int i = 0; i < count; i++) {
	            raf.seek(ACCSIZE_LENGTH + i * RECORD_SIZE + 2 * NAME_LENGTH * 2 + SSN_LENGTH * 2);
	            int acctNum = raf.readInt();
	            if (acctNum == accountNumber) {
	                deleteIndex = i;
	                break;
	            }
	        }

	        if (deleteIndex == -1) return; // account not found

	        // If deleting the last account, just decrement count and truncate
	        if (deleteIndex == count - 1) {
	            raf.setLength(ACCSIZE_LENGTH + (count - 1) * RECORD_SIZE);
	        } else {
	            // Read last account
	            long lastPos = ACCSIZE_LENGTH + (count - 1) * RECORD_SIZE;
	            byte[] lastRecord = new byte[RECORD_SIZE];
	            raf.seek(lastPos);
	            raf.readFully(lastRecord);

	            // Overwrite the deleted account with last account
	            long deletePos = ACCSIZE_LENGTH + deleteIndex * RECORD_SIZE;
	            raf.seek(deletePos);
	            raf.write(lastRecord);

	            // Truncate file
	            raf.setLength(ACCSIZE_LENGTH + (count - 1) * RECORD_SIZE);
	        }

	        // Update account count
	        raf.seek(0);
	        raf.writeInt(count - 1);
	    }
	}
	/* Processes a withdrawal request for a specific account.
	 * Finds the account by account number, and if found, delegates the withdrawal
	 * to the Account object. If the account is not found, returns a failure receipt.
	 */
	public TransactionReceipt makeWithdrawal(TransactionTicket ticket, int accountNumber, Scanner userinput) throws AccountClosedException, CDMaturityDateException, InsufficientFundsException {
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
	public TransactionReceipt makedeposit(TransactionTicket ticket, int accountNumber, Scanner userinput) throws AccountClosedException, CDMaturityDateException {
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
	public TransactionReceipt getBalance(TransactionTicket ticket, int accountNumber) throws IOException {
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
	public TransactionReceipt clearCheck(TransactionTicket ticket, int accountNumber, Calendar checkDate) throws InvalidAccountException {
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
	public TransactionReceipt closeAcct(TransactionTicket ticket, int accountNumber) throws IOException {
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
	public TransactionReceipt openAcct(TransactionTicket ticket, int accountNumber) throws IOException {
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
	public TransactionReceipt deleteAcct(TransactionTicket ticket, int accountNumber) throws IOException {
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
					deleteAccFromFile(accountNumber);
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
