package com.api.payment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.payment.domain.Cobranca;
import com.api.payment.domain.Usuario;
import com.api.payment.dto.cobranca.CobrancaRequestDTO;
import com.api.payment.enums.StatusCobranca;

@Repository
public interface CobrancaRepository extends JpaRepository<Cobranca, Long> {
	
	Optional<Usuario>findByCpf(String cpf);
	
	Cobranca criarCobranca(CobrancaRequestDTO dto);
	List<Cobranca> findByOriginadorCobranca(Long idOriginador, StatusCobranca status);
	List<Cobranca> findByDestinatarioCobranca(String cpfDestinatario, StatusCobranca status);
}
