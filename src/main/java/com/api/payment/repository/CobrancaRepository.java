package com.api.payment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.payment.domain.Usuario;

@Repository
public interface CobrancaRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario>findByCpf(String cpf);
}
