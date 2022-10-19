package beans;

import java.sql.Date;

public class ReaderBean {

	private int id;
	private String name, phone;
	private Date dob;
	private String bookId;
	private Date borrowOn;
	private String borrowDate;
	private String returnStatus;

	public ReaderBean() {

	}

	public ReaderBean(String name, Date dob, String phone) {
		super();
		this.name = name;
		this.dob = dob;
		this.phone = phone;
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

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookId() {
		return this.bookId;
	}

	public void setBorrowOn(Date borrowOn) {
		this.borrowOn = borrowOn;
	}

	public Date getBorrowOn() {
		return this.borrowOn;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	public String getReturnStatus() {
		return this.returnStatus;
	}

	public void setBorrowDate(String borrowDate) {
		this.borrowDate = borrowDate;

	}

	public String getBorrowDate() {
		return this.borrowDate;
	}
}
