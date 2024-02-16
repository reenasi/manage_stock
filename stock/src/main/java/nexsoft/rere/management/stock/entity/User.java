package nexsoft.rere.management.stock.entity;

import javax.persistence.*;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true)
	private String email;
	private String password;
	private String name;
	private String role;
	private boolean deleted;

	public User() {
		this.role = "ROLE_ADMIN";
		this.deleted = false;
	}

	public User(int id, String email, String name) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.role = "ROLE_ADMIN";
		this.deleted = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", password=" + password + ", name=" + name + ", role=" + role + "]";
	}
}
