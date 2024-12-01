package dev.susana.ciArcoIris.users;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class UserTest {

    @Test
    void testUserBuilderAndGetters() {
        
        User user = User.builder()
                .id(1L)
                .username("testuser")
                .password("password123")
                .email("test@example.com")
                .role("USER")
                .build();


        assertThat(user.getId(), is(1L));
        assertThat(user.getUsername(), is("testuser"));
        assertThat(user.getPassword(), is("password123"));
        assertThat(user.getEmail(), is("test@example.com"));
        assertThat(user.getRole(), is("USER"));
    }

    @Test
    void testSetters() {
        
        User user = new User();
        user.setId(2L);
        user.setUsername("updatedUser");
        user.setPassword("newPassword");
        user.setEmail("updated@example.com");
        user.setRole("ADMIN");


        assertThat(user.getId(), is(2L));
        assertThat(user.getUsername(), is("updatedUser"));
        assertThat(user.getPassword(), is("newPassword"));
        assertThat(user.getEmail(), is("updated@example.com"));
        assertThat(user.getRole(), is("ADMIN"));
    }

    @Test
    void testEqualsAndHashCode() {
    
        User user1 = User.builder()
                .id(1L)
                .username("testuser")
                .password("password123")
                .email("test@example.com")
                .role("USER")
                .build();

        User user2 = User.builder()
                .id(1L)
                .username("testuser")
                .password("password123")
                .email("test@example.com")
                .role("USER")
                .build();


        assertThat(user1, is(equalTo(user2)));
        assertThat(user1.hashCode(), is(user2.hashCode()));
    }

    @Test
    void testToString() {
    
        User user = User.builder()
                .id(1L)
                .username("testuser")
                .password("password123")
                .email("test@example.com")
                .role("USER")
                .build();

        
        String expectedString = "User(id=1, username=testuser, password=password123, email=test@example.com, role=USER)";
        assertThat(user.toString(), is(expectedString));
    }
}
