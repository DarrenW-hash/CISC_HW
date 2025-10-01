package pg_004_CISC3315_hw4;

public class Name {

	private String FirstName;
	private String LastName;
	
	public Name() {
		FirstName = null;
		LastName = null;
	}
	
	public Name(String _FirstName, String _LastName) {
		FirstName = _FirstName;
		LastName = _LastName;
	}
	
	//getters
	public String getFirstName() {
		return FirstName;
	}
	public String getLastName() {
		return LastName;
	}
	
	//setters
	private void setFirstName(String _FirstName) {
		FirstName = _FirstName;
	}
	private void setLastName(String _LastName) {
		LastName = _LastName;
	}
}
