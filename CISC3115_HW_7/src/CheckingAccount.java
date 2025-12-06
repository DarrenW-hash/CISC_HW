import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;
import Exceptions.*;

public class CheckingAccount extends Account{
	
	public CheckingAccount(Depositor depositor, int AccountNumber, String accountType, double balance, String accountStatus) {
		super(depositor, AccountNumber, accountType, balance, accountStatus);
	 }
	
	public CheckingAccount(Depositor depositor, int AccountNumber, String accountType, double balance, String accountStatus, Calendar date) {
		super(depositor, AccountNumber, accountType, balance, accountStatus, date);
	}
	
	public CheckingAccount(CheckingAccount other) {
        super(other);
    }

	@Override
	public Account getCopy() {
		return new CheckingAccount(this);
	}

	@Override
	public TransactionReceipt makeWithDrawal(TransactionTicket ticket, Scanner userinput) {
		double preTransaction = getbalance();
		Calendar currentDate = Calendar.getInstance();
		
		try {
			if (getStatus().equals("Closed")) {
                throw new AccountClosedException("Error: Account Number : " + ticket.getAccountnumber()+ " is CLOSED");
            }
            if (ticket.getTransactionAmount() < 0) {
                throw new InvalidAmountException(ticket.getTransaction(),ticket.getTransactionAmount());
            }
            if (preTransaction < ticket.getTransactionAmount()) {
                throw new InsufficientFundsException(
                    ticket.getTransactionAmount(),
                    preTransaction);
            }
            
            double newBalance = preTransaction - ticket.getTransactionAmount();
            setbalance(newBalance);

            TransactionReceipt receipt = new TransactionReceipt(
                    ticket, true, preTransaction, newBalance, currentDate, getStatus(), getaccountType());

            writeReceiptstoFile(receipt);
            addtransactionReceipt(receipt);
            return receipt;
		}catch(Exception e) {
			TransactionReceipt receipt = new TransactionReceipt(
                    ticket, false, e.getMessage(),
                    preTransaction, preTransaction,
                    currentDate, getStatus(), getaccountType()
            );

            try { writeReceiptstoFile(receipt); 
            } 
            catch (IOException ex) 
            { System.out.println("writeReceiptstoFile ERROR"); }

            addtransactionReceipt(receipt);
            return receipt;
			}
		}
		
	@Override
	public TransactionReceipt makedeposit(TransactionTicket ticket, Scanner userinput) {
		double preTransaction = getbalance();
		Calendar currentDate = Calendar.getInstance();
		
		try {
			if (getStatus().equals("Closed")) {
                throw new AccountClosedException("Error: Account Number : " + ticket.getAccountnumber()+ " is CLOSED");
            }
            if (ticket.getTransactionAmount() < 0) {
                throw new InvalidAmountException(ticket.getTransaction(),ticket.getTransactionAmount());
            }
            double newBalance = preTransaction + ticket.getTransactionAmount();
            setbalance(newBalance);

            TransactionReceipt receipt = new TransactionReceipt(
                    ticket, true, preTransaction, newBalance, currentDate, getStatus(), getaccountType());

            writeReceiptstoFile(receipt);
            addtransactionReceipt(receipt);
            return receipt;
            
		}catch(Exception e) {
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, e.getMessage(),
                    preTransaction, preTransaction,
                    currentDate, getStatus(), getaccountType()
            );

            try { writeReceiptstoFile(receipt);
            } 
            catch (IOException ex){ 
            	System.out.println("writeReceiptstoFile ERROR"); 
            	}

            addtransactionReceipt(receipt);
            return receipt;
		}
	}
	
	@Override
	public TransactionReceipt clearCheck(TransactionTicket ticket, Calendar checkDate) {
		double preTransaction = getbalance();
		Calendar currentDate = Calendar.getInstance();
		Calendar sixMonthsAgo = (Calendar)currentDate.clone();
		sixMonthsAgo.add(Calendar.MONTH, -6);
		Check check = new Check(ticket.getAccountnumber(), ticket.getTransactionAmount(), checkDate);	
		
		try {
			if (getStatus().equals("Closed")) {
                throw new AccountClosedException("Error: Account Number : " + ticket.getAccountnumber()+ " is CLOSED");
            }
			if (check.getDateofCheck().after(currentDate)) {
                throw new PostDatedCheckException(check.getDateofCheck());
            }
			if (check.getDateofCheck().before(sixMonthsAgo)) {
                throw new CheckTooOldException(check.getDateofCheck(), sixMonthsAgo);
            }
			if (preTransaction < check.getCheckAmount()) {
                // bounce check fee
                double newBalance = preTransaction - 2.50;
                setbalance(newBalance);

                throw new InsufficientFundsException(check.getCheckAmount(), preTransaction);
            }
			
			double newBalance = preTransaction - check.getCheckAmount();
            setbalance(newBalance);

            TransactionReceipt receipt = new TransactionReceipt(
                    ticket, true, preTransaction, newBalance, currentDate, getStatus(), getaccountType());

            writeReceiptstoFile(receipt);
            addtransactionReceipt(receipt);
            return receipt;
		}catch(Exception e) {
			TransactionReceipt receipt = new TransactionReceipt(
                    ticket, false, e.getMessage(),
                    preTransaction, getbalance(),
                    currentDate, getStatus(), getaccountType()
            );

            try { 
            	writeReceiptstoFile(receipt);
            	} 
            catch (IOException ex) {
            	System.out.println("writeReceiptstoFile ERROR"); 
            	}

            addtransactionReceipt(receipt);
            return receipt;
		}
	}
}
		

