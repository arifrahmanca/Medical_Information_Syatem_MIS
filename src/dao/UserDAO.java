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

import bean.UserBean;

public class UserDAO {
	private DataSource ds;

	public UserDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public DataSource getDS() {
		return this.ds;
	}
	
	public List<UserBean> retrieveAllUsers() throws SQLException {
		String query = "SELECT * FROM USERS";
		List<UserBean> users = new ArrayList<UserBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();

		while (r.next()) {
			String username = r.getString("USERNAME");
			String password = r.getString("PASSWORD");
	
			UserBean user = new UserBean(username, password);
			users.add(user);
		}
		r.close();
		p.close();
		con.close();
		return users;
	}
	
	public int insertUser(String username, String password) throws SQLException {
		String query = "INSERT INTO USERS (USERNAME, PASSWORD) VALUES  (?,?)";
		
		Connection con = (Connection) this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		
		p.setString(1, username);
		p.setString(2, password);
		
		int i = p.executeUpdate();		
		con.close();		
		return i;
	}

}
