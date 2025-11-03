package com.api.payment.service;

import com.api.payment.domain.Usuario;
import com.api.payment.dto.usuario.UsuarioRequestDTO;
import com.api.payment.dto.usuario.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioServiceInterface {

    public Usuario criarUsuario(UsuarioRequestDTO dto);
    public Usuario editarUsuario(Long id, UsuarioRequestDTO dto);
    public Usuario removerUsuario(Long id);
    public List<Usuario>listarTodosUsuario();
}
