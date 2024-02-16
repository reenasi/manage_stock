package nexsoft.rere.management.stock.dto;

import java.time.LocalDate;

public class DetailStockDto {
	private int id;
	private String batchID;
	private LocalDate expireDate;
	private int price;
	private int qty;
	private int currentQty;
	private int balance;
	private String state;
	private String batchFrom;
	private LocalDate created_at;
	private int stock;
	private int product;
	
	public DetailStockDto() {
	}
	
	
	public DetailStockDto(int id, String batchID, int price, int qty, int currentQty, int stock,
			int product) {
		this.id = id;
		this.batchID = batchID;
		this.price = price;
		this.qty = qty;
		this.currentQty = currentQty;
		this.stock = stock;
		this.product = product;
	}
	
	public DetailStockDto(int id, String batchID, LocalDate expireDate, int price, int qty, int currentQty, int stock,
			int product) {
		this.id = id;
		this.batchID = batchID;
		this.expireDate = expireDate;
		this.price = price;
		this.qty = qty;
		this.currentQty = currentQty;
		this.stock = stock;
		this.product = product;
	}

	public DetailStockDto(int id, String batchID, LocalDate expireDate, int price, int qty, int currentQty,
			int balance, String state, String batchFrom, LocalDate created_at, int stock, int product) {
		this.id = id;
		this.batchID = batchID;
		this.expireDate = expireDate;
		this.price = price;
		this.qty = qty;
		this.currentQty = currentQty;
		this.balance = balance;
		this.state = state;
		this.batchFrom = batchFrom;
		this.created_at = created_at;
		this.stock = stock;
		this.product = product;
	}

	public int getId() {
		return id;
	}


	public String getBatchID() {
		return batchID;
	}


	public LocalDate getExpireDate() {
		return expireDate;
	}


	public int getPrice() {
		return price;
	}


	public int getQty() {
		return qty;
	}

	public int getCurrentQty() {
		return currentQty;
	}

	public int getBalance() {
		return balance;
	}


	public String getState() {
		return state;
	}


	public String getBatchFrom() {
		return batchFrom;
	}

	public int getStock() {
		return stock;
	}

	public int getProduct() {
		return product;
	}
}
