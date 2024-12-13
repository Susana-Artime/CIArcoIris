package dev.susana.ciArcoIris.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "Teresa_Garcia","password", "teresagarcia@example.com","658450329","Profesora");
    }

    @Test
    void createUser_shouldReturnCreatedUser() {

        when(userService.createUser(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @SuppressWarnings("null")
    @Test
    void listUsers_shouldReturnListOfUsers() {
    
        List<User> users = Arrays.asList(user, new User(2L, "Paloma_Sanchez","password", "palomasanchez@example.com","695043569","Profesora"));
        when(userService.getUsers()).thenReturn(users);


        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() {
        User updatedUser = new User(1L, "Paloma_Sanchez","password", "palomasanchez@example.com","695043569","Profesora");
        ResponseEntity<User> mockResponse = ResponseEntity.ok(updatedUser);

        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(mockResponse);

        ResponseEntity<User> response = userController.updateUser(1L, updatedUser);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
    }

    @Test
    void getUserById_shouldReturnUserWhenUserExists() {
        
        User user = new User(1L, "Concha_Lopez", "ciarcoiris#direc1", "conchalopez@ciarcoiris.com", "695043569", "Directora");

        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }


    @Test
    void getUserById_shouldReturnNotFoundWhenUserDoesNotExist() {
    
        when(userService.getUserById(999L)).thenReturn(Optional.empty());
        
        ResponseEntity<User> response = userController.getUserById(999L);
    
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    void deleteUser_shouldReturnNotFoundWhenUserDoesNotExist() {
       
        when(userService.deleteUser(999L)).thenReturn(false);
    
        
        ResponseEntity<Void> response = userController.deleteUser(999L);
    
      
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void deleteUser_shouldReturnNoContentWhenUserIsDeleted() {

        when(userService.deleteUser(1L)).thenReturn(true);
       
        ResponseEntity<Void> response = userController.deleteUser(1L);    
        
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
      
        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void validateUser_shouldReturnUserWhenCredentialsAreCorrect() {
   
    User user = new User(1L, "Concha_Lopez", "ciarcoiris#direc1", "conchalopez@ciarcoiris.com", "695043569", "Directora");
    
    when(userService.validateUser("conchalopez@ciarcoiris.com", "ciarcoiris#direc1")).thenReturn(Optional.of(user));
    
    Optional<User> result = userController.validateUser("conchalopez@ciarcoiris.com", "ciarcoiris#direc1");

    assertTrue(result.isPresent());
    assertEquals(user, result.get());

    }

    @Test
    void validateUser_shouldReturnEmptyWhenCredentialsAreIncorrect() {
    
    when(userService.validateUser("conchalopez@ciarcoiris.com", "wrongPassword")).thenReturn(Optional.empty());
    
    Optional<User> result = userController.validateUser("conchalopez@ciarcoiris.com", "wrongPassword");

    assertFalse(result.isPresent());
    
    }

}
