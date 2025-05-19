package br.edu.utfpr.pb.pw44s.server.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_pedido")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime data;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private User usuario;
    
    @ManyToOne
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;
}
