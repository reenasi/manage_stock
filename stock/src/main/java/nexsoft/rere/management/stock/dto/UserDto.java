package nexsoft.rere.management.stock.dto;

public class UserDto {
	private int id;
	private String name, token, email, password;
	
	public UserDto() {
	}
	
	public UserDto(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public UserDto(int id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}
	
	public UserDto(int id, String name, String email, String token) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.token = token;
	}

	public UserDto(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getToken() {
		return token;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}