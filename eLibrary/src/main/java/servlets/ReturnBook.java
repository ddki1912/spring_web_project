package servlets;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import beans.BookBean;
import beans.ReaderBean;
import dao.LibrarianDao;

/**
 * Servlet implementation class ReturnBook
 */
public class ReturnBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		String readerId = request.getParameter("readerID");
		String bookId = request.getParameter("bookID");
		
		ReaderBean reader = new ReaderBean();
		reader.setId(Integer.parseInt(readerId));
		
		BookBean book = new BookBean();
		book.setId(Integer.parseInt(bookId));
		
		LibrarianDao.returnBook(reader, book);
		
		request.getRequestDispatcher("ViewReaderAndBook").include(request, response);
		
	}

}
