package org.serratec.TrabalhoFinalAPI.controller;

import java.util.List;

import org.serratec.TrabalhoFinalAPI.domain.Produto;
import org.serratec.TrabalhoFinalAPI.service.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/{clienteId}/{produtoId}")
    public ResponseEntity<Void> adicionarFavorito(@PathVariable Long clienteId, @PathVariable Long produtoId) {
        favoritoService.adicionarFavorito(clienteId, produtoId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    @GetMapping("/{clienteId}/listar")
    public ResponseEntity<List<Produto>> listarFavoritos(@PathVariable Long clienteId) {
        List<Produto> favoritos = favoritoService.listarFavoritos(clienteId);
        return ResponseEntity.ok(favoritos);
    }

    //deleta um item do favorido 
    @DeleteMapping("/{clienteId}/{produtoId}")
    public ResponseEntity<Void> removerFavorito(@PathVariable Long clienteId, @PathVariable Long produtoId) {
        favoritoService.removerFavorito(clienteId, produtoId);
        return ResponseEntity.noContent().build();
    }

}

