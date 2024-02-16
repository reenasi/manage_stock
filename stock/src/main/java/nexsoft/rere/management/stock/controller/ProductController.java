package nexsoft.rere.management.stock.controller;

import nexsoft.rere.management.stock.entity.Product;
import nexsoft.rere.management.stock.response.ResponseList;
import nexsoft.rere.management.stock.response.ResponseObject;
import nexsoft.rere.management.stock.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService service;
	
	@PostMapping("/product")
	public ResponseEntity<ResponseObject> add(@RequestBody Product payload) {
		return service.addProduct(payload);
	}
	
	@GetMapping(value = "/products", produces = "application/json")
	public ResponseEntity<ResponseList> getAllProduct() {
		return service.getAllProduct();
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<ResponseObject> findById(@PathVariable int id) {
		return service.findById(id);
	}
	
	@GetMapping("/product/search/{data}")
	public ResponseEntity<ResponseList> searchProductByProductIdOrProductName(@PathVariable String data) {
		return service.searchProductByProductIdOrProductName(data);
	}
	
	@PutMapping(value = "/product/delete/{productid}")
	public ResponseEntity<ResponseObject> deleteProductByProductId(@PathVariable String productid) {
		return service.deleteProductByProductId(productid);
	}
	
	@GetMapping(value = "/product/user/{user_id}", produces = "application/json")
	public ResponseEntity<ResponseList> getProductUser(@PathVariable int user_id) {
		return service.getProductUser(user_id);
	}
}