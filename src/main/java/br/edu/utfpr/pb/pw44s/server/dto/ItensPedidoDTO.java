package br.edu.utfpr.pb.pw44s.server.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItensPedidoDTO {
    
    private Long id;
    
    @NotNull
    private Long pedidoId;
    
    @NotNull
    private Long produtoId;
    
    @NotNull
    @Positive
    private BigDecimal preco;
    
    @NotNull
    @Positive
    private Integer quantidade;
}
