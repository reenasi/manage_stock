package nexsoft.rere.management.stock.service;

import nexsoft.rere.management.stock.dto.StockDto;
import nexsoft.rere.management.stock.entity.DetailStock;
import nexsoft.rere.management.stock.entity.Stock;
import nexsoft.rere.management.stock.repository.DetailStockRepository;
import nexsoft.rere.management.stock.repository.ProductRepository;
import nexsoft.rere.management.stock.repository.StockRepository;
import nexsoft.rere.management.stock.response.ResponseList;
import nexsoft.rere.management.stock.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StockService {

	@Autowired
	private StockRepository stockRepo;

	@Autowired
	private DetailStockRepository detailStockRepo;

	@Autowired
	private ProductRepository productRepo;

	@Transactional
	public ResponseEntity<ResponseObject> addStock(int idU, int idP, DetailStock payload) {
		Stock st = new Stock();
		String getCodeStock = this.generateStockID(idU);
		st.setStockID("STOCK" + getCodeStock);
		st.setDescription("Stock In, Product " + idP);
		stockRepo.insertStock(st.getStockID(), st.getDescription(), 0, idU, st.getCreated_at());

		String getBatchID = generateBatchID(idP);
		System.out.println("batch id " + getBatchID);
		payload.setBatchID("BATCH" + getBatchID);
		payload.setState("IN");

		int sumQty = detailStockRepo.sumQtyForBalance(idP);
		System.out.println("aoi " + sumQty);
		int balance = sumQty + payload.getQty();
		int idS = stockRepo.getLastStockByUser(idU).getId();

		detailStockRepo.insertDetailStock(payload.getBatchID(), payload.getExpireDate(), payload.getPrice(),
				payload.getQty(), payload.getQty(), balance, payload.getState(), "-", payload.getCreated_at(), idS,
				idP);

		int totalPrice = 0;
		totalPrice = totalPrice + (payload.getPrice() * payload.getQty());
		stockRepo.updateNetAmount(idS, totalPrice);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "Success", payload));
	}

	@Transactional
	public ResponseEntity<ResponseObject> stockEnd(int idU, int idP, String nameP) {
		Stock st = new Stock();
		String getCodeStock = this.generateStockID(idU);
		st.setStockID("STOCK" + getCodeStock);
		st.setDescription("Stock Out Product " + idP);
		stockRepo.insertStock(st.getStockID(), st.getDescription(), 0, idU, st.getCreated_at());

		int balance = 0;
		int idS = stockRepo.getLastStockByUser(idU).getId();

		DetailStock dS = new DetailStock();
		dS.setState("End");

		int getQty = detailStockRepo.findByProductIdtoGetLastValueOfBalance(idP).getBalance();
		List<DetailStock> batchList = detailStockRepo.filterBatch(idP);
		int qtyEnd = getQty;
		int totalPrice = 0;

		for (DetailStock detailStockDto : batchList) {
			if (detailStockDto.getQty() <= qtyEnd && qtyEnd != 0) {
				System.out.println("qty db : " + detailStockDto.getQty());
				System.out.println("qty end : " + qtyEnd);
				qtyEnd = qtyEnd - detailStockDto.getQty();

				detailStockRepo.insertDetailStockOut(detailStockDto.getQty(), balance, dS.getState(),
						detailStockDto.getBatchID(), dS.getCreated_at(), idS, idP);

				totalPrice = totalPrice + (detailStockDto.getPrice() * qtyEnd);
				stockRepo.updateNetAmount(idS, totalPrice);
				
			} else {
				detailStockRepo.insertDetailStockOut(qtyEnd, balance, dS.getState(), detailStockDto.getBatchID(),
						dS.getCreated_at(), idS, idP);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "Success", dS));
	}

	public ResponseEntity<ResponseObject> updateStock(int idP, int idS, DetailStock payload) {
		String getBatchID = generateBatchID(idP);
		System.out.println("batch id " + getBatchID);

		payload.setBatchID("BATCH" + getBatchID);
		System.out.println("generate batch : " + payload.getBatchID());

		detailStockRepo.insertDetailStock(payload.getBatchID(), payload.getExpireDate(), payload.getPrice(),
				payload.getQty(), payload.getQty(), payload.getBalance(), payload.getState(), payload.getBatchFrom(),
				payload.getCreated_at(), idS, idP);

		System.out.println(idS);
		StockDto stockId = stockRepo.findByID(idS);

		System.out.println("Stock id  " + stockId);

		if (stockId == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(404, "Stock Not Found"));
		}

		int totalPrice = 0;
		totalPrice = totalPrice + (payload.getPrice() * payload.getQty());

		System.out.println("total price : " + totalPrice);

		Stock stok = new Stock();
		stok.setDescription(idP + " in");
		stok.setNetAmount(totalPrice);
		System.out.println("description stock : " + stok.getDescription());;
		System.out.println("total price stock : " + stok.getNetAmount());

		stockRepo.updateStock(idS, stok.getDescription(), stok.getNetAmount());

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "Update Stock Success"));
	}

	public String generateStockID(int user_id) {
		String codeStock = String.valueOf((stockRepo.getStockUser(user_id)).size() + 1);
		String result = "";

		if (codeStock.length() == 1) {
			result = "000" + codeStock;
		} else if (codeStock.length() == 2) {
			result = "00" + codeStock;
		} else if (codeStock.length() == 3) {
			result = "0" + codeStock;
		} else if (codeStock.length() == 4) {
			result = codeStock;
		}

		return result;
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

	public ResponseEntity<ResponseList> getAllStock() {
		List<StockDto> result = stockRepo.getAllStock();
		if (result.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseList(404, "Data Not Found"));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseList(200, "Data Found", result));
	}

	public ResponseEntity<ResponseObject> findById(int id) {
		StockDto stockId = stockRepo.findByID(id);

		if (stockId == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(404, "Data Not Found"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, "Data Found", stockId));
	}
}
