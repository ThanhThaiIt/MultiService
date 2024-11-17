package com.example.multiservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity // optional
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${jwt.secret.key}")
    protected String secretKey;

    private CustomJwtDecoder jwtDecoder;

    // Identify which endpoints need protection
    private static final String[] PUBLIC_ENDPOINTS = {
        "/users", "/auth/token", "/auth/introspect", "/auth/logout", "/auth/refresh"
    };

    @Bean
    PasswordEncoder passwordEncoder() {
        // 10 is default
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity httpSecurity, JwtAuthenticationConverter getJwtAuthenticationConverter) throws Exception {

        httpSecurity.cors(Customizer.withDefaults());

        httpSecurity.authorizeRequests(authorizeRequests -> authorizeRequests
                .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS)
                .permitAll()
                .anyRequest()
                .authenticated());

        // create provider mannager: support for jwt token
        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer ->
                        jwtConfigurer.decoder(jwtDecoder).jwtAuthenticationConverter(jwtAuthenticationConverter()))
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // Specify allowed origins (frontend URLs)
        corsConfiguration.addAllowedOrigin("http://localhost:3000"); // Add only trusted origins
        corsConfiguration.addAllowedMethod("*"); // Allow all methods (or restrict to POST, GET, etc.)
        corsConfiguration.addAllowedHeader("*"); // Allow all headers (or restrict to specific headers)
        corsConfiguration.setAllowCredentials(true); // Allow cookies or credentials if necessary

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    // change prefix of Role
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    //    @Bean
    //    JwtDecoder jwtdecoder() {
    //        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HS512");
    //
    //
    //        return NimbusJwtDecoder
    //                .withSecretKey(secretKeySpec)
    //                .macAlgorithm(MacAlgorithm.HS512)
    //                .build();
    //    }

}
