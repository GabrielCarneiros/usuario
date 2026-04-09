package com.carneiro.usuario.bussiness;

import com.carneiro.usuario.bussiness.converter.UsuarioConverter;
import com.carneiro.usuario.bussiness.dto.UsuarioDTO;
import com.carneiro.usuario.infrastructure.entity.Usuario;
import com.carneiro.usuario.infrastructure.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

}
