package de.bigzee.siegertipp;

import de.bigzee.siegertipp.config.WebAppConfigurationAware;
import org.junit.After;
import org.junit.Before;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

/**
 * Created by lzimmerm on 20.06.2014.
 */
public abstract class BaseControllerTest extends WebAppConfigurationAware {

    protected void loginAdmin() {
        logout();
        SecurityContextHolder.getContext().setAuthentication(createAdmin());
    }

    protected void loginUser() {
        logout();
        SecurityContextHolder.getContext().setAuthentication(createUser());
    }

    protected void logout() {
        SecurityContextHolder.clearContext();
    }

    public Authentication currentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private Authentication createUser() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        User user = new User("noman@nomail.com", "empty", Collections.singleton(grantedAuthority));
        return new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(grantedAuthority));
    }

    private Authentication createAdmin() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        User user = new User("noman@nomail.com", "empty", Collections.singleton(grantedAuthority));
        return new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(grantedAuthority));
    }
}