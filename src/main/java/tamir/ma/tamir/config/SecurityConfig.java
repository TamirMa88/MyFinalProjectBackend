package tamir.ma.tamir.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import tamir.ma.tamir.security.JwtAuthenticationFilter;
import tamir.ma.tamir.security.JwtUtilities;

import java.io.IOException;
import java.util.Arrays;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtAuthenticationFilter filter;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    @SuppressWarnings("removal")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors().configurationSource(corsConfiguration())
                .and()
                .csrf()
                .disable()
                .addFilterBefore(filter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(
                        "/api/v1/user/me/**")
                        .hasRole("USER")
                        .requestMatchers(
                        "/api/v1/shop/create/**",
                        "/api/v1/shop/update/**",
                        "/api/v1/shop/delete/**")
                        .hasRole("ADMIN")
                        .requestMatchers("/api/v1/user/**")
                        .permitAll()
                        .requestMatchers(
                                "/api/v1/shop/create-order/**",
                                "/api/v1/shop/orders/**")
                        .hasRole("USER")
                        .requestMatchers("/api/v1/shop")
                         .permitAll()
                        .requestMatchers("/error/**")
                        .permitAll())
                .build();
    }


    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
