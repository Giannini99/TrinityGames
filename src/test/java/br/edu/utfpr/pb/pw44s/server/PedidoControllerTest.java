package br.edu.utfpr.pb.pw44s.server;

import br.edu.utfpr.pb.pw44s.server.dto.PedidoDTO;
import br.edu.utfpr.pb.pw44s.server.model.Endereco;
import br.edu.utfpr.pb.pw44s.server.model.Pedido;
import br.edu.utfpr.pb.pw44s.server.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PedidoControllerTest {
    private static final String API_PEDIDO = "/pedidos";
    private static final String API_USER = "/users";
    private static final String API_ENDERECO = "/enderecos";
    
    @Autowired
    private TestRestTemplate testRestTemplate;
    
    private Long usuarioId;
    private Long enderecoId;
    
    @BeforeEach
    public void setUp() {
        // Criar um usuário para usar nos testes
        User user = User.builder()
                .username("pedido-test-user")
                .displayName("Teste Pedido")
                .password("P4ssword")
                .email("pedido@example.com")
                .build();
        
        ResponseEntity<User> userResponse = testRestTemplate.postForEntity(API_USER, user, User.class);
        usuarioId = userResponse.getBody().getId();
        
        // Criar um endereço para usar nos testes
        Endereco endereco = Endereco.builder()
                .usuario(user)
                .logradouro("Rua dos Jogos")
                .complemento("Casa 123")
                .cep("85503-600")
                .cidade("Pato Branco")
                .uf("PR")
                .build();
        
        ResponseEntity<Endereco> enderecoResponse = testRestTemplate.postForEntity(API_ENDERECO, endereco, Endereco.class);
        enderecoId = enderecoResponse.getBody().getId();
    }
    
    @Test
    public void postPedido_whenPedidoIsValid_receiveCreated() {
        PedidoDTO pedido = createValidPedido();
        
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_PEDIDO, pedido, Object.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
    
    @Test
    public void postPedido_whenPedidoIsValid_pedidoSavedToDatabase() {
        PedidoDTO pedido = createValidPedido();
        
        testRestTemplate.postForEntity(API_PEDIDO, pedido, Object.class);
        
        ResponseEntity<Pedido[]> response = testRestTemplate.getForEntity(API_PEDIDO, Pedido[].class);
        Pedido[] pedidos = response.getBody();
        
        assertThat(pedidos.length).isGreaterThan(0);
    }
    
    @Test
    public void getPedidosByUsuario_whenThereArePedidos_receiveOk() {
        PedidoDTO pedido = createValidPedido();
        testRestTemplate.postForEntity(API_PEDIDO, pedido, Object.class);
        
        ResponseEntity<Object> response = testRestTemplate.getForEntity(API_PEDIDO + "/usuario/" + usuarioId, Object.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
    private PedidoDTO createValidPedido() {
        return PedidoDTO.builder()
                .data(LocalDateTime.now())
                .usuarioId(usuarioId)
                .enderecoId(enderecoId)
                .build();
    }
}
