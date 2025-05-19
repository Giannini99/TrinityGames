package br.edu.utfpr.pb.pw44s.server.service;

import br.edu.utfpr.pb.pw44s.server.model.ItensPedido;
import br.edu.utfpr.pb.pw44s.server.model.Pedido;
import br.edu.utfpr.pb.pw44s.server.repository.ItensPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItensPedidoService {
    
    private final ItensPedidoRepository itensPedidoRepository;
    
    public ItensPedido save(ItensPedido itensPedido) {
        return itensPedidoRepository.save(itensPedido);
    }
    
    public List<ItensPedido> findAll() {
        return itensPedidoRepository.findAll();
    }
    
    public Optional<ItensPedido> findOne(Long id) {
        return itensPedidoRepository.findById(id);
    }
    
    public List<ItensPedido> findByPedido(Pedido pedido) {
        return itensPedidoRepository.findByPedido(pedido);
    }
    
    public void delete(Long id) {
        itensPedidoRepository.deleteById(id);
    }
}
