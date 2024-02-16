package nexsoft.rere.management.stock.service;

import nexsoft.rere.management.stock.config.JwtUtil;
import nexsoft.rere.management.stock.config.WebSecurityConfig;
import nexsoft.rere.management.stock.dto.UserDto;
import nexsoft.rere.management.stock.entity.User;
import nexsoft.rere.management.stock.repository.UserRepository;
import nexsoft.rere.management.stock.response.ResponseList;
import nexsoft.rere.management.stock.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {
	private final JwtUtil jwtTokenUtil;
	private final MyUserDetailService userDetailsService;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private WebSecurityConfig passwordEncoder;

	public UserService(JwtUtil jwtTokenUtil, MyUserDetailService userDetailsService) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetailsService = userDetailsService;
	}

	public ResponseEntity<ResponseObject> login(@RequestBody User user) throws Exception {
		User u = userRepo.findByEmail(user.getEmail());
		try {
			
			if (u == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(404, "Data Not Found"));
			}

			UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
			final String jwt = jwtTokenUtil.generateToken(userDetails);
			
			UserDto UserDto = new UserDto(u.getId(), u.getName(), u.getEmail(), jwt);

			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject(HttpStatus.OK.value(), "Login Success", UserDto));

		} catch (Exception e) {
			throw new Exception("Incorrect username or password", e);
		}
	}

	public ResponseEntity<ResponseObject> save(User user) {
		boolean regexEmail = Pattern.compile("[A-Za-z0-9]+[@]+[a-z]+[.]+[c][o][m]").matcher(user.getEmail()).find();
		boolean regexPassword = Pattern
				.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
				.matcher(user.getPassword()).find();

		if (regexEmail && regexPassword) {
			
			if(userRepo.findByEmail(user.getEmail()) != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(400, "Register Failed, email already exist"));
			}
			
			user.setPassword(passwordEncoder.passwordEncoder().encode(user.getPassword()));
			
			userRepo.insertUser(user.getName(), user.getEmail(), user.getPassword());;

			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject(HttpStatus.OK.value(), "Register Success", user));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(400, "Register Failed"));
	}
	
	public ResponseEntity<ResponseList> getAllUser() {
		List<UserDto> result = userRepo.getAll();
		if (result.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseList(404, "Data Not Found"));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseList(200, "Data Found", result));
	}
	
	public ResponseEntity<ResponseObject> updateUser(int id, User payload) {
		UserDto userId = userRepo.findByID(id);

		if (userId == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(404, "User Not Found"));
		}
		
		User u = new User();

		if (payload.getName() != null) {	
			u.setName(payload.getName());
		} else {
			u.setName(userId.getName());
		}

		if (payload.getEmail() != null) {
			u.setEmail(payload.getEmail());
		} else {
			u.setEmail(userId.getEmail());
		}

		if (payload.getPassword() != null) {
			u.setPassword(passwordEncoder.passwordEncoder().encode(payload.getPassword()));
		} else {
			u.setPassword(userId.getPassword());
		}

		userRepo.updateUser(id, u.getName(), u.getEmail(), u.getPassword());
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "Update User Success"));
	}

	public ResponseEntity<ResponseObject> findById(int id) {
		UserDto userId = userRepo.findByID(id);

		if (userId == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(404, "Data Not Found"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, "Data Found", userId));
	}
	
	public ResponseEntity<ResponseObject> findByUsername(String name) {
		UserDto userName = userRepo.findByUsername(name);

		if (userName == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(404, "Data Not Found"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, "Data Found", userName));
	}
	
	public ResponseEntity<ResponseObject> findByEmail(String email) {
		UserDto emailUser = userRepo.findByEmailDto(email);

		if (emailUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(404, "Data Not Found"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, "Data Found", emailUser));
	}
}