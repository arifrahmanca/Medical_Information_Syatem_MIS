package model;

import java.sql.SQLException;
import java.util.List;

import bean.PatientBean;
import dao.PatientDAO;

public class MIS {
	private static MIS instance;
	private PatientDAO patientData;
	
	//getInstance will return that ONE instance of the pattern
	//with the the DAO objects initialized..
	public static MIS getInstance()throws ClassNotFoundException{
		if (instance == null) {
			instance = new MIS();
			instance.patientData = new PatientDAO();
		}
		return instance;
	}
		
	//note that the constructor is private, cannot be called from other classes
	private MIS() {
	}
	
	public List<PatientBean> getAllPatients() throws SQLException{
		return patientData.retrieveAllPatients();
	}	
}
