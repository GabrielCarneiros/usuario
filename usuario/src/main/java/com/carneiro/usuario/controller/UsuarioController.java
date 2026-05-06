package com.carneiro.usuario.controller;

import com.carneiro.usuario.ViaCepService;
import com.carneiro.usuario.bussiness.UsuarioService;
import com.carneiro.usuario.bussiness.dto.EnderecoDTO;
import com.carneiro.usuario.bussiness.dto.TelefoneDTO;
import com.carneiro.usuario.bussiness.dto.UsuarioDTO;
import com.carneiro.usuario.infrastructure.client.ViaCepDTO;
import com.carneiro.usuario.infrastructure.entity.Usuario;
import com.carneiro.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ViaCepService viaCepService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
        );
        return jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity <UsuarioDTO> buscaUsuarioPorEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity <Void> deletaUsuarioPorEmail(@PathVariable String email){
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizaDadosUsuario(@RequestBody UsuarioDTO dto, @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizarDadosUsuario(token, dto));
    }

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizaDadosEndereco(@RequestBody EnderecoDTO dto, @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, dto));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizaTelefone(@RequestBody TelefoneDTO dto, @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto));
    }

    @PostMapping("endereco")
    public ResponseEntity<EnderecoDTO> cadastraEndereco(@RequestBody EnderecoDTO dto, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastroEndereco(token, dto));
    }

    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDTO> cadastraTelefone(@RequestBody TelefoneDTO dto, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastroTelefone(token, dto));
    }

    @GetMapping("/endereco{cep}")
    public ResponseEntity<ViaCepDTO> buscaDadosCep(@PathVariable("cep") String cep){
        return ResponseEntity.ok(viaCepService.buscaDadosEndereco(cep));
    }
}
