package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.CategoriaDTO;
import br.edu.utfpr.pb.pw44s.server.model.Categoria;
import br.edu.utfpr.pb.pw44s.server.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    
    private final CategoriaService categoriaService;
    private final ModelMapper modelMapper;
    
    @PostMapping
    public ResponseEntity<CategoriaDTO> create(@RequestBody @Valid CategoriaDTO categoriaDTO) {
        Categoria categoria = modelMapper.map(categoriaDTO, Categoria.class);
        categoria = categoriaService.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(categoria, CategoriaDTO.class));
    }
    
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        return ResponseEntity.ok(
                categoriaService.findAll().stream()
                        .map(categoria -> modelMapper.map(categoria, CategoriaDTO.class))
                        .collect(Collectors.toList())
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> findOne(@PathVariable Long id) {
        return categoriaService.findOne(id)
                .map(categoria -> ResponseEntity.ok(modelMapper.map(categoria, CategoriaDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> update(@PathVariable Long id, @RequestBody @Valid CategoriaDTO categoriaDTO) {
        if (!categoriaService.findOne(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Categoria categoria = modelMapper.map(categoriaDTO, Categoria.class);
        categoria.setId(id);
        categoria = categoriaService.save(categoria);
        
        return ResponseEntity.ok(modelMapper.map(categoria, CategoriaDTO.class));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!categoriaService.findOne(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
