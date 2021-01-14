package bean;

public class PatientBean {	
	private String lastName;
	private String firstName;
	private String email;
	private String phone;
	private AddressBean address;
	
	public PatientBean(String lName, String fName, String email, String phone, AddressBean address) {		
		this.lastName = lName;
		this.firstName = fName;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public AddressBean getAddress() {
		return address;
	}

	public void setAddress(AddressBean address) {
		this.address = address;
	}
}
