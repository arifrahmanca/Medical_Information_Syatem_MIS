package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.AddressBean;
import bean.PatientBean;

public class PatientDAO {
	private DataSource ds;

	public PatientDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public DataSource getDS() {
		return this.ds;
	}
	
	public AddressBean retrieveAddressById(int id) throws SQLException {
		String query = "SELECT * FROM ADDRESS WHERE ID=" + id;
		Connection con = this.ds.getConnection();
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
		con.close();
		return address;
	}

	public List<PatientBean> retrieveAllPatients() throws SQLException {
		String query = "SELECT * FROM PATIENT";
		List<PatientBean> patients = new ArrayList<PatientBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();

		while (r.next()) {
			String lastName = r.getString("LNAME");
			String firstName = r.getString("FNAME");
			int age = r.getInt("AGE");
			int id = r.getInt("ADDRESS");
			AddressBean address = retrieveAddressById(id);
			String email = r.getString("EMAIL");
			String phone = r.getString("PHONE");
	
			PatientBean pb = new PatientBean(lastName, firstName, age, address, email, phone);
			patients.add(pb);
		}
		r.close();
		p.close();
		con.close();
		return patients;
	}
}
