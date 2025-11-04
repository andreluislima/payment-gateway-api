package com.api.payment.controller;

import com.api.payment.domain.Usuario;
import com.api.payment.dto.usuario.UsuarioRequestDTO;
import com.api.payment.dto.usuario.UsuarioResponseDTO;
import com.api.payment.dto.usuario.UsuarioResponseDeleteDTO;
import com.api.payment.dto.usuario.UsuarioResponseLoginDTO;
import com.api.payment.repository.UsuarioRepository;
import com.api.payment.security.TokenService;
import com.api.payment.service.UsuarioServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioServiceInterface usuarioServiceInterface;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TokenService tokenService;

    @PostMapping("/criarUsuario")
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioRequestDTO dto){
        Optional<Usuario> user = usuarioRepository.findByCpf(dto.cpf());
        if(user.isPresent()){
            return ResponseEntity.badRequest().body("CPF já cadastrado");
        }
        Usuario usuario = usuarioServiceInterface.criarUsuario(dto);
        usuarioRepository.save(usuario);

        String token = tokenService.generateToken(usuario);
        return ResponseEntity.ok(new UsuarioResponseLoginDTO(
                "Usuario criado com  sucesso", usuario.getNome(), token));
    }

    @PutMapping("editar/{id}")
    public ResponseEntity<UsuarioResponseDTO>editarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO dto) {
    Usuario usuarioAtualizado = usuarioServiceInterface.editarUsuario(id, dto);

    return ResponseEntity.ok(new UsuarioResponseDTO(
            "Usuário editado com sucesso",
            usuarioAtualizado.getNome(),
            usuarioAtualizado.getCpf(),
            usuarioAtualizado.getEmail()
    ));
    }

    @DeleteMapping("remover/{id}")
    public ResponseEntity<UsuarioResponseDeleteDTO>deletarUsuario(@PathVariable Long id) {
        Usuario usuarioDeletado = usuarioServiceInterface.removerUsuario(id);
        return ResponseEntity.ok(new UsuarioResponseDeleteDTO(
                "Usuário deletado com sucesso", usuarioDeletado.getId()
        ));
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>>listarUsuarios(){
        List<Usuario> todosUsuarios = usuarioServiceInterface.listarTodosUsuario();
        return ResponseEntity.ok(todosUsuarios);
    }


}
