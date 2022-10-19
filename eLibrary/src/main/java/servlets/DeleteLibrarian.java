package servlets;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import beans.LibrarianBean;
import dao.AdminDao;

/**
 * Servlet implementation class DeleteLibrarian
 */
public class DeleteLibrarian extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		String id = request.getParameter("libraID");
		String submit = request.getParameter("libraDeleteBtn");
		
		if(submit.equals("yes")) {
			LibrarianBean lb = new LibrarianBean();
			lb.setId(Integer.parseInt(id));
			
			AdminDao.deleteLibrarian(lb);
		}
		request.getRequestDispatcher("ViewLibrarian").include(request, response);
	}

}
