package servlets;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
//import java.io.PrintWriter;

import beans.ReaderBean;
import dao.LibrarianDao;

/**
 * Servlet implementation class DeleteReader
 */
public class DeleteReader extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("readerID");
		String submit = request.getParameter("readerDeleteBtn");

		if (submit.equals("yes")) {
			ReaderBean rb = new ReaderBean();
			rb.setId(Integer.parseInt(id));
			LibrarianDao.deleteReader(rb);
		}
		request.getRequestDispatcher("ViewReaderAndBook").include(request, response);

	}

}
