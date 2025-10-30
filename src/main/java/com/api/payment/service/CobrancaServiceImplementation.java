package com.api.payment.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.payment.domain.Cobranca;
import com.api.payment.domain.Usuario;
import com.api.payment.dto.cobranca.CobrancaRequestDTO;
import com.api.payment.enums.StatusCobranca;
import com.api.payment.exception.OperationException;
import com.api.payment.exception.ResourceNotFoundException;
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
				.orElseThrow(()-> new ResourceNotFoundException("Originador da cobrança não encontrado"));
		
		try {
			Cobranca cobranca = new Cobranca();
			cobranca.setOriginadorCobranca(usuarioOriginador);
			cobranca.setCpfDestinatario(dto.cpfDestinatario());
			cobranca.setValorCobranca(dto.valorCobranca());
			cobranca.setDescricao(dto.descricao());
			cobranca.setStatus(dto.status());
			
			return cobrancaRepository.save(cobranca);
		} catch (Exception e) {
			throw new OperationException("Erro ao criar cobrança: " + e.getMessage());
		}
	
	}

	@Override
	public List<Cobranca> listaCobrancasEnviadas(Long idOriginador, StatusCobranca status) {
		
		return cobrancaRepository.findByOriginadorCobrancaIdAndStatus(idOriginador, status);
	}

	@Override
	public List<Cobranca> listaCobrancasRecebidas(String cpfDestinatario, StatusCobranca status) {
		return cobrancaRepository.findByCpfDestinatarioAndStatus(cpfDestinatario, status);
	}

	@Override
	public List<Cobranca> listaCobrancas() {
		List<Cobranca> lista = cobrancaRepository.findAll();
		if(lista.isEmpty()) {
			throw new ResourceNotFoundException("Nenhuma cobrança cadastrada");
		}else {
			return lista;
		}
			
	}


	@Override
	public Cobranca buscarCobrancaPorId(Long id) {
		
		return cobrancaRepository.findById(id).orElseThrow(
				()-> new RuntimeException("Cobranca nao encontrada"));
	}

	@Override
	public Cobranca removeCobranca(Long id, Long idOriginador) {
		 Cobranca cobranca = cobrancaRepository.findById(id).orElseThrow(
				 ()-> new RuntimeException("Cobranca nao encontrada"));
		 
		 if(!Objects.equals(cobranca.getOriginadorCobranca().getId(), idOriginador)) {
			 throw new RuntimeException("Voce nao tem permissao para remover essa cobranca");
			 
		 }else if (cobranca.getStatus() != StatusCobranca.PENDENTE) {
			throw new RuntimeException("Apenas cobrancas PENDENTES podem ser removidas");
		}
		 
		 cobrancaRepository.delete(cobranca);
		 
		return cobranca;
	}

	
	
}
