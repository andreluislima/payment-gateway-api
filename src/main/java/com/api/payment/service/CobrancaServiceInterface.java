package com.api.payment.service;

import java.util.List;

import com.api.payment.domain.Cobranca;
import com.api.payment.dto.cobranca.CobrancaRequestDTO;
import com.api.payment.enums.StatusCobranca;

public interface CobrancaServiceInterface {

	public Cobranca criarCobranca(CobrancaRequestDTO dto);
	public List<Cobranca> listaCobrancasEnviadas(Long idOriginador, StatusCobranca status);
	public List<Cobranca> listaCobrancasRecebidas(String cpfDestinatario, StatusCobranca status);
	public List<Cobranca>listaCobrancas();
	public Cobranca buscarCobrancaPorId(Long id);
	public Cobranca removeCobranca(Long id, Long idOriginador);
	
}
