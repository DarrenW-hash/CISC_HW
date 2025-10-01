package pg_004_CISC3315_hw4;

import java.util.ArrayList;

public class Account {

	private Depositor depositor;
	private int AccountNumber;
	private String accountType;
	private boolean accountStatus;
	private ArrayList<TransactionReceipt> transactionRecipts= new ArrayList<TransactionReceipt>();
	private Check check;
	
	public Account(Depositor _depositor, int _AccountNumber, String _accountType, boolean _accountStatus, ArrayList transactionRecipt) {
		
	}
}
