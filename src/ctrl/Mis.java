package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.PatientBean;
import bean.UserBean;
import model.MIS;

/**
 * Servlet implementation class Mis
 */
@WebServlet({"/Mis", "/Mis/"})
public class Mis extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MIS model;
	
	private String loginUsername; 
    private String loginPassword;
    boolean isLogged = false;
    boolean isLoginFailed = true;
    boolean isFailedSignup = false;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Mis() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try { 
			model = MIS.getInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String loginNav = request.getParameter("loginNav"); 
		String loginButton = request.getParameter("login-button");
		String logoutButton = request.getParameter("logoutNav");
		String createAccountButton = request.getParameter("createAccountButton");
		loginUsername = request.getParameter("login-user-name");
		loginPassword = request.getParameter("login-password");
		
		// Redirection to corresponding pages
		if (loginNav != null) {
			request.getRequestDispatcher("/Login.jspx").forward(request, response);
		} else if (logoutButton != null) {
			isLogged = false;
			isLoginFailed = true;
			request.getRequestDispatcher("/index.jspx").forward(request, response);
		}
		else if (loginButton != null) {
			validateLogin();
			request.setAttribute("isLogged", isLogged);
			request.setAttribute("isLoginFailed", isLoginFailed);
			PatientBean patient = null;
			if (isLogged) {
				String username = request.getParameter("login-user-name"); 
				try {
					patient = model.retrieveUserInfo(username);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				request.setAttribute("patient", patient);
				request.getRequestDispatcher("/UserPage.jspx").forward(request, response);
			} else {
				request.getRequestDispatcher("/Login.jspx").forward(request, response);
			}
		} else if (createAccountButton != null) {
			try {
				createAccount(request);
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
			request.setAttribute("isFailedSignup", isFailedSignup);
			
			if (isFailedSignup) { 
				request.getRequestDispatcher("/SignUp.jspx").forward(request, response);
			} else {
				request.getRequestDispatcher("/Login.jspx").forward(request, response);
			}
		}
		else {
			request.getRequestDispatcher("/index.jspx").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private List<PatientBean> getPatients() {
		List<PatientBean> patients = new ArrayList<PatientBean>();
		try {
			patients = model.getAllPatients();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return patients;
	}
	
	private List<UserBean> getUsers(){
		List<UserBean> users = new ArrayList<UserBean>();
		try {
			users = model.retrieveAllUsers();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		return users;
	}
	
	private void validateLogin() {
		List<UserBean> users = getUsers();
		UserBean user = new UserBean(loginUsername, loginPassword);
		for (UserBean ub : users) {
			if (user.getUsername().equals(ub.getUsername()) && user.getPassword().equals(ub.getPassword())) {
				isLoginFailed = false;
				isLogged = true;
			}
		}
	}
	
	private boolean isUserExist(String username) {
		List<UserBean> users = getUsers();
		for (UserBean user : users) {
			if (user.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	private void createAccount(HttpServletRequest request) throws SQLException {
		String registerUsername = request.getParameter("registerUsername");
		String registerPassword = request.getParameter("registerPassword");
		String firstName = request.getParameter("registerFirstName");
		String lastName = request.getParameter("registerLastName");
		String street1 = request.getParameter("registerStreet");
		String street2 = request.getParameter("registerStreet2");
		String street = street1 + "\n" + street2;
		String city = request.getParameter("registerCity");
		String province = request.getParameter("registerProvince");
		String zip = request.getParameter("registerPostalCode");
		String country = request.getParameter("registerCountry"); 
		String phone = request.getParameter("registerPhone");
		String email = request.getParameter("email");
		
		if (isUserExist(registerUsername)) {
			isFailedSignup = true;
		} else {
			isFailedSignup = false;
			model.getPatientData().insertPatient(firstName, lastName, phone, email);
			model.getUserData().insertUser(registerUsername, registerPassword);
			model.getPatientData().insertAddress(street, city, province, zip, country);
		}
	}
}
