
public class Depositor {

	private Name name;
	private String SSnumber;
	
	public Depositor() {
		
	}
	
	public Depositor(Name name, String SSnumber) {
		this.name = name;
		this.SSnumber = SSnumber;
	}
	
	//copy constructor
	public Depositor(Depositor other) {
		this.name = other.name;
		this.SSnumber = other.SSnumber;
	}
	
	//getters
	public Name getNames() {
		return new Name(name);
	}
	public String getSSnumber() {
		return SSnumber;
	}
	
	//setters
	public void setSSnumber(String _SSnumber) {
		SSnumber = _SSnumber;
	}
	
	//toString()	
	@Override
	public String toString()	{
		return name.toString() + " " + SSnumber;
	}
	
	//eqauls method 
	@Override
	public boolean equals(Object obj)	{
		if(this == obj) {
			return true;
		}
		if(obj == null)	{
			return false;
		}
		
		Depositor other  = (Depositor) obj;
		
		return this.name.equals(other.name) && this.SSnumber.equals(other.SSnumber);
	}
}
