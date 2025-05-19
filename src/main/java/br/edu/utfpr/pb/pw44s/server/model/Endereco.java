package br.edu.utfpr.pb.pw44s.server.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_endereco")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Endereco {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private User usuario;
    
    @Column(length = 200, nullable = false)
    private String logradouro;
    
    @Column(length = 100)
    private String complemento;
    
    @Column(length = 10, nullable = false)
    private String cep;
    
    @Column(length = 100, nullable = false)
    private String cidade;
    
    @Column(length = 2, nullable = false)
    private String uf;
}
