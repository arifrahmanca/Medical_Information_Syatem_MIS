package bean;

public class AddressBean {
	private int id;
	private String street;
	private String city;
	private String province;
	private String zip;
	private String country;
	
	public AddressBean(int id, String street, String city, String province, String zip, String country) {
		this.id = id;
		this.street = street;
		this.city = city;
		this.province = province;
		this.zip = zip;
		this.country = country;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
