package br.edu.utfpr.pb.pw44s.server.service;

import br.edu.utfpr.pb.pw44s.server.model.Categoria;
import br.edu.utfpr.pb.pw44s.server.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    
    private final CategoriaRepository categoriaRepository;
    
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }
    
    public Optional<Categoria> findOne(Long id) {
        return categoriaRepository.findById(id);
    }
    
    public void delete(Long id) {
        categoriaRepository.deleteById(id);
    }
}
