package com.api.payment.dto.cobranca;

import com.api.payment.domain.Usuario;
import com.api.payment.enums.StatusCobranca;

public record CobrancaResponseDTO(
		Long id,
		Double valor,
		String descricao,
		Usuario nomeOriginadorCobranca,
		Usuario emailOriginadorCobranca,
		StatusCobranca status
		) {

}
