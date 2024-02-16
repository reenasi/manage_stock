package nexsoft.rere.management.stock.repository;

import nexsoft.rere.management.stock.dto.StockDto;
import nexsoft.rere.management.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "Insert into stock("
			+ "stockid, description, net_amount, user_id, created_at, deleted) "
			+ "value(?1, ?2, ?3, ?4, ?5, false)", nativeQuery = true)
	void insertStock(String stockid, String description, int net_amount, int user_id, LocalDate created_at);
	
	
	@Query(value = "Select new nexsoft.rere.management.stock.dto.StockDto("
			+ "s.id, s.stockID, s.description, s.netAmount, u.id) "
			+ "From Stock s JOIN User u ON s.user = u.id where s.id=?1")
	StockDto findByID(int id);	
	
	@Query(value = "Select new nexsoft.rere.management.stock.dto.StockDto("
			+ "s.id, s.stockID, s.created_at, s.description, s.netAmount, u.id) "
			+ "From Stock s JOIN User u ON s.user = u.id")
	List<StockDto> getAllStock();
	
	@Query(value = "Select new nexsoft.rere.management.stock.dto.StockDto("
			+ "s.id, s.stockID, s.description, s.netAmount, u.id) "
			+ "From Stock s JOIN User u ON s.user = u.id where u.id=?1")
	List<StockDto> getStockUser(int user_id);	
	
	@Transactional
	@Modifying
	@Query(value = "Update stock s set s.description=:description, s.net_amount=:net_amount where s.id=:idS", nativeQuery = true)
	void updateStock(@Param("idS") int idS, @Param("description") String description, @Param("net_amount") int net_amount);
	
	@Query(value = "SELECT * From stock s where s.user_id=:user_id ORDER BY s.id DESC LIMIT 1 ", nativeQuery = true)
	Stock getLastStockByUser(@Param("user_id") int user_id);
	
	@Transactional
	@Modifying
	@Query(value = "Update stock s set s.net_amount=:net_amount where s.id=:idS", nativeQuery = true)
	void updateNetAmount(@Param("idS") int idS, @Param("net_amount") int net_amount);
	
}