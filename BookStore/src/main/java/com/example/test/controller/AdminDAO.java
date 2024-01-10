package com.example.test.controller;

import java.util.*;
import java.sql.*;
import java.sql.Date;
import com.example.test.model.*;

public class AdminDAO {

	private String jdbcURL = "jdbc:mysql://localhost:3306/test";
	private String jdbcUsername = "root";
	private String jdbcPassword = "qaz123";

	// define SQL statements
	private static final String SELECT_ADMIN = "select * from admin where adminname = ? and password = ?";
	private static final String SELECT_ADMIN_BY_ID_OR_ACCNAME = "select * from admin where id = ? or adminname = ?";
	private static final String UPDATE_ADMIN_INFO = "update admin set realname = ?, dob = ?, email = ?, phone = ? where id = ?";
	private static final String UPDATE_ADMIN_PASSWORD = "update admin set password = ? where id = ?";
	private static final String SELECT_ALL_BOOKS = "select * from book";
	private static final String SELECT_BOOK_BY_ID = "select * from book where id = ?";
	private static final String SELECT_BOOKS_BY_TITLE_OR_AUTHOR = "select * from book where title like ? or author like ?";
	private static final String SELECT_LAST_BOOK = "select * from book order by id desc limit 1";
	private static final String SELECT_BOOKS_FROM_A_TO_Z = "select * from book order by title asc";
	private static final String SELECT_BOOKS_FROM_Z_TO_A = "select * from book order by title desc";
	private static final String SELECT_NEWEST_BOOKS = "select * from book order by `release` desc";
	private static final String SELECT_OLDEST_BOOKS = "select * from book order by `release` asc";
	private static final String SELECT_MOST_POPULAR_BOOKS = "select * from book order by sold desc";
	private static final String SELECT_EXISTED_BOOK_WHEN_INSERT = "select * from book where title = ? and author = ?";
	private static final String SELECT_EXISTED_BOOK_WHEN_UPDATE = "select * from book where title = ? and author = ? and id != ?";
	private static final String INSERT_BOOK = "insert into book(title, author, category, `release`, pages, price, sold, description, `bookcover`) values (?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_BOOK = "update book set title = ?, author = ?, category = ?, `release` = ?, pages = ?, price = ?, description = ?, bookcover = ? where id = ?";
	private static final String UPDATE_BOOK_SOLD = "update book set sold = ? where id = ?";
	private static final String DELETE_BOOK = "delete from book where id = ?";
	private static final String SELECT_CATEGORIES = "select * from category";
	private static final String SELECT_ALL_ORDERS = "select * from test.order where confirm = 1";
	private static final String SELECT_ORDER_BY_ID = "select * from test.order where orderId = ?";
	private static final String SELECT_ORDERS_BY_STATUS = "select * from test.order where confirm = 1 and status = ?";
	private static final String SELECT_ORDERS_FROM_TO = "select * from test.order where confirm = 1 and date >= ? and date <= ?";
	private static final String UPDATE_ORDER_STATUS = "update test.order set status = ? where orderId = ?";
	private static final String SELECT_USER_BY_ID = "select * from user where id = ?";
	private static final String SELECT_CART_BY_ORDERID = "select * from cart where orderId = ?";

	public AdminDAO() {
		// TODO Auto-generated constructor stub
	}

