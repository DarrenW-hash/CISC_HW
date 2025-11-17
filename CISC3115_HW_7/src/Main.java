import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Main {
	
	public static void main (String args [])throws IOException	{
		File inputFile = new File("C:\\Users\\dweng\\eclipse-workspace\\CISC3115_HW_7\\input.txt");
		PrintWriter outputWriter = new PrintWriter("C:\\Users\\dweng\\eclipse-workspace\\CISC3115_HW_7\\output.txt");
		Scanner userinput = new Scanner(inputFile);
		
	}
	
	public static void readaccts() throws IOException{
		int currentAccount = 0;
		
		File ReadAccts = new File("C:\\Users\\dweng\\eclipse-workspace\\CISC3115_HW_7\\src\\RegisteredAccounts.txt");
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
			Account account;
			
			if(acctType.equals("CD"))	{
				String [] date = line[7].split("/");
				Calendar maturityDate = Calendar.getInstance();
				maturityDate.set(Integer.parseInt(date[2]), Integer.parseInt(date[0]) -1, Integer.parseInt(date[1]));
				account = new Account(depositor, acctnum, acctType, balance, Status, maturityDate);
			}else {
				account = new Account(depositor, acctnum, acctType, balance, Status);
			}
			currentAccount++;
			Bank.addAccounts(account);
		}
		return currentAccount;
	}
	
}
