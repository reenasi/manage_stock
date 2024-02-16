package nexsoft.rere.management.stock.response;

public class ResponseObject implements Response {
	private int status;
	private String message;
	private Object data;
	
	public ResponseObject(int status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public ResponseObject(int status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}
	public ResponseObject() {
		super();
	}
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public Object getData() {
		return data;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseObject [status=" + status + ", message=" + message + ", data=" + data + "]";
	}
}
