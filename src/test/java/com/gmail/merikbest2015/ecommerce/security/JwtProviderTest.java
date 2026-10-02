package com.gmail.merikbest2015.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for JwtProvider security component.
 * Tests JWT token generation, validation, and authentication extraction.
 */
@ExtendWith(MockitoExtension.class)
class JwtProviderTest {

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private JwtProvider jwtProvider;

    private String testSecret;
    private long testExpiration;
    private String testUsername = "testuser";
    private String testRole = "ROLE_USER";

    @BeforeEach
    void setUp() {
        testSecret = "test-secret-key-for-jwt-testing-purposes-only";
        testExpiration = 604800; // 7 days in seconds
        
        ReflectionTestUtils.setField(jwtProvider, "authorizationHeader", "Authorization");
        ReflectionTestUtils.setField(jwtProvider, "validityInMilliseconds", testExpiration);
    }

    @Test
    void testCreateToken_Success() {
        // Act
        String token = jwtProvider.createToken(testUsername, testRole);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.split("\\.").length == 3); // JWT has 3 parts separated by dots
    }

    @Test
    void testCreateToken_ContainsUsername() {
        // Act
        String token = jwtProvider.createToken(testUsername, testRole);

        // Assert
        assertNotNull(token);
        String[] parts = token.split("\\.");
        String payloadJson = new String(java.util.Base64.getDecoder().decode(parts[1]));
        assertTrue(payloadJson.contains(testUsername));
    }

    @Test
    void testCreateToken_ContainsRole() {
        // Act
        String token = jwtProvider.createToken(testUsername, testRole);

        // Assert
        assertNotNull(token);
        String[] parts = token.split("\\.");
        String payloadJson = new String(java.util.Base64.getDecoder().decode(parts[1]));
        assertTrue(payloadJson.contains(testRole));
    }

    @Test
    void testValidateToken_ValidToken() {
        // Arrange
        String token = jwtProvider.createToken(testUsername, testRole);

        // Act & Assert
        assertDoesNotThrow(() -> jwtProvider.validateToken(token));
    }

    @Test
    void testValidateToken_ExpiredToken() {
        // Arrange
        ReflectionTestUtils.setField(jwtProvider, "validityInMilliseconds", -1L);
        String expiredToken = jwtProvider.createToken(testUsername, testRole);

        // Act & Assert
        assertThrows(JwtAuthenticationException.class, () -> jwtProvider.validateToken(expiredToken));
    }

    @Test
    void testValidateToken_InvalidToken() {
        // Arrange
        String invalidToken = "invalid.token.here";

        // Act & Assert
        assertThrows(JwtAuthenticationException.class, () -> jwtProvider.validateToken(invalidToken));
    }

    @Test
    void testGetAuthentication_Success() {
        // Arrange
        String token = jwtProvider.createToken(testUsername, testRole);
        UserDetails userDetails = new User(testUsername, "password", java.util.Collections.emptyList());
        when(userDetailsService.loadUserByUsername(testUsername)).thenReturn(userDetails);

        // Act
        Authentication authentication = jwtProvider.getAuthentication(token);

        // Assert
        assertNotNull(authentication);
        assertEquals(testUsername, authentication.getName());
        verify(userDetailsService, times(1)).loadUserByUsername(testUsername);
    }

    @Test
    void testGetUsername_Success() {
        // Arrange
        String token = jwtProvider.createToken(testUsername, testRole);

        // Act
        String username = jwtProvider.getUsername(token);

        // Assert
        assertEquals(testUsername, username);
    }

    @Test
    void testResolveToken_WithValidHeader() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        String bearerToken = "Bearer eyJhbGciOiJIUzI1NiJ9.test.token";
        when(request.getHeader("Authorization")).thenReturn(bearerToken);

        // Act
        String token = jwtProvider.resolveToken(request);

        // Assert
        assertEquals(bearerToken, token);
    }

    @Test
    void testResolveToken_WithNoHeader() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        String token = jwtProvider.resolveToken(request);

        // Assert
        assertNull(token);
    }

    @Test
    void testTokenExpiration() {
        // Arrange
        ReflectionTestUtils.setField(jwtProvider, "validityInMilliseconds", 1L); // 1 millisecond
        String token = jwtProvider.createToken(testUsername, testRole);

        // Act
        try {
            Thread.sleep(100); // Wait for token to expire
            jwtProvider.validateToken(token);
            fail("Token should be expired");
        } catch (JwtAuthenticationException e) {
            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, e.getHttpStatus());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
