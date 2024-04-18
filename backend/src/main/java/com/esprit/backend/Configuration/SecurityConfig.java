package com.esprit.backend.Configuration;

import io.swagger.models.HttpMethod;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui/index.html#/**",


    };


    @Bean
    public SecurityFilterChain securityFilterchain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .requestMatchers("/api/v1/auth/**", "/reclamation/**", "/user/forgetPassword/**", "/user/resetPassword/**", "/offre/**", "/Assignment/**", "/Journal/**", "/submission/**", "/Agreement/**", "/request/**","/Grille/**").permitAll()


      .requestMatchers("/api/v1/auth/**","/reclamation/**","/user/forgetPassword/**","/user/resetPassword/**","/offre/** ").permitAll()


                .requestMatchers("/api/v1/auth/**","/reclamation/**","/user/**","/offre/** ","/user/forgetPassword/**","/user/resetPassword/**").permitAll()

                .requestMatchers("/api/v1/auth/**","/reclamation/**","/user/forgetPassword/**","/user/resetPassword/**","/offre/** ").permitAll()
                .requestMatchers("/api/v1/auth/**","/reclamation/**","/user/**","/offre/** ","/user/forgetPassword/**","/user/resetPassword/**")
                .permitAll()
                .requestMatchers("/api/v1/auth/**","/reclamation/**","/user/forgetPassword/**","/user/resetPassword/**","/offre/** ")
                .permitAll()
                .requestMatchers("/api/v1/auth/**","/reclamation/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                        .maxAge(3600);
            }
        };
    }

}
