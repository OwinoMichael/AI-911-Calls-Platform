package com.callsprediction.demo.security;

import com.callsprediction.demo.service.MyAppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Collection;

@Configuration
public class SecurityConfig {

    private final MyAppUserDetailsService appUserService;

    public SecurityConfig(MyAppUserDetailsService appUserService) {
        this.appUserService = appUserService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (request, response, authentication) -> {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            String redirectUrl = null;

            for (GrantedAuthority auth : authorities) {
                switch (auth.getAuthority()) {
                    case "ROLE_ADMIN":
                        redirectUrl = "/admin/dashboard";
                        break;
                    case "ROLE_DEVELOPER":
                        redirectUrl = "/developer/dashboard";
                        break;
                    case "ROLE_GENERAL":
                        redirectUrl = "/general/dashboard";
                        break;
                }
            }

            if (redirectUrl == null) {
                response.sendRedirect("/access-denied");
            } else {
                response.sendRedirect(redirectUrl);
            }
        };
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> {
                    form.loginPage("/login").permitAll();
                    form.successHandler(customSuccessHandler());
                })
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/", "/login", "/signup", "/css/**", "/js/**").permitAll()
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .requestMatchers("/developer/**").hasRole("DEVELOPER")
                            .requestMatchers("/general/**").hasRole("GENERAL")
                            .anyRequest().authenticated();
                })
                .build();
    }
}