package nexsoft.rere.management.stock.controller;

import nexsoft.rere.management.stock.entity.DetailStock;
import nexsoft.rere.management.stock.response.ResponseList;
import nexsoft.rere.management.stock.response.ResponseObject;
import nexsoft.rere.management.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StockController {

	@Autowired
	private StockService service;

	@PostMapping("/stock/in/{idU}/{idP}")
	public ResponseEntity<ResponseObject> stockIn(@PathVariable int idU, @PathVariable int idP,
			@RequestBody DetailStock payload) {
		return service.addStock(idU, idP, payload);
	}

	@PostMapping("/stock/end/{idU}/{idP}/{nameP}")
	public ResponseEntity<ResponseObject> stockEnd(@PathVariable int idU, @PathVariable int idP, @PathVariable String nameP) {
		return service.stockEnd(idU, idP, nameP);
	}

	@GetMapping(value = "/stocks", produces = "application/json")
	public ResponseEntity<ResponseList> getAllStock() {
		return service.getAllStock();
	}

	@GetMapping("/stock/{id}")
	public ResponseEntity<ResponseObject> findById(@PathVariable int id) {
		return service.findById(id);
	}

	@PutMapping(value = "/stock/update/{idP}/{idS}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<ResponseObject> updateStock(@PathVariable int idP, @PathVariable int idS,
			@RequestBody DetailStock payload) {
		return service.updateStock(idP, idS, payload);
	}

}
