package com.carneiro.usuario.bussiness.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TelefoneDTO {

    private Long id;
    private String numeroTelefone;
    private String ddd;
}

