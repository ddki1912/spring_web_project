package servlets;

import java.io.*;

//import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class AdminLogin
 */
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();

		String account = request.getParameter("user");
		String password = request.getParameter("password");
		if (account.equals("admin") && password.equals("admin123")) {
			HttpSession session = request.getSession();
			session.setAttribute("admin", "true");
			
			response.sendRedirect("ViewLibrarian");
		} else {
			out.println("<!DOCTYPE html>\r\n"
					+ "<html lang=\"en\">\r\n"
					+ "\r\n"
					+ "<head>\r\n"
					+ "<meta charset=\"UTF-8\">\r\n"
					+ "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
					+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
					+ "<title>eLibrary</title>\r\n"
					+ "<link rel=\"icon\" type=\"image/x-icon\" href=\"./asset/img/ava-title.png\">"
					+ "<link rel=\"stylesheet\"\r\n"
					+ "	href=\"https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css\">\r\n"
					+ "<link rel=\"stylesheet\" href=\"./asset/css/base.css\">\r\n"
					+ "<link rel=\"stylesheet\" href=\"./asset/css/login.css\">\r\n"
					+ "<link rel=\"stylesheet\"\r\n"
					+ "	href=\"./asset/font/themify-icons-font/themify-icons/themify-icons.css\">\r\n"
					+ "</head>\r\n"
					+ "\r\n"
					+ "<body>");
			request.getRequestDispatcher("navhome.html").include(request, response);
			request.getRequestDispatcher("adminloginform.html").include(request, response);
			out.println("<script src=\"./asset/js/login.js\">\r\n"
					+ "    </script>\r\n"
					+ "</body>\r\n"
					+ "\r\n"
					+ "</html>");
		}
		out.close();
	}

}
