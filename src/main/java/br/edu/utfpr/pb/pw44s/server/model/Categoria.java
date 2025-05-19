package br.edu.utfpr.pb.pw44s.server.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_categoria")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    private String nome;
}
