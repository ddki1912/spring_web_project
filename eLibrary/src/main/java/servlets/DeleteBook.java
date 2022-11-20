package servlets;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import beans.BookBean;
import dao.LibrarianDao;

/**
 * Servlet implementation class DeleteBook
 */
public class DeleteBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("bookID");
		String submit = request.getParameter("bookDeleteBtn");

		if (submit.equals("yes")) {
			BookBean bb = new BookBean();
			bb.setId(Integer.parseInt(id));
			LibrarianDao.deleteBook(bb);
		}
		
		request.getRequestDispatcher("ViewBookAndReader").include(request, response);
	}

}
