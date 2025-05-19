package br.edu.utfpr.pb.pw44s.server.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    
    private Long id;
    
    @NotNull
    private LocalDateTime data;
    
    @NotNull
    private Long usuarioId;
    
    @NotNull
    private Long enderecoId;
}
