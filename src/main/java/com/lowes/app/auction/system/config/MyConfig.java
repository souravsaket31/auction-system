package com.lowes.app.auction.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
class MyConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails auctioneer = User.builder().
                username("auctioneer")
                .password(passwordEncoder().encode("password")).roles("AUCTIONEER").
                build();
        UserDetails participants = User.builder().
                username("participant")
                .password(passwordEncoder().encode("password")).roles("PARTICIPANT").
                build();
        UserDetails admin = User.builder().
                username("admin")
                .password(passwordEncoder().encode("admin")).roles("ADMIN").
                build();

        return new InMemoryUserDetailsManager(auctioneer, participants, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
