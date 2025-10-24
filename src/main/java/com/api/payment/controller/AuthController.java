package com.api.payment.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body) {

		try {
			 Usuario user = userRepository.findByCpf(body.cpf())
					 .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
			 
			 if(!passwordEncoder.matches(body.senha(), user.getSenha())) {
				 throw new BadCredentialsException("Senha inválida");
			 }
			 
			 String token = tokenService.generateToken(user);
			 ResponseDTO response = new ResponseDTO("Acesso autorizado", user.getNome(), token);
			 return ResponseEntity.ok(response);
					 
		} catch (UsernameNotFoundException e) {
		
			ResponseDTO error = new ResponseDTO(e.getMessage(), null, null);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
			
		}catch (BadCredentialsException e) {
			
			ResponseDTO errorResponse = new ResponseDTO(e.getMessage(), null, null);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
			
		}catch (Exception e) {
			 ResponseDTO errorResponse = new ResponseDTO("Erro inesperado" + e.getMessage(), null, null);
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
		
		 
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
