package com.example.test.controller;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import com.example.test.model.*;

public class UserDAO {

	private String jdbcURL = "jdbc:mysql://localhost:3306/test";
	private String jdbcUsername = "root";
	private String jdbcPassword = "qaz123";

	// define SQL statements
	private static final String SELECT_USER = "select * from user where username = ? and password = ?";
	private static final String SELECT_USER_BY_ID_OR_USERNAME = "select * from user where id = ? or username = ?";
	private static final String INSERT_USER = "insert into user(username, password, realname, dob, email, phone) values (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_USER_INFO = "update user set realname = ?, dob = ?, email = ?, phone = ? where id = ?";
	private static final String UPDATE_USER_PASSWORD = "update user set password = ? where id = ?";
	private static final String SELECT_CATEGORIES = "select * from category";
	private static final String SELECT_ALL_BOOKS = "select * from book";
	private static final String SELECT_BOOK_BY_ID = "select * from book where id = ?";
	private static final String SELECT_BOOKS_BY_TITLE_OR_AUTHOR = "select * from book where title like ? or author like ?";
	private static final String SELECT_BOOK_BY_CATEGORY = "select * from book where category = ?";
	private static final String SELECT_TOP_10_BEST_SELLERS = "select * from book where sold > 0 order by sold desc limit 10";
	private static final String SELECT_BOOKS_FROM_A_TO_Z = "select * from book order by title asc";
	private static final String SELECT_BOOKS_FROM_Z_TO_A = "select * from book order by title desc";
	private static final String SELECT_NEWEST_BOOKS = "select * from book order by `release` desc";
	private static final String SELECT_OLDEST_BOOKS = "select * from book order by `release` asc";
	private static final String SELECT_COMMENTS = "select * from comment where bookId = ?";
	private static final String INSERT_COMMENT = "insert into comment(userId, bookId, rating, comment) values (?, ?, ?, ?)";
	private static final String UPDATE_COMMENT = "update comment set rating = ?, comment = ? where id = ?";
	private static final String DELETE_COMMENT = "delete from comment where id = ?";
	private static final String INSERT_ORDER = "insert into test.order(userId, confirm, totalPrice, status, date) values (?, ?, ?, ?, ?)";
	private static final String SELECT_LAST_ORDER = "select * from test.order where userId = ? order by orderId desc limit 1";
	private static final String SELECT_ORDER_BY_ORDERID_OR_USERID = "select * from test.order where orderId = ? or userId = ?";
	private static final String SELECT_CONFIRMED_ORDERS = "select * from test.order where confirm = ? and userId = ?";
	private static final String UPDATE_ORDER_CONFIRM = "update test.order set confirm = ? where orderId = ?";
	private static final String UPDATE_ORDER_TOTALPRICE = "update test.order set totalPrice = ? where orderId = ?";
	private static final String UPDATE_ORDER_DATE = "update test.order set date = ? where orderId = ?";
	private static final String DELETE_ORDER = "delete from test.order where orderId = ?";
	private static final String SELECT_CART = "select * from cart where orderId = ?";
	private static final String INSERT_CART = "insert into cart(orderId, bookId, quantity) values (?,?,?)";
	private static final String UPDATE_CART = "update cart set quantity = ? where orderId = ? and bookId = ?";
	private static final String DELETE_ITEM = "delete from cart where orderId = ? and bookId = ?";
	private static final String DELETE_CART = "delete from cart where orderId = ?";

	public UserDAO() {
		// TODO Auto-generated constructor stub
	}

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public User selectExistedUser(String userName, String password) {
		User user = new User();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_USER);
			ps.setString(1, userName);
			ps.setString(2, password);
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

	public User selectUserByIdOrName(int id, String name) {
		User user = new User();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_ID_OR_USERNAME);
			ps.setInt(1, id);
			ps.setString(2, name);
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

