package de.bigzee.siegertipp.account;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserService implements UserDetailsService {

    @Inject
    private PasswordEncoder passwordEncoder;

	@Autowired
	private AccountRepository accountRepository;
	
	@PostConstruct	
	protected void initialize() {
		accountRepository.save(new Account("user", passwordEncoder.encode("demo"), "ROLE_USER"));
		accountRepository.save(new Account("admin", passwordEncoder.encode("admin"), "ROLE_ADMIN"));
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(username);
		if(account == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return createUser(account);
	}

    public Account findByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username);
    }

    public void signin(Account account) {
		SecurityContextHolder.getContext().setAuthentication(authenticate(account));
	}

    public Account current() {
        return accountRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }
	
	private Authentication authenticate(Account account) {
		return new UsernamePasswordAuthenticationToken(createUser(account), null, Collections.singleton(createAuthority(account)));		
	}
	
	private User createUser(Account account) {
		return new User(account.getEmail(), account.getPassword(), Collections.singleton(createAuthority(account)));
	}

	private GrantedAuthority createAuthority(Account account) {
		return new SimpleGrantedAuthority(account.getRole());
	}

    public Account save(Account account) {
        return accountRepository.save(account);
    }
}
