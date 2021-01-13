package bean;

public class PatientBean {	
	private String lastName;
	private String firstName;
	private int age;
	private AddressBean address;
	private String email;
	private String phone;
	
	public PatientBean(String lName, String fName, int age, AddressBean address, String email, String phone) {		
		this.lastName = lName;
		this.firstName = fName;
		this.age = age;
		this.address = address;
		this.email = email;
		this.phone = phone;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public AddressBean getAddress() {
		return address;
	}

	public void setAddress(AddressBean address) {
		this.address = address;
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
}
