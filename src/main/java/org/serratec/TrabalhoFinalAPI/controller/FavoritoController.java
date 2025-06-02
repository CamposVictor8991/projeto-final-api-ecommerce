package org.serratec.TrabalhoFinalAPI.controller;

import java.util.List;

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




@RestController
@RequestMapping("/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/{clienteId}/{produtoId}")
    public ResponseEntity<Object> adicionarFavorito(@PathVariable Long clienteId, @PathVariable Long produtoId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Cliente usuarioAutenticado = clienteService.acharCliente(username);

        if (!usuarioAutenticado.getId().equals(clienteId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para alterar este usuário.");
        }

        favoritoService.adicionarFavorito(clienteId, produtoId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{clienteId}/listar")
    public ResponseEntity<Object> listarFavoritos(@PathVariable Long clienteId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Cliente usuarioAutenticado = clienteService.acharCliente(username);

        if (!usuarioAutenticado.getId().equals(clienteId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para acessar este usuário.");
        }

        List<Produto> favoritos = favoritoService.listarFavoritos(clienteId);
        return ResponseEntity.ok(favoritos);
    }

    //deleta um item do favorido 
    @DeleteMapping("/{clienteId}/{produtoId}")
    public ResponseEntity<Object> removerFavorito(@PathVariable Long clienteId, @PathVariable Long produtoId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Cliente usuarioAutenticado = clienteService.acharCliente(username);

        if (!usuarioAutenticado.getId().equals(clienteId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para alterar este usuário.");
        }

        favoritoService.removerFavorito(clienteId, produtoId);
        return ResponseEntity.noContent().build();
    }

}

