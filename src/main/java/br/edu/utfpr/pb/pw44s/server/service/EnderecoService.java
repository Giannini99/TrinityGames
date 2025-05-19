package br.edu.utfpr.pb.pw44s.server.service;

import br.edu.utfpr.pb.pw44s.server.model.Endereco;
import br.edu.utfpr.pb.pw44s.server.model.User;
import br.edu.utfpr.pb.pw44s.server.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnderecoService {
    
    private final EnderecoRepository enderecoRepository;
    
    public Endereco save(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }
    
    public List<Endereco> findAll() {
        return enderecoRepository.findAll();
    }
    
    public Optional<Endereco> findOne(Long id) {
        return enderecoRepository.findById(id);
    }
    
    public List<Endereco> findByUsuario(User usuario) {
        return enderecoRepository.findByUsuario(usuario);
    }
    
    public void delete(Long id) {
        enderecoRepository.deleteById(id);
    }
}
