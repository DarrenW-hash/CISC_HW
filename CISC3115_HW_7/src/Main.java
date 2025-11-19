import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Main {
	
	public static void main (String args [])throws IOException	{
		File inputFile = new File("C:\\Users\\dweng\\git\\CISC_HW\\CISC3115_HW_7\\input.txt");
		PrintWriter outputWriter = new PrintWriter("C:\\Users\\dweng\\git\\CISC_HW\\CISC3115_HW_7\\output.txt");
		Scanner userinput = new Scanner(inputFile);
		String userchoice; 
		Bank bank = new Bank();
		readaccts(bank);
		
		printAccts(bank,outputWriter);

		do {
			menu();
			System.out.println("Enter choice : ");
			userchoice = userinput.next();
			switch(userchoice) {
				case("w"):
				case("W"):
					withdrawalMethod(bank, outputWriter, userinput);
					break;
				case("D"):
				case("d"):
					deposit(bank, userinput, outputWriter);
					break;
				case("B"):
				case("b"):
					balance(bank, userinput, outputWriter);
					break;
				case("C"):
				case("c"):
					clearCheck(bank, userinput, outputWriter);
					break;
				case("N"):
				case("n"):
					outputWriter.println("Transaction: New Account");
					newAccount(bank, userinput, outputWriter);
					break;
				case("I"):
				case("i"):
					outputWriter.println("Transaction: Account Info");
					acctInfo(bank, userinput, outputWriter);
					break;
				case("H"):
				case("h"):
					outputWriter.println("Transaction: Account Info With Transaction History");
					acctInfoHistory(bank, userinput, outputWriter);
					break;
				case("S"):
				case("s"):
					outputWriter.println("Transaction: Close Account");
					closeAcct(bank, userinput, outputWriter);
					break;
				case("R"):
				case("r"):
					outputWriter.println("Transaction: Reopen Account");
					reopenAcct(bank, userinput, outputWriter);
					break;
				case("X"):
				case("x"):
					outputWriter.println("Transaction: Delete Account");
					deleteAcct(bank, userinput, outputWriter);
					break;
				case("Q"):
				case("q"):
					outputWriter.println();
					outputWriter.println("Ending Program");
					outputWriter.println("Updated Bank Account");
					// Print account info
					printAccts(bank,outputWriter);
					bank.recalcTotals();
					outputWriter.printf("Total Savings: $ %.2f%n" ,bank.getTotalSavings());
					outputWriter.printf("Total Checking: $ %.2f%n" , bank.getTotalChecking());
					outputWriter.printf("Total CD: $ %.2f%n" ,bank.getTotalCDAccts());
					outputWriter.printf("Total All Accounts: $ %.2f%n" ,bank.getTotalAll());
					break;
			
			
			default:
			System.out.println("Not a choice ");
			break;
			}
		}while(!userchoice.equalsIgnoreCase("Q"));
		outputWriter.flush();
		outputWriter.close();
	}
	
	public static int readaccts(Bank bank) throws IOException{
		int currentAccount = 0;
		
		File ReadAccts = new File("C:\\Users\\dweng\\git\\CISC_HW\\CISC3115_HW_7\\src\\RegisteredAccounts.txt");
		Scanner fileReader = new Scanner(ReadAccts);
		
		while(fileReader.hasNext())	{
			String [] line = fileReader.nextLine().split(" ");
			String firstName = line[0];
			String lastName = line[1];
			String SSnumber = line[2];
			int acctnum = Integer.parseInt(line[3]);
			String acctType = line[4];
			String Status = line[5];
			double balance = Double.parseDouble(line[6]);
			
			Name name = new Name(firstName,lastName);
			Depositor depositor = new Depositor(name, SSnumber);
			Account account = null;
			if(acctType.equals("Savings")) {
				account = new SavingAccounts(depositor,acctnum,acctType,balance,Status);
			}else if (acctType.equals("CD")) {
				String [] date = line[7].split("/");
				Calendar maturityDate = Calendar.getInstance();
				maturityDate.set(Integer.parseInt(date[2]), Integer.parseInt(date[0]) -1, Integer.parseInt(date[1]));
				account = new CDAccount(depositor, acctnum, acctType, balance, Status, maturityDate);
			}
			else if (acctType.equals("Checking")){
				account = new CheckingAccount(depositor, acctnum, acctType, balance, Status);
			}
			currentAccount++;
			bank.addAccounts(account);
		}
		return currentAccount;
	}
	public static void menu()	{
		System.out.println("Select one of the Following : ");
		System.out.println("W - Withdrawal");
		System.out.println("D - Deposit");
		System.out.println("C - Clear Account");
		System.out.println("N - New Account");
		System.out.println("B - Balance");
		System.out.println("I - Account Info)");
		System.out.println("H - Account Transaction History");
		System.out.println("S - Close Account");
		System.out.println("R - Reopen Account");
		System.out.println("X - Delete Account");
		System.out.println("Q - Quit");
	}
	
	public static void printAccts(Bank bank, PrintWriter outputWriter)	{
//		ArrayList<Accounts> acc = bank.getbankAccounts();
		int numAccounts = bank.getbankSize();
		outputWriter.printf("%-12s %-12s %-12s %-10s %-12s %-12s %-12s %8s%n",
				"First Name", "Last Name", "SSN", "ACCT NUM","ACCT TYPE", "STATUS", "BALANCE","MATURITY DATE");
		for(int i = 0; i < numAccounts; i ++) {
			Account a = bank.getbankAccounts(i);
			
			if(a != null) {
				outputWriter.println(a.toString());
				System.out.println(a.toString());
			}
		}
		
		outputWriter.println();
		outputWriter.flush();
	}
	/* Handles a withdrawal transaction from a user's account.
	 * Prompts the user for the account number and withdrawal amount,
	 * creates a TransactionTicket, requests the Bank to process it,
	 * and prints the resulting TransactionReceipt.
	 */
	public static void withdrawalMethod(Bank bank, PrintWriter outputWriter, Scanner userinput)	{
		//ArrayList<Accounts> acc = bank.getbankAccounts();
		outputWriter.println("Transaction Type : Withdrawal ");
		System.out.println("Enter Account Number : ");
		int accountNumber = userinput.nextInt();
		System.out.println("Withdrawal Amount : ");
		double withdrawalAmount = userinput.nextDouble();
		Calendar withdrawalTime = Calendar.getInstance();
		TransactionTicket ticket = new TransactionTicket(accountNumber, withdrawalTime, "WITHDRAWAL", withdrawalAmount, 0);
		TransactionReceipt receipt = bank.makeWithdrawal(ticket, accountNumber, userinput);
//		printTransactionReceipt(bank,outputWriter,receipt);
		outputWriter.println(receipt.toString());
	}
	/* * Handles a deposit transaction for a specific bank account.
	 * Prompts the user for an account number and deposit amount,
	 * creates a TransactionTicket, and processes the deposit through the Bank object.
	 */
	public static void deposit(Bank bank, Scanner userInput, PrintWriter outputWriter) {
		//ArrayList<Accounts> accounts = bank.getbankAccounts();			
		outputWriter.println("Transaction: Deposit");
		System.out.println("Enter Account Number : ");
		int accountNumber = userInput.nextInt();
		System.out.println("Enter Deposit Amount: ");
		double amount = userInput.nextDouble();
		Calendar time = Calendar.getInstance();
		TransactionTicket ticket = new TransactionTicket(accountNumber, time, "DEPOSIT", amount, 0);		
		TransactionReceipt receipt = bank.makedeposit(ticket, accountNumber, userInput);
		int index = bank.getindex();
//		printTransactionReceipt(bank,outputWriter,receipt);
		outputWriter.println(receipt);
	}
	/* Handles a balance inquiry for a specific bank account.
	 * Prompts the user for an account number, creates a TransactionTicket,
	 * retrieves the current balance through the Bank object, and outputs the result.
	 */
	public static void balance(Bank bank, Scanner userInput, PrintWriter outputWriter) {
		//ArrayList<Accounts>accounts = bank.getbankAccounts();
		outputWriter.println("Transaction: Balance");
		System.out.println("Enter Account Number : ");
		int accountNumber = userInput.nextInt();
		Calendar time = Calendar.getInstance();
		TransactionTicket ticket = new TransactionTicket(accountNumber, time, "BALANCE", 0.0 , 0);	
		TransactionReceipt receipt = bank.getBalance(ticket,accountNumber);
		int index = bank.getindex();		
		if(index != -1) {
			outputWriter.println(receipt.toString());
		}else {
			outputWriter.println(receipt.toString());

		}	
	}
	/* Processes a check-clearing transaction for a specific bank account.
	 * Prompts the user for account number, check details (amount and date),
	 * creates a Check and TransactionTicket object, and requests the Bank
	 * to validate and clear the check.
	 */
	public static void clearCheck(Bank bank, Scanner userInput, PrintWriter outputWriter) {
		//ArrayList<Accounts>accounts = bank.getbankAccounts();
		outputWriter.println("Transaction: Clear Check");
		Calendar time = Calendar.getInstance();
		// Prompt for account number
		System.out.println("Enter Account Number : ");
		int accountNumber = userInput.nextInt();
		// Prompt for the check amount
		System.out.println("Enter Check Amount : ");
		double amount = userInput.nextDouble();
		// Prompt for the check’s date information (month, day, year)
	    System.out.print("Enter check month (1-12): ");
		int month = userInput.nextInt();
		System.out.print("Enter check day: ");
		int day = userInput.nextInt();
		System.out.print("Enter check year: ");
	    int year = userInput.nextInt();
	    Calendar checkDate = Calendar.getInstance();
	    checkDate.set(year, month - 1, day);
	    Check check = new Check(accountNumber, amount, checkDate);
	   	TransactionTicket ticket = new TransactionTicket(accountNumber, time, "Clear Check", amount,0);			TransactionReceipt receipt = bank.clearCheck(ticket, accountNumber, checkDate);
//		printTransactionReceipt(bank,outputWriter,receipt);
	   	outputWriter.println(receipt.toString());
	}
	/* Creates a new bank account (Checking, Savings, or CD) and adds it to the bank.
	 * Prompts the user for personal and account information, constructs the appropriate
	 * Account object, and delegates account creation to the Bank class.
	 */
	public static void newAccount(Bank bank, Scanner userInput, PrintWriter outputWriter) {
		//ArrayList<Accounts>accounts = bank.getbankAccounts();
		System.out.println("Enter First Name: ");
		String firstName = userInput.next();
		System.out.println("Enter Last Name: ");
		String lastName = userInput.next();
		System.out.println("Enter SSN: ");
		String ssn = userInput.next();
		System.out.println("Enter Account Number: ");
		int accountNumber = userInput.nextInt();
		System.out.println("Enter Account Type (Checking, Savings, CD): ");
		String accountType = userInput.next();
		System.out.println("Enter Account Status (Open/Closed): ");
		String status = userInput.next();
		Name name = new Name(firstName, lastName);
		Depositor d = new Depositor(name, ssn);
		Calendar maturityDate = null;
		if (accountType.equalsIgnoreCase("CD")) {
			// Prompt for maturity date only if the new account is a Certificate of Deposit
			maturityDate = Calendar.getInstance();
			System.out.println("Enter Maturity Date (MM DD YYYY): ");
			int month = userInput.nextInt();
			int day = userInput.nextInt();
			int year = userInput.nextInt();
			// Adjust for zero-based months in Calendar
			maturityDate.set(year, month - 1, day);    
		    Account newAccount = new Account(d,accountNumber,accountType,0.0, status, maturityDate);
		    TransactionReceipt receipt = bank.makeNewAcct(newAccount);
		    
		        if(receipt.getTransactionSuccessIndicatorFlag() == false) {
		        	outputWriter.println(receipt.toString());
		        }else {
		        	// Print success message including maturity date	
		        	outputWriter.println(receipt.toString());
//		        	outputWriter.printf("Account Type : %s%nSSN : %s%nAccount Status : %s%nMaturity Date : %tD%n", 
//		        			accountType,
//		        			ssn,
//		        			status,
//		        			maturityDate);
		        }

		    }else {
		    	// Create a non-CD account (Checking or Savings) with default balance = 0.0
			    Account newAccount = new Account(d, accountNumber,accountType,0.0, status);
			    TransactionReceipt receipt = bank.makeNewAcct(newAccount);
			    // Handle success or failure messages
			    if(receipt.getTransactionSuccessIndicatorFlag() == false) {
			    	outputWriter.println(receipt.toString());
		        }else {
		        	// Print confirmation message for non-CD account
//		        	outputWriter.printf("Account Number : %d%nAccount Type : %s%nSSN : %s%nAccount Status : %s%n", 
//		        			receipt.getTransactionTicket().getAccountnumber(),
//		        			accountType,
//		        			ssn,
//		        			status);
		        	outputWriter.println(receipt.toString());
//		        	outputWriter.println();
		        }
		    }
		}
	/* Prints detailed account information for a customer based on their SSN.
	 * Searches all accounts in the bank for matches to the provided SSN.
	 * If matching accounts are found, displays depositor information and account details.
	 * If no matching account exists, an error message is printed.
	 */
	public static void acctInfo(Bank bank, Scanner userInput, PrintWriter outputWriter) {
//		ArrayList<Accounts> accounts = bank.getbankAccounts();
		int numOfAccounts = bank.getbankSize();
		System.out.print("SSN: ");
		String socialSecurityNumber = userInput.next();
		boolean found = false;
		// Print headers once
		outputWriter.printf("%-12s %-12s %-12s %-10s %-12s %-12s %-10s %-15s%n",
				"First Name", "Last Name", "SSN", "Acct Num", "Acct Type", "Status", "Balance", "Maturity Date");

		// Loop through accounts to find matching SSN
		for (int i = 0; i < numOfAccounts; i++) {
			if (socialSecurityNumber.equals(bank.getbankAccounts(i).getdepositor().getSSnumber())) {
				found = true;
				Account acc = bank.getbankAccounts(i);
				outputWriter.println(acc.toString());
			    }
			}

			// If no account matched the entered SSN
			if (!found) {
			    outputWriter.println();
			    outputWriter.println("Transaction Failed");
			    outputWriter.println("ERROR: Account not found for SSN " + socialSecurityNumber);
			    outputWriter.println("--------------------");
			}
			outputWriter.println();
		}
	/* Prints the transaction history for a specific bank account based on SSN.
	 * Searches all accounts in the bank for a matching SSN and prints all past transactions.
	 * If no matching account exists or there are no transactions, appropriate messages are displayed 
	 */

	public static void acctInfoHistory(Bank bank, Scanner userInput, PrintWriter outputWriter) {
	    int numOfAccts = bank.getbankSize();
	    System.out.print("SSN: ");
	    String socialSecurityNumber = userInput.next();
	    boolean found = false;
	    // Print headers for account information
	    outputWriter.printf("%-12s %-12s %-12s %-10s %-12s %-12s %-10s %-15s%n",
	            "First Name", "Last Name", "SSN", "Acct Num", "Acct Type", "Status", "Balance", "Maturity Date");
	    outputWriter.println("----------------------------------------------------------------------------------------------------------------");
	    for (int i = 0; i < numOfAccts; i++) {
	        Account acc = bank.getbankAccounts(i);
	        if (acc != null && socialSecurityNumber.equals(acc.getdepositor().getSSnumber())) {
	            found = true;
	            outputWriter.println(acc.toString());
	            ArrayList<TransactionReceipt> receipts = acc.gettransactionRecipts();
	            if (receipts.isEmpty()) {
	                outputWriter.println("No transactions yet.");
	            } else {
	                outputWriter.println("***** Account Transactions (Full Receipt Details) *****");
	                for (TransactionReceipt receipt : receipts) {
	                    outputWriter.println(receipt.toString());
	                    outputWriter.println("------------------------");
	                }
	            }
	            outputWriter.println();
	        }	
	    }

	    // If no account matched the entered SSN
	    if (!found) {
	        outputWriter.println();
	        outputWriter.println("Transaction Failed");
	        outputWriter.println("ERROR: Account not found for SSN " + socialSecurityNumber);
	        outputWriter.println("--------------------");
	    }
	    outputWriter.println();
	}	
	
	/* Closes a specific bank account based on the account number entered by the user.
	 * Creates a "Close Account" transaction, requests the Bank to process it,
	 * and prints the updated account status or an error message if the account is not found.
	 */
	public static void closeAcct(Bank bank, Scanner userinput, PrintWriter outputWriter) {
	    Calendar time = Calendar.getInstance();
	    System.out.print("Enter Account Number to Close: ");
	    int acctNum = userinput.nextInt();
	    TransactionTicket ticket = new TransactionTicket(acctNum, time, "Close Account", 0, 0);
	    TransactionReceipt receipt = bank.closeAcct(ticket, acctNum);
	    outputWriter.println(receipt.toString());
	}
	
	/* Reopens a previously closed bank account based on the account number entered by the user.
	 * Creates an "Open Account" transaction, requests the Bank to process it,
	 * and prints the updated account status or an error message if the account is not found.
	 */
	public static void reopenAcct(Bank bank, Scanner userinput, PrintWriter outputWriter) {
	    Calendar time = Calendar.getInstance();
	    System.out.print("Enter Account Number to Reopen: ");
	    int acctNum = userinput.nextInt();
	    TransactionTicket ticket = new TransactionTicket(acctNum, time, "Open Account", 0, 0);
	    TransactionReceipt receipt = bank.openAcct(ticket, acctNum);
	    outputWriter.println(receipt.toString());
	}
	/* Deletes a specific bank account based on the account number entered by the user.
	 * Creates a "Delete Account" transaction, requests the Bank to process it,
	 * and prints an error message if the deletion fails.
	 */
	public static void deleteAcct(Bank bank, Scanner userinput, PrintWriter outputWriter) {
		//ArrayList<Accounts>accounts = bank.getbankAccounts();
		Calendar time = Calendar.getInstance();
		// Prompt user to enter the account number to delete
		System.out.println("Enter Account Number : ");
		int accountNumber = userinput.nextInt();
		TransactionTicket ticket = new TransactionTicket(accountNumber, time, "Delete Account",0,0);
		TransactionReceipt receipt = bank.deleteAcct(ticket, accountNumber);
		outputWriter.println(receipt.toString());
	}
}
