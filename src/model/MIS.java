package model;

import java.sql.SQLException;
import java.util.List;

import bean.PatientBean;
import bean.UserBean;
import dao.PatientDAO;
import dao.UserDAO;

public class MIS {
	private static MIS instance;
	private PatientDAO patientData;
	private UserDAO userData;
	
	//getInstance will return that ONE instance of the pattern
	//with the the DAO objects initialized..
	public static MIS getInstance()throws ClassNotFoundException{
		if (instance == null) {
			instance = new MIS();
			instance.patientData = new PatientDAO();
			instance.userData = new UserDAO();
		}
		return instance;
	}
		
	//note that the constructor is private, cannot be called from other classes
	private MIS() {
	}
	
	public List<PatientBean> getAllPatients() throws SQLException{
		return patientData.retrieveAllPatients();
	}
	
	public List<UserBean> retrieveAllUsers() throws SQLException{
		return userData.retrieveAllUsers();
	}

	public PatientDAO getPatientData() {
		return patientData;
	}

	public UserDAO getUserData() {
		return userData;
	}
	
	public PatientBean retrieveUserInfo(String username) throws SQLException {
		return userData.retriveUserInfo(username);
	}
}
