package com.api.payment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.payment.domain.Cobranca;
import com.api.payment.dto.cobranca.CobrancaRequestDTO;
import com.api.payment.enums.StatusCobranca;
import com.api.payment.service.CobrancaServiceInterface;

@RestController
@RequestMapping("/cobranca")
public class CobrancaController {

	@Autowired
	private CobrancaServiceInterface cobrancaServiceInterface;
	
	@PostMapping("/criarCobranca")
	public ResponseEntity<Cobranca> criarCobranca(@RequestBody CobrancaRequestDTO dto){
		Cobranca cobranca = cobrancaServiceInterface.criarCobranca(dto);
		return ResponseEntity.ok(cobranca);
	}
	
	@GetMapping("/enviada")
	public ResponseEntity<List<Cobranca>>listarEnviadas(@RequestParam Long idOriginador, 
						@RequestParam StatusCobranca status){
		
		return ResponseEntity.ok(cobrancaServiceInterface.listaCobrancasEnviadas(idOriginador, status));
	}
	
	@GetMapping("/recebida")
	public ResponseEntity<List<Cobranca>>listarRecebidas(@RequestParam String cpfDestinatario,
						@RequestParam StatusCobranca status
			){
		return ResponseEntity.ok(cobrancaServiceInterface.listaCobrancasRecebidas(cpfDestinatario, status));
	}
	
	
	
}