	protected Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(this.jdbcURL, this.jdbcUsername, this.jdbcPassword);
		return connection;
	}

	public Admin selectAdmin(String name, String password) {
		Admin admin = new Admin();
		try {
			Connection connection = this.getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_ADMIN);
			ps.setString(1, name);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				admin.setId(rs.getInt("id"));
				admin.setAccName(rs.getString("adminname"));
				admin.setPassword(rs.getString("password"));
				admin.setRealName(rs.getString("realname"));
				admin.setDob(rs.getDate("dob"));
				admin.setEmail(rs.getString("email"));
				admin.setPhone(rs.getString("phone"));
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return admin;
	}

	public Admin selectAdminByIdOrName(int id, String name) {
		Admin admin = new Admin();
		try {
			Connection connection = this.getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_ADMIN_BY_ID_OR_ACCNAME);
			ps.setInt(1, id);
			ps.setString(2, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				admin.setId(rs.getInt("id"));
				admin.setAccName(rs.getString("adminname"));
				admin.setPassword(rs.getString("password"));
				admin.setRealName(rs.getString("realname"));
				admin.setDob(rs.getDate("dob"));
				admin.setEmail(rs.getString("email"));
				admin.setPhone(rs.getString("phone"));
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return admin;
	}
	
	public void updateAdminInfo(int id, String fullName, Date dob, String email, String phone) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_ADMIN_INFO);
			ps.setString(1, fullName);
			ps.setDate(2, dob);
			ps.setString(3, email);
			ps.setString(4, phone);
			ps.setInt(5, id);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateAdminPassword(int id, String password) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_ADMIN_PASSWORD);
			ps.setString(1, password);
			ps.setInt(2, id);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean checkExistedBookWhenInsert(Book book) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_EXISTED_BOOK_WHEN_INSERT);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return true;
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkExistedBookWhenUpdate(Book book) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_EXISTED_BOOK_WHEN_UPDATE);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setInt(3, book.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return true;
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Book> getAllBooks() {
		List<Book> books = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_ALL_BOOKS);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String category = rs.getString("category");
				Date release = rs.getDate("release");
				int pages = rs.getInt("pages");
				int sold = rs.getInt("sold");
				String desc = rs.getString("description");
				String bookCover = rs.getString("bookcover");
				int price = rs.getInt("price");
				books.add(new Book(id, title, author, category, release, pages, sold, desc, bookCover, price));
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	public List<Category> getCategories() {
		List<Category> categories = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_CATEGORIES);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				categories.add(new Category(id, name));
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categories;
	}

	public Book getBookById(int id) {
		Book book = new Book();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_BOOK_BY_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setCategory(rs.getString("category"));
				book.setRelease(rs.getDate("release"));
				book.setPages(rs.getInt("pages"));
				book.setPrice(rs.getInt("price"));
				book.setSold(rs.getInt("sold"));
				book.setDescription(rs.getString("description"));
				book.setBookCover(rs.getString("bookcover"));
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return book;
	}
	
	public Book getLastBook() {
		Book book = new Book();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_LAST_BOOK);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setCategory(rs.getString("category"));
				book.setRelease(rs.getDate("release"));
				book.setPages(rs.getInt("pages"));
				book.setPrice(rs.getInt("price"));
				book.setSold(rs.getInt("sold"));
				book.setDescription(rs.getString("description"));
				book.setBookCover(rs.getString("bookcover"));
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return book;
	}

	public List<Book> getBooksByTitleOrAuthor(String bookTitle, String bookAuthor) {
		List<Book> books = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_BOOKS_BY_TITLE_OR_AUTHOR);
			ps.setString(1, "%" + bookTitle + "%");
			ps.setString(2, "%" + bookAuthor + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String category = rs.getString("category");
				Date release = rs.getDate("release");
				int pages = rs.getInt("pages");
				int sold = rs.getInt("sold");
				String desc = rs.getString("description");
				String bookCover = rs.getString("bookcover");
				int price = rs.getInt("price");
				books.add(new Book(id, title, author, category, release, pages, sold, desc, bookCover, price));
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	
	public List<Book> getNewestBooks() {
		List<Book> books = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_NEWEST_BOOKS);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setCategory(rs.getString("category"));
				book.setRelease(rs.getDate("release"));
				book.setPages(rs.getInt("pages"));
				book.setPrice(rs.getInt("price"));
				book.setSold(rs.getInt("sold"));
				book.setDescription(rs.getString("description"));
				book.setBookCover(rs.getString("bookcover"));
				books.add(book);
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	
	public List<Book> getOldestBooks() {
		List<Book> books = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_OLDEST_BOOKS);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setCategory(rs.getString("category"));
				book.setRelease(rs.getDate("release"));
				book.setPages(rs.getInt("pages"));
				book.setPrice(rs.getInt("price"));
				book.setSold(rs.getInt("sold"));
				book.setDescription(rs.getString("description"));
				book.setBookCover(rs.getString("bookcover"));
				books.add(book);
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	
	public List<Book> getMostPopularBooks() {
		List<Book> books = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_MOST_POPULAR_BOOKS);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setCategory(rs.getString("category"));
				book.setRelease(rs.getDate("release"));
				book.setPages(rs.getInt("pages"));
				book.setPrice(rs.getInt("price"));
				book.setSold(rs.getInt("sold"));
				book.setDescription(rs.getString("description"));
				book.setBookCover(rs.getString("bookcover"));
				books.add(book);
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	
	public List<Book> getBooksFromAToZ() {
		List<Book> books = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_BOOKS_FROM_A_TO_Z);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setCategory(rs.getString("category"));
				book.setRelease(rs.getDate("release"));
				book.setPages(rs.getInt("pages"));
				book.setPrice(rs.getInt("price"));
				book.setSold(rs.getInt("sold"));
				book.setDescription(rs.getString("description"));
				book.setBookCover(rs.getString("bookcover"));
				books.add(book);
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	
	public List<Book> getBooksFromZToA() {
		List<Book> books = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_BOOKS_FROM_Z_TO_A);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setCategory(rs.getString("category"));
				book.setRelease(rs.getDate("release"));
				book.setPages(rs.getInt("pages"));
				book.setPrice(rs.getInt("price"));
				book.setSold(rs.getInt("sold"));
				book.setDescription(rs.getString("description"));
				book.setBookCover(rs.getString("bookcover"));
				books.add(book);
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	public void insertBook(Book book) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(INSERT_BOOK);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getCategory());
			ps.setDate(4, book.getRelease());
			ps.setInt(5, book.getPages());
			ps.setInt(6, book.getPrice());
			ps.setInt(7, 0);
			ps.setString(8, book.getDescription());
			ps.setString(9, book.getBookCover());
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateBook(Book book) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_BOOK);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getCategory());
			ps.setDate(4, book.getRelease());
			ps.setInt(5, book.getPages());
			ps.setInt(6, book.getPrice());
			ps.setString(7, book.getDescription());
			ps.setString(8, book.getBookCover());
			ps.setInt(9, book.getId());
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateBookSold(int bookId, int sold) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_BOOK_SOLD);
			ps.setInt(1, sold);
			ps.setInt(2, bookId);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteBook(int id) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_BOOK);
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public User getUserById(int userId) {
		User user = new User();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_ID);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setAccName(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRealName(rs.getString("realname"));
				user.setDob(rs.getDate("dob"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public List<Order> getAllOrders() {
		List<Order> orders = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_ALL_ORDERS);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int orderId = rs.getInt("orderId");
				User user = getUserById(rs.getInt("userId"));
				boolean confirm = rs.getInt("confirm") == 1 ? true : false;
				int totalPrice = rs.getInt("totalPrice");
				int status = rs.getInt("status");
				Date date = rs.getDate("date");
				orders.add(new Order(orderId, user, confirm, date, totalPrice, status));
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}

	public Order getOrderById(int orderId) {
		Order order = new Order();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_ORDER_BY_ID);
			ps.setInt(1, orderId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = getUserById(rs.getInt("userId"));
				boolean confirm = rs.getInt("confirm") == 1 ? true : false;
				int totalPrice = rs.getInt("totalPrice");
				Date date = rs.getDate("date");
				int status = rs.getInt("status");
				order.setId(orderId);
				order.setUser(user);
				order.setConfirm(confirm);
				order.setDate(date);
				order.setTotalPrice(totalPrice);
				order.setStatus(status);
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

	public List<Order> getOrdersByStatus(int status) {
		List<Order> orders = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_ORDERS_BY_STATUS);
			ps.setInt(1, status);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int orderId = rs.getInt("orderId");
				User user = getUserById(rs.getInt("userId"));
				boolean confirm = rs.getInt("confirm") == 1 ? true : false;
				int totalPrice = rs.getInt("totalPrice");
				Date date = rs.getDate("date");
				orders.add(new Order(orderId, user, confirm, date, totalPrice, status));
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}

	public List<Order> getOrdersFromTo(Date from, Date to) {
		List<Order> orders = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_ORDERS_FROM_TO);
			ps.setDate(1, from);
			ps.setDate(2, to);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int orderId = rs.getInt("orderId");
				User user = getUserById(rs.getInt("userId"));
				boolean confirm = rs.getInt("confirm") == 1 ? true : false;
				int totalPrice = rs.getInt("totalPrice");
				int status = rs.getInt("status");
				Date date = rs.getDate("date");
				orders.add(new Order(orderId, user, confirm, date, totalPrice, status));
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}

	public void updateOrderStatus(int orderId, int status) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_ORDER_STATUS);
			ps.setInt(1, status);
			ps.setInt(2, orderId);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Cart getCartByOrderId(int orderId) {
		Cart cart = new Cart();
		cart.setOrder(getOrderById(orderId));
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_CART_BY_ORDERID);
			ps.setInt(1, orderId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book book = getBookById(rs.getInt("bookId"));
				cart.setBooks(book, rs.getInt("quantity"));
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cart;
	}

}
