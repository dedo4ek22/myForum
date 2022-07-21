package com.kostApp.kostApp.config;

import com.kostApp.kostApp.services.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * security configuration
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailService userDetailService;

    /**
     * method for configure login, registration, authorities, logout etc
     *
     * @param httpSecurity
     */
    public void configure(HttpSecurity httpSecurity){
        try {

            httpSecurity.authorizeRequests()
                    .antMatchers("/admin").hasRole("ADMIN")
                    .antMatchers("/authentication/login","/authentication/registration","/error").permitAll()
                    .anyRequest().hasAnyRole("USER","ADMIN")
                    .and()
                    .formLogin()
                    .loginPage("/authentication/login")
                    .loginProcessingUrl("/process_login")
                    .defaultSuccessUrl("/kostApp/welcome", true)
                    .failureUrl("/authentication/login?error")
                    .and()
                    .logout().logoutUrl("/logout").logoutSuccessUrl("/authentication/login");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * config of encode password
     *
     * @param builder
     */
    public void configure(AuthenticationManagerBuilder builder){

        try {
            builder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
