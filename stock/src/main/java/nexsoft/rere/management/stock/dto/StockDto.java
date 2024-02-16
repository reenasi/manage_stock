package nexsoft.rere.management.stock.dto;

import java.time.LocalDate;

public class StockDto {
	private int id, netAmount, userId;
	private String stockID, description;
	private LocalDate created_at;

	public StockDto() {
	}
	
	public StockDto(int id, String stockID, String description, int netAmount, int userId) {
		this.id = id;
		this.stockID = stockID;
		this.description = description;
		this.netAmount = netAmount;
		this.userId = userId;
	}
	
	public StockDto(int id, String stockID, LocalDate created_at, String description, int netAmount, int userId) {
		this.id = id;
		this.stockID = stockID;
		this.created_at = created_at;
		this.description = description;
		this.netAmount = netAmount;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public String getStockID() {
		return stockID;
	}

	public LocalDate getCreated_at() {
		return created_at;
	}

	public String getDescription() {
		return description;
	}

	public int getNetAmount() {
		return netAmount;
	}
}
