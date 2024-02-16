package nexsoft.rere.management.stock.controller;

import nexsoft.rere.management.stock.entity.DetailStock;
import nexsoft.rere.management.stock.response.ResponseList;
import nexsoft.rere.management.stock.response.ResponseObject;
import nexsoft.rere.management.stock.service.DetailStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class DetailStockController {
	
	@Autowired
	private DetailStockService service;
	
	@PostMapping("/detailStock")
	public ResponseEntity<ResponseObject> add(@RequestBody DetailStock payload) {
		return service.addDetailStock(payload);
	}
	
	@GetMapping(value = "/detailStocks", produces = "application/json")
	public ResponseEntity<ResponseList> getAllDetailStock() {
		return service.getAllDetailStock();
	}

	@GetMapping("/detailStock/{id}")
	public ResponseEntity<ResponseObject> findById(@PathVariable int id) {
		return service.findById(id);
	}
	
	@GetMapping("/detailStockk/{productid}")
	public ResponseEntity<ResponseObject> findByProductId(@PathVariable int productid) {
		return service.findByProductId(productid);
	}
}