package pg_004_CISC3315_hw4;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;


public class Bank {
	
	
	public static void main(String args []) throws IOException{
		ArrayList<Account> bankaccounts = new ArrayList<Account>();
		String userchoice;
		
		Scanner userinput = new Scanner(System.in);
		ReadAccount(bankaccounts);
		
		int currenttotal = bankaccounts.size();
		
		//printing to console
		for(int i = 0 ; i < currenttotal; i++) {
			if(bankaccounts.get(i).getaccountType().equals("CD")) {
				System.out.printf("%s %s %s %d %s %.2f %tD%n", bankaccounts.get(i).getdepositor().getNames().getFirstName(), 
						bankaccounts.get(i).getdepositor().getNames().getLastName(),
						bankaccounts.get(i).getdepositor().getSSnumber(),
						bankaccounts.get(i).getAccountNumber(), 
						bankaccounts.get(i).getaccountType(),
						bankaccounts.get(i).getbalance(),
						bankaccounts.get(i).getdate().getTime());
		    }
			else {
				System.out.printf("%s %s %s %d %s %.2f%n", bankaccounts.get(i).getdepositor().getNames().getFirstName(), 
						bankaccounts.get(i).getdepositor().getNames().getLastName(),
						bankaccounts.get(i).getdepositor().getSSnumber(),
						bankaccounts.get(i).getAccountNumber(), 
						bankaccounts.get(i).getaccountType(),
						bankaccounts.get(i).getbalance());
			}
		}
		
		do{
			menu();
			userchoice = userinput.next();
			
			switch(userchoice) {
				case("W"):
				case("w"):
					System.out.println("Enter Account Number : ");
					int accountnum  = userinput.nextInt();
					System.out.println("Enter Withdrawal Amount :");
					double withdrawalAmount = userinput.nextDouble();
					Calendar currentTime = Calendar.getInstance();
					
					TransactionTicket ticket = new TransactionTicket(accountnum, currentTime, userchoice, withdrawalAmount, 0);
					withdrawal(bankaccounts,ticket);
					break;
				case("D"):
				case("d"):
				
				case("C"):
				case("c"):
				
				case("N"):
				case("n"):
				
				case("B"):
				case("b"):
				
				case("I"):
				case("i"):
				
				case("H"):
				case("h"):
				
				case("S"):
				case("s"):
				
				case("R"):
				case("r"):
				
				case("X"):
				case("x"):
			}
			
		}while(userchoice != "Q" || userchoice != "q");
		
	}
	/*This method reads in the data from BankAccounts.txt and fills out the bankaccounts Arraylist
	 *lines are read in and split into the string [] each token repersents a data value of the bankaccount user 
	 */
	public static void ReadAccount(ArrayList bankaccounts) throws IOException{
		File bankaccountfile = new File("/Users/darrenweng/git/CISC_HW/pg_004_CISC3315_hw4/src/BankAccounts.txt");
		Scanner fileReader = new Scanner(bankaccountfile);
		
		while (fileReader.hasNext()){
			String [] line = fileReader.nextLine().split(" ");
			//first Name
			String token0 = line[0];
			//last Name
			String token1= line[1];
			//SSnumber
			String token2 = line[2];
			//account Number 
			int token3 = Integer.parseInt(line[3]);
			//account Type
			String token4 = line[4];
			//double balance
			double token5 = Double.parseDouble(line[5]);
			
			Name name = new Name(token0, token1);
            Depositor depositor = new Depositor(name, token2);
			
			 if (token4.equals("CD")) {
	                String[] Date = line[6].split("/");
	                int month = Integer.parseInt(Date[0]) - 1; // Calendar months are 0-based
	                int day = Integer.parseInt(Date[1]);
	                int year = Integer.parseInt(Date[2]);
	                
	                Calendar maturityDate = Calendar.getInstance();
	                maturityDate.set(year, month, day);
	                
	                Account account = new Account(depositor, token3, token5, token4, maturityDate);
	                bankaccounts.add(account);
	            } else {
	                Account account = new Account(depositor, token3, token5, token4);
	                bankaccounts.add(account);
	            }
		}
	}
	
	public static void printAccounts(ArrayList bankAccounts) {
		
	}
	/*Searches throught the list of bankAccounts to find the requested accountnumber
	 *once found the index location of the requested accountnumber is returned
	 *else if not found index of -1 is returned
	 */
	private static int findAcct(ArrayList<Account> bankAccounts, int ReqaccNumber) {
		int index = 0;
		for(int i = 0; i < bankAccounts.size(); i ++) {
			if (bankAccounts.get(i).getAccountNumber() == ReqaccNumber) {
	            return i; // Found the account, return its index
	        }
		}
		return index = -1;
	}
	/*This method prints the menu
	 *for the user choices
	 */
	public static void menu() {
		System.out.println("Select one of the Following : ");
		System.out.println("W - Withdrawal ");
		System.out.println("D - Deposit ");
		System.out.println("C - Clear Check ");
		System.out.println("N - New Account");
		System.out.println("B - Balance");
		System.out.println("I - Account Info");
		System.out.println("H - Account Info plus Account Transaction History");	
		System.out.println("S - Close Account");
		System.out.println("R - Reopen a closed account");
		System.out.println("X - Delete Account");
		System.out.println("Q - Quit");
	}
	
	
	public static void withdrawal(ArrayList<Account>bankAccounts, TransactionTicket Ticket) {
		int accountnumber = Ticket.getAccountnumber();
		int index = findAcct(bankAccounts, accountnumber);
		if(index != -1) {
			
			
			
		}
		else {
			System.out.println("Error Account Number : "+ accountnumber +" not found.");
		}
	}
}
