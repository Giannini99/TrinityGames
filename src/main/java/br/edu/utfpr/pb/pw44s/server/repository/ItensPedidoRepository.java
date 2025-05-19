package br.edu.utfpr.pb.pw44s.server.repository;

import br.edu.utfpr.pb.pw44s.server.model.ItensPedido;
import br.edu.utfpr.pb.pw44s.server.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItensPedidoRepository extends JpaRepository<ItensPedido, Long> {
    List<ItensPedido> findByPedido(Pedido pedido);
}
