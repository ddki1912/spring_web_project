package com.example.test.model;
import java.sql.Date;
public class Client{

	private int id;
	private String accName, password, realName, email, phone;
	private Date dob;

	public Client() {
		// TODO Auto-generated constructor stub
	}

	public Client(int id, String accName, String password, String realName, Date dob, String email, String phone) {
		super();
		this.id = id;
		this.accName = accName;
		this.password = password;
		this.realName = realName;
		this.dob = dob;
		this.email = email;
		this.phone=phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
