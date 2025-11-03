package com.api.payment.service;

import com.api.payment.domain.Usuario;
import com.api.payment.dto.usuario.UsuarioRequestDTO;
import com.api.payment.dto.usuario.UsuarioResponseDTO;
import com.api.payment.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImplementation implements UsuarioServiceInterface{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario criarUsuario(UsuarioRequestDTO dto) {
        if(usuarioRepository.findByCpf(dto.cpf()).isPresent()){
            throw new RuntimeException(dto.cpf() + " CPF já cadastrado");
        }

        Usuario user = new Usuario();
        user.setNome(dto.nome());
        user.setCpf(dto.cpf());
        user.setEmail(dto.email());
        user.setSenha(dto.senha());

        return usuarioRepository.save(user);
        
    }

    @Override
    public Usuario editarUsuario(Long id, UsuarioRequestDTO dto) {
        Usuario user = usuarioRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Usuário não encontrado.")
        );
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setCpf(dto.cpf());
        usuario.setEmail(dto.email());

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario removerUsuario(Long id) {

    Usuario user = usuarioRepository.findById(id).orElseThrow(
            ()->new RuntimeException("Usuário não encontrado.")
    );
    usuarioRepository.delete(user);

    return user;
    }

    @Override
    public List<Usuario> listarTodosUsuario() {
        List<Usuario> lista = usuarioRepository.findAll();
        if(lista.isEmpty()){
            throw new RuntimeException("Nenhum usuaário encontrado.");
        }
        return lista;
    }
}
