package backend.bookstore;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import backend.bookstore.domain.User;
import backend.bookstore.domain.UserRepository;

@Service 
public class UserDetailsServiceImpl implements UserDetailsService  {
	private final UserRepository repository; 

    public UserDetailsServiceImpl(UserRepository userRepository) {
	this.repository = userRepository;
	}

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User curruser = repository.findByUsername(username);
			if (curruser == null) {
			throw new UsernameNotFoundException(username + " Käyttäjää ei löydy");
			}
		UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPassword(),
		AuthorityUtils.createAuthorityList(curruser.getRole()));
		return user;
    }    
}
