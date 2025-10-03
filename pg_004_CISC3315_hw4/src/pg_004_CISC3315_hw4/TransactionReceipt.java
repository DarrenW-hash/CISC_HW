package pg_004_CISC3315_hw4;
import java.util.Calendar;

public class TransactionReceipt {
	
	private TransactionTicket ticket;
	private boolean TransactionSuccessIndicatorFlag;
	private String ReasonForFailure;
	private double PreTransactionbalance;
	private double PostTransactionbalance;
	private Calendar PostTransactionMaturityDate;
	
	public TransactionReceipt(TransactionTicket _ticket, boolean _TransactionSuccessIndiactorFlag, String _ReasonForFailure, double _PreTransactionbalance,
			double _PostTransactionbalance, Calendar _PostTransactionMaturityDate) {
		ticket = _ticket;
		TransactionSuccessIndicatorFlag = TransactionSuccessIndicatorFlag;
		ReasonForFailure = _ReasonForFailure;
		PreTransactionbalance = _PreTransactionbalance;
		PostTransactionbalance = _PostTransactionbalance;
		PostTransactionMaturityDate = PostTransactionMaturityDate;
	}
	
	public TransactionTicket getTransactionTicket() {
		return ticket;
	}
	public boolean getTransactionSuccessIndicatorFlag() {
		return TransactionSuccessIndicatorFlag;
	}
	public String getTransactionFailureReason() {
		return ReasonForFailure;
	}
	public double getPreTransactionBalance() {
		return PreTransactionbalance;
	}
	public double getPostTransactionBalance() {
		return PostTransactionbalance;
	}
	public Calendar getPostTransactionMaturityDate() {
		return PostTransactionMaturityDate;
	}
}
