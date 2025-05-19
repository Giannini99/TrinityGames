package br.edu.utfpr.pb.pw44s.server;

import br.edu.utfpr.pb.pw44s.server.dto.EnderecoDTO;
import br.edu.utfpr.pb.pw44s.server.model.Endereco;
import br.edu.utfpr.pb.pw44s.server.model.User;
import org.junit.jupiter.api.BeforeEach;
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
public class EnderecoControllerTest {
    private static final String API_ENDERECO = "/enderecos";
    private static final String API_USER = "/users";
    
    @Autowired
    private TestRestTemplate testRestTemplate;
    
    private Long usuarioId;
    
    @BeforeEach
    public void setUp() {
        // Criar um usuário para usar nos testes
        User user = User.builder()
                .username("endereco-test-user")
                .displayName("Teste Endereço")
                .password("P4ssword")
                .email("endereco@example.com")
                .build();
        
        ResponseEntity<User> response = testRestTemplate.postForEntity(API_USER, user, User.class);
        usuarioId = response.getBody().getId();
    }
    
    @Test
    public void postEndereco_whenEnderecoIsValid_receiveCreated() {
        EnderecoDTO endereco = createValidEndereco();
        
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_ENDERECO, endereco, Object.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
    
    @Test
    public void postEndereco_whenEnderecoIsValid_enderecoSavedToDatabase() {
        EnderecoDTO endereco = createValidEndereco();
        
        testRestTemplate.postForEntity(API_ENDERECO, endereco, Object.class);
        
        ResponseEntity<Endereco[]> response = testRestTemplate.getForEntity(API_ENDERECO, Endereco[].class);
        Endereco[] enderecos = response.getBody();
        
        assertThat(enderecos.length).isGreaterThan(0);
    }
    
    @Test
    public void getEnderecosByUsuario_whenThereAreEnderecos_receiveOk() {
        EnderecoDTO endereco = createValidEndereco();
        testRestTemplate.postForEntity(API_ENDERECO, endereco, Object.class);
        
        ResponseEntity<Object> response = testRestTemplate.getForEntity(API_ENDERECO + "/usuario/" + usuarioId, Object.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
    private EnderecoDTO createValidEndereco() {
        return EnderecoDTO.builder()
                .usuarioId(usuarioId)
                .logradouro("Rua das Cartas")
                .complemento("Apto 42")
                .cep("85503-500")
                .cidade("Pato Branco")
                .uf("PR")
                .build();
    }
}
