import java.util.Calendar;
import java.util.Scanner;

public class CDAccount extends Account{
	
	public CDAccount(Depositor depositor, int AccountNumber, String AccountType, double balance, String accountStatus, Calendar date) {
		super(depositor,AccountNumber,AccountType,balance,accountStatus,date);
	}
	
	public CDAccount(CDAccount other) {
		super(other);
	}
	
	@Override
	public Account getCopy() {
		return new CDAccount(this);
	}

	@Override
	public TransactionReceipt makeWithDrawal(TransactionTicket ticket, Scanner userinput) {
		Calendar currentDate = Calendar.getInstance();
		double preTransaction = getbalance();
		if(getStatus().equals("Closed")) {
			String reason = "Error: Account Number \" + ticket.getAccountnumber() + \" is CLOSED";
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate, getStatus(),getaccountType());
			addtransactionReceipt(receipt);
			return receipt;
		}else {
			if(currentDate.before(getDate())) {
				Calendar maturity = getDate();
				int month = maturity.get(Calendar.MONTH);
				int day = maturity.get(Calendar.DAY_OF_MONTH);
				int year = maturity.get(Calendar.YEAR);
				String reason = "Error: Maturity Date " + month + "/" + day + "/" + year + " not reached ";
				TransactionReceipt receipt = new TransactionReceipt(ticket, false, reason, getbalance(), getbalance(), getDate(), getStatus(),getaccountType());
				addtransactionReceipt(receipt);
				return receipt;
			}else {
				double newBalance = preTransaction - ticket.getTransactionAmount();
				System.out.println("Select new Maturity period in months: 6, 12, 18, or 24");
				int month = userinput.nextInt();
				if(month == 6 || month == 12 || month == 18 || month == 24){
					Calendar newMaturity = (Calendar) currentDate.clone();
					newMaturity.add(Calendar.MONTH, month);
					setCalendar(newMaturity);
			}else {
				System.out.println("Invalid input. Maturity date not updated.");
			}
				//successful withdrawal 
				setbalance(newBalance);
				TransactionReceipt receipt = new TransactionReceipt(ticket, true, preTransaction, newBalance, getDate(), getStatus(), getaccountType());
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
			if(currentDate.before(getDate())) {
				Calendar maturity = getDate();
				int month = maturity.get(Calendar.MONTH) + 1; // months are 0-based
				int day = maturity.get(Calendar.DAY_OF_MONTH);
				int year = maturity.get(Calendar.YEAR);
				String reason = "Error: Maturity Date " + month + "/" + day + "/" + year + " not reached ";
				TransactionReceipt receipt = new TransactionReceipt(ticket, false, reason, getbalance(),getbalance(), getDate(), getStatus(),getaccountType());
				addtransactionReceipt(receipt); 
				return receipt;
			}else {
				double newBalance = preTransaction + ticket.getTransactionAmount();
				// Prompt user to select new maturity period for CD
				System.out.println("Select new maturity period in months: 6, 12, 18, or 24");
				int months = userinput.nextInt();
				if (months == 6 || months == 12 || months == 18 || months == 24) {
	                Calendar newMaturity = (Calendar) currentDate.clone();
	                newMaturity.add(Calendar.MONTH, months);
	                setCalendar(newMaturity);
				}else {
					System.out.println("Invalid input. Maturity date not updated.");
				}
				// Successful deposit
				setbalance(newBalance);
				TransactionReceipt receipt = new TransactionReceipt(ticket, true, preTransaction, newBalance, getDate(), getStatus(),getaccountType());
				addtransactionReceipt(receipt); 
				return receipt;
			}
		}
	}

	//Clearing a CD account is not allowed
	@Override
	public TransactionReceipt clearCheck(TransactionTicket ticket, Calendar checkDate) {
		Calendar date = Calendar.getInstance();
		double preTransactionBalance = getbalance();
		String reason = "Error only Clear Checking Accounts. " + "Account Type : " + getaccountType();
		TransactionReceipt receipt = new TransactionReceipt(
				ticket,false,reason,preTransactionBalance,0.0,date,getStatus(),getaccountType());
		addtransactionReceipt(receipt); 
		return receipt;
	}
}
