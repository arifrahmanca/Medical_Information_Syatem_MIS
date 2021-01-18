package bean;

public class PatientBean {	
	private int id;
	private String lastName;
	private String firstName;
	private String email;
	private String phone;
	private AddressBean address;
	
	public PatientBean(int id, String lName, String fName, String email, String phone, AddressBean address) {		
		this.id = id;
		this.lastName = lName;
		this.firstName = fName;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
