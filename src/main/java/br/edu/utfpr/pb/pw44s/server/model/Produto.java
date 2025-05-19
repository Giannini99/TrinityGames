package br.edu.utfpr.pb.pw44s.server.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_produto")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    private String nome;
    
    @Column(length = 1000)
    private String descricao;
    
    @Column(nullable = false)
    private BigDecimal preco;
    
    @Column(name = "url_imagem")
    private String urlImagem;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    private Categoria categoria;
}
