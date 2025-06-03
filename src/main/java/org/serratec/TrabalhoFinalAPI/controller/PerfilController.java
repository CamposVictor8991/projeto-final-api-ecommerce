package org.serratec.TrabalhoFinalAPI.controller;

import java.util.List;

import org.serratec.TrabalhoFinalAPI.domain.Categoria;
import org.serratec.TrabalhoFinalAPI.domain.Perfil;
import org.serratec.TrabalhoFinalAPI.dto.PerfilInserirDTO;
import org.serratec.TrabalhoFinalAPI.repository.PerfilRepository;
import org.serratec.TrabalhoFinalAPI.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/perfilCliente")
public class PerfilController {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PerfilService perfilService;

    @GetMapping
    @Operation(summary = "Listar o perfil", description = "Lista todos os perfis de clientes cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna lista de perfis"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<List<Perfil>> listarPerfis() {
        List<Perfil> perfis = perfilService.listarPefis();
        return ResponseEntity.ok(perfis);
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastra um perfil", description = "Adiciona um novo perfil de cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna categoria atualizada"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<String> cadastrarPerfil(@RequestBody Perfil perfil) {
        Perfil p = new Perfil(perfil);
        perfilRepository.save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body("Perfil cadastrado com sucesso!");

    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar o perfil", description = "Atualiza um perfil de cliente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna perfil atualizado"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Perfil> atualizar(@PathVariable Long id, @Valid @RequestBody PerfilInserirDTO perfil) {
        Perfil perfilAtualizado = perfilService.atualizarPerfil(id, perfil);
        if (perfilAtualizado != null) {
            return ResponseEntity.ok(perfilAtualizado);
        }
        return ResponseEntity.notFound().build();
    }
}
