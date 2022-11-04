package beans;

import java.util.List;

public class BookBean {

	private int id, quantity, borrowed;
	private String name, author;
	private List<ReaderBean> readerList;

	public BookBean() {
		super();
	}

	public BookBean(String name, String author, int quantity) {
		super();
		this.name = name;
		this.author = author;
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getBorrowed() {
		return borrowed;
	}

	public void setBorrowed(int borrowed) {
		this.borrowed = borrowed;
	}

	public int getId() {
		return id;
	}

	public List<ReaderBean> getReaderList() {
		return readerList;
	}

	public void setReaderList(List<ReaderBean> readerList) {
		this.readerList = readerList;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
