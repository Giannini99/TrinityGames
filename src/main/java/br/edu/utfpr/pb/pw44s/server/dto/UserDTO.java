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
public class UserDTO {
    
    private Long id;
    
    @NotNull
    @Size(min = 4, max = 100)
    private String username;
    
    @NotNull
    @Size(min = 4, max = 100)
    private String displayName;
    
    @NotNull
    @Size(min = 6, max = 255)
    private String password;
    
    @Size(max = 255)
    private String email;
}
