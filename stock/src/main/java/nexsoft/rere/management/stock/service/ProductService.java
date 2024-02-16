package nexsoft.rere.management.stock.service;

import nexsoft.rere.management.stock.dto.ProductDto;
import nexsoft.rere.management.stock.entity.DetailStock;
import nexsoft.rere.management.stock.entity.Product;
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
public class ProductService {

	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private StockRepository stockRepo;

	@Autowired
	private DetailStockRepository detailStockRepo;

	@Transactional
	public ResponseEntity<ResponseObject> addProduct(Product payload) {
		int idU = payload.getUser().getId();
		String getCodeProduct = this.generateProductID(idU);
		payload.setProductID("PROD" + getCodeProduct);


		productRepo.insertProduct(payload.getProductID(), payload.getProductName(), 
				payload.getDescription(), payload.getUser().getId(), payload.getImage());

		DetailStock dS = new DetailStock();
		dS.setBatchID("BATCH0000");
		dS.setState("BEGINNING");
		productRepo.getLastProductByUser(idU);
		System.out.println("test : "+productRepo.getLastProductByUser(idU));
		int idP = productRepo.getLastProductByUser(idU).getId();
		System.out.println("l "+idP);
		detailStockRepo.insertDetailStock2(dS.getBatchID(), dS.getExpireDate(), dS.getPrice(), dS.getQty(), 
				dS.getCurrentQty(), dS.getBalance(), dS.getState(), dS.getBatchFrom(), dS.getCreated_at(), idP);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "Success", payload));
	}
	
	public String generateProductID(int user_id) {
		String codeProduct = String.valueOf((productRepo.getProductUser(user_id)).size() + 1);
		String result = "";

		if (codeProduct.length() == 1) {
			result = "000" + codeProduct;
		} else if (codeProduct.length() == 2) {
			result = "00" + codeProduct;
		} else if (codeProduct.length() == 3) {
			result = "0" + codeProduct;
		} else if (codeProduct.length() == 4) {
			result = codeProduct;
		}
		
		return result;
	}

	public ResponseEntity<ResponseList> getAllProduct() {
		List<ProductDto> result = productRepo.getAllProduct();
		if (result.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseList(404, "Data Not Found"));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseList(200, "Data Found", result));
	}

	public ResponseEntity<ResponseObject> findById(int id) {
		ProductDto productId = productRepo.findById(id);

		if (productId == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(404, "Data Not Found"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, "Data Found", productId));
	}

	public ResponseEntity<ResponseList> searchProductByProductIdOrProductName(String search) {
		List<ProductDto> product;
		product = productRepo.searchProductByProductIdOrProductName(search);

		if (product.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseList(404, "Data Not Found"));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseList(200, "Data Found", product));
		}
	}
	
	public ResponseEntity<ResponseObject> deleteProductByProductId(String productid){
		ProductDto product = productRepo.findByProductIdisReady(productid);
		
		if(product == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(404, "Product Not Found"));
		}
		
		Product p = new Product();
		p.setDeleted(true);
		
		productRepo.deleteProduct(p.isDeleted(), product.getProductID());
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "Product deleted, Success", product));
	}
	
	public ResponseEntity<ResponseList> getProductUser(int user_id) {
		List<ProductDto> result = productRepo.getProductUser(user_id);
		if (result.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseList(404, "Data Not Found"));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseList(200, "Data Found", result));
	}
}