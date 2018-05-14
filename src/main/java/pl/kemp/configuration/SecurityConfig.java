package pl.kemp.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import pl.kemp.interceptor.SecurityHeadersInterceptor;
import pl.kemp.services.MyAuthProviderE;

import java.util.Collections;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        MyAuthProviderE myAuthProvider = new MyAuthProviderE();
        myAuthProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
         auth.authenticationProvider(myAuthProvider).eraseCredentials(false);
    }

    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers(
                        "/css/**",
                        "/imgs/**",
                        "/changeApi",
                        "/js/**",
                        "/signup").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()

                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");;
    }
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new SecurityHeadersInterceptor()));
        return restTemplate;
    }
}