package com.api.payment.dto.usuario;

public record UsuarioResponseLoginDTO(
        String mensagem,
        String nome,
        String token
) {
}
