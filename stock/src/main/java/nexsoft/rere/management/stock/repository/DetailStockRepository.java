package nexsoft.rere.management.stock.repository;

import nexsoft.rere.management.stock.dto.DetailStockDto;
import nexsoft.rere.management.stock.entity.DetailStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DetailStockRepository extends JpaRepository<DetailStock, Integer> {
	@Transactional
	@Modifying
	@Query(value = "Insert into detail_stock(batchid, expire_date, price, qty, current_qty, balance, state, "
			+ "batch_from, created_at, stock_id, product_id, deleted) "
			+ "value(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, false)", nativeQuery = true)
	void insertDetailStock(String batchid, LocalDate expireDate, int price, int qty, int current_qty, int balance,
			String state, String batchFrom, LocalDate created_at, int stock_id, int product_id);

	@Transactional
	@Modifying
	@Query(value = "Insert into detail_stock(batchid, expire_date, price, qty, current_qty, balance, state, "
			+ "batch_from, created_at, product_id, deleted) "
			+ "value(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, false)", nativeQuery = true)
	void insertDetailStock2(String batchid, LocalDate expireDate, int price, int qty, int current_qty, int balance,
			String state, String batchFrom, LocalDate created_at, int product_id);

	
	@Transactional
	@Modifying
	@Query(value = "Insert into detail_stock(qty, balance, state, "
			+ "batch_from, created_at, stock_id, product_id, deleted) "
			+ "value(?1, ?2, ?3, ?4, ?5, ?6, ?7, false)", nativeQuery = true)
	void insertDetailStockOut(int qty, int balance, String state, String batchFrom, 
			LocalDate created_at, int stock_id, int product_id);

	
	@Query("Select new nexsoft.rere.management.stock.dto.DetailStockDto("
			+ "d.id, d.batchID, d.price, d.qty, d.currentQty, s.id, p.id) From DetailStock d JOIN "
			+ " Stock s ON s.id = d.stock JOIN Product p ON p.id = d.product where d.id=:id")
	DetailStockDto findByID(@Param("id") int id);

	@Query("Select new nexsoft.rere.management.stock.dto.DetailStockDto("
			+ "d.id, d.batchID, d.price, d.qty, d.currentQty, s.id, p.id) From DetailStock d JOIN "
			+ " Stock s ON s.id = d.stock JOIN Product p ON p.id = d.product where d.product=:product_id")
	DetailStockDto findByProductId(@Param("product_id") int product_id);

	@Query("Select new nexsoft.rere.management.stock.dto.DetailStockDto("
			+ "d.id, d.batchID, d.expireDate, d.price, d.qty, d.currentQty, "
			+ "d.balance, d.state, d.batchFrom, d.created_at, s.id, p.id) "
			+ "From DetailStock d JOIN Stock s ON s.id = d.stock JOIN Product p ON p.id = d.product")
	List<DetailStockDto> getAllDetailStock();

	@Query("Select new nexsoft.rere.management.stock.dto.DetailStockDto("
			+ "d.id, d.batchID, d.expireDate, d.price, d.qty, d.currentQty,"
			+ "d.balance, d.state, d.batchFrom, d.created_at, s.id, p.id) "
			+ "From DetailStock d JOIN Stock s ON s.id = d.stock "
			+ "JOIN Product p ON p.id = d.product where p.id=:id")
	List<DetailStockDto> getDetailStockByProduct(@Param("id") int id);
	
	@Transactional
	@Modifying
	@Query(value = "Update detail_stock ds set ds.batchid=:batchid, ds.expire_date=:expire_date, ds.price=:price, "
			+ "ds.qty=:qty, ds.current_qty=:current_qty, ds.balance=:balance, ds.state=:state, ds.batch_from=:batch_from,"
			+ "ds.created_at=:created_at, ds.product_id=:product_id where ds.id=:id", nativeQuery = true)
	void updateDetailStock(@Param("id") int id, @Param("batchid") String batchid, @Param("expire_date") LocalDate expire_date,
						   @Param("price") int price, @Param("qty") int qty, @Param("current_qty") int current_qty, @Param("balance") int balance,
						   @Param("batch_from") String batch_from, @Param("created_at") LocalDate created_at, @Param("product_id") int product_id);
	
	@Query(value = "Select SUM(qty) From detail_stock where product_id =?1", nativeQuery = true)
	int sumQtyForBalance(int product_id);

	@Query(value = "Select * From detail_stock d JOIN stock s ON s.id = d.stock_id "
			+ "JOIN Product p ON p.id = d.product_id where p.id=?1 and d.state = 'IN' "
			+ "ORDER BY d.price desc, d.expire_date asc", nativeQuery = true)
	List<DetailStock> filterBatch(int id);

	@Query(value = "Select * from detail_stock d JOIN stock s ON s.id = d.stock_id JOIN product p "
			+ "ON p.id = d.product_id where d.product_id=:product_id Order By d.id asc Limit 1", nativeQuery =true)
	DetailStock findByProductIdtoGetLastValueOfBalance(@Param("product_id") int product_id);
}