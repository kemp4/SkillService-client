package pl.kemp.services;

import java.nio.charset.Charset;
import java.util.*;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class MyAuthProviderE extends AbstractUserDetailsAuthenticationProvider {

    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";
    private PasswordEncoder passwordEncoder;
    private volatile String userNotFoundEncodedPassword;
    private UserDetailsService userDetailsService;

    private static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }
    private static String getApiURI(){
        HttpSession session = session();
        String result = (String) session.getAttribute("apiURI");
        return result;
    }

    public MyAuthProviderE() {
        this.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }

    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            this.logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            String presentedPassword = authentication.getCredentials().toString();
            if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
                this.logger.debug("Authentication failed: password does not match stored value");
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }
    }

    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }

    protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String password = authentication.getCredentials().toString();
        UserDetails loadedUser = null;
        try {
            String authBase64 = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    authBase64.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization",authHeader);
            headers.add("Accept","*/*");
            HttpEntity<String> entity = new HttpEntity<>( headers);
            ResponseEntity authenticationResponse=restTemplate.exchange(getApiURI()+"skills", HttpMethod.GET, entity,Object.class);
            Set<String> privilegesFromRest = new HashSet<>();
            String[] authoritiesAsArray =
                    privilegesFromRest.toArray(new String[privilegesFromRest.size()]);
            List<GrantedAuthority> authorities =
                    AuthorityUtils.createAuthorityList(authoritiesAsArray);
            loadedUser = new User(username, password,new LinkedList<>());
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
            } else {
                return loadedUser;
            }
        } catch (UsernameNotFoundException var4) {
            this.mitigateAgainstTimingAttack(authentication);
            throw new UsernameNotFoundException("Bad password or username");
        } catch (HttpClientErrorException httpException) {
            if(httpException.getStatusCode()== HttpStatus.UNAUTHORIZED)
                return new User("wrongUsername", "wrongPass",new LinkedList<>());
            throw httpException;
        } catch (InternalAuthenticationServiceException var5) {
            throw var5;
        }catch (Exception var6) {
            throw new InternalAuthenticationServiceException(var6.getMessage(), var6);
        }
    }
    private void mitigateAgainstTimingAttack(UsernamePasswordAuthenticationToken authentication) {
        if (authentication.getCredentials() != null) {
            String presentedPassword = authentication.getCredentials().toString();
            this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
        }

    }
//
//    private void prepareTimingAttackProtection() {
//        if (this.userNotFoundEncodedPassword == null) {
//            this.userNotFoundEncodedPassword = this.passwordEncoder.encode("userNotFoundPassword");
//        }
//
//    }
//
//    private void mitigateAgainstTimingAttack(UsernamePasswordAuthenticationToken authentication) {
//        if (authentication.getCredentials() != null) {
//            String presentedPassword = authentication.getCredentials().toString();
//            this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
//        }
//
//    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
        this.passwordEncoder = passwordEncoder;
        this.userNotFoundEncodedPassword = null;
    }
//
//    protected PasswordEncoder getPasswordEncoder() {
//        return this.passwordEncoder;
//    }
//
//    public void setUserDetailsService(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    protected UserDetailsService getUserDetailsService() {
//        return this.userDetailsService;
//    }
}
