package com.api.payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.payment.domain.Cobranca;
import com.api.payment.domain.Usuario;
import com.api.payment.dto.cobranca.CobrancaRequestDTO;
import com.api.payment.enums.StatusCobranca;
import com.api.payment.repository.CobrancaRepository;
import com.api.payment.repository.UsuarioRepository;

@Service
public class CobrancaServiceImplementation implements CobrancaServiceInterface {

	@Autowired
	private CobrancaRepository cobrancaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public Cobranca criarCobranca(CobrancaRequestDTO dto) {
		
		Usuario usuarioOriginador = usuarioRepository.findById(dto.idOriginadorCobranca())
				.orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
		
		Cobranca cobranca = new Cobranca();
		cobranca.setOriginadorCobranca(usuarioOriginador);
		cobranca.setCpfDestinatario(dto.cpfDestinatario());
		cobranca.setValorCobranca(dto.valorCobranca());
		cobranca.setDescricao(dto.descricao());
		cobranca.setStatus(dto.status());
		
		return cobrancaRepository.save(cobranca);
	}

	@Override
	public List<Cobranca> listaCobrancasEnviadas(Long idOriginador, StatusCobranca status) {
		 
		return cobrancaRepository.findByOriginadorCobranca(idOriginador, status);
	}

	@Override
	public List<Cobranca> listaCobrancasRecebidas(String cpfDestinatario, StatusCobranca status) {
		 
		return cobrancaRepository.findByDestinatarioCobranca(cpfDestinatario, status);
	}

	
}
