package dev.susana.ciArcoIris.users;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_encodesPasswordAndSavesUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setRole("Directora");
        user.setEmail("test@example.com");
        user.setPhone("689540238");

        when(passwordEncoder.encode("password")).thenReturn("password");
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("testUser", createdUser.getUsername());
        assertEquals("password", createdUser.getPassword());
        assertEquals("Directora", createdUser.getRole());
        assertEquals("test@example.com", createdUser.getEmail());
        assertEquals("689540238", createdUser.getPhone());

        verify(passwordEncoder).encode("password");
        verify(userRepository).save(user);
    }

    @Test
    void listUsers_returnsAllUsers() {
        User user1 = new User();
        User user2 = new User();

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.getUsers();

        assertEquals(2, users.size());
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_returnsUserIfExists() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals(1L, foundUser.get().getId());
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserById_throwsExceptionIfNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUserById(1L);

        assertTrue(foundUser.isEmpty());
        verify(userRepository).findById(1L);
    }

    @Test
    void updateUser_updatesAndSavesUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("oldUsername");
        existingUser.setPassword("oldPassword");
        existingUser.setRole("Profesora");

        User updatedDetails = new User();
        updatedDetails.setUsername("newUsername");
        updatedDetails.setPassword("newPassword");
        updatedDetails.setRole("Directora");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        ResponseEntity<User> response = userService.updateUser(1L, updatedDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("newUsername", response.getBody().getUsername());
        assertEquals("encodedNewPassword", response.getBody().getPassword());
        assertEquals("Directora", response.getBody().getRole());

        User responseBody = response.getBody();
        assertNotNull(responseBody); // Aseguramos que no sea null
        assertEquals(1L, responseBody.getId());

        verify(passwordEncoder).encode("newPassword");
        verify(userRepository).save(existingUser);
        assertNotNull(response);
    }

    @Test
    void updateUser_throwsExceptionIfUserNotFound() {

    when(userRepository.findById(1L)).thenReturn(Optional.empty());

    ResponseEntity<User> response = userService.updateUser(1L, new User());    
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNull(response.getBody());

    }

    @Test
void updateUser_shouldEncodePasswordWhenPasswordIsProvided() {
   
    User existingUser = new User();
    existingUser.setId(1L);
    existingUser.setUsername("oldUsername");
    existingUser.setPassword("oldPassword");
    existingUser.setRole("Profesora");

  
    User updatedDetails = new User();
    updatedDetails.setUsername("newUsername");
    updatedDetails.setPassword("newPassword");
    updatedDetails.setRole("Directora");

   
    when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
    when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
    when(userRepository.save(existingUser)).thenReturn(existingUser);

   
    ResponseEntity<User> updatedUserResponse = userService.updateUser(1L, updatedDetails);

    
    assertEquals("encodedNewPassword", updatedUserResponse.getBody().getPassword());
    verify(passwordEncoder).encode("newPassword");
    verify(userRepository).save(existingUser);
}

@Test
void updateUser_shouldNotUpdatePasswordWhenPasswordIsNotProvided() {
    
    User existingUser = new User();
    existingUser.setId(1L);
    existingUser.setUsername("oldUsername");
    existingUser.setPassword("oldPassword");
    existingUser.setRole("Profesora");

 
    User updatedDetails = new User();
    updatedDetails.setUsername("newUsername");
    updatedDetails.setPassword(""); 
    updatedDetails.setRole("Directora");
   
    when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
    when(userRepository.save(existingUser)).thenReturn(existingUser);

    
    ResponseEntity<User> updatedUserResponse = userService.updateUser(1L, updatedDetails);


    assertEquals("oldPassword", updatedUserResponse.getBody().getPassword());
    verify(passwordEncoder, never()).encode(anyString()); // Verifica que no se haya llamado a encode()
    verify(userRepository).save(existingUser); // Verifica que el repositorio guarda el usuario
}

    @Test
    void deleteUser_deletesIfExists() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_throwsExceptionIfUserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        
        boolean result = userService.deleteUser(1L);
        assertFalse(result);
        verify(userRepository, times(1)).existsById(1L);
    }

    @Test
    void validateUser_returnsUserIfCredentialsAreCorrect() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");
        user.setEmail("testUser@example.com");
        user.setRole("USER");

        
        when(userRepository.findByEmail("testUser@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        Optional<User> validatedUser = userService.validateUser("testUser@example.com", "password");

        assertTrue(validatedUser.isPresent());
        assertEquals(user.getUsername(), validatedUser.get().getUsername());
    }

        @Test
        void validateUser_returnsEmptyIfCredentialsAreIncorrect() {
        
        when(userRepository.findByEmail("unknownUser")).thenReturn(Optional.empty());

        Optional<User> validatedUser = userService.validateUser("unknownUser", "wrongPassword");

        assertTrue(validatedUser.isEmpty());
    }

    @Test
void validateUser_shouldReturnUserWhenPasswordMatches() {
    
    User user = new User();
    user.setEmail("test@example.com");
    user.setPassword("hashedPassword"); 
    user.setUsername("testUser");
    
    when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
    
   
    when(passwordEncoder.matches("password", "hashedPassword")).thenReturn(true);  // Contraseña correcta
 
    Optional<User> validatedUser = userService.validateUser("test@example.com", "password");

   
    assertTrue(validatedUser.isPresent());
    assertEquals("testUser", validatedUser.get().getUsername());

    
    verify(passwordEncoder).matches("password", "hashedPassword");
}

@Test
void validateUser_shouldReturnEmptyWhenPasswordDoesNotMatch() {
  
    User user = new User();
    user.setEmail("test@example.com");
    user.setPassword("hashedPassword"); 
    user.setUsername("testUser");
    
    
    when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
    
    
    when(passwordEncoder.matches("wrongPassword", "hashedPassword")).thenReturn(false);  // Contraseña incorrecta

   
    Optional<User> validatedUser = userService.validateUser("test@example.com", "wrongPassword");

   
    assertTrue(validatedUser.isEmpty());

    
    verify(passwordEncoder).matches("wrongPassword", "hashedPassword");
}

 
}