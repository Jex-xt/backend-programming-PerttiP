package backend.huittmp;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import backend.huittmp.domain.Jasen;
import backend.huittmp.domain.JasenRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    private final JasenRepository repository;

    public UserDetailsServiceImpl(JasenRepository repository) {
	    this.repository = repository;
	}

    @Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Jasen curruser = repository.findByEmail(email).orElse(null);
			if (curruser == null) {
			throw new UsernameNotFoundException(email + "Tiliä ei löydy");
		}
            if (curruser.getSalasana() == null) {
            throw new UsernameNotFoundException("Salasana ei ole asetettu");
        }
        String rooli;

        if (curruser.isYllapitaja()) {
            rooli = "ROLE_ADMIN";
        } else {
            rooli = "ROLE_USER";
        }

		UserDetails user = new org.springframework.security.core.userdetails.User(email, curruser.getSalasana(),
		AuthorityUtils.createAuthorityList(rooli));
		return user;
    }    
}
