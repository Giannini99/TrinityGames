package br.edu.utfpr.pb.pw44s.server.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {
    
    private Long id;
    
    @NotNull
    @Size(min = 2, max = 100)
    private String nome;
    
    @Size(max = 1000)
    private String descricao;
    
    @NotNull
    @Positive
    private BigDecimal preco;
    
    private String urlImagem;
    
    @NotNull
    private Long categoriaId;
}
