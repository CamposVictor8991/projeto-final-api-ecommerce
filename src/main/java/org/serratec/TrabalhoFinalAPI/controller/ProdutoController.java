package org.serratec.TrabalhoFinalAPI.controller;

import java.util.List;

import org.serratec.TrabalhoFinalAPI.domain.Categoria;
import org.serratec.TrabalhoFinalAPI.domain.Produto;
import org.serratec.TrabalhoFinalAPI.dto.ProdutoInserirDTO;
import org.serratec.TrabalhoFinalAPI.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    @Operation(summary = "Listar os produtos", description = "Lista todos os produtos cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna lista de produtos"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<List<Produto>> listarTodos() {
        List<Produto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    @PostMapping
    @Operation(summary = "Inserir produto", description = "Insere um novo produto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna o produto inserido"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Produto> inserir(@Valid @RequestBody ProdutoInserirDTO produto) {
        Produto novoProduto = produtoService.inserir(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar o produto", description = "Atualiza um produto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna o produto atualizado"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoInserirDTO produto) {
        Produto produtoAtualizado = produtoService.atualizar(id, produto);
        if (produtoAtualizado != null) {
            return ResponseEntity.ok(produtoAtualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/relacionados")
    @Operation(summary = "Listar produtos relacionados", description = "Aplica um filtro para listar produtos relacionados a um produto específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna lista de produtos relacionados"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<List<Produto>> listarRelacionados(@PathVariable Long id) {
        List<Produto> relacionados = produtoService.listarRelacionados(id);
        return ResponseEntity.ok(relacionados);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um produto", description = "Remove um produto existente")
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
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deleted = produtoService.deletar(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
