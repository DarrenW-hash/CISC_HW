
public class Name {
	
	private String FirstName;
	private String lastName;
	
	public Name() {
		FirstName = null;
		lastName = null;
	}
	
	public Name(String FirstName, String lastName) {
		this.FirstName = FirstName;
		this.lastName = lastName;
	}
	//copy constuctor
	public Name(Name other) {
		this.FirstName = other.FirstName;
		this.lastName = other.lastName;
	}
	
	//getters
	public String getFirstName() {
		return FirstName;
	}
	public String getLastName() {
		return lastName;
	}
	
	//setters
	private void setFirstname(String firstname) {
		FirstName = firstname;
	}

	private void setLastname(String lastname) {
		lastName = lastname;
	}
	
	//toString 
	@Override
	public String toString() {
		return FirstName + " " + lastName;
	}
	
	//equals method
	public boolean equals(Object obj)	{
		 if (this == obj)
		        return true; 
		    if (obj == null)
		        return false; 

		    Name other = (Name) obj; 

		    return this.FirstName.equals(other.FirstName)
		            && this.lastName.equals(other.lastName);
	}
}

