package com.petra.final_exam_work.security;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomUserDetailsService customUserDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> {} )
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // allow preflight requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        //public
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/logout").permitAll()
                        .requestMatchers("/user/signup-contributor").permitAll()

                        //All loggedIn
                        .requestMatchers("/user/username").authenticated()
                        .requestMatchers("/uploads/**").authenticated()
                        .requestMatchers("/member/albums").authenticated()
                        .requestMatchers("/member/albums/{albumPublicUuid}").authenticated()

                        //role-based
                        .requestMatchers("/contributor/general/info").hasRole("CONTRIBUTOR")
                        .requestMatchers("/contributor/welcome").hasRole("CONTRIBUTOR")
                        .requestMatchers("/contributor/consent").hasRole("CONTRIBUTOR")
                        .requestMatchers("/contributor/upload/photo").hasRole("CONTRIBUTOR")
                        .requestMatchers("contributor/albums/cover-photo").hasRole("CONTRIBUTOR")
                        .requestMatchers("contributor/albums/{albumPublicUuid}").hasRole("CONTRIBUTOR")
                        .requestMatchers("contributor/albums/{albumPublicUuid}/title-description").hasRole("CONTRIBUTOR")
                        .requestMatchers("contributor/albums/{albumPublicUuid}/cover-photo").hasRole("CONTRIBUTOR")
                        .requestMatchers("contributor/albums/{albumPublicUuid}/photos").hasRole("CONTRIBUTOR")
                        .requestMatchers("contributor/albums/{albumPublicUuid}/reorder").hasRole("CONTRIBUTOR")
                        .requestMatchers("contributor/albums/{albumPublicUuid}/status").hasRole("CONTRIBUTOR")
                        .requestMatchers("contributor/albums/{albumPublicUuid}/scheduled").hasRole("CONTRIBUTOR")

                        .requestMatchers("/admin/dashboard").hasRole("ADMIN")
                        .requestMatchers("/admin/consent/{id}").hasRole("ADMIN")
                        .requestMatchers("/admin/consent/{id}/document/{type}").hasRole("ADMIN")
                        .requestMatchers("/admin/consent/{id}/review").hasRole("ADMIN")

                        //everything else
                        .anyRequest().denyAll()
                )

                .authenticationProvider(authenticationProvider())
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }

        //authentication setup

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
            provider.setUserDetailsService(customUserDetailsService);
            provider.setPasswordEncoder(passwordEncoder());
            return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

    //hashing the passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:3000")); //next.js dev
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

}
