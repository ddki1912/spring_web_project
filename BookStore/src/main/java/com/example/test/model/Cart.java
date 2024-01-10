package com.example.test.model;

import java.util.*;

public class Cart {

	private Order order;
	private LinkedHashMap<Book, Integer> books = new LinkedHashMap<>();

	public Cart() {
		// TODO Auto-generated constructor stub
	}

	public Cart(Order order, LinkedHashMap<Book, Integer> books) {
		super();
		this.order = order;
		this.books = books;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public LinkedHashMap<Book, Integer> getBooks() {
		return books;
	}

	public void setBooks(Book book, int quantity) {
		this.books.put(book, quantity);
	}

	public int getTotalPrice() {
		int totalPrice = 0;
		Set<Book> keySet = this.books.keySet();
		for (Book x : keySet) {
			totalPrice += x.getPrice() * this.books.get(x);
		}
		return totalPrice;
	}
}
