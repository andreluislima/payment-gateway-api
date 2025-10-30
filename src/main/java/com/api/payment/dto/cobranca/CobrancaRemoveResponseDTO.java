package com.api.payment.dto.cobranca;

public record CobrancaRemoveResponseDTO(
		String mensagem,
		Long id,
		Double valorCobranca
		) {
}
