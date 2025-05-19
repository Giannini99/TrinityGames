package br.edu.utfpr.pb.pw44s.server;

import br.edu.utfpr.pb.pw44s.server.dto.ItensPedidoDTO;
import br.edu.utfpr.pb.pw44s.server.model.ItensPedido;
import br.edu.utfpr.pb.pw44s.server.model.Pedido;
import br.edu.utfpr.pb.pw44s.server.model.Produto;
import br.edu.utfpr.pb.pw44s.server.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ItensPedidoControllerTest {
    private static final String API_ITENS_PEDIDO = "/itens-pedido";
    private static final String API_USER = "/users";
    private static final String API_CATEGORIA = "/categorias";
    private static final String API_PRODUTO = "/produtos";
    private static final String API_ENDERECO = "/enderecos";
    private static final String API_PEDIDO = "/pedidos";
    
    @Autowired
    private TestRestTemplate testRestTemplate;
    
    private Long pedidoId;
    private Long produtoId;
    
    @BeforeEach
    public void setUp() {
        // Criar um usuário
        User user = User.builder()
                .username("itens-test-user")
                .displayName("Teste Itens")
                .password("P4ssword")
                .email("itens@example.com")
                .build();
        
        ResponseEntity<User> userResponse = testRestTemplate.postForEntity(API_USER, user, User.class);
        Long usuarioId = userResponse.getBody().getId();
        
        // Criar um endereço
        br.edu.utfpr.pb.pw44s.server.dto.EnderecoDTO endereco = br.edu.utfpr.pb.pw44s.server.dto.EnderecoDTO.builder()
                .usuarioId(usuarioId)
                .logradouro("Rua dos Itens")
                .complemento("Sala 42")
                .cep("85503-700")
                .cidade("Pato Branco")
                .uf("PR")
                .build();
        
        ResponseEntity<Object> enderecoResponse = testRestTemplate.postForEntity(API_ENDERECO, endereco, Object.class);
        Long enderecoId = 1L; // Simplificação para o teste
        
        // Criar uma categoria
        br.edu.utfpr.pb.pw44s.server.dto.CategoriaDTO categoria = br.edu.utfpr.pb.pw44s.server.dto.CategoriaDTO.builder()
                .nome("Jogos de Estratégia")
                .build();
        
        ResponseEntity<Object> categoriaResponse = testRestTemplate.postForEntity(API_CATEGORIA, categoria, Object.class);
        Long categoriaId = 1L; // Simplificação para o teste
        
        // Criar um produto
        br.edu.utfpr.pb.pw44s.server.dto.ProdutoDTO produto = br.edu.utfpr.pb.pw44s.server.dto.ProdutoDTO.builder()
                .nome("Catan")
                .descricao("Jogo de estratégia e negociação")
                .preco(new BigDecimal("199.90"))
                .urlImagem("https://example.com/catan.jpg")
                .categoriaId(categoriaId)
                .build();
        
        ResponseEntity<Object> produtoResponse = testRestTemplate.postForEntity(API_PRODUTO, produto, Object.class);
        produtoId = 1L; // Simplificação para o teste
        
        // Criar um pedido
        br.edu.utfpr.pb.pw44s.server.dto.PedidoDTO pedido = br.edu.utfpr.pb.pw44s.server.dto.PedidoDTO.builder()
                .data(LocalDateTime.now())
                .usuarioId(usuarioId)
                .enderecoId(enderecoId)
                .build();
        
        ResponseEntity<Object> pedidoResponse = testRestTemplate.postForEntity(API_PEDIDO, pedido, Object.class);
        pedidoId = 1L; // Simplificação para o teste
    }
    
    @Test
    public void postItensPedido_whenItensPedidoIsValid_receiveCreated() {
        ItensPedidoDTO itensPedido = createValidItensPedido();
        
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_ITENS_PEDIDO, itensPedido, Object.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
    
    @Test
    public void getItensPedidoByPedido_whenThereAreItensPedido_receiveOk() {
        ItensPedidoDTO itensPedido = createValidItensPedido();
        testRestTemplate.postForEntity(API_ITENS_PEDIDO, itensPedido, Object.class);
        
        ResponseEntity<Object> response = testRestTemplate.getForEntity(API_ITENS_PEDIDO + "/pedido/" + pedidoId, Object.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
    private ItensPedidoDTO createValidItensPedido() {
        return ItensPedidoDTO.builder()
                .pedidoId(pedidoId)
                .produtoId(produtoId)
                .preco(new BigDecimal("199.90"))
                .quantidade(1)
                .build();
    }
}
