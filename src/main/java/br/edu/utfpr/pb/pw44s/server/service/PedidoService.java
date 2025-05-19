package br.edu.utfpr.pb.pw44s.server.service;

import br.edu.utfpr.pb.pw44s.server.model.Pedido;
import br.edu.utfpr.pb.pw44s.server.model.User;
import br.edu.utfpr.pb.pw44s.server.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {
    
    private final PedidoRepository pedidoRepository;
    
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }
    
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }
    
    public Optional<Pedido> findOne(Long id) {
        return pedidoRepository.findById(id);
    }
    
    public List<Pedido> findByUsuario(User usuario) {
        return pedidoRepository.findByUsuario(usuario);
    }
    
    public void delete(Long id) {
        pedidoRepository.deleteById(id);
    }
}
