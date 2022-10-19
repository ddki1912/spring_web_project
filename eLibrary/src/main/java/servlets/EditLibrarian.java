package servlets;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

import beans.LibrarianBean;
import dao.AdminDao;

/**
 * Servlet implementation class EditLibrarian
 */
public class EditLibrarian extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		
		String id= request.getParameter("libraID");
		String name = request.getParameter("libraName");
		String tmp = request.getParameter("libraDob");
		Date dob = Date.valueOf(tmp);
		String phone = request.getParameter("libraPhone");
		String email = request.getParameter("libraEmail");
		String[] data = tmp.split("-");
		String password = data[2] + data[1] + data[0];

		LibrarianBean lb = new LibrarianBean(name, dob, phone, email, password);
		lb.setId(Integer.parseInt(id));
		
		AdminDao.editLibrarian(lb);
		request.getRequestDispatcher("ViewLibrarian").include(request, response);
	}

}
