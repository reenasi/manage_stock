package nexsoft.rere.management.stock.entity;

import javax.persistence.*;
import java.util.Arrays;


@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private int id;
	private String productID;
	private String productName;
	private String description;	
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] image;
	private boolean deleted;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	public Product() {
		this.deleted = false;
	}

	public Product(String productID, String productName, String description, byte[] image, boolean deleted, User user) {
		this.productID = productID;
		this.productName = productName;
		this.description = description;
		this.image = image;
		this.deleted = deleted;
		this.user = user;
		this.deleted = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Product [productID=" + productID + ", productName=" + productName + ", description=" + description
				+ ", image=" + Arrays.toString(image) + ", deleted="
				+ deleted + ", user=" + user + "]";
	}
}
