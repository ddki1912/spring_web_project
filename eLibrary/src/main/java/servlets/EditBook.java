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
 * Servlet implementation class EditBook
 */
public class EditBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String id = request.getParameter("bookID");
		String name = request.getParameter("bookName");
		String author = request.getParameter("author");
		String quantity = request.getParameter("quantity");
		
		BookBean bb = new BookBean(name, author, Integer.parseInt(quantity));
		bb.setId(Integer.parseInt(id));
		
		LibrarianDao.editBook(bb);
		
		request.getRequestDispatcher("ViewBookAndReader").include(request, response);
	}

}
