import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

import Exceptions.*;

public class SavingAccounts extends Account{

	private final double WITHDRAWAL_FEE = 1.50;
	private Calendar date; 
	
	public SavingAccounts(Depositor depositor, int AccountNumber, String AccountType, double balance, String accountStatus) {
		super(depositor, AccountNumber, AccountType, balance, accountStatus);
		this.date = Calendar.getInstance();
	}
	public SavingAccounts(Depositor depositor, int AccountNumber, String AccountType, double balance, String accountStatus, Calendar date ) {
		super(depositor, AccountNumber, AccountType, balance, accountStatus, date);
	}
	//Copy Constructor
	public SavingAccounts(SavingAccounts other) {
		super(other);
	}

	@Override
	public Account getCopy() {
		return new SavingAccounts(this);
	}

	@Override
	public TransactionReceipt makeWithDrawal(TransactionTicket ticket, Scanner userinput) {
		double preTransaction = getbalance();
		Calendar currentDate = Calendar.getInstance();
		
		try{
			if (getStatus().equals("Closed")) {
		        throw new AccountClosedException("Error: Account Number " + ticket.getAccountnumber() + " is CLOSED");
			}
			if (ticket.getTransactionAmount() < 0) {
		        throw new InvalidAmountException(ticket.getTransaction(),ticket.getTransactionAmount());
		    }
			if(preTransaction < ticket.getTransactionAmount()) {
				throw new InsufficientFundsException(
						ticket.getTransactionAmount(), getbalance());
			}
			double newBalance = preTransaction - ticket.getTransactionAmount();
		    setbalance(newBalance);

		    TransactionReceipt receipt =
		        new TransactionReceipt(ticket, true, preTransaction, newBalance, currentDate,
		                               getStatus(), getaccountType());
		    writeReceiptstoFile(receipt);
            addtransactionReceipt(receipt);
            return receipt;
		}catch(Exception e ) {
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
		
	@Override
	public TransactionReceipt makedeposit(TransactionTicket ticket, Scanner userinput) {
		double preTransaction = getbalance();
		Calendar currentDate = Calendar.getInstance();
		
		try {
			if(getStatus().equals("Closed")) {
				throw new AccountClosedException( "Error: Account Number : " + ticket.getAccountnumber() + " is CLOSED");
			}
			if(ticket.getTransactionAmount() < 0) {
				throw new InvalidAmountException(ticket.getTransaction(),ticket.getTransactionAmount());
			}
			double newBalance = preTransaction + ticket.getTransactionAmount();
		    setbalance(newBalance);

		    TransactionReceipt receipt =
		        new TransactionReceipt(ticket, true, preTransaction, newBalance,
		                               currentDate, getStatus(), getaccountType());
		    
		    writeReceiptstoFile(receipt);
            addtransactionReceipt(receipt);
            return receipt;
		}catch(Exception e) {
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

	//Clearing a Savings Account is not Allowed
	@Override
	public TransactionReceipt clearCheck(TransactionTicket ticket, Calendar checkDate) {
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
