package com.api.payment.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.api.payment.domain.Cobranca;
import com.api.payment.enums.StatusCobranca;

public class CobrancaTest {

	@Test
	void definirValoresCorretamente (){
		Cobranca cobranca = new Cobranca();
		cobranca.setCpfDestinatario("12345678900");
		cobranca.setDescricao("Servico prestado");
		cobranca.setValorCobranca(150.00);
		cobranca.setStatus(StatusCobranca.PAGA);
		
		assertEquals("12345678900", cobranca.getCpfDestinatario());
		assertEquals("Servico prestado", cobranca.getDescricao());
		assertEquals(150.00, cobranca.getValorCobranca());
		assertEquals(StatusCobranca.PAGA, cobranca.getStatus());
	}
	                                    
	@Test
	void terStatusPadraoPendente() {
		Cobranca cobranca = new Cobranca();
		assertEquals(StatusCobranca.PENDENTE, cobranca.getStatus());
	}
}






