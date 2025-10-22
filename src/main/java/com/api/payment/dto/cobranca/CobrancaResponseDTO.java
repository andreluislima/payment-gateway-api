package com.api.payment.dto.cobranca;

import com.api.payment.domain.Usuario;

public record CobrancaResponseDTO(
		Long id,
		Double valor,
		String descricao,
		Usuario nomeOriginadorCobranca,
		Usuario emailOriginadorCobranca
		) {

}
