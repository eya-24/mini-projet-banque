package com.ds.banque.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Configurations {

    // Configuration des autorisations
    private static final String[] PUBLIC_RESOURCES = {
        "/login", 
        "/css/**", 
        "/js/**", 
        "/webjars/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Désactivation CSRF pour API REST (à adapter selon vos besoins)
            .csrf(AbstractHttpConfigurer::disable)
            
            // Configuration des autorisations
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(PUBLIC_RESOURCES).permitAll()
                .anyRequest().authenticated()
            )
            
            // Configuration du formulaire de login
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/comptes")
                .failureUrl("/login?error")
                .permitAll()
            )
            
            // Configuration du logout
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails admin = User.builder()
            .username("TakwaEya")
            .password(passwordEncoder().encode("MYPassword"))
            .roles("TakwaEya", "USER")
            .build();

        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder().encode("userPass"))
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}