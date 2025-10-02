package pg_004_CISC3315_hw4;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;


public class Bank {
	
	
	public static void main(String args []) throws IOException{
		ArrayList<Account> bankaccounts = new ArrayList<Account>();
		
		ReadAccount(bankaccounts);
		
		int currenttotal = bankaccounts.size();
		
		for (int i = 0; i < currenttotal; i++) {
		    System.out.println(bankaccounts.get(i).getdepositor().getNames().getFirstName() + " " 
		        + bankaccounts.get(i).getdepositor().getNames().getLastName() + " "
		        + bankaccounts.get(i).getdepositor().getSSnumber() + " "
		        + bankaccounts.get(i).getAccountNumber() + " "
		        + bankaccounts.get(i).getaccountType() + " "
		        + bankaccounts.get(i).getbalance() + " "
		        + bankaccounts.get(i).getdate());
		 
//		    if(bankaccounts.get(i).getaccountType() == "CD") {
//		    	System.out.println(bankaccounts.get(i).getdepositor().getNames().getFirstName() + " " 
//				        + bankaccounts.get(i).getdepositor().getNames().getLastName() + " "
//				        + bankaccounts.get(i).getdepositor().getSSnumber() + " "
//				        + bankaccounts.get(i).getAccountNumber() + " "
//				        + bankaccounts.get(i).getaccountType() + " "
//				        + bankaccounts.get(i).getbalance() + " "
//		    			+ bankaccounts.get(i).getdate());
//		    }
		}
		
		
	}
	
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
			
			if(token4 == "CD") {
				String [] Date = line[6].split("/");
				int month = Integer.parseInt(Date[0]);
				int day = Integer.parseInt(Date[1]);
				int year = Integer.parseInt(Date[2]);
				
				Calendar maturityDate = Calendar.getInstance();
				maturityDate.set(year, month, day);
				
				Name name = new Name(token0,token1);
				Depositor depositor = new Depositor(name,token2);
				Account account = new Account(depositor, token3,token5,token4,maturityDate);
				bankaccounts.add(account);
				
	
			}
			Name name = new Name(token0,token1);
			Depositor depositor = new Depositor(name,token2);
			Account account = new Account(depositor, token3,token5,token4);
			bankaccounts.add(account);
		}
		
	}
}
