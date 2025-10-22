package com.api.payment.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.api.payment.domain.Usuario;
import com.api.payment.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = this.userRepository.findByCpf(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
		return new org.springframework.security.core.userdetails.User(user.getCpf(), user.getSenha(), new ArrayList<>());
	}
}
