package org.serratec.TrabalhoFinalAPI.controller;

import java.util.List;

import org.serratec.TrabalhoFinalAPI.domain.Categoria;
import org.serratec.TrabalhoFinalAPI.dto.CategoriaInserirDTO;
import org.serratec.TrabalhoFinalAPI.service.CategoriaService;
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
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    @Operation(summary = "Listar todas as categorias de produto", description = "A resposta lista os dados de todas as categorias, com seus produtos associados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna todas as categorias"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<List<Categoria>> listarTodos() {
        List<Categoria> categorias = categoriaService.listarTodos();
        return ResponseEntity.ok(categorias);
    }

    @PostMapping
    @Operation(summary = "Insere uma categoria de produto", description = "A resposta retorna os dados da categoria inserida.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna a categoria inserida"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Categoria> inserir(@Valid @RequestBody CategoriaInserirDTO categoria) {
        Categoria novaCategoria = categoriaService.inserir(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edita uma categoria de produto", description = "A resposta retorna os dados da categoria atualizada.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna a categoria atualizada"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Categoria> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaInserirDTO categoria) {
        Categoria categoriaAtualizada = categoriaService.atualizar(id, categoria);
        if (categoriaAtualizada != null) {
            return ResponseEntity.ok(categoriaAtualizada);
        }
        return ResponseEntity.notFound().build();
    }

    //Delete não implementado, pois não é necessário para o projeto final.
}
