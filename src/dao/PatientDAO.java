package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import bean.AddressBean;
import bean.PatientBean;

public class PatientDAO {
	Connection con;
	String url = "jdbc:db2://dashdb-txn-sbox-yp-dal09-08.services.dal.bluemix.net:50000/BLUDB";
	String user = "sgr65162";
	String password = "7-z7882xkzvpmxth";

	public PatientDAO() throws ClassNotFoundException {		
		try {
			// Load the IBM Data Server Driver for JDBC and SQLJ with DriverManager
			Class.forName("com.ibm.db2.jcc.DB2Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// Create Connection
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public AddressBean retrieveAddressById(int id) throws SQLException {
		String query = "SELECT * FROM ADDRESS WHERE ID=" + id;
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();		
		AddressBean address = null;
		
		while (r.next()) {
			String street = r.getString("STREET"); 
			String city = r.getString("CITY"); 
			String province = r.getString("PROVINCE"); 
			String zip = r.getString("ZIP");
			String country = r.getString("COUNTRY"); 
			address = new AddressBean(street, city, province, zip, country);
		}
		
		r.close();
		p.close();
		return address;
	}

	public List<PatientBean> retrieveAllPatients() throws SQLException {
		String query = "SELECT * FROM PATIENT";
		List<PatientBean> patients = new ArrayList<PatientBean>();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();

		while (r.next()) {
			String lastName = r.getString("LNAME");
			String firstName = r.getString("FNAME");
			String email = r.getString("EMAIL");
			String phone = r.getString("PHONE");
			int id = r.getInt("ADDRESSID");
			AddressBean address = retrieveAddressById(id);
	
			PatientBean pb = new PatientBean(lastName, firstName, email, phone, address);
			patients.add(pb);
		}
		r.close();
		p.close();
		return patients;
	}
	
	public int insertAddress(String street, String city, String province, String zip, String country) throws SQLException {
		String query = "INSERT INTO ADDRESS (STREET, CITY, PROVINCE, ZIP, COUNTRY) VALUES (?,?,?,?,?)";
		PreparedStatement p = con.prepareStatement(query);
			
		p.setString(1, street);
		p.setString(2, city);
		p.setString(3, province);
		p.setString(4, zip);
		p.setString(5, country);
			
		int i = p.executeUpdate();
		return i;
	}

	public int insertPatient(String lName, String fName, String phone, String email) throws SQLException {
		String query = "INSERT INTO PATIENT (LNAME, FNAME, PHONE, EMAIL) VALUES (?,?,?,?)";
		PreparedStatement p = con.prepareStatement(query);
		
		p.setString(1, lName);
		p.setString(2, fName);
		p.setString(3, phone);
		p.setString(4, email);
			
		int i = p.executeUpdate();	
		return i;
	}
}
