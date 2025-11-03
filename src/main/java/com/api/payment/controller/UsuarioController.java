package com.api.payment.controller;

import com.api.payment.domain.Usuario;
import com.api.payment.dto.usuario.UsuarioRequestDTO;
import com.api.payment.dto.usuario.UsuarioResponseDTO;
import com.api.payment.repository.UsuarioRepository;
import com.api.payment.security.TokenService;
import com.api.payment.service.UsuarioServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping
	public ResponseEntity<String>getUser(){
		return ResponseEntity.ok("Success");
	}

    @PostMapping("/criarUsuario")
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioRequestDTO dto){
        Optional<Usuario> user = usuarioRepository.findByCpf(dto.cpf());

        if(user.isPresent()){
            return ResponseEntity.badRequest().body("CPF já cadastrado");
        }
        Usuario usuario = usuarioServiceInterface.criarUsuario(dto);
        usuarioRepository.save(usuario);

        String token = tokenService.generateToken(usuario);
        return ResponseEntity.ok(new UsuarioResponseDTO(
                "Usuario criado com  sucesso", usuario.getNome(), token));
    }

}
