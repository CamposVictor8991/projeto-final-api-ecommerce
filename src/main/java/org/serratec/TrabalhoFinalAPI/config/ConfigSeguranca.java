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
                .authorizeHttpRequests(authorize
                        -> authorize
                        // consulta geral
                        .requestMatchers(HttpMethod.GET, "/categorias/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/produtos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/perfilCliente/**").hasAuthority("ADMINISTRADOR")
                        // colaboradores
                        .requestMatchers(HttpMethod.POST, "/categorias/**").hasAnyAuthority("COLABORADOR", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/categorias/**").hasAnyAuthority("COLABORADOR", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/categorias/**").hasAnyAuthority("COLABORADOR", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/produtos/**").hasAnyAuthority("COLABORADOR", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/produtos/**").hasAnyAuthority("COLABORADOR", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/produtos/**").hasAnyAuthority("COLABORADOR", "ADMINISTRADOR")
                        // clientes
                        .requestMatchers(HttpMethod.POST, "/clientes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/clientes/{id}").hasAnyAuthority("CLIENTE", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/clientes/**").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/clientes/**").hasAnyAuthority("CLIENTE", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/clientes/**").hasAnyAuthority("CLIENTE", "ADMINISTRADOR")
                        // administrador
                        .requestMatchers(HttpMethod.POST, "/perfilCliente/**").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/perfilCliente/**").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/perfilCliente/**").hasAuthority("ADMINISTRADOR")
                        // favorito
                        .requestMatchers(HttpMethod.POST, "/favoritos/**").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/favoritos/**").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/favoritos/**").hasAuthority("CLIENTE")
                        .requestMatchers("/h2-console/**").permitAll() // Permite acesso ao console H2
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers
                .frameOptions().disable()) // Desabilita o bloqueio de frames para o H2
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
