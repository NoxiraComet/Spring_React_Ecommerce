# Test Suite Documentation

## Overview
This test suite provides comprehensive coverage for the Spring React E-Commerce application, including unit tests and integration tests.

## Test Files

### Unit Tests

#### 1. JwtProviderTest
Tests JWT token generation, validation, and authentication extraction.
- `testCreateToken_Success` - Verifies token creation with valid input
- `testCreateToken_ContainsUsername` - Validates token payload contains username
- `testCreateToken_ContainsRole` - Validates token payload contains role
- `testValidateToken_ValidToken` - Tests token validation with valid token
- `testValidateToken_ExpiredToken` - Tests expired token rejection
- `testValidateToken_InvalidToken` - Tests invalid token rejection
- `testGetAuthentication_Success` - Tests authentication extraction from token
- `testGetUsername_Success` - Tests username extraction from token
- `testResolveToken_WithValidHeader` - Tests token resolution from HTTP header
- `testResolveToken_WithNoHeader` - Tests handling of missing auth header
- `testTokenExpiration` - Tests token expiration behavior

#### 2. UserDetailsServiceImplTest
Tests user loading and authentication details retrieval.
- `testLoadUserByUsername_UserExists` - Validates user loading for existing user
- `testLoadUserByUsername_UserNotFound` - Tests exception for non-existent user
- `testLoadUserByUsername_EmptyUsername` - Tests handling of empty username
- `testLoadUserByUsername_NullUsername` - Tests handling of null username
- `testLoadUserByUsername_UserDetailsHasAuthorities` - Validates user authorities loading

### Integration Tests

#### 1. AuthenticationControllerTest
Tests login, logout, and JWT authentication flow.
- `testLogin_Success` - Validates successful login and token generation
- `testLogin_InvalidCredentials` - Tests rejection of invalid credentials
- `testLogin_EmptyUsername` - Tests validation of empty username
- `testLogin_EmptyPassword` - Tests validation of empty password
- `testLogout_Success` - Validates logout functionality
- `testLogout_NoAuth` - Tests logout without authentication
- `testGetAuthInfo_WithValidToken` - Tests auth info retrieval with valid token
- `testGetAuthInfo_NoToken` - Tests rejection without auth token

#### 2. PerfumeControllerTest
Tests product listing, searching, filtering, and retrieval.
- `testGetAllPerfumes_Success` - Validates product list retrieval
- `testGetAllPerfumes_Pagination` - Tests pagination of products
- `testGetPerfumeById_Success` - Validates single product retrieval
- `testGetPerfumeById_NotFound` - Tests 404 for non-existent product
- `testSearchPerfumes_ByBrand` - Tests search by brand
- `testSearchPerfumes_ByPerfumer` - Tests search by perfumer
- `testFilterPerfumes_ByPrice` - Tests price range filtering
- `testGetPerfumesByFilter_Sort` - Tests sorting by price
- `testSavePerfume_AsAdmin` - Tests admin product creation
- `testSavePerfume_NoAuth` - Tests unauthorized product creation

#### 3. UserControllerTest
Tests user profile, cart management, and account settings.
- `testGetUserInfo_Success` - Validates user info retrieval
- `testGetUserInfo_NoAuth` - Tests unauthorized access
- `testUpdateUserProfile_Success` - Tests profile update
- `testGetUserCart_Success` - Validates cart retrieval
- `testAddToCart_Success` - Tests adding item to cart
- `testRemoveFromCart_Success` - Tests removing item from cart
- `testClearCart_Success` - Tests cart clearing
- `testChangePassword_Success` - Tests password change
- `testChangePassword_WrongOldPassword` - Tests password validation
- `testGetUserOrders_Success` - Tests order history retrieval

#### 4. OrderControllerTest
Tests order creation, retrieval, and status updates.
- `testCreateOrder_Success` - Validates order creation
- `testCreateOrder_EmptyCart` - Tests order with empty cart
- `testCreateOrder_NoAuth` - Tests unauthorized order creation
- `testGetOrderById_Success` - Validates order retrieval by ID
- `testGetOrderById_NotFound` - Tests 404 for non-existent order
- `testGetAllUserOrders_Success` - Tests user orders retrieval
- `testGetAllOrders_AsAdmin` - Tests admin all orders retrieval
- `testGetAllOrders_UserForbidden` - Tests authorization check
- `testUpdateOrderStatus_Success` - Tests status update by admin
- `testUpdateOrderStatus_UserForbidden` - Tests authorization for status update

#### 5. AdminControllerTest
Tests admin operations with authorization checks.
- `testGetAllUsers_AsAdmin` - Validates users list for admin
- `testGetAllUsers_UserForbidden` - Tests authorization for users list
- `testGetAllUsers_NoAuth` - Tests unauthenticated access
- `testGetUserById_AsAdmin` - Tests user retrieval by admin
- `testDeleteUser_AsAdmin` - Tests user deletion by admin
- `testDeleteUser_UserForbidden` - Tests authorization for deletion
- `testUpdateUserRole_AsAdmin` - Tests role assignment by admin
- `testGetAllOrders_AsAdmin` - Tests orders list for admin
- `testGetAllPerfumes_AsAdmin` - Tests perfumes list for admin
- `testDeletePerfume_AsAdmin` - Tests product deletion by admin
- `testGetDashboardStatistics_AsAdmin` - Tests statistics retrieval

#### 6. RegistrationControllerTest
Tests user registration flow with validation.
- `testRegister_Success` - Validates successful registration
- `testRegister_EmptyFirstName` - Tests first name validation
- `testRegister_EmptyLastName` - Tests last name validation
- `testRegister_InvalidEmail` - Tests email validation
- `testRegister_EmptyPassword` - Tests password requirement
- `testRegister_WeakPassword` - Tests password strength
- `testRegister_DuplicateEmail` - Tests duplicate email handling
- `testActivateAccount_ValidCode` - Tests account activation
- `testActivateAccount_InvalidCode` - Tests invalid activation code
- `testResendActivationEmail_Success` - Tests resend functionality
- `testResendActivationEmail_UserNotFound` - Tests user not found handling

## Running the Tests

### Run all tests
```bash
mvn test
```

### Run specific test class
```bash
mvn test -Dtest=JwtProviderTest
```

### Run with coverage report
```bash
mvn test jacoco:report
```

### Run integration tests only
```bash
mvn test -Dgroups=integration
```

## Test Coverage Goals

- **Unit Tests**: Target 80%+ coverage for security and utility classes
- **Integration Tests**: Target 70%+ coverage for API endpoints
- **Overall**: Target 75%+ code coverage

## Dependencies

- JUnit 5 (Jupiter)
- Mockito
- Spring Boot Test
- Spring Security Test
- MockMvc

## Best Practices

1. Each test should test one specific behavior
2. Use meaningful test names that describe what is being tested
3. Follow AAA pattern: Arrange, Act, Assert
4. Use @WithMockUser for security-related tests
5. Mock external dependencies
6. Keep tests fast and independent
7. Use appropriate assertions (isA, exists, contains, etc.)

## CI/CD Integration

These tests are configured to run automatically in CI/CD pipelines:
- On every pull request
- Before merge to main/master
- In release builds
- With code coverage reports

## Known Issues

None currently documented.

## Future Enhancements

- Add performance tests for large datasets
- Add E2E tests for critical user flows
- Add security scanning in test pipeline
- Add mutation testing for deeper coverage analysis
