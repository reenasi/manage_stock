package nexsoft.rere.management.stock.service;

import nexsoft.rere.management.stock.dto.DetailStockDto;
import nexsoft.rere.management.stock.entity.DetailStock;
import nexsoft.rere.management.stock.repository.DetailStockRepository;
import nexsoft.rere.management.stock.response.ResponseList;
import nexsoft.rere.management.stock.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class DetailStockService {

	@Autowired
	private DetailStockRepository detailStockRepo;

	@Transactional
	public ResponseEntity<ResponseObject> addDetailStock(DetailStock payload) {
		int idP = payload.getProduct().getId();
		String getBatchID = generateBatchID(idP);

		payload.setBatchID("BATCH" + getBatchID);
		payload.setState("IN");
		payload.getProduct().getId();

		if (LocalDate.now().compareTo(payload.getExpireDate()) > 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(404, "Expired date cannot be less than today's date"));
		}
		
		detailStockRepo.insertDetailStock(payload.getBatchID(), payload.getExpireDate(), payload.getPrice(), 
				payload.getQty(), payload.getQty(), payload.getBalance(), payload.getState(), payload.getBatchFrom(), 
				payload.getCreated_at(), payload.getStock().getId(), payload.getProduct().getId());
		
		return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject(HttpStatus.OK.value(), "Success", payload));
	}	

	public String generateBatchID(int product_id) {
		String batchID = String.valueOf((detailStockRepo.getDetailStockByProduct(product_id)).size() + 1);
		String result = "";

		if (batchID.length() == 1) {
			result = "000" + batchID;
		} else if (batchID.length() == 2) {
			result = "00" + batchID;
		} else if (batchID.length() == 3) {
			result = "0" + batchID;
		} else if (batchID.length() == 4) {
			result = batchID;
		}
		
		System.out.println(result);
		
		return result;
	}
	
	public ResponseEntity<ResponseList> getAllDetailStock() {
		List<DetailStockDto> result = detailStockRepo.getAllDetailStock();
		if (result.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseList(404, "Data Not Found"));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseList(200, "Data Found", result));
	}
	
	public ResponseEntity<ResponseObject> findById(int id) {
		DetailStockDto detailStockId = detailStockRepo.findByID(id);

		if (detailStockId == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(404, "Data Not Found"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, "Data Found", detailStockId));
	}

	public ResponseEntity<ResponseObject> findByProductId(int id) {
		DetailStockDto detailStockId = detailStockRepo.findByProductId(id);

		if (detailStockId == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(404, "Data Not Found"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, "Data Found", detailStockId));
	}
}
