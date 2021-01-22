package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import bean.AddressBean;
import bean.PatientBean;
import bean.UserBean;
import model.MIS;

/**
 * Servlet implementation class Mis
 */
@WebServlet({"/Mis", "/Mis/", "/Mis/*"})
public class Mis extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MIS model;
	
    boolean isLogged = false;
    boolean isWrongPassword = false;
    boolean isLoginFailed = true;
    boolean isFailedSignup = false;
    boolean isPasswordMismatched = false;
    boolean isUpdateError = false;
    
    
    String updateErrorMsg = "";
    
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
		String username = request.getParameter("login-user-name"); 
		String password = request.getParameter("login-password");
		String createAccountButton = request.getParameter("createAccountButton");
		String profileButton = request.getParameter("profileButton");
		String updateButton = request.getParameter("updateButton");
		String saveProfileButton = request.getParameter("saveProfileButton");
		String uri = request.getRequestURI();
		String adminLoginButton = request.getParameter("admin-login-button");
		
		HttpSession session = request.getSession();
		
		if (logoutButton != null) {
			isLogged = false;
			isLoginFailed = true;
		}
		request.setAttribute("isLogged", isLogged);
		
		// Redirection to corresponding pages
		if (loginNav != null) {
			request.getRequestDispatcher("/Login.jspx").forward(request, response);
		} else if (loginButton != null) {
			validateLogin(username, password);
			request.setAttribute("isLogged", isLogged);
			request.setAttribute("isLoginFailed", isLoginFailed);
			PatientBean patient = null;
			if (isLogged) { 
				try {
					patient = model.retrieveUserInfo(username);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				session.setAttribute("patient", patient);
				request.getRequestDispatcher("/ProfilePage.jspx").forward(request, response);
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
			request.setAttribute("isPasswordMismatched", isPasswordMismatched);
			
			if (isFailedSignup || isPasswordMismatched) { 
				request.getRequestDispatcher("/SignUp.jspx").forward(request, response);
			} else {
				request.getRequestDispatcher("/Login.jspx").forward(request, response);
			}
		} else if (profileButton != null) {
			request.getRequestDispatcher("/ProfilePage.jspx").forward(request, response);
		} else if (updateButton != null) {
			request.getRequestDispatcher("/UpdatePage.jspx").forward(request, response);
		} else if (saveProfileButton != null) {
			try {
				updateProfile(request);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}
			if (isUpdateError) {
				request.getRequestDispatcher("/UpdatePage.jspx").forward(request, response);
			} else {
				request.getRequestDispatcher("/ProfilePage.jspx").forward(request, response);
			}
		} else if (adminLoginButton != null) {
			validateLogin(username, password);
			request.setAttribute("isLogged", isLogged);
			request.setAttribute("isLoginFailed", isLoginFailed);
			PatientBean patient = null; 
			if (isLogged) { 
				try {
					patient = model.retrieveUserInfo(username);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				session.setAttribute("patient", patient);
				request.getRequestDispatcher("/ProfilePage.jspx").forward(request, response);
			} else {
				request.getRequestDispatcher("/AdminLogin.jspx").forward(request, response);
			}
		} else if (uri.contains("Admin")) {
			isLoginFailed = false;
			request.getRequestDispatcher("/AdminLogin.jspx").forward(request, response);
		} else {
			request.getRequestDispatcher("/index.jspx").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private PatientBean getPatientById(int id) {
		PatientBean patient = null;
		try {
			patient = model.getPatientById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patient;
	}
	
	private List<UserBean> getUsers(){
		List<UserBean> users = new ArrayList<UserBean>();
		try {
			users = model.retrieveAllUsers();
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		return users;
	}
	
	private void validateLogin(String username, String password) {
		if (isValidLogin(username, password)) {
			isLoginFailed = false;
			isLogged = true;
		} else {
			isLoginFailed = true;
			isLogged = false;
		}
	}
	
	private boolean isValidLogin(String username, String password) {
		List<UserBean> users = getUsers();
		for (UserBean user : users) {
			if (user.getUsername().equals(username)) {
				if (user.getPassword().equals(password)) {
					return true;
				}
			}
		}
		return false;
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
		String retypePassword = request.getParameter("retype-password");
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
			isPasswordMismatched = false;
		} else if (!registerPassword.equals(retypePassword)) {
			isPasswordMismatched = true;
			isFailedSignup = false;
		} else {
			isFailedSignup = false;
			isPasswordMismatched = false;
			model.getPatientData().insertPatient(lastName, firstName, phone, email);
			model.getUserData().insertUser(registerUsername, registerPassword);
			model.getPatientData().insertAddress(street, city, province, zip, country);
		}
	}
	
	private void updateProfile(HttpServletRequest request) throws SQLException {
		String street = request.getParameter("updateStreet");;
		String city = request.getParameter("updateCity");
		String province = request.getParameter("updateProvince");
		String zip = request.getParameter("updatePostalCode");
		String country = request.getParameter("updateCountry"); 
		String phone = request.getParameter("updatePhone");
		String email = request.getParameter("updateEmail");
		
		PatientBean patient = (PatientBean) request.getSession().getAttribute("patient");
		int id = patient.getId();		
		AddressBean address = new AddressBean(id, street, city, province, zip, country);
		
		if (email == "") {
			setUpdateErrorMsg("Error! Email cannot be empty.");
		} else if (street == "") {
			setUpdateErrorMsg("Error! Street cannot be empty.");
		} else if (city == "") {
			setUpdateErrorMsg("Error! City cannot be empty.");
		} else if (province == "") {
			setUpdateErrorMsg("Error! Province cannot be empty.");
		} else if (zip == "") {
			setUpdateErrorMsg("Error! Postal Code cannot be empty.");
		} else if (country == "") {
			setUpdateErrorMsg("Error! Country cannot be empty.");
		} else {
			isUpdateError = false;
			model.updatePatient(email, phone, id);
			model.updateAddress(address);
		}		
		patient = getPatientById(id);
		request.getSession().setAttribute("patient", patient);
		request.getSession().setAttribute("isUpdateError", isUpdateError);
		request.getSession().setAttribute("updateErrorMsg", updateErrorMsg);
	}
	
	private void setUpdateErrorMsg(String msg) {
		isUpdateError = true;
		this.updateErrorMsg = msg;
	}
}
