package nexsoft.rere.management.stock.entity;


import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class Stock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private int id;
	private String stockID;
	private LocalDate created_at;
	private String description;
	private int netAmount;
	private boolean deleted;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	
	public Stock() {
		this.created_at = created_at.now();
		this.deleted = false;
	}

	public Stock(String stockID, String description, int netAmount,User user) {
		this.stockID = stockID;
		this.created_at = created_at.now();
		this.description = description;
		this.netAmount = netAmount;
		this.user = user;
		this.deleted = false;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getStockID() {
		return stockID;
	}


	public void setStockID(String stockID) {
		this.stockID = stockID;
	}


	public LocalDate getCreated_at() {
		return created_at;
	}

	
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getNetAmount() {
		return netAmount;
	}


	public void setNetAmount(int netAmount) {
		this.netAmount = netAmount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + ", stockID=" + stockID + ", created_at=" + created_at + ", description="
				+ description + ", netAmount=" + netAmount + ", deleted=" + deleted + ", user=" + user + "]";
	}	
}
