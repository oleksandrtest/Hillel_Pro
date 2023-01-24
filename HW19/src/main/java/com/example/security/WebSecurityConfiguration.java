package com.example.security;

import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    UserService userService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, enable FROM garage.user WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, role FROM garage.user WHERE username=?")
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((request) -> {
                    try {
                        request
                                .requestMatchers("/registration").permitAll()
                                .requestMatchers("/showFormForUpdate/**","/showNewOwnerForm/**",
                                        "/showNewCarForm/**","/deleteOwner/**","/deleteCar","/showFormForUpdate/**",
                                        "/showNewOwnerForm/**", "/saveCar/**","/updateOwner/**","/saveOwner/**")
                                .hasAnyAuthority("OWNER","MANAGER")
                                .anyRequest().authenticated().and()
                                .formLogin(
                                        form -> form
                                                .loginProcessingUrl("/spring_security_check")
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/")
                                                .failureForwardUrl("/login?error=true")
                                                .permitAll()
                                ).logout(
                                        logout -> logout
                                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                                .clearAuthentication(true).deleteCookies()
                                                .permitAll()
                                ).exceptionHandling().accessDeniedPage("/denied");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });


        return http.build();
    }

}
