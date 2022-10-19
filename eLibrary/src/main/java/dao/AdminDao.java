package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beans.LibrarianBean;

public class AdminDao {

	public static boolean checkExisted(LibrarianBean lb) {
		boolean ok = false;
		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect
					.prepareStatement("select * from librarians where phone=?");
			ps.setString(1, lb.getPhone());
//			String[] data = new SimpleDateFormat("dd/MM/yyyy").format(lb.getDob()).split("/");
//			String newDob = data[2] + "-" + data[1] + "-" + data[0];
//			ps.setString(2, newDob);
//			ps.setString(3, lb.getPhone());
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

	public static int addLibrarian(LibrarianBean lb) {
		int status = 0;
		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect
					.prepareStatement("insert into librarians(name, dob, phone, email, password) values(?,?,?,?,?)");
			ps.setString(1, lb.getName());
			String[] data = new SimpleDateFormat("dd/MM/yyyy").format(lb.getDob()).split("/");
			String newDob = data[2] + "-" + data[1] + "-" + data[0];
			ps.setString(2, newDob);
			ps.setString(3, lb.getPhone());
			ps.setString(4, lb.getEmail());
			ps.setString(5, lb.getPassword());
			status = ps.executeUpdate();
			connect.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

	public static int editLibrarian(LibrarianBean lb) {
		int status = 0;
		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect
					.prepareStatement("update librarians set name=?,dob=?,phone=?,email=?,password=? where id=?");
			ps.setString(1, lb.getName());
			String[] data = new SimpleDateFormat("dd/MM/yyyy").format(lb.getDob()).split("/");
			String newDob = data[2] + "-" + data[1] + "-" + data[0];
			ps.setString(2, newDob);
			ps.setString(3, lb.getPhone());
			ps.setString(4, lb.getEmail());
			ps.setString(5, lb.getPassword());
			ps.setInt(6, lb.getId());
			status = ps.executeUpdate();
			connect.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return status;
	}

	public static List<LibrarianBean> viewLibrarian() {
		List<LibrarianBean> lbList = new ArrayList<LibrarianBean>();

		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect.prepareStatement("select * from librarians");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				LibrarianBean lb = new LibrarianBean();
				lb.setId(rs.getInt("id"));
				lb.setName(rs.getString("name"));
				lb.setDob(rs.getDate("dob"));
				lb.setPhone(rs.getString("phone"));
				lb.setEmail(rs.getString("email"));
				lb.setPassword(rs.getString("password"));
				lbList.add(lb);
			}
			connect.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return lbList;
	}

	public static List<LibrarianBean> viewLibrarianByName(String name) {
		List<LibrarianBean> lbList = new ArrayList<LibrarianBean>();

		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect.prepareStatement("select * from librarians where name=?");
			ps.setString(1, name.trim());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				LibrarianBean lb = new LibrarianBean();
				lb.setId(rs.getInt("id"));
				lb.setName(rs.getString("name"));
				lb.setDob(rs.getDate("dob"));
				lb.setPhone(rs.getString("phone"));
				lb.setEmail(rs.getString("email"));
				lb.setPassword(rs.getString("password"));
				lbList.add(lb);
			}
			connect.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return lbList;
	}

	public static int deleteLibrarian(LibrarianBean lb) {
		int status = 0;
		try {
			Connection connect = DB.getMySQLConnection();
			PreparedStatement ps = connect.prepareStatement("delete from librarians where id=?");
			ps.setInt(1, lb.getId());
			status = ps.executeUpdate();
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
			PreparedStatement ps = connect.prepareStatement("select * from admins where id=? and password=?");
			ps.setString(1, id);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				status = true;
			} else {
				System.out.print("Your ID or your password was incorrect!");
			}
			connect.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return status;
	}

}
