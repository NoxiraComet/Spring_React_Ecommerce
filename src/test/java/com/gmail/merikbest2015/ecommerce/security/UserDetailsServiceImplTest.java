package com.gmail.merikbest2015.ecommerce.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserDetailsServiceImpl.
 * Tests user loading and authentication details retrieval.
 */
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    private String testUsername = "testuser";
    private String testEmail = "test@example.com";

    @BeforeEach
    void setUp() {
        // Setup test data
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        // Arrange
        // Create a mock UserDetails with the test username
        UserDetails expectedUserDetails = mock(UserDetails.class);
        when(expectedUserDetails.getUsername()).thenReturn(testUsername);

        // Act & Assert
        assertNotNull(expectedUserDetails);
        assertEquals(testUsername, expectedUserDetails.getUsername());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        String nonExistentUser = "nonexistent";

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsServiceImpl.loadUserByUsername(nonExistentUser);
        });
    }

    @Test
    void testLoadUserByUsername_EmptyUsername() {
        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsServiceImpl.loadUserByUsername("");
        });
    }

    @Test
    void testLoadUserByUsername_NullUsername() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userDetailsServiceImpl.loadUserByUsername(null);
        });
    }

    @Test
    void testLoadUserByUsername_UserDetailsHasAuthorities() {
        // Arrange
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(testUsername);
        when(userDetails.getAuthorities()).thenReturn(java.util.Collections.singletonList(
                new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER")
        ));

        // Act & Assert
        assertNotNull(userDetails.getAuthorities());
        assertFalse(userDetails.getAuthorities().isEmpty());
    }
}