	public void insertUser(String userName, String password, String realName, Date dob, String email, String phone) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(INSERT_USER);
			ps.setString(1, userName);
			ps.setString(2, password);
			ps.setString(3, realName);
			ps.setDate(4, dob);
			ps.setString(5, email);
			ps.setString(6, phone);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateUserInfo(int id, String fullName, Date dob, String email, String phone) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_USER_INFO);
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
	
	public void updateUserPassword(int id, String password) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_USER_PASSWORD);
			ps.setString(1, password);
			ps.setInt(2, id);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Category> getCategories(){
		List<Category> categories = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_CATEGORIES);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				categories.add(new Category(id, name));
			}
			ps.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return categories;
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
				book.setSold(rs.getInt("sold"));
				book.setDescription(rs.getString("description"));
				book.setBookCover(rs.getString("bookcover"));
				book.setPrice(rs.getInt("price"));
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return book;
	}
	
	public List<Book> getBookByCategory(String category){
		List<Book> books = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_BOOK_BY_CATEGORY);
			ps.setString(1, category);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setCategory(rs.getString("category"));
				book.setRelease(rs.getDate("release"));
				book.setPages(rs.getInt("pages"));
				book.setSold(rs.getInt("sold"));
				book.setDescription(rs.getString("description"));
				book.setBookCover(rs.getString("bookcover"));
				book.setPrice(rs.getInt("price"));
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
	
	public List<Book> getTop10BestSellers(){
		List<Book> books = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_TOP_10_BEST_SELLERS);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setCategory(rs.getString("category"));
				book.setRelease(rs.getDate("release"));
				book.setPages(rs.getInt("pages"));
				book.setSold(rs.getInt("sold"));
				book.setDescription(rs.getString("description"));
				book.setBookCover(rs.getString("bookcover"));
				book.setPrice(rs.getInt("price"));
				books.add(book);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	public void addOrder(int userId) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(INSERT_ORDER);
			ps.setInt(1, userId);
			ps.setInt(2, 0);
			ps.setInt(3, 0);
			ps.setInt(4, 0);
			ps.setDate(5, new Date(System.currentTimeMillis()));
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Order getLastOrder(int userId) {
		Order order = new Order();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_LAST_ORDER);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				order.setId(rs.getInt("orderId"));
				order.setUser(selectUserByIdOrName(rs.getInt("userId"), ""));
				order.setConfirm(rs.getInt("confirm") == 1 ? true : false);
				order.setTotalPrice(rs.getInt("totalPrice"));
				order.setStatus(rs.getInt("status"));
				order.setDate(rs.getDate("date"));
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

	public Order getOrderByOrderId(int orderId) {
		Order order = new Order();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_ORDER_BY_ORDERID_OR_USERID);
			ps.setInt(1, orderId);
			ps.setInt(2, 0);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				order.setId(rs.getInt("orderId"));
				order.setUser(selectUserByIdOrName(rs.getInt("userId"), ""));
				order.setConfirm(rs.getInt("confirm") == 1 ? true : false);
				order.setTotalPrice(rs.getInt("totalPrice"));
				order.setStatus(rs.getInt("status"));
				order.setDate(rs.getDate("date"));
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

	public List<Order> getOrderByUserId(int userId) {
		List<Order> orders = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_ORDER_BY_ORDERID_OR_USERID);
			ps.setInt(1, 0);
			ps.setInt(2, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Order order = new Order();
				order.setId(rs.getInt("orderId"));
				order.setUser(selectUserByIdOrName(rs.getInt("userId"), ""));
				order.setConfirm(rs.getInt("confirm") == 1 ? true : false);
				order.setTotalPrice(rs.getInt("totalPrice"));
				order.setStatus(rs.getInt("status"));
				order.setDate(rs.getDate("date"));
				orders.add(order);
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}
	
	
	public List<Order> getConfirmedOrders(int userId){
		List<Order> orders = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_CONFIRMED_ORDERS);
			ps.setInt(1, 1);
			ps.setInt(2, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Order order = new Order();
				order.setId(rs.getInt("orderId"));
				order.setUser(selectUserByIdOrName(rs.getInt("userId"), ""));
				order.setConfirm(rs.getInt("confirm") == 1 ? true : false);
				order.setTotalPrice(rs.getInt("totalPrice"));
				order.setStatus(rs.getInt("status"));
				order.setDate(rs.getDate("date"));
				orders.add(order);
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}
	
	public void updateOrderConfirm(int orderId, int confirm) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_ORDER_CONFIRM);
			ps.setInt(1, confirm);
			ps.setInt(2, orderId);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateOrderTotalPrice(int orderId, int totalPrice) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_ORDER_TOTALPRICE);
			ps.setInt(1, totalPrice);
			ps.setInt(2, orderId);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateOrderDate(int orderId, Date date) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_ORDER_DATE);
			ps.setDate(1, date);
			ps.setInt(2, orderId);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteOrder(int orderId) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_ORDER);
			ps.setInt(1, orderId);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Cart> getCarts(int userId) {
		List<Cart> carts = new ArrayList<>();

		List<Order> orders = getOrderByUserId(userId);
		for (Order x : orders) {
			Cart cart = getCart(x.getId());
			carts.add(cart);
		}

		return carts;
	}

	public Cart getCart(int orderId) {
		Cart cart = new Cart();
		cart.setOrder(getOrderByOrderId(orderId));
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_CART);
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

	public void insertCart(int orderId, int bookId, int quantity) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(INSERT_CART);
			ps.setInt(1, orderId);
			ps.setInt(2, bookId);
			ps.setInt(3, quantity);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCart(int orderId, int bookId, int quantity) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_CART);
			ps.setInt(1, quantity);
			ps.setInt(2, orderId);
			ps.setInt(3, bookId);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteItem(int orderId, int bookId) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_ITEM);
			ps.setInt(1, orderId);
			ps.setInt(2, bookId);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteCart(int orderId) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_CART);
			ps.setInt(1, orderId);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Comment> getComments(int bookId) {
		List<Comment> comments = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_COMMENTS);
			ps.setInt(1, bookId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setUser(selectUserByIdOrName(rs.getInt("userId"), ""));
				comment.setBook(getBookById(rs.getInt("bookId")));
				comment.setRating(rs.getInt("rating"));
				comment.setComment(rs.getString("comment"));
				comments.add(comment);
			}
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}

	public void insertComment(int userId, int bookId, int rating, String comment) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(INSERT_COMMENT);
			ps.setInt(1, userId);
			ps.setInt(2, bookId);
			ps.setInt(3, rating);
			ps.setString(4, comment);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateComment(int id, int rating, String comment) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_COMMENT);
			ps.setInt(1, rating);
			ps.setString(2, comment);
			ps.setInt(3, id);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteComment(int id) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_COMMENT);
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
