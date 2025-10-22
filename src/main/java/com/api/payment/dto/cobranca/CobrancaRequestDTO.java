package com.api.payment.dto.cobranca;

public record CobrancaRequestDTO(
		Long idOriginadorCobranca,
		String cpfDestinatario, 
		Double valorCobranca, 
		String descricao) {
}
