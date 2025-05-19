package br.edu.utfpr.pb.pw44s.server.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {
    
    private Long id;
    
    @NotNull
    private Long usuarioId;
    
    @NotNull
    @Size(min = 3, max = 200)
    private String logradouro;
    
    @Size(max = 100)
    private String complemento;
    
    @NotNull
    @Size(min = 8, max = 10)
    private String cep;
    
    @NotNull
    @Size(min = 2, max = 100)
    private String cidade;
    
    @NotNull
    @Size(min = 2, max = 2)
    private String uf;
}
