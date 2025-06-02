package org.serratec.TrabalhoFinalAPI.controller;

import java.util.List;

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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        List<Produto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    @PostMapping
    public ResponseEntity<Produto> inserir(@Valid @RequestBody ProdutoInserirDTO produto) {
        Produto novoProduto = produtoService.inserir(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoInserirDTO produto) {
        Produto produtoAtualizado = produtoService.atualizar(id, produto);
        if (produtoAtualizado != null) {
            return ResponseEntity.ok(produtoAtualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{id}/relacionados")
    public ResponseEntity<List<Produto>> listarRelacionados(@PathVariable Long id) {
        List<Produto> relacionados = produtoService.listarRelacionados(id);
        return ResponseEntity.ok(relacionados);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deleted = produtoService.deletar(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    

}
