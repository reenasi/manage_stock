package nexsoft.rere.management.stock.dto;

public class ProductDto {
	private int id;
	private String productID, productName, description;
	private byte[] image;
	private boolean deleted;

	public ProductDto(int id) {
		this.id = id;
	}
	
	public ProductDto(int id, String productID, String productName) {
		this.id = id;
		this.productID = productID;
		this.productName = productName;
	}

	public ProductDto(int id, String productID, String productName, String description, byte[] image, boolean deleted) {
		this.id = id;
		this.productID = productID;
		this.productName = productName;
		this.description = description;
		this.image = image;
		this.deleted = deleted;
	}

	public int getId() {
		return id;
	}

	public String getProductID() {
		return productID;
	}

	public String getProductName() {
		return productName;
	}

	public String getDescription() {
		return description;
	}

	public byte[] getImage() {
		return image;
	}

	public boolean isDeleted() {
		return deleted;
	}
}
