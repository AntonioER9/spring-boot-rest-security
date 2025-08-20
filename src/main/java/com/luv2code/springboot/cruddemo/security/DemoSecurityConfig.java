package com.luv2code.springboot.cruddemo.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig {

        // add support for JDBC
        @Bean
        public UserDetailsManager userDetailsManager(DataSource dataSource) {
                return new JdbcUserDetailsManager(dataSource);
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests(authz -> authz
                                .requestMatchers("/api/employees/**").hasRole("EMPLOYEE")
                                // .requestMatchers(HttpMethod.PATCH, "/api/employees/**").hasRole("EMPLOYEE")
                                .requestMatchers("/api/managers/**").hasRole("MANAGER")
                                .requestMatchers("/api/admins/**").hasRole("ADMIN"));

                // use HTTP basic authentication
                http.httpBasic(Customizer.withDefaults());

                // disable CSRF protection
                http.csrf(csrf -> csrf.disable());

                return http.build();
        }

        // @Bean
        // public InMemoryUserDetailsManager userDetailsManager() {
        // UserDetails john = User.builder()
        // .username("john")
        // .password("{noop}test123")
        // .roles("EMPLOYEE")
        // .build();

        // UserDetails mary = User.builder()
        // .username("mary")
        // .password("{noop}test123")
        // .roles("EMPLOYEE", "MANAGER")
        // .build();

        // UserDetails susan = User.builder()
        // .username("susan")
        // .password("{noop}test123")
        // .roles("EMPLOYEE", "MANAGER", "ADMIN")
        // .build();

        // return new InMemoryUserDetailsManager(john, mary, susan);
        // }

}
