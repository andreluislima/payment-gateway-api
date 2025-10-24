package com.api.payment.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.payment.domain.Usuario;
import com.api.payment.dto.login.LoginRequestDTO;
import com.api.payment.dto.login.RegisterRequestDTO;
import com.api.payment.dto.login.ResponseDTO;
import com.api.payment.repository.UsuarioRepository;
import com.api.payment.security.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UsuarioRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
		
		Usuario user = this.userRepository.findByCpf(body.cpf()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		if(!passwordEncoder.matches(body.senha(),user.getSenha())) {
			throw new BadCredentialsException("Senha inválida.");
		}
		
		String token = tokenService.generateToken(user);
		return ResponseEntity.ok(new ResponseDTO("Acesso Autorizado", user.getNome(), token));
 
	
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {
		
		Optional<Usuario>user = this.userRepository.findByCpf(body.cpf());
		
		if(user.isEmpty()) {	
			Usuario newUser = new Usuario();
			newUser.setNome(body.nome());
			newUser.setCpf(body.cpf());
			newUser.setEmail(body.email());
			newUser.setSenha(passwordEncoder.encode(body.senha()));
			this.userRepository.save(newUser);
			
 				String token = this.tokenService.generateToken(newUser);
				return ResponseEntity.ok(new ResponseDTO("Usuário criado com sucesso", newUser.getNome(), token));
		}
		return ResponseEntity.badRequest().build();
	}
}
