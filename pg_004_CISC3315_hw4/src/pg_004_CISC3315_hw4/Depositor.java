package pg_004_CISC3315_hw4;

public class Depositor {

	private Name name;
	private String SSnumber;
	
	public Depositor() {
		
	}
	
	public Depositor(Name _name, String _SSnumber) {
		name = _name;
		SSnumber = _SSnumber;
	}
	
	public Name getNames(){
		return name;
	}
	public String getSSnumber() {
		return SSnumber;
	}
	private void setSSnumber(String _SSnumber) {
		SSnumber = _SSnumber;
	}
}
