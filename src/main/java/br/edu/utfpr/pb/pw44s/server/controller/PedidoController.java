package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.PedidoDTO;
import br.edu.utfpr.pb.pw44s.server.model.Endereco;
import br.edu.utfpr.pb.pw44s.server.model.Pedido;
import br.edu.utfpr.pb.pw44s.server.model.User;
import br.edu.utfpr.pb.pw44s.server.service.EnderecoService;
import br.edu.utfpr.pb.pw44s.server.service.PedidoService;
import br.edu.utfpr.pb.pw44s.server.service.UserService;
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
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    
    private final PedidoService pedidoService;
    private final UserService userService;
    private final EnderecoService enderecoService;
    private final ModelMapper modelMapper;
    
    @PostMapping
    public ResponseEntity<PedidoDTO> create(@RequestBody @Valid PedidoDTO pedidoDTO) {
        Optional<User> usuarioOpt = userService.findOne(pedidoDTO.getUsuarioId());
        if (!usuarioOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<Endereco> enderecoOpt = enderecoService.findOne(pedidoDTO.getEnderecoId());
        if (!enderecoOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        Pedido pedido = modelMapper.map(pedidoDTO, Pedido.class);
        pedido.setUsuario(usuarioOpt.get());
        pedido.setEndereco(enderecoOpt.get());
        pedido = pedidoService.save(pedido);
        
        PedidoDTO dto = modelMapper.map(pedido, PedidoDTO.class);
        dto.setUsuarioId(pedido.getUsuario().getId());
        dto.setEnderecoId(pedido.getEndereco().getId());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> findAll() {
        return ResponseEntity.ok(
                pedidoService.findAll().stream()
                        .map(pedido -> {
                            PedidoDTO dto = modelMapper.map(pedido, PedidoDTO.class);
                            dto.setUsuarioId(pedido.getUsuario().getId());
                            dto.setEnderecoId(pedido.getEndereco().getId());
                            return dto;
                        })
                        .collect(Collectors.toList())
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> findOne(@PathVariable Long id) {
        return pedidoService.findOne(id)
                .map(pedido -> {
                    PedidoDTO dto = modelMapper.map(pedido, PedidoDTO.class);
                    dto.setUsuarioId(pedido.getUsuario().getId());
                    dto.setEnderecoId(pedido.getEndereco().getId());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoDTO>> findByUsuario(@PathVariable Long usuarioId) {
        Optional<User> usuarioOpt = userService.findOne(usuarioId);
        if (!usuarioOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(
                pedidoService.findByUsuario(usuarioOpt.get()).stream()
                        .map(pedido -> {
                            PedidoDTO dto = modelMapper.map(pedido, PedidoDTO.class);
                            dto.setUsuarioId(pedido.getUsuario().getId());
                            dto.setEnderecoId(pedido.getEndereco().getId());
                            return dto;
                        })
                        .collect(Collectors.toList())
        );
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> update(@PathVariable Long id, @RequestBody @Valid PedidoDTO pedidoDTO) {
        if (!pedidoService.findOne(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Optional<User> usuarioOpt = userService.findOne(pedidoDTO.getUsuarioId());
        if (!usuarioOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<Endereco> enderecoOpt = enderecoService.findOne(pedidoDTO.getEnderecoId());
        if (!enderecoOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        Pedido pedido = modelMapper.map(pedidoDTO, Pedido.class);
        pedido.setId(id);
        pedido.setUsuario(usuarioOpt.get());
        pedido.setEndereco(enderecoOpt.get());
        pedido = pedidoService.save(pedido);
        
        PedidoDTO dto = modelMapper.map(pedido, PedidoDTO.class);
        dto.setUsuarioId(pedido.getUsuario().getId());
        dto.setEnderecoId(pedido.getEndereco().getId());
        
        return ResponseEntity.ok(dto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!pedidoService.findOne(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
