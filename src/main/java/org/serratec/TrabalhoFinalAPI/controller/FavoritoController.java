package org.serratec.TrabalhoFinalAPI.controller;

import java.util.List;

import org.serratec.TrabalhoFinalAPI.domain.Categoria;
import org.serratec.TrabalhoFinalAPI.domain.Cliente;
import org.serratec.TrabalhoFinalAPI.domain.Produto;
import org.serratec.TrabalhoFinalAPI.service.ClienteService;
import org.serratec.TrabalhoFinalAPI.service.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/{clienteId}/{produtoId}")
    @Operation(summary = "Adicionar ao favorito", description = "Adiciona um produto aos favoritos de um cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna o produto adicionado aos favoritos"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Object> adicionarFavorito(@PathVariable Long clienteId, @PathVariable Long produtoId) {
        // Obtém o nome de usuário autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Aqui você pega o usuário autenticado (do banco) e verifica o ID
        Cliente usuarioAutenticado = clienteService.acharCliente(username);

        boolean isAdmin = usuarioAutenticado.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMINISTRADOR"));

        if (!usuarioAutenticado.getId().equals(clienteId) && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para acessar este usuário.");
        }

        favoritoService.adicionarFavorito(clienteId, produtoId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{clienteId}/listar")
    @Operation(summary = "Listar os favoritos", description = "Lista os produtos favoritos de um cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna a lista de produtos favoritos do cliente"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Object> listarFavoritos(@PathVariable Long clienteId) {
        // Obtém o nome de usuário autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Aqui você pega o usuário autenticado (do banco) e verifica o ID
        Cliente usuarioAutenticado = clienteService.acharCliente(username);

        boolean isAdmin = usuarioAutenticado.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMINISTRADOR"));

        if (!usuarioAutenticado.getId().equals(clienteId) && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para acessar este usuário.");
        }

        List<Produto> favoritos = favoritoService.listarFavoritos(clienteId);
        return ResponseEntity.ok(favoritos);
    }

    //deleta um item do favorido 
    @DeleteMapping("/{clienteId}/{produtoId}")
    @Operation(summary = "Deleta um favorito", description = "Remove um produto dos favoritos de um cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna sucesso na remoção do favorito"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Object> removerFavorito(@PathVariable Long clienteId, @PathVariable Long produtoId) {
        // Obtém o nome de usuário autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Aqui você pega o usuário autenticado (do banco) e verifica o ID
        Cliente usuarioAutenticado = clienteService.acharCliente(username);

        boolean isAdmin = usuarioAutenticado.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMINISTRADOR"));

        if (!usuarioAutenticado.getId().equals(clienteId) && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para acessar este usuário.");
        }

        favoritoService.removerFavorito(clienteId, produtoId);
        return ResponseEntity.noContent().build();
    }

}
