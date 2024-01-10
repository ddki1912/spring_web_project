package com.example.test.model;

import java.sql.Date;

public class Order {
	
	private int id, totalPrice;
	private boolean confirm;
	private User user;
	private Date date;
	private int status;

	public Order() {
		// TODO Auto-generated constructor stub
	}

	public Order(int id, User user, boolean confirm, Date date, int totalPrice, int status) {
		super();
		this.id = id;
		this.user = user;
		this.confirm = confirm;
		this.date= date;
		this.totalPrice = totalPrice;
		this.status = status;
	}

	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean getConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
