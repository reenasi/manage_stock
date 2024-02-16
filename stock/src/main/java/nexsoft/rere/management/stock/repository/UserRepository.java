package nexsoft.rere.management.stock.repository;

import nexsoft.rere.management.stock.dto.UserDto;
import nexsoft.rere.management.stock.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "Insert into user("
			+ "name, email, password, role, deleted) value(?1, ?2, ?3, 'ROLE_ADMIN', false)", nativeQuery = true)
	void insertUser(String name, String email, String password);
	
	@Query(value = "Select new nexsoft.rere.management.stock.dto.UserDto(id, name, email) From User where email=?1")
	UserDto findByEmailDto(String email);
	
	@Query(value = "Select * From User where email=?1", nativeQuery = true)
	User findByEmail(String email);
	
	@Query(value = "Select new nexsoft.rere.management.stock.dto.UserDto(id, name, email) From User where name=?1")
	UserDto findByUsername(String username);
	
	@Query("Select new nexsoft.rere.management.stock.dto.UserDto(u.name, u.email, u.password) From User u where u.id=?1")
	UserDto findByID(int id);	
	
	@Query(value = "Select new nexsoft.rere.management.stock.dto.UserDto(id, name, email) From User")
	List<UserDto> getAll();	
 
	@Transactional
	@Modifying
	@Query(value = "Update User u set u.name=:name, u.email=:email, u.password=:password where u.id=:id", nativeQuery = true)
	void updateUser(@Param("id") int id, @Param("name") String name, @Param("email") String email,  @Param("password") String password);
}