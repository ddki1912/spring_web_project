package servlets;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import beans.BookBean;
import beans.ReaderBean;
import dao.LibrarianDao;

/**
 * Servlet implementation class AddBook
 */
public class AddBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>\r\n"
				+ "<html lang=\"en\">\r\n"
				+ "\r\n"
				+ "<head>\r\n"
				+ "    <meta charset=\"UTF-8\">\r\n"
				+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "    <title>Librarian Section</title>\r\n"
				+ "	   <link rel=\"icon\" type=\"image/x-icon\" href=\"./asset/img/ava-title.png\">"
				+ "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css\">\r\n"
				+ "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css\">\r\n"
				+ "    <link rel=\"stylesheet\" href=\"./asset/css/base.css\">\r\n"
				+ "    <link rel=\"stylesheet\" href=\"./asset/css/libra.css\">\r\n"
				+ "    <link rel=\"stylesheet\" href=\"./asset/font/themify-icons-font/themify-icons/themify-icons.css\">\r\n"
				+ "</head>\r\n"
				+ "\r\n"
				+ "<body>\r\n"
				+ "    <!-- Content -->\r\n"
				+ "    <div id=\"main\">");
		
		request.getRequestDispatcher("navlibrabooks.html").include(request, response);
		
		out.println("<div id=\"body\">\r\n"
				+ "\r\n"
				+ "            <!-- Reader -->\r\n"
				+ "            <div class=\"reader-content reader-content-js\">\r\n"
				+ "                <!-- Search -->\r\n"
				+ "                <form action=\"SearchReader\" class=\"search\">\r\n"
				+ "                    <input class=\"search__inp\" type=\"search\" placeholder=\"Search reader\" name=\"searchReader\">\r\n"
				+ "                    <button class=\"search-btn\" type=\"submit\" name=\"searchReaderBtn\">\r\n"
				+ "                        <i class=\"search__icon ti-search\"></i>\r\n"
				+ "                    </button>\r\n"
				+ "                </form>\r\n"
				+ "\r\n"
				+ "                <!-- Add reader -->\r\n"
				+ "                <div class=\"add\">\r\n"
				+ "                    <button class=\"add-btn btn reader-add-js\">+ Add reader</button>\r\n"
				+ "                </div>\r\n"
				+ "\r\n"
				+ "                <!-- Show list of readers -->\r\n"
				+ "                <div class=\"table\">\r\n"
				+ "                    <table class=\"reader-list\">\r\n"
				+ "                        <tr class=\"reader-list__item\">\r\n"
				+ "                            <th class=\"reader-list__title\" colspan=\"8\">Readers' Information</th>\r\n"
				+ "                        </tr>\r\n"
				+ "                        <tr class=\"reader-list__item\">\r\n"
				+ "                            <th class=\"reader-list__heading\">ID</th>\r\n"
				+ "                            <th class=\"reader-list__heading\">Name</th>\r\n"
				+ "                            <th class=\"reader-list__heading\">Date of birth</th>\r\n"
				+ "                            <th class=\"reader-list__heading\">Phone</th>\r\n"
				+ "                            <th class=\"reader-list__heading\">Book's ID</th>\r\n"
				+ "                            <th class=\"reader-list__heading\">Borrow on</th>\r\n"
				+ "                            <th class=\"reader-list__heading\">Return status</th>\r\n"
				+ "							   <th class=\"reader-list__heading\"></th>\r\n"
				+ "                        </tr>");
		
		List<ReaderBean> readerList = LibrarianDao.viewReader();
		
		for (ReaderBean bean : readerList) {
			out.println("<tr class=\"reader-list__item reader-item-js\">\r\n"
					+ "                            <td class=\"reader-list__value reader-item-value-js\">"+bean.getId()+"</td>\r\n"
					+ "                            <td class=\"reader-list__value reader-item-value-js\">"+bean.getName()+"</td>\r\n"
					+ "                            <td class=\"reader-list__value reader-item-value-js\">"+bean.getDob()+"</td>\r\n"
					+ "                            <td class=\"reader-list__value reader-item-value-js\">"+bean.getPhone()+"</td>\r\n"
					+ "                            <td class=\"reader-list__value book-id-js\">"+bean.getBookId()+"</td>\r\n"
					+ "                            <td class=\"reader-list__value\">"+bean.getBorrowDate()+"</td>\r\n"
					+ "                            <td class=\"reader-list__value\">"+bean.getReturnStatus()+"</td>\r\n"
					+ "                            <td class=\"reader-item__function\">\r\n"
					+ "                                <i class=\"borrow-btn fa-solid fa-book borrow-js\"></i>\r\n"
					+ "                                <div class=\"return-modal-adjust\">\r\n"
					+ "                                    <form class=\"body-content-adjust\" action=\"ReturnBook\">\r\n"
					+ "                                        <!-- Form to fill in -->\r\n"
					+ "                                        <div class=\"return-info\" style=\"display: none;\">\r\n"
					+ "                                            <input class=\"info--inp reader-input-js\" style=\"display: none;\" required type=\"text\"\r\n"
					+ "                                                name=\"readerID\">\r\n"
					+ "                                            <span class=\"return-info__label\" style=\"display: none;\">Books' ID: </span>\r\n"
					+ "                                            <input class=\"return-info__inp book-input-js\" style=\"display: none;\" required type=\"text\" placeholder=\"Eg. EB001\"\r\n"
					+ "                                                name=\"bookID\">\r\n"
					+ "                                        </div>\r\n"
					+ "                            \r\n"
					+ "                                        <Button class=\"return-btn return-js\" style=\"background-color: rgba(0, 0, 0, 0); border: none;\">\r\n"
					+ "                                            <i class=\"fa-solid fa-rotate-left\"></i>\r\n"
					+ "                                        </Button>\r\n"
					+ "                                    </form>\r\n"
					+ "                                </div>\r\n"
					+ "                                <i class=\"edit-btn fa-solid fa-pen reader-edit-js\"></i>\r\n"
					+ "                                <i class=\"delete-btn fa-solid fa-trash reader-delete-js\"></i>\r\n"
					+ "                            </td>\r\n"
					+ "                        </tr>");
		}
		
		out.println("</table>\r\n"
				+ "                </div>\r\n"
				+ "            </div>");
		
		out.println("<!-- Books -->\r\n"
				+ "            <div class=\"books-content books-content-js active\">\r\n"
				+ "                <!-- Search -->\r\n"
				+ "                <form action=\"SearchBook\" class=\"search\">\r\n"
				+ "                    <input class=\"search__inp\" type=\"search\" placeholder=\"Search book\" name=\"searchBook\">\r\n"
				+ "                    <button class=\"search-btn\" type=\"submit\" name=\"searchBookBtn\">\r\n"
				+ "                        <i class=\"search__icon ti-search\"></i>\r\n"
				+ "                    </button>\r\n"
				+ "                </form>\r\n"
				+ "\r\n"
				+ "                <!-- Add books -->\r\n"
				+ "                <div class=\"add\">\r\n"
				+ "                    <button class=\"add-btn btn books-add-js\">+ Add book</button>\r\n"
				+ "                </div>\r\n"
				+ "\r\n"
				+ "                <!-- Show list of books -->\r\n"
				+ "                <div class=\"table\">\r\n"
				+ "                    <table class=\"books-list\">\r\n"
				+ "                        <tr class=\"books-list__item\">\r\n"
				+ "                            <th class=\"books-list__title\" colspan=\"7\">Books' Information</th>\r\n"
				+ "                        </tr>\r\n"
				+ "                        <tr class=\"books-list__item\">\r\n"
				+ "                            <th class=\"books-list__heading\">ID</th>\r\n"
				+ "                            <th class=\"books-list__heading\">Name of book</th>\r\n"
				+ "                            <th class=\"books-list__heading\">Author</th>\r\n"
				+ "                            <th class=\"books-list__heading\">Quantity</th>\r\n"
				+ "                            <th class=\"books-list__heading\">Borrowed</th>\r\n"
				+ "                            <th class=\"books-list__heading\">Borrowed by</th>\r\n"
				+ "							   <th class=\"books-list__heading\"></th>\r\n"
				+ "                        </tr>");
		
		String name = request.getParameter("bookName");
		String author = request.getParameter("author");
		String quantity = request.getParameter("quantity");

		BookBean bb = new BookBean(name, author, Integer.parseInt(quantity));
		bb.setBorrowed(0);

		int ok = 0;

		if (!LibrarianDao.checkBookExisted(bb)) {
			ok = 1;
		} else {
			ok = 2;
		}

		LibrarianDao.addBook(bb);

		List<BookBean> bookList = LibrarianDao.viewBook();
		
		for(BookBean bean : bookList) {
			out.println("<tr class=\"books-list__item books-item-js\">\r\n"
					+ "                            <td class=\"books-list__value books-item-value-js\">"+bean.getId()+"</td>\r\n"
					+ "                            <td class=\"books-list__value books-item-value-js\">"+bean.getName()+"</td>\r\n"
					+ "                            <td class=\"books-list__value books-item-value-js\">"+bean.getAuthor()+"</td>\r\n"
					+ "                            <td class=\"books-list__value books-item-value-js\">"+bean.getQuantity()+"</td>\r\n"
					+ "                            <td class=\"books-list__value\">"+bean.getBorrowed()+"</td>\r\n"
					+ "							   <td class=\"books-list__value\">\r\n"
					+ "                                <i class=\"show-borrowed-icon fa-solid fa-eye show-borrowed-js\"></i>\r\n"
					+ "								   <div class=\"show-borrowed-list borrowed-list-modal\">\r\n"
					+ "                                    <div class=\"body-content\">\r\n"
					+ "                            \r\n"
					+ "                                        <div class=\"modal-close modal-close-js\">\r\n"
					+ "                                            <i class=\"ti-close\"></i>\r\n"
					+ "                                        </div>\r\n"
					+ "                            \r\n"
					+ "                                        <table class=\"borrowed-list\">\r\n"
					+ "                                            <tr class=\"borrowed-list__item\">\r\n"
					+ "                                                <th class=\"borrowed-list__title\" colspan=\"2\">Readers' Information</th>\r\n"
					+ "                                            </tr>\r\n"
					+ "                                            <tr class=\"borrowed-list__item\">\r\n"
					+ "                                                <th class=\"borrowed-list__heading\">Reader's ID</th>\r\n"
					+ "                                                <th class=\"borrowed-list__heading\">Borrow on</th>\r\n"
					+ "                                            </tr>");
			
			List<ReaderBean> borrowedList = bean.getReaderList();
			
			for(ReaderBean reader : borrowedList) {
				out.println("<tr class=\"borrowed-list__item borrowed-item-js\">\r\n"
						+ "                                                <td class=\"borrowed-list__value\">"+reader.getId()+"</td>\r\n"
						+ "                                                <td class=\"borrowed-list__value\">"+reader.getBorrowOn()+"</td>\r\n"
						+ "                                            </tr>");
			}
			
			out.println("</table>\r\n"
					+ "                                    </div>\r\n"
					+ "                                </div>"
					+ "                            </td>\r\n"
					+ "                            <td class=\"books-item__function\">\r\n"
					+ "                                <i class=\"edit-btn fa-solid fa-pen books-edit-js\"></i>\r\n"
					+ "                                <i class=\"delete-btn fa-solid fa-trash books-delete-js\"></i>\r\n"
					+ "                            </td>\r\n"
					+ "                        </tr>");
		}
		
		out.println("</table>\r\n"
				+ "                </div>\r\n"
				+ "            </div>\r\n"
				+ "        </div>\r\n"
				+ "\r\n"
				+ "    </div>");
		
		if (ok == 1) {
			out.println("<div class=\"notice success active notice-js\">\r\n"
					+ "        <span class=\"notice-content notice-content-js\">\r\n"
					+ "            The book was successfully added!\r\n"
					+ "        </span>\r\n"
					+ "    </div>");
		} else {
			out.println("<div class=\"notice fail active notice-js\">\r\n"
					+ "        <span class=\"notice-content notice-content-js\">\r\n"
					+ "            The books has existed, so the number of books increased!\r\n"
					+ "        </span>\r\n"
					+ "    </div>");
		}
		
		request.getRequestDispatcher("addreaderform.html").include(request, response);
		request.getRequestDispatcher("editreaderform.html").include(request, response);
		request.getRequestDispatcher("deletereaderform.html").include(request, response);
		request.getRequestDispatcher("borrowbook.html").include(request, response);
		
		request.getRequestDispatcher("addbookform.html").include(request, response);
		request.getRequestDispatcher("editbookform.html").include(request, response);
		request.getRequestDispatcher("deletebookform.html").include(request, response);
		
		out.println("<script type=\"text/javascript\" src=\"./asset/js/libra.js\">\r\n"
				+ "    </script>\r\n"
				+ "</body>\r\n"
				+ "\r\n"
				+ "</html>");
		out.close();
		
	}

}
