package com.carneiro.usuario.bussiness.converter;

import com.carneiro.usuario.bussiness.dto.EnderecoDTO;
import com.carneiro.usuario.bussiness.dto.TelefoneDTO;
import com.carneiro.usuario.bussiness.dto.UsuarioDTO;
import com.carneiro.usuario.infrastructure.entity.Endereco;
import com.carneiro.usuario.infrastructure.entity.Telefone;
import com.carneiro.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {
    //“Vou pegar um DTO e transformar em um Usuario”
    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefone(usuarioDTO.getTelefones()))

                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS){
        return enderecoDTOS.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .bairro(enderecoDTO.getBairro())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())

                .build();
    }

    public List<Telefone> paraListaTelefone(List <TelefoneDTO> telefoneDTOS){
        return telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .numeroTelefone(telefoneDTO.getNumeroTelefone())
                .ddd(telefoneDTO.getDdd())

                .build();
    }

//-----------------------------------------------------------------------------

    public UsuarioDTO paraUsuarioDTO(Usuario usuario){
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDTO(usuario.getEnderecos()))
                .telefones(paraListaTelefoneDTO(usuario.getTelefones()))

                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecoDTOS){
        return enderecoDTOS.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco){
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .bairro(endereco.getBairro())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())

                .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List <Telefone> telefoneDTOS){
        return telefoneDTOS.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone){
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .numeroTelefone(telefone.getNumeroTelefone())
                .ddd(telefone.getDdd())

                .build();
    }

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity){
        return Usuario.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome() )
                .id(entity.getId())
                .senha(usuarioDTO.getNome() != null ? usuarioDTO.getSenha() : entity.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())

                .build();
    }

    public Endereco updateEndereco(EnderecoDTO enderecoDTO, Endereco entity){
        return Endereco.builder()
                .id(entity.getId())
                .rua(enderecoDTO.getRua() != null ? enderecoDTO.getRua() : entity.getRua())
                .numero(enderecoDTO.getNumero() != null ? enderecoDTO.getNumero() : entity.getNumero())
                .bairro(enderecoDTO.getBairro() != null ? enderecoDTO.getBairro() : entity.getBairro())
                .cidade(enderecoDTO.getCidade() != null ? enderecoDTO.getCidade() : entity.getCidade())
                .estado(enderecoDTO.getEstado() != null ? enderecoDTO.getEstado() : entity.getEstado())
                .cep(enderecoDTO.getCep() != null ? enderecoDTO.getCep() : entity.getCep())


                .build();
    }

    public Telefone updateTelefone(TelefoneDTO telefoneDTO, Telefone entity){
        return Telefone.builder()
                .id(entity.getId())
                .numeroTelefone(telefoneDTO.getNumeroTelefone() != null ? telefoneDTO.getNumeroTelefone() : entity.getNumeroTelefone())
                .ddd(telefoneDTO.getDdd() != null ? telefoneDTO.getDdd() : entity.getDdd())

                .build();
    }

    public Endereco paraEnderecoEntity(EnderecoDTO enderecoDTO, Long idUsuario){
       return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .bairro(enderecoDTO.getBairro())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .usuarioId(idUsuario)

                .build();
    }

    public Telefone paraTelefoneEntity(TelefoneDTO telefoneDTO, Long idUsuario){
       return Telefone.builder()
                .numeroTelefone(telefoneDTO.getNumeroTelefone())
                .ddd(telefoneDTO.getDdd())
                .usuarioId(idUsuario)

                .build();
    }
}
