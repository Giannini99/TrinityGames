package br.edu.utfpr.pb.pw44s.server;

import br.edu.utfpr.pb.pw44s.server.dto.ProdutoDTO;
import br.edu.utfpr.pb.pw44s.server.model.Categoria;
import br.edu.utfpr.pb.pw44s.server.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProdutoControllerTest {
    private static final String API_PRODUTO = "/produtos";
    private static final String API_CATEGORIA = "/categorias";
    
    @Autowired
    private TestRestTemplate testRestTemplate;
    
    private Long categoriaId;
    
    @BeforeEach
    public void setUp() {
        // Criar uma categoria para usar nos testes
        Categoria categoria = Categoria.builder()
                .nome("Jogos de Cartas")
                .build();
        
        ResponseEntity<Categoria> response = testRestTemplate.postForEntity(API_CATEGORIA, categoria, Categoria.class);
        categoriaId = response.getBody().getId();
    }
    
    @Test
    public void postProduto_whenProdutoIsValid_receiveCreated() {
        ProdutoDTO produto = createValidProduto();
        
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_PRODUTO, produto, Object.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
    
    @Test
    public void postProduto_whenProdutoIsValid_produtoSavedToDatabase() {
        ProdutoDTO produto = createValidProduto();
        
        testRestTemplate.postForEntity(API_PRODUTO, produto, Object.class);
        
        ResponseEntity<Produto[]> response = testRestTemplate.getForEntity(API_PRODUTO, Produto[].class);
        Produto[] produtos = response.getBody();
        
        assertThat(produtos.length).isGreaterThan(0);
    }
    
    @Test
    public void getProdutos_whenThereAreProdutos_receiveOk() {
        ProdutoDTO produto = createValidProduto();
        testRestTemplate.postForEntity(API_PRODUTO, produto, Object.class);
        
        ResponseEntity<Object> response = testRestTemplate.getForEntity(API_PRODUTO, Object.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
    @Test
    public void getProdutosByCategoria_whenThereAreProdutos_receiveOk() {
        ProdutoDTO produto = createValidProduto();
        testRestTemplate.postForEntity(API_PRODUTO, produto, Object.class);
        
        ResponseEntity<Object> response = testRestTemplate.getForEntity(API_PRODUTO + "/categoria/" + categoriaId, Object.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
    private ProdutoDTO createValidProduto() {
        return ProdutoDTO.builder()
                .nome("Magic: The Gathering")
                .descricao("Jogo de cartas colecionáveis")
                .preco(new BigDecimal("89.90"))
                .urlImagem("https://example.com/magic.jpg")
                .categoriaId(categoriaId)
                .build();
    }
}
