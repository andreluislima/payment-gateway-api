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
	
	List<Cobranca> findByOriginadorCobrancaIdAndStatus(Long originadorId, StatusCobranca status);
	List<Cobranca> findByCpfDestinatarioAndStatus(String cpfDestinatario, StatusCobranca status);
}
