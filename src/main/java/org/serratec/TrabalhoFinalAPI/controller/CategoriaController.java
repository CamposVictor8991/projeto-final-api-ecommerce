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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodos() {
        List<Categoria> categorias = categoriaService.listarTodos();
        return ResponseEntity.ok(categorias);
    }

    @PostMapping
    public ResponseEntity<Categoria> inserir(@Valid @RequestBody CategoriaInserirDTO categoria) {
        Categoria novaCategoria = categoriaService.inserir(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaInserirDTO categoria) {
        Categoria categoriaAtualizada = categoriaService.atualizar(id, categoria);
        if (categoriaAtualizada != null) {
            return ResponseEntity.ok(categoriaAtualizada);
        }
        return ResponseEntity.notFound().build();
    }

    //Delete não implementado, pois não é necessário para o projeto final.
}
