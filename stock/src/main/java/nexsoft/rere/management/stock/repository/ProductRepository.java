package nexsoft.rere.management.stock.repository;

import nexsoft.rere.management.stock.dto.ProductDto;
import nexsoft.rere.management.stock.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Transactional
	@Modifying
	@Query(value = "Insert into product(" + "productid, product_name, description, deleted, user_id, image) "
			+ "value(?1, ?2, ?3, false, ?4, ?5)", nativeQuery = true)
	void insertProduct(String productid, String product_name, String description, int user_id, byte[] image);

	@Query(value = "Select new nexsoft.rere.management.stock.dto.ProductDto("
			+ "p.id, p.productID, p.productName) From Product p")
	List<ProductDto> getAllProduct();

	@Query(value = "Select new nexsoft.rere.management.stock.dto.ProductDto("
			+ "p.id, p.productID, p.productName) From Product p where id=?1")
	ProductDto findById(int id);

	@Query("Select new nexsoft.rere.management.stock.dto.ProductDto(p.id, p.productID, p.productName) "
			+ "From Product p where ((p.productID LIKE %:search% AND p.deleted is false) OR "
			+ "(p.productName LIKE %:search% AND p.deleted is false))")
	List<ProductDto> searchProductByProductIdOrProductName(String search);

	@Query(value = "Select new nexsoft.rere.management.stock.dto.ProductDto(p.id, p.productID, p.productName) "
			+ "From Product p where productid=?1 and p.deleted is false")
	ProductDto findByProductIdisReady(String productid);

	@Transactional
	@Modifying
	@Query(value = "Update product set deleted = ?1 where productid = ?2", nativeQuery = true)
	void deleteProduct(boolean deleted, String productid);

	@Query(value = "Select new nexsoft.rere.management.stock.dto.ProductDto(p.id, p.productID, p.productName) "
			+ "From Product p where user_id=?1")
	List<ProductDto> getProductUser(int user_id);

	@Query(value = "SELECT * From product p where p.user_id=:user_id ORDER BY p.id DESC LIMIT 1 ", nativeQuery = true)
	Product getLastProductByUser(@Param("user_id") int user_id);
}