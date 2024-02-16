package nexsoft.rere.management.stock.controller;


import nexsoft.rere.management.stock.entity.User;
import nexsoft.rere.management.stock.response.ResponseList;
import nexsoft.rere.management.stock.response.ResponseObject;
import nexsoft.rere.management.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping("/public/register")
	public ResponseEntity<ResponseObject> register(@RequestBody User user) {
		return service.save(user);
	}
	
	@PostMapping(value = "/public/login")
	public ResponseEntity<ResponseObject> createAuthenticationToken(@RequestBody User user) throws Exception {
		return service.login(user);
	}
	
	@GetMapping(value = "/users", produces = "application/json")
	public ResponseEntity<ResponseList> getAllUser() {
		return service.getAllUser();
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<ResponseObject> findById(@PathVariable int id) {
		return service.findById(id);
	}
	
	@GetMapping("/user/name/{name}")
	public ResponseEntity<ResponseObject> findById(@PathVariable String name) {
		return service.findByUsername(name);
	}
	
	@GetMapping("/user/email/{email}")
	public ResponseEntity<ResponseObject> findByEmail(@PathVariable String email) {
		return service.findByEmail(email);
	}
	
	@PutMapping(value = "/user/{id}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<ResponseObject> updateUser(@PathVariable int id, @RequestBody User payload) {
		return service.updateUser(id, payload);
	}
}
