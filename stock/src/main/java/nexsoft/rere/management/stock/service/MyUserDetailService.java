package nexsoft.rere.management.stock.service;


import nexsoft.rere.management.stock.entity.MyUserDetails;
import nexsoft.rere.management.stock.entity.User;
import nexsoft.rere.management.stock.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = Optional.ofNullable(userRepo.findByEmail(email));
		return user.map(MyUserDetails::new).get();
	}
}