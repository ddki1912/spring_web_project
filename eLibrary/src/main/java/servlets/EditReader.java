package servlets;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

import beans.ReaderBean;
import dao.LibrarianDao;

/**
 * Servlet implementation class EditReader
 */
public class EditReader extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id= request.getParameter("readerID");
		String name = request.getParameter("readerName");
		String tmp = request.getParameter("readerDob");
		Date dob = Date.valueOf(tmp);
		String phone = request.getParameter("readerPhone");

		ReaderBean rb = new ReaderBean(name, dob, phone);
		rb.setId(Integer.parseInt(id));
		
		LibrarianDao.editReader(rb);
		request.getRequestDispatcher("ViewReaderAndBook").include(request, response);
	}

}
