package org.serratec.TrabalhoFinalAPI.config;

import java.util.Arrays;

import org.serratec.TrabalhoFinalAPI.security.JwtAuthenticationFilter;
import org.serratec.TrabalhoFinalAPI.security.JwtAuthorizationFilter;
import org.serratec.TrabalhoFinalAPI.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class ConfigSeguranca {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors((cors) -> cors.configurationSource(corsConfigurationsource()))
            .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.GET, "/clientes/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/clientes/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/clientes/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/clientes/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/categorias/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/categorias/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/categorias/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/categorias/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/produtos/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/produtos/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/produtos/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/produtos/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll() //ṕermitir acesso ao h2 console
                .anyRequest().authenticated()
            )
            //permitir exibição do h2 console no browser
            .headers(headers -> headers
                .frameOptions().disable() // Allow frames for H2 console
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        JwtAuthenticationFilter jwtAuthenticationFilter
            = new JwtAuthenticationFilter(authenticationManager(
            http.getSharedObject(AuthenticationConfiguration.class)),
            jwtUtil);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        JwtAuthorizationFilter jwtAuthorizationFilter
            = new JwtAuthorizationFilter(authenticationManager(
            http.getSharedObject(AuthenticationConfiguration.class)),
            jwtUtil,
            userDetailsService);

        http.addFilter(jwtAuthenticationFilter);
        http.addFilter(jwtAuthorizationFilter);

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationsource() {
        CorsConfiguration corsConfiguraion = new CorsConfiguration();
        corsConfiguraion.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        corsConfiguraion.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguraion.applyPermitDefaultValues());
        return source;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
