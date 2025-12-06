import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;
import Exceptions.*;

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
	public TransactionReceipt makeWithDrawal(TransactionTicket ticket, Scanner userinput) 
			throws AccountClosedException, CDMaturityDateException {
		Calendar currentDate = Calendar.getInstance();
		double preTransaction = getbalance();
		
		try {
			if(getStatus().equals("Closed")) {
				throw new AccountClosedException("Error: Account Number " + ticket.getAccountnumber() + " is CLOSE");
			}
			if(currentDate.before(getDate())) {
				Calendar maturity = getDate();
				int month = maturity.get(Calendar.MONTH);
				int day = maturity.get(Calendar.DAY_OF_MONTH);
				int year = maturity.get(Calendar.YEAR);
				throw new CDMaturityDateException("Error: Maturity Date " + month + "/" + day + "/" + year + " not reached ");
			}
			if (ticket.getTransactionAmount() <= 0) {
                throw new InvalidAmountException(ticket.getTransaction(),ticket.getTransactionAmount());
            }
			double newBalance = preTransaction + ticket.getTransactionAmount();
			
			System.out.println("Select new Maturity period in months: 6, 12, 18, or 24");
			int month = userinput.nextInt();
			if(month == 6 || month == 12 || month == 18 || month == 24){
				Calendar newMaturity = (Calendar) currentDate.clone();
				newMaturity.add(Calendar.MONTH, month);
				setCalendar(newMaturity);

	            // Update balance
	            setbalance(newBalance);
	            
	            TransactionReceipt receipt =
	                    new TransactionReceipt(ticket, true, preTransaction, newBalance, getDate(),
	                                           getStatus(), getaccountType());

	            writeReceiptstoFile(receipt);
	            addtransactionReceipt(receipt);
	            return receipt;
			}else {
				throw new InvalidMenuSelectionException("Invalid input. Maturity date not updated.");
			}
			

		}catch (Exception e) {
			double newBalance = preTransaction - ticket.getTransactionAmount();
            setbalance(newBalance);

            TransactionReceipt receipt = new TransactionReceipt(
                    ticket, true, preTransaction, newBalance, currentDate, getStatus(), getaccountType());

            try {
				writeReceiptstoFile(receipt);
			} catch (IOException e1) {
				System.out.println("writeReceiptstoFile ERROR");
			}
            addtransactionReceipt(receipt);
            return receipt;
		}
	}
		
//		if(getStatus().equals("Closed")) {
//			String reason = "Error: Account Number \" + ticket.getAccountnumber() + \" is CLOSED";
//			TransactionReceipt receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate, getStatus(),getaccountType());
//			try {
//				writeReceiptstoFile(receipt);
//			} catch (IOException e) {
//				System.out.println("writeReceiptstoFile - withdrwal/ CD Account/ ERROR");
//			}
//			addtransactionReceipt(receipt);
//			return receipt;
//		}else {
//			if(currentDate.before(getDate())) {
//				Calendar maturity = getDate();
//				int month = maturity.get(Calendar.MONTH);
//				int day = maturity.get(Calendar.DAY_OF_MONTH);
//				int year = maturity.get(Calendar.YEAR);
//				String reason = "Error: Maturity Date " + month + "/" + day + "/" + year + " not reached ";
//				TransactionReceipt receipt = new TransactionReceipt(ticket, false, reason, getbalance(), getbalance(), getDate(), getStatus(),getaccountType());
//				try {
//					writeReceiptstoFile(receipt);
//				} catch (IOException e) {
//					System.out.println("writeReceiptstoFile - withdrawal/ CD Account/ ERROR");
//				}
//				addtransactionReceipt(receipt);
//				return receipt;
//			}else {
//				double newBalance = preTransaction - ticket.getTransactionAmount();
//				System.out.println("Select new Maturity period in months: 6, 12, 18, or 24");
//				int month = userinput.nextInt();
//				if(month == 6 || month == 12 || month == 18 || month == 24){
//					Calendar newMaturity = (Calendar) currentDate.clone();
//					newMaturity.add(Calendar.MONTH, month);
//					setCalendar(newMaturity);
//			}else {
//				System.out.println("Invalid input. Maturity date not updated.");
//			}
//				//successful withdrawal 
//				setbalance(newBalance);
//				TransactionReceipt receipt = new TransactionReceipt(ticket, true, preTransaction, newBalance, getDate(), getStatus(), getaccountType());
//				try {h
//					writeReceiptstoFile(receipt);
//				} catch (IOException e) {
//					System.out.println("writeReceiptstoFile - withdrawal/ CD Account/ ERROR");
//				}
//				addtransactionReceipt(receipt);
//				return receipt;
//			}
//		}

	@Override
	public TransactionReceipt makedeposit(TransactionTicket ticket, Scanner userinput) {
		double preTransaction = getbalance();
		Calendar currentDate = Calendar.getInstance(); 
		try {
			if(getStatus().equals("Closed")) {
				throw new AccountClosedException("Error: Account Number : " + ticket.getAccountnumber()+ " is CLOSED");
			}
			
			if(currentDate.before(getDate())) {
				Calendar maturity = getDate();
				int month = maturity.get(Calendar.MONTH) + 1; // months are 0-based
				int day = maturity.get(Calendar.DAY_OF_MONTH);
				int year = maturity.get(Calendar.YEAR);
				
				throw new CDMaturityDateException("Error: Maturity Date " + month + "/" + day + "/" + year + " not reached ");
			}
			
			double newBalance = preTransaction +  ticket.getTransactionAmount();
			// Prompt user to select new maturity period for CD
			System.out.println("Select new maturity period in months: 6, 12, 18, or 24");
			int months = userinput.nextInt();
			if(months == 6 || months == 12 || months == 18 || months == 24) {
				Calendar newMaturity = (Calendar) currentDate.clone();
				newMaturity.add(Calendar.MONTH, months);
				setCalendar(newMaturity);

	            // Update balance
	            setbalance(newBalance);
	            
	            TransactionReceipt receipt =
	                    new TransactionReceipt(ticket, true, preTransaction, newBalance, getDate(),
	                                           getStatus(), getaccountType());

	            writeReceiptstoFile(receipt);
	            addtransactionReceipt(receipt);
	            return receipt;
			}else {
				throw new InvalidMenuSelectionException("Invalid input. Maturity date not updated.");
			}
		}catch(Exception e)	{
			 TransactionReceipt receipt =
	                    new TransactionReceipt(ticket, false, e.getMessage(),
	                                            preTransaction, preTransaction, getDate(), getStatus(), getaccountType());
	            try {
	                writeReceiptstoFile(receipt);
	            } catch (IOException io) {
	                System.out.println("ERROR writing withdrawal receipt");
	            }
	            addtransactionReceipt(receipt);
	            return receipt;
	        }
		}
		
		
