package backend.huittmp;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import backend.huittmp.domain.Jasen;
import backend.huittmp.domain.JasenRepository;

//UserDetailsServiceImpl-luokka hakee käyttäjän kirjautumistiedot ja määrittää käyttöoikeusroolin.

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    private final JasenRepository repository;

    public UserDetailsServiceImpl(JasenRepository repository) {
	    this.repository = repository;
	}

    //Hakee käyttäjän tietokannasta sähköpostiosoitteen perusteella.
    @Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Jasen curruser = repository.findByEmail(email).orElse(null);
        //Tarkistetaan löytyy syötetyllä emaililla käyttäjä.
			if (curruser == null) {
			throw new UsernameNotFoundException(email + "Tiliä ei löydy");
		}
        //Estää kirjautumisen jos salasanaa ei ole asetettu.
            if (curruser.getSalasana() == null) {
            throw new UsernameNotFoundException("Salasana ei ole asetettu");
        }

        //Määrittää käyttäjän käyttöoikeusroolin Spring securityä varten.
        //Roolia käyteetään ohjaukseen AdminControllerin /aloitus-metodissa.
        String rooli;
        if (curruser.isYllapitaja()) { 
            rooli = "ROLE_ADMIN";
        } else {
            rooli = "ROLE_USER";
        }

        // Luodaan Spring securityn käyttäjäolio emailista, salasanasta ja käyttöoikeusroolista.
		UserDetails user = new org.springframework.security.core.userdetails.User(email, curruser.getSalasana(),
		AuthorityUtils.createAuthorityList(rooli));
		return user;
    }    
}
