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
import model.MIS;

/**
 * Servlet implementation class Mis
 */
@WebServlet({"/Mis", "/Mis/"})
public class Mis extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MIS model;
       
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
		boolean isLogged = false;
		
		if (loginButton != null) {
			isLogged = true;
		}
		
		List<PatientBean> patients = new ArrayList<PatientBean>();
		try {
			patients = model.getAllPatients();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("patients", patients);
		request.setAttribute("isLogged", isLogged);
		
		if (loginNav != null) {
			request.getRequestDispatcher("/Login.jspx").forward(request, response);
		} else {
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

}
