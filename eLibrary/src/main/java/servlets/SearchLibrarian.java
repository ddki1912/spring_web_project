package servlets;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import beans.LibrarianBean;
import dao.AdminDao;

/**
 * Servlet implementation class SearchLibrarian
 */
public class SearchLibrarian extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String name = request.getParameter("searchLibra");
		
		if(name.equals("")) {
			response.sendRedirect("ViewLibrarian");
		}else {
			List<LibrarianBean> libraList = AdminDao.viewLibrarianByName(name);
	
			out.println("<!DOCTYPE html>\r\n" 
					+ "<html lang=\"en\">\r\n" 
					+ "\r\n" 
					+ "<head>\r\n"
					+ "    <meta charset=\"UTF-8\">\r\n"
					+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
					+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
					+ "    <title>Admin Section</title>\r\n"
					+ "    <link rel=\"icon\" type=\"image/x-icon\" href=\"./asset/img/ava-title.png\">"
					+ "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css\">\r\n"
					+ "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css\">\r\n"
					+ "    <link rel=\"stylesheet\" href=\"./asset/css/base.css\">\r\n"
					+ "    <link rel=\"stylesheet\" href=\"./asset/css/admin.css\">\r\n"
					+ "    <link rel=\"stylesheet\" href=\"./asset/font/themify-icons-font/themify-icons/themify-icons.css\">\r\n"
					+ "</head>\r\n" 
					+ "<body>");
		
			out.println("<div id=\"main\">");
	
			request.getRequestDispatcher("navadmin.html").include(request, response);
		
			out.println("<div id=\"body\">\r\n"
					+ "            <div id=\"content\">\r\n"
					+ "\r\n"
					+ "                <!-- Add librarian -->\r\n"
					+ "                <div id=\"add\">\r\n"
					+ "					   <form action=\"ViewLibrarian\" id=\"view\">\r\n"
					+ "                        <input class=\"view-btn btn\" type=\"submit\" value=\"View all librarians\">\r\n"
					+ "                    </form>\r\n"
					+ "                    <button class=\"add-btn btn add-js\">+ Add a librarian</button>\r\n"
					+ "                </div>");
		
			out.println("<div id=\"table\">\r\n" 
					+ "                    <table class=\"libra-list\">\r\n"
					+ "                        <tr class=\"libra-list__item\">\r\n"
					+ "                            <th class=\"libra-list__title\" colspan=\"6\">Librarians' Information</th>\r\n"
					+ "                        </tr>\r\n" 
					+ "                        <tr class=\"libra-list__item\">\r\n"
					+ "                            <th class=\"libra-list__heading\">ID</th>\r\n"
					+ "                            <th class=\"libra-list__heading\">Full name</th>\r\n"
					+ "                            <th class=\"libra-list__heading\">Date of birth</th>\r\n"
					+ "                            <th class=\"libra-list__heading\">Phone</th>\r\n"
					+ "                            <th class=\"libra-list__heading\">Email</th>\r\n"
					+ "							   <th class=\"libra-list__heading\">      </th>\r\n"
					+ "                        </tr>");
		
			for (LibrarianBean bean : libraList) {
				out.println("<tr class=\"libra-list__item libra-list-js\">\r\n"
						+ "                            <td class=\"libra-list__value libra-list-value-js\">" + bean.getId() + "</td>\r\n"
						+ "                            <td class=\"libra-list__value libra-list-value-js\">" + bean.getName() + "</td>\r\n"
						+ "                            <td class=\"libra-list__value libra-list-value-js\">" + bean.getDob() + "</td>\r\n"
						+ "                            <td class=\"libra-list__value libra-list-value-js\">" + bean.getPhone() + "</td>\r\n"
						+ "                            <td class=\"libra-list__value libra-list-value-js\">" + bean.getEmail() + "</td>\r\n"
						+ "                            <td class=\"libra-item__function\">\r\n"
						+ "                                <i class=\"edit-btn fa-solid fa-pen edit-js\"></i>\r\n"
						+ "                                <i class=\"delete-btn fa-solid fa-trash delete-js\"></i>\r\n"
						+ "                            </td>\r\n" 
						+ "                        </tr>");
			}
			out.println("</table>\r\n" 
					+ "                </div>\r\n" 
					+ "            </div>\r\n" 
					+ "        </div>\r\n"
					+ "    </div>");
		
			request.getRequestDispatcher("addlibrarianform.html").include(request, response);
			request.getRequestDispatcher("editlibrarianform.html").include(request, response);
			request.getRequestDispatcher("deletelibrarianform.html").include(request, response);
	
			out.println("<script src=\"./asset/js/admin.js\">\r\n" 
					+ "    </script>\r\n" 
					+ "</body>\r\n" 
					+ "\r\n" 
					+ "</html>");
			
		}
		out.close();
	}

}
