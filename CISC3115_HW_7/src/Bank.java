import java.util.ArrayList;
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
	public Account getBankAccounts(int index) {
		if()
	}
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
	public int getIndex() {
		return index;
	}
	public int getBankSize() {
		return bankAccounts.size();
	}
	
	private int findAccts(int acctNumber) {
		int index = 0;
		for(int i = 0; i < getBankSize(); i ++) {
			if(bankAccounts.get(i).getAccountNumber() == acctNumber) {
				
			}
		}
	}
}
