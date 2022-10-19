package beans;

import java.sql.Date;

public class LibrarianBean {
	private int id;
	private String name, email, password, phone;
	private Date dob;

	public LibrarianBean() {

	}

	public LibrarianBean(String name, Date dob, String phone, String email, String password) {
		super();
		this.name = name;
		this.dob = dob;
		this.phone = phone;
		this.email = email;
		this.password = password;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

}
