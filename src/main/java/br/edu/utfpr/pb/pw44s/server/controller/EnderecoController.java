package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.EnderecoDTO;
import br.edu.utfpr.pb.pw44s.server.model.Endereco;
import br.edu.utfpr.pb.pw44s.server.model.User;
import br.edu.utfpr.pb.pw44s.server.service.EnderecoService;
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
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController {
    
    private final EnderecoService enderecoService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    
    @PostMapping
    public ResponseEntity<EnderecoDTO> create(@RequestBody @Valid EnderecoDTO enderecoDTO) {
        Optional<User> usuarioOpt = userService.findOne(enderecoDTO.getUsuarioId());
        if (!usuarioOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        Endereco endereco = modelMapper.map(enderecoDTO, Endereco.class);
        endereco.setUsuario(usuarioOpt.get());
        endereco = enderecoService.save(endereco);
        
        EnderecoDTO dto = modelMapper.map(endereco, EnderecoDTO.class);
        dto.setUsuarioId(endereco.getUsuario().getId());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    
    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> findAll() {
        return ResponseEntity.ok(
                enderecoService.findAll().stream()
                        .map(endereco -> {
                            EnderecoDTO dto = modelMapper.map(endereco, EnderecoDTO.class);
                            dto.setUsuarioId(endereco.getUsuario().getId());
                            return dto;
                        })
                        .collect(Collectors.toList())
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> findOne(@PathVariable Long id) {
        return enderecoService.findOne(id)
                .map(endereco -> {
                    EnderecoDTO dto = modelMapper.map(endereco, EnderecoDTO.class);
                    dto.setUsuarioId(endereco.getUsuario().getId());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<EnderecoDTO>> findByUsuario(@PathVariable Long usuarioId) {
        Optional<User> usuarioOpt = userService.findOne(usuarioId);
        if (!usuarioOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(
                enderecoService.findByUsuario(usuarioOpt.get()).stream()
                        .map(endereco -> {
                            EnderecoDTO dto = modelMapper.map(endereco, EnderecoDTO.class);
                            dto.setUsuarioId(endereco.getUsuario().getId());
                            return dto;
                        })
                        .collect(Collectors.toList())
        );
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDTO> update(@PathVariable Long id, @RequestBody @Valid EnderecoDTO enderecoDTO) {
        if (!enderecoService.findOne(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Optional<User> usuarioOpt = userService.findOne(enderecoDTO.getUsuarioId());
        if (!usuarioOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        Endereco endereco = modelMapper.map(enderecoDTO, Endereco.class);
        endereco.setId(id);
        endereco.setUsuario(usuarioOpt.get());
        endereco = enderecoService.save(endereco);
        
        EnderecoDTO dto = modelMapper.map(endereco, EnderecoDTO.class);
        dto.setUsuarioId(endereco.getUsuario().getId());
        
        return ResponseEntity.ok(dto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!enderecoService.findOne(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
