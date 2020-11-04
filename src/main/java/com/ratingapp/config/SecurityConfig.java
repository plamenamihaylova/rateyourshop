package com.ratingapp.config;

import com.ratingapp.model.UserRole;
import com.ratingapp.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf().disable()
                .httpBasic(Customizer.withDefaults())
                .authorizeRequests()
                    .antMatchers("/swagger-ui.html").permitAll()
                    .antMatchers("/actuator/**").permitAll()

                    .antMatchers(HttpMethod.GET, "/api/v1/categories").permitAll()

                    .antMatchers(HttpMethod.GET, "/api/v1/shops/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/shops").hasRole(UserRole.ADMIN)
                    .antMatchers(HttpMethod.PUT, "/api/v1/shops/**").hasRole(UserRole.ADMIN)
                    .antMatchers(HttpMethod.DELETE, "/api/v1/shops*").hasRole(UserRole.ADMIN)

                    .antMatchers(HttpMethod.GET, "/api/v1/reviews/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/reviews*").authenticated()
                    .antMatchers(HttpMethod.PUT, "/api/v1/reviews/**").authenticated()
                    .antMatchers(HttpMethod.DELETE, "/api/v1/reviews/**").hasRole(UserRole.ADMIN)

                    .antMatchers("/api/v1/users/**").hasRole(UserRole.ADMIN)

                .and()
                    .formLogin()
                .and()
                    .logout();
    }

    @Bean
    protected UserDetailsService userDetailsService(UserService userService){
        return username -> userService.findByUsername(username);
    }

}