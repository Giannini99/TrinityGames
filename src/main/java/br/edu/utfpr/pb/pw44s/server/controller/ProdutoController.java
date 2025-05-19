package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.ProdutoDTO;
import br.edu.utfpr.pb.pw44s.server.model.Categoria;
import br.edu.utfpr.pb.pw44s.server.model.Produto;
import br.edu.utfpr.pb.pw44s.server.service.CategoriaService;
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
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    
    private final ProdutoService produtoService;
    private final CategoriaService categoriaService;
    private final ModelMapper modelMapper;
    
    @PostMapping
    public ResponseEntity<ProdutoDTO> create(@RequestBody @Valid ProdutoDTO produtoDTO) {
        Optional<Categoria> categoriaOpt = categoriaService.findOne(produtoDTO.getCategoriaId());
        if (!categoriaOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        Produto produto = modelMapper.map(produtoDTO, Produto.class);
        produto.setCategoria(categoriaOpt.get());
        produto = produtoService.save(produto);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(produto, ProdutoDTO.class));
    }
    
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> findAll() {
        return ResponseEntity.ok(
                produtoService.findAll().stream()
                        .map(produto -> {
                            ProdutoDTO dto = modelMapper.map(produto, ProdutoDTO.class);
                            dto.setCategoriaId(produto.getCategoria().getId());
                            return dto;
                        })
                        .collect(Collectors.toList())
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> findOne(@PathVariable Long id) {
        return produtoService.findOne(id)
                .map(produto -> {
                    ProdutoDTO dto = modelMapper.map(produto, ProdutoDTO.class);
                    dto.setCategoriaId(produto.getCategoria().getId());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProdutoDTO>> findByCategoriaId(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(
                produtoService.findByCategoriaId(categoriaId).stream()
                        .map(produto -> {
                            ProdutoDTO dto = modelMapper.map(produto, ProdutoDTO.class);
                            dto.setCategoriaId(produto.getCategoria().getId());
                            return dto;
                        })
                        .collect(Collectors.toList())
        );
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> update(@PathVariable Long id, @RequestBody @Valid ProdutoDTO produtoDTO) {
        if (!produtoService.findOne(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Optional<Categoria> categoriaOpt = categoriaService.findOne(produtoDTO.getCategoriaId());
        if (!categoriaOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        Produto produto = modelMapper.map(produtoDTO, Produto.class);
        produto.setId(id);
        produto.setCategoria(categoriaOpt.get());
        produto = produtoService.save(produto);
        
        ProdutoDTO dto = modelMapper.map(produto, ProdutoDTO.class);
        dto.setCategoriaId(produto.getCategoria().getId());
        
        return ResponseEntity.ok(dto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!produtoService.findOne(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
