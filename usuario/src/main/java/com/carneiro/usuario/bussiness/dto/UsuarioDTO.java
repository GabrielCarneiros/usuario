package com.carneiro.usuario.bussiness.dto;


import com.carneiro.usuario.infrastructure.entity.Endereco;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
    private String nome;
    private String email;
    private String senha;
    private List <EnderecoDTO> enderecos;
    private List <TelefoneDTO> telefones;
}
