package com.carneiro.usuario.bussiness;

import com.carneiro.usuario.bussiness.converter.UsuarioConverter;
import com.carneiro.usuario.bussiness.dto.EnderecoDTO;
import com.carneiro.usuario.bussiness.dto.TelefoneDTO;
import com.carneiro.usuario.bussiness.dto.UsuarioDTO;
import com.carneiro.usuario.infrastructure.entity.Endereco;
import com.carneiro.usuario.infrastructure.entity.Telefone;
import com.carneiro.usuario.infrastructure.entity.Usuario;
import com.carneiro.usuario.infrastructure.excepitions.ConfilctException;
import com.carneiro.usuario.infrastructure.excepitions.ResourceNotFoundException;
import com.carneiro.usuario.infrastructure.repository.EnderecoRepository;
import com.carneiro.usuario.infrastructure.repository.TelefoneRepository;
import com.carneiro.usuario.infrastructure.repository.UsuarioRepository;
import com.carneiro.usuario.infrastructure.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {

        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConfilctException("Email já cadastrado!" + email);

            }
        } catch (ConfilctException e) {
            throw new ConfilctException("Email já cadastrado!" + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email){
        try {
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado" + email)));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Email não encontrado" + email);

        }
    }

    public void deletaUsuarioPorEmail(String email){
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizarDadosUsuario(String token, UsuarioDTO dto){
        String email = jwtUtil.extraiEmailToken(token.substring(7));
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado"));
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuarioEntity));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() -> new ResourceNotFoundException("Id não encontrado" + idEndereco));
        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));

    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO){
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(()-> new ResourceNotFoundException("Id não encontrado"));
        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, entity);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    public EnderecoDTO cadastroEndereco(String token, EnderecoDTO enderecoDTO){
        String email = jwtUtil.extraiEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(",Email não encontrado" + email));
        Endereco endereco = usuarioConverter.paraEnderecoEntity(enderecoDTO, usuario.getId());
        Endereco enderecoEntity = enderecoRepository.save(endereco);
        return usuarioConverter.paraEnderecoDTO(enderecoEntity);
    }

    public TelefoneDTO cadastroTelefone(String token, TelefoneDTO telefoneDTO){
        String email = jwtUtil.extraiEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(",Email não encontrado" + email));
        Telefone telefone = usuarioConverter.paraTelefoneEntity(telefoneDTO, usuario.getId());
        Telefone telefoneEntity = telefoneRepository.save(telefone);
        return usuarioConverter.paraTelefoneDTO(telefoneEntity);
    }
}
