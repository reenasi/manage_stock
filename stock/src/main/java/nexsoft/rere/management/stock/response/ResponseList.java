package nexsoft.rere.management.stock.response;

import java.util.List;


public class ResponseList implements Response {
	private int status;
	private String message;
	private List<?> data;
	
	public ResponseList() {
	}
	
	public ResponseList(int status, String message, List<?> data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	public ResponseList(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}
}
