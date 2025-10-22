package com.api.payment.dto.cobranca;

import com.api.payment.enums.StatusCobranca;

public record CobrancaRequestDTO(
		Long idOriginadorCobranca,
		String cpfDestinatario, 
		Double valorCobranca, 
		String descricao,
		StatusCobranca status
		) {
}
