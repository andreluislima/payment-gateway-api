package com.api.payment.dto.login;

public record ResponseDTO (
        String mensagem,
        String nome,
        String token) {

}
