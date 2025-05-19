package br.edu.utfpr.pb.pw44s.server;

import br.edu.utfpr.pb.pw44s.server.dto.CategoriaDTO;
import br.edu.utfpr.pb.pw44s.server.model.Categoria;
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
public class CategoriaControllerTest {
    private static final String API_CATEGORIA = "/categorias";
    
    @Autowired
    private TestRestTemplate testRestTemplate;
    
    @Test
    public void postCategoria_whenCategoriaIsValid_receiveCreated() {
        CategoriaDTO categoria = createValidCategoria();
        
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_CATEGORIA, categoria, Object.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
    
    @Test
    public void postCategoria_whenCategoriaIsValid_categoriaSavedToDatabase() {
        CategoriaDTO categoria = createValidCategoria();
        
        testRestTemplate.postForEntity(API_CATEGORIA, categoria, Object.class);
        
        ResponseEntity<Categoria[]> response = testRestTemplate.getForEntity(API_CATEGORIA, Categoria[].class);
        Categoria[] categorias = response.getBody();
        
        assertThat(categorias.length).isGreaterThan(0);
    }
    
    @Test
    public void getCategorias_whenThereAreCategorias_receiveOk() {
        CategoriaDTO categoria = createValidCategoria();
        testRestTemplate.postForEntity(API_CATEGORIA, categoria, Object.class);
        
        ResponseEntity<Object> response = testRestTemplate.getForEntity(API_CATEGORIA, Object.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
    private CategoriaDTO createValidCategoria() {
        return CategoriaDTO.builder()
                .nome("Jogos de Tabuleiro")
                .build();
    }
}
