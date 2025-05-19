package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.dto.UserDTO;
import br.edu.utfpr.pb.pw44s.server.model.User;
import br.edu.utfpr.pb.pw44s.server.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    private final ModelMapper modelMapper;
    
    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(user, UserDTO.class));
    }
    
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(
                userService.findAll().stream()
                        .map(user -> modelMapper.map(user, UserDTO.class))
                        .collect(Collectors.toList())
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findOne(@PathVariable Long id) {
        return userService.findOne(id)
                .map(user -> ResponseEntity.ok(modelMapper.map(user, UserDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        if (!userService.findOne(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        User user = modelMapper.map(userDTO, User.class);
        user.setId(id);
        user = userService.save(user);
        
        return ResponseEntity.ok(modelMapper.map(user, UserDTO.class));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!userService.findOne(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
