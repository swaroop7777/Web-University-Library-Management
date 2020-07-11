package com.abc.librarymgmt;
public class DetailsInitializer {
	private int id;
	private String first_name;
	private String last_name;
	private String email;
	private int stock;
	private int bid;
	private int lid;
	public DetailsInitializer(int bid, int id, int lid) {
		super();
		this.id = id;
		this.bid=bid;
		this.lid=lid;
	}
	public DetailsInitializer() {
		super();
	}
	public DetailsInitializer(int id, String first_name, String last_name, String email) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
	}

	public DetailsInitializer(String first_name, String last_name, String email) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
	}

	public DetailsInitializer(int id, String book_name, String author, int stock) {
		super();
		this.id = id;
		this.first_name =book_name;
		this.last_name = author;
		this.stock=stock;
	}
	public DetailsInitializer(int id, int bid, String first_name, String last_name, int lid) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.bid = bid;
		this.lid=lid;
	}
	public String toString() {
		return "Student [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", email=" + email+
				", stock="+ stock+ "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}

}
