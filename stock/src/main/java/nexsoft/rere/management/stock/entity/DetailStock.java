package nexsoft.rere.management.stock.entity;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class DetailStock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
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
	
	@ManyToOne
	@JoinColumn(name = "stock_id", referencedColumnName = "id")
	private Stock stock;
	
	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;
	private boolean deleted;
	
	public DetailStock() {
		this.deleted = false;
		this.created_at = created_at.now();
	}

	public DetailStock(String batchID, LocalDate expireDate, int price, int qty, int currentQty, int balance,
			String state, String batchFrom, Stock stock, Product product, boolean deleted) {
		this.batchID = batchID;
		this.expireDate = expireDate;
		this.price = price;
		this.qty = qty;
		this.currentQty = currentQty;
		this.balance = balance;
		this.state = state;
		this.batchFrom = batchFrom;
		this.stock = stock;
		this.product = product;
		this.deleted = deleted;
		this.created_at = created_at.now();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBatchID() {
		return batchID;
	}

	public void setBatchID(String batchID) {
		this.batchID = batchID;
	}

	public LocalDate getExpireDate() {
		return expireDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getCurrentQty() {
		return currentQty;
	}

	public void setCurrentQty(int currentQty) {
		this.currentQty = currentQty;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setExpireDate(LocalDate expireDate) {
		this.expireDate = expireDate;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBatchFrom() {
		return batchFrom;
	}

	public void setBatchFrom(String batchFrom) {
		this.batchFrom = batchFrom;
	}

	public LocalDate getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDate created_at) {
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "DetailStock [id=" + id + ", batchID=" + batchID + ", expireDate=" + expireDate + ", price=" + price
				+ ", qty=" + qty + ", currentQty=" + currentQty + ", balance=" + balance + ", state=" + state
				+ ", batchFrom=" + batchFrom + ", created_at=" + created_at + ", stock=" + stock + ", product="
				+ product + ", deleted=" + deleted + "]";
	}
}
