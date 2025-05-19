package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.ItensPedidoDTO;
import br.edu.utfpr.pb.pw44s.server.model.ItensPedido;
import br.edu.utfpr.pb.pw44s.server.model.Pedido;
import br.edu.utfpr.pb.pw44s.server.model.Produto;
import br.edu.utfpr.pb.pw44s.server.service.ItensPedidoService;
import br.edu.utfpr.pb.pw44s.server.service.PedidoService;
import br.edu.utfpr.pb.pw44s.server.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/itens-pedido")
@RequiredArgsConstructor
public class ItensPedidoController {
    
    private final ItensPedidoService itensPedidoService;
    private final PedidoService pedidoService;
    private final ProdutoService produtoService;
    private final ModelMapper modelMapper;
    
    @PostMapping
    public ResponseEntity<ItensPedidoDTO> create(@RequestBody @Valid ItensPedidoDTO itensPedidoDTO) {
        Optional<Pedido> pedidoOpt = pedidoService.findOne(itensPedidoDTO.getPedidoId());
        if (!pedidoOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<Produto> produtoOpt = produtoService.findOne(itensPedidoDTO.getProdutoId());
        if (!produtoOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        ItensPedido itensPedido = modelMapper.map(itensPedidoDTO, ItensPedido.class);
        itensPedido.setPedido(pedidoOpt.get());
        itensPedido.setProduto(produtoOpt.get());
        itensPedido = itensPedidoService.save(itensPedido);
        
        ItensPedidoDTO dto = modelMapper.map(itensPedido, ItensPedidoDTO.class);
        dto.setPedidoId(itensPedido.getPedido().getId());
        dto.setProdutoId(itensPedido.getProduto().getId());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    
    @GetMapping
    public ResponseEntity<List<ItensPedidoDTO>> findAll() {
        return ResponseEntity.ok(
                itensPedidoService.findAll().stream()
                        .map(itensPedido -> {
                            ItensPedidoDTO dto = modelMapper.map(itensPedido, ItensPedidoDTO.class);
                            dto.setPedidoId(itensPedido.getPedido().getId());
                            dto.setProdutoId(itensPedido.getProduto().getId());
                            return dto;
                        })
                        .collect(Collectors.toList())
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ItensPedidoDTO> findOne(@PathVariable Long id) {
        return itensPedidoService.findOne(id)
                .map(itensPedido -> {
                    ItensPedidoDTO dto = modelMapper.map(itensPedido, ItensPedidoDTO.class);
                    dto.setPedidoId(itensPedido.getPedido().getId());
                    dto.setProdutoId(itensPedido.getProduto().getId());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<ItensPedidoDTO>> findByPedido(@PathVariable Long pedidoId) {
        Optional<Pedido> pedidoOpt = pedidoService.findOne(pedidoId);
        if (!pedidoOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(
                itensPedidoService.findByPedido(pedidoOpt.get()).stream()
                        .map(itensPedido -> {
                            ItensPedidoDTO dto = modelMapper.map(itensPedido, ItensPedidoDTO.class);
                            dto.setPedidoId(itensPedido.getPedido().getId());
                            dto.setProdutoId(itensPedido.getProduto().getId());
                            return dto;
                        })
                        .collect(Collectors.toList())
        );
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ItensPedidoDTO> update(@PathVariable Long id, @RequestBody @Valid ItensPedidoDTO itensPedidoDTO) {
        if (!itensPedidoService.findOne(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Optional<Pedido> pedidoOpt = pedidoService.findOne(itensPedidoDTO.getPedidoId());
        if (!pedidoOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<Produto> produtoOpt = produtoService.findOne(itensPedidoDTO.getProdutoId());
        if (!produtoOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        ItensPedido itensPedido = modelMapper.map(itensPedidoDTO, ItensPedido.class);
        itensPedido.setId(id);
        itensPedido.setPedido(pedidoOpt.get());
        itensPedido.setProduto(produtoOpt.get());
        itensPedido = itensPedidoService.save(itensPedido);
        
        ItensPedidoDTO dto = modelMapper.map(itensPedido, ItensPedidoDTO.class);
        dto.setPedidoId(itensPedido.getPedido().getId());
        dto.setProdutoId(itensPedido.getProduto().getId());
        
        return ResponseEntity.ok(dto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!itensPedidoService.findOne(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        itensPedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
