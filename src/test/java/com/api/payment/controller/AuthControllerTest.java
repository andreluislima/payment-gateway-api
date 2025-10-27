package com.api.payment.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.api.payment.controller.AuthController;
import com.api.payment.domain.Usuario;
import com.api.payment.repository.UsuarioRepository;
import com.api.payment.security.TokenService;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private TokenService tokenService;

    // ✅ Cenário positivo: login válido
    @Test
    void retornarTokenQuandoLoginValido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setCpf("12345678900");
        usuario.setSenha("user123@");
        usuario.setNome("userTeste");

        when(usuarioRepository.findByCpf("12345678900")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senha", "user123@")).thenReturn(true);
        when(tokenService.generateToken(usuario)).thenReturn("fake-token");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cpf\":\"12345678900\",\"senha\":\"senha\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Acesso autorizado"))
                .andExpect(jsonPath("$.token").value("fake-token"));
    }

    // ❌ Cenário negativo 1: usuário não encontrado
    @Test
    void retornarNotFoundQuandoUsuarioNaoExiste() throws Exception {
        when(usuarioRepository.findByCpf("99999999999")).thenReturn(Optional.empty());

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cpf\":\"99999999999\",\"senha\":\"senha\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Usuário não encontrado"));
    }

    // ❌ Cenário negativo 2: senha incorreta
    @Test
    void retornarUnauthorizedQuandoSenhaIncorreta() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setCpf("12345678900");
        usuario.setSenha("senhaCorreta");
        usuario.setNome("userTeste");

        when(usuarioRepository.findByCpf("12345678900")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senhaErrada", "senhaCorreta")).thenReturn(false);

        // Simula o comportamento da sua classe de serviço ao lançar exceção
        when(passwordEncoder.matches("senha", "senhaCorreta"))
                .thenThrow(new BadCredentialsException("Senha inválida."));

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cpf\":\"12345678900\",\"senha\":\"senha\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.mensagem").value("Acesso negado"));
    }
}
