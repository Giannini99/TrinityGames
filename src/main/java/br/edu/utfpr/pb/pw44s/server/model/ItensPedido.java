package br.edu.utfpr.pb.pw44s.server.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_itens_pedido")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItensPedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private Pedido pedido;
    
    @ManyToOne
    @JoinColumn(name = "produto_id", referencedColumnName = "id")
    private Produto produto;
    
    @Column(nullable = false)
    private BigDecimal preco;
    
    @Column(nullable = false)
    private Integer quantidade;
}
