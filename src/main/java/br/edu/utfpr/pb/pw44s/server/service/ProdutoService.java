package br.edu.utfpr.pb.pw44s.server.service;

import br.edu.utfpr.pb.pw44s.server.model.Produto;
import br.edu.utfpr.pb.pw44s.server.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    
    private final ProdutoRepository produtoRepository;
    
    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }
    
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }
    
    public Optional<Produto> findOne(Long id) {
        return produtoRepository.findById(id);
    }
    
    public List<Produto> findByCategoriaId(Long categoriaId) {
        return produtoRepository.findByCategoriaId(categoriaId);
    }
    
    public void delete(Long id) {
        produtoRepository.deleteById(id);
    }
}
