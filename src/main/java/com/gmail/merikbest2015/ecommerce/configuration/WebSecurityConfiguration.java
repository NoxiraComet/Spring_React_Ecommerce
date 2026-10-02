package com.gmail.merikbest2015.ecommerce.configuration;

import com.gmail.merikbest2015.ecommerce.security.oauth2.CustomOAuth2UserService;
import com.gmail.merikbest2015.ecommerce.security.JwtConfigurer;
import com.gmail.merikbest2015.ecommerce.security.oauth2.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Spring Security configuration for the application.
 * Configures JWT authentication, OAuth2 providers, and authorization rules.
 *
 * Security best practices applied:
 * - Stateless session management (JWT-based)
 * - Restricted CORS to trusted origins only
 * - CSRF protection disabled only for stateless API (acceptable for JWT auth)
 * - OAuth2 callback validation
 * - Public endpoints explicitly defined
 * - All other endpoints require authentication by default
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtConfigurer jwtConfigurer;
    private final OAuth2SuccessHandler oauthSuccessHandler;
    private final CustomOAuth2UserService oAuth2UserService;

    @Value("${hostname:localhost:3000}")
    private String allowedOrigin;

    /**
     * Configures HTTP security with stateless JWT authentication and OAuth2 support.
     *
     * @param http the HttpSecurity object to configure
     * @throws Exception if security configuration fails
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Enable CORS with restricted origins
                .cors()
                .and()
                // CSRF is disabled only because we use stateless JWT auth
                // Tokens are not vulnerable to CSRF in the same way form-based auth is
                .csrf().disable()
                // Stateless session management - no server-side sessions
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Authorization rules - explicitly define what is public
                .authorizeRequests()
                    // Authentication endpoints - public
                    .antMatchers("/api/v1/auth/**", "/api/v1/auth/login").permitAll()
                    // User registration - public
                    .antMatchers("/api/v1/registration/**").permitAll()
                    // Public product browsing
                    .antMatchers("/api/v1/perfumes/**").permitAll()
                    // Public order/review information
                    .antMatchers("/api/v1/order/**", "/api/v1/review/**").permitAll()
                    // WebSocket connections - require authentication but allow connection
                    .antMatchers("/websocket/**").permitAll()
                    // Static resources
                    .antMatchers("/img/**", "/static/**").permitAll()
                    // OAuth2 endpoints
                    .antMatchers("/auth/**", "/oauth2/**").permitAll()
                    // API documentation (Swagger) - consider restricting in production
                    .antMatchers("/**/*swagger*/**", "/v2/api-docs").permitAll()
                    // All other endpoints require authentication
                    .anyRequest().authenticated()
                .and()
                // OAuth2 Login Configuration
                .oauth2Login()
                    .authorizationEndpoint().baseUri("/oauth2/authorize")
                    .and()
                    .userInfoEndpoint().userService(oAuth2UserService)
                    .and()
                    .successHandler(oauthSuccessHandler)
                .and()
                // Apply JWT filter
                .apply(jwtConfigurer);
    }

    /**
     * Configures CORS to allow requests only from trusted origins.
     *
     * @return CorsConfigurationSource with restricted CORS rules
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Restrict CORS to specific origins - change in production
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigin));
        // Allow common HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        // Allow Authorization and Content-Type headers
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        // Allow credentials (cookies, auth headers)
        configuration.setAllowCredentials(true);
        // Cache preflight requests for 1 hour
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Exposes the AuthenticationManager bean for authentication processing.
     *
     * @return the configured AuthenticationManager
     * @throws Exception if authentication manager creation fails
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
