package br.edu.utfpr.pb.pw44s.server;

import br.edu.utfpr.pb.pw44s.server.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {
    private static final String API_USER = "/users";
    
    @Autowired
    private TestRestTemplate testRestTemplate;
    
    @Test
    public void postUser_whenUserIsValid_receiveCreated() {
        User user = createValidUser();
        
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_USER, user, Object.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
    
    @Test
    public void postUser_whenUserIsValid_userSavedToDatabase() {
        User user = createValidUser();
        
        testRestTemplate.postForEntity(API_USER, user, Object.class);
        
        ResponseEntity<User[]> response = testRestTemplate.getForEntity(API_USER, User[].class);
        User[] users = response.getBody();
        
        assertThat(users.length).isGreaterThan(0);
    }
    
    @Test
    public void postUser_whenUserIsInvalid_receiveBadRequest() {
        User user = new User();
        
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_USER, user, Object.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    
    @Test
    public void getUsers_whenThereAreUsers_receiveOk() {
        User user = createValidUser();
        testRestTemplate.postForEntity(API_USER, user, Object.class);
        
        ResponseEntity<Object> response = testRestTemplate.getForEntity(API_USER, Object.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
    @Test
    public void getUserById_whenUserExists_receiveOk() {
        User user = createValidUser();
        ResponseEntity<User> createResponse = testRestTemplate.postForEntity(API_USER, user, User.class);
        User savedUser = createResponse.getBody();
        
        ResponseEntity<User> response = testRestTemplate.getForEntity(API_USER + "/" + savedUser.getId(), User.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUsername()).isEqualTo(user.getUsername());
    }
    
    private User createValidUser() {
        return User.builder()
                .username("test-user")
                .displayName("Test User")
                .password("P4ssword")
                .email("test@example.com")
                .build();
    }
}