//		if(getStatus().equals("Closed")) {
//			String reason = "Error: Account Number : " + ticket.getAccountnumber()+ " is CLOSED";
//			TransactionReceipt receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate, getStatus(),getaccountType());
//			try {
//				writeReceiptstoFile(receipt);
//			} catch (IOException e) {
//				System.out.println("writeReceiptstoFile - clearCheck/ CD Account/ ERROR");
//			}
//			addtransactionReceipt(receipt); 
//			return receipt;
//		}else {
//			if(currentDate.before(getDate())) {
//				Calendar maturity = getDate();
//				int month = maturity.get(Calendar.MONTH) + 1; // months are 0-based
//				int day = maturity.get(Calendar.DAY_OF_MONTH);
//				int year = maturity.get(Calendar.YEAR);
//				String reason = "Error: Maturity Date " + month + "/" + day + "/" + year + " not reached ";
//				TransactionReceipt receipt = new TransactionReceipt(ticket, false, reason, getbalance(),getbalance(), getDate(), getStatus(),getaccountType());
//				try {
//					writeReceiptstoFile(receipt);
//				} catch (IOException e) {
//					System.out.println("writeReceiptstoFile - clearCheck/ CD Account/ ERROR");
//				}
//				addtransactionReceipt(receipt); 
//				return receipt;
//			}else {
//				double newBalance = preTransaction + ticket.getTransactionAmount();
//				// Prompt user to select new maturity period for CD
//				System.out.println("Select new maturity period in months: 6, 12, 18, or 24");
//				int months = userinput.nextInt();
//				if (months == 6 || months == 12 || months == 18 || months == 24) {
//	                Calendar newMaturity = (Calendar) currentDate.clone();
//	                newMaturity.add(Calendar.MONTH, months);
//	                setCalendar(newMaturity);
//				}else {
//					System.out.println("Invalid input. Maturity date not updated.");
//				}
//				// Successful deposit
//				setbalance(newBalance);
//				TransactionReceipt receipt = new TransactionReceipt(ticket, true, preTransaction, newBalance, getDate(), getStatus(),getaccountType());
//				try {
//					writeReceiptstoFile(receipt);
//				} catch (IOException e) {
//					System.out.println("writeReceiptstoFile - clearCheck/ CD Account/ ERROR");
//				}
//				addtransactionReceipt(receipt); 
//				return receipt;
//			}
//		}
//	}

	//Clearing a CD account is not allowed
	@Override
	public TransactionReceipt clearCheck(TransactionTicket ticket, Calendar checkDate) {
		Calendar date = Calendar.getInstance();
		double preTransactionBalance = getbalance();
		
		try{
			throw new InvalidAccountException("Error only Clear Checking Accounts. " + "Account Type : " + getaccountType());
		}catch(Exception e) {
			TransactionReceipt receipt =
                    new TransactionReceipt(ticket, false, e.getMessage(),
                    	preTransactionBalance, preTransactionBalance, getDate(), getStatus(), getaccountType());
            try {
                writeReceiptstoFile(receipt);
            } catch (IOException io) {
                System.out.println("ERROR writing clearCheck receipt");
            }
            addtransactionReceipt(receipt);
            return receipt;
			}
		}
	}
