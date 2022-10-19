package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beans.ReaderBean;
import beans.BookBean;

public class LibrarianDao {

	public static boolean checkReaderExisted(ReaderBean rb) {
		boolean ok = false;
		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect.prepareStatement("select * from readers where phone=?");
			ps.setString(1, rb.getPhone());
//			String[] data = new SimpleDateFormat("dd/MM/yyyy").format(rb.getDob()).split("/");
//			String newDob = data[2] + "-" + data[1] + "-" + data[0];
//			ps.setString(2, newDob);
//			ps.setString(3, rb.getPhone());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ok = true;
			}
			connect.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return ok;
	}

	public static int addReader(ReaderBean rb) {
		int status = 0;
		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect.prepareStatement("insert into readers(name, dob, phone) values(?,?,?)");
			ps.setString(1, rb.getName());
			String[] data = new SimpleDateFormat("dd/MM/yyyy").format(rb.getDob()).split("/");
			String newDob = data[2] + "-" + data[1] + "-" + data[0];
			ps.setString(2, newDob);
			ps.setString(3, rb.getPhone());
			status = ps.executeUpdate();
			connect.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

	public static int editReader(ReaderBean rb) {
		int status = 0;
		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect.prepareStatement("update readers set name=?,dob=?,phone=? where id=?");
			ps.setString(1, rb.getName());
			String[] data = new SimpleDateFormat("dd/MM/yyyy").format(rb.getDob()).split("/");
			String newDob = data[2] + "-" + data[1] + "-" + data[0];
			ps.setString(2, newDob);
			ps.setString(3, rb.getPhone());
			ps.setInt(4, rb.getId());
			status = ps.executeUpdate();
			connect.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return status;
	}

	public static List<ReaderBean> viewReader() {
		List<ReaderBean> rbList = new ArrayList<>();

		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect.prepareStatement("select * from readers");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ReaderBean rb = new ReaderBean();
				rb.setId(rs.getInt("id"));
				rb.setName(rs.getString("name"));
				rb.setDob(rs.getDate("dob"));
				rb.setPhone(rs.getString("phone"));
				PreparedStatement ps2 = connect.prepareStatement("select * from borrowbook where readerId =?");
				ps2.setInt(1, rb.getId());
				ResultSet rs2 = ps2.executeQuery();
				if (rs2.next()) {
					rb.setBookId(rs2.getString("bookId"));
					rb.setBorrowOn(rs2.getDate("borrowOn"));
					rb.setBorrowDate(rs2.getString("borrowOn"));
					rb.setReturnStatus(rs2.getString("returnStatus"));
				} else {
					rb.setBookId("");
					rb.setBorrowDate("");
					rb.setReturnStatus("");
				}
				rbList.add(rb);
			}
			connect.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return rbList;
	}

	public static List<ReaderBean> viewReaderByName(String name) {
		List<ReaderBean> rbList = new ArrayList<>();

		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect.prepareStatement("select * from readers where name=?");
			ps.setString(1, name.trim());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ReaderBean rb = new ReaderBean();
				rb.setId(rs.getInt("id"));
				rb.setName(rs.getString("name"));
				rb.setDob(rs.getDate("dob"));
				rb.setPhone(rs.getString("phone"));
				PreparedStatement ps2 = connect.prepareStatement("select * from borrowbook where readerId=?");
				ps2.setInt(1, rb.getId());
				ResultSet rs2 = ps2.executeQuery();
				if (rs2.next()) {
					rb.setBookId(rs2.getString("bookId"));
					rb.setBorrowOn(rs2.getDate("borrowOn"));
					rb.setBorrowDate(rs2.getString("borrowOn"));
					rb.setReturnStatus(rs2.getString("returnStatus"));
				} else {
					rb.setBookId("");
					rb.setBorrowDate("");
					rb.setReturnStatus("");
				}
				rbList.add(rb);
			}
			connect.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return rbList;
	}

	public static boolean checkToDelete(ReaderBean rb) {
		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect.prepareStatement("select * from borrowbook where readerId=?");
			ps.setInt(1, rb.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("returnStatus").equals("NO")) {
					return false;
				}
			}
			connect.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return true;
	}

	public static int deleteReader(ReaderBean rb) {
		int status = 0;
		try {
			if (checkToDelete(rb)) {
				Connection connect = DB.getMySQLConnection();
				PreparedStatement ps = connect.prepareStatement("delete from borrowbook where readerId=?");
				ps.setInt(1, rb.getId());
				status = ps.executeUpdate();
				if (status >= 0) {
					PreparedStatement ps2 = connect.prepareStatement("delete from readers where id=?");
					ps2.setInt(1, rb.getId());
					status = ps2.executeUpdate();
				}
				connect.close();
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return status;
	}

	public static boolean checkBookExisted(BookBean bb) {
		boolean ok = false;
		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect.prepareStatement("select * from books where name=? and author=?");
			ps.setString(1, bb.getName());
			ps.setString(2, bb.getAuthor());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ok = true;
			}
			connect.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return ok;
	}

	public static int addBook(BookBean bb) {
		int status = 0;
		try {
			Connection connect = DB.getMySQLConnection();
			if (!checkBookExisted(bb)) {
				PreparedStatement ps = connect
						.prepareStatement("insert into books(name, author, quantity, borrowed) values(?,?,?,?)");
				ps.setString(1, bb.getName());
				ps.setString(2, bb.getAuthor());
				ps.setInt(3, bb.getQuantity());
				ps.setInt(4, 0);
				status = ps.executeUpdate();
			} else {
				PreparedStatement ps2 = connect
						.prepareStatement("select (quantity) from books where name=? and author=?");
				ps2.setString(1, bb.getName());
				ps2.setString(2, bb.getAuthor());
				ResultSet rs = ps2.executeQuery();
				if (rs.next()) {
					int quantity = rs.getInt("quantity");
					PreparedStatement ps3 = connect
							.prepareStatement("update books set quantity=? where name=? and author=?");
					ps3.setInt(1, quantity + bb.getQuantity());
					ps3.setString(2, bb.getName());
					ps3.setString(3, bb.getAuthor());
					status = ps3.executeUpdate();
				}
			}
			connect.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

	public static int editBook(BookBean bb) {
		int status = 0;
		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect.prepareStatement("update books set name=?, author=?, quantity=? where id=?");
			ps.setString(1, bb.getName());
			ps.setString(2, bb.getAuthor());
			ps.setInt(3, bb.getQuantity());
			ps.setInt(4, bb.getId());
			status = ps.executeUpdate();
			connect.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

	public static List<BookBean> viewBook() {
		List<BookBean> bbList = new ArrayList<>();
		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect.prepareStatement("select * from books");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BookBean bb = new BookBean();
				bb.setId(rs.getInt("id"));
				bb.setName(rs.getString("name"));
				bb.setAuthor(rs.getString("author"));
				bb.setQuantity(rs.getInt("quantity"));
				bb.setBorrowed(rs.getInt("borrowed"));
				PreparedStatement ps2 = connect
						.prepareStatement("select (readerId) from borrowbook where bookId=? and returnStatus='NO'");
				ps2.setInt(1, bb.getId());
				ResultSet rs2 = ps2.executeQuery();
				List<Integer> borrowedBy = new ArrayList<>();
				while (rs2.next()) {
					int readerId = rs2.getInt("readerId");
					borrowedBy.add(readerId);
				}
				bb.setBorrowedBy(borrowedBy);
				bbList.add(bb);
			}
			connect.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return bbList;
	}

	public static List<BookBean> viewByNameOrAuthor(String s) {
		List<BookBean> bbList = new ArrayList<>();
		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect.prepareStatement("select * from books where name=? or author=?");
			ps.setString(1, s);
			ps.setString(2, s);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BookBean bb = new BookBean();
				bb.setId(rs.getInt("id"));
				bb.setName(rs.getString("name"));
				bb.setAuthor(rs.getString("author"));
				bb.setQuantity(rs.getInt("quantity"));
				bb.setBorrowed(rs.getInt("borrowed"));
				PreparedStatement ps2 = connect
						.prepareStatement("select (readerId) from borrowbook where bookId=? and returnStatus='NO'");
				ps2.setInt(1, bb.getId());
				ResultSet rs2 = ps2.executeQuery();
				List<Integer> borrowedBy = new ArrayList<>();
				while (rs2.next()) {
					int readerId = rs2.getInt("readerId");
					borrowedBy.add(readerId);
				}
				bb.setBorrowedBy(borrowedBy);

				bbList.add(bb);
			}
			connect.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return bbList;
	}

	public static int getBorrowedBook(int bookId) {
		int borrowed = 0;
		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect.prepareStatement("select (borrowed) from books where id=?");
			ps.setInt(1, bookId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				borrowed = rs.getInt("borrowed");
			}
			connect.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return borrowed;
	}

	public static boolean checkToBorrow(int readerId, int bookId) {
		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect.prepareStatement("select * from books where id=? and quantity>borrowed");
			ps.setInt(1, bookId);
			ResultSet rs1 = ps.executeQuery();

			if (rs1.next()) {
				PreparedStatement ps2 = connect.prepareStatement("select * from borrowbook where readerId=?");
				ps2.setInt(1, readerId);
				ResultSet rs2 = ps2.executeQuery();
				while (rs2.next()) {
					if (!rs2.getString("returnStatus").equals("YES")) {
						return false;
					}
				}
			}
			connect.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return true;
	}

	public static int borrowBook(ReaderBean rb, BookBean bb) {
		int status = 0;
		try {
			if (checkToBorrow(rb.getId(), bb.getId())) {
				Connection connect = DB.getMySQLConnection();
				PreparedStatement ps = connect.prepareStatement(
						"insert into borrowbook(readerId, bookId, borrowOn, returnStatus) values(?,?,?,?)");
				ps.setInt(1, rb.getId());
				ps.setInt(2, bb.getId());
				ps.setDate(3, new Date(System.currentTimeMillis()));
				ps.setString(4, "NO");
				status = ps.executeUpdate();
				if (status > 0) {
					PreparedStatement ps2 = connect.prepareStatement("update books set borrowed=? where id=?");
					ps2.setInt(1, getBorrowedBook(bb.getId()) + 1);
					ps2.setInt(2, bb.getId());
					status = ps2.executeUpdate();
					if (status > 0) {
						PreparedStatement ps3 = connect
								.prepareStatement("delete from borrowbook where readerId=? and returnStatus='YES'");
						ps3.setInt(1, rb.getId());
						status = ps3.executeUpdate();
					}

				}
				connect.close();
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return status;

	}

	public static int returnBook(ReaderBean rb, BookBean bb) {
		int status = 0;
		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect
					.prepareStatement("select * from borrowbook where readerId=? and bookId=? and returnStatus='NO'");
			ps.setInt(1, rb.getId());
			ps.setInt(2, bb.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				PreparedStatement ps2 = connect
						.prepareStatement("update borrowbook set returnStatus='YES' where readerId=? and bookId=?");
				ps2.setInt(1, rb.getId());
				ps2.setInt(2, bb.getId());
				status = ps2.executeUpdate();
				if (status > 0) {
					PreparedStatement ps3 = connect.prepareStatement("update books set borrowed=? where id=?");
					ps3.setInt(1, getBorrowedBook(bb.getId()) - 1);
					ps3.setInt(2, bb.getId());
					status = ps3.executeUpdate();
				}
			}
			connect.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

	public static boolean authenticate(String id, String password) {
		boolean status = false;

		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect.prepareStatement("select * from librarians where id=? and password=?");
			ps.setString(1, id);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				status = true;
			} else {
				System.out.println("Your ID or your password was incorrect!");
			}
			connect.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return status;
	}
}
