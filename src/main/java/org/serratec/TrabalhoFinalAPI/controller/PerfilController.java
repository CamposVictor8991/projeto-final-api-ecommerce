package org.serratec.TrabalhoFinalAPI.controller;

import java.util.List;

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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/perfilCliente")
public class PerfilController {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PerfilService perfilService;

    @GetMapping
    public ResponseEntity<List<Perfil>> listarPerfis() {
        List<Perfil> perfis = perfilService.listarPefis();
        return ResponseEntity.ok(perfis);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarPerfil(@RequestBody Perfil perfil) {
        Perfil p = new Perfil(perfil);
        perfilRepository.save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body("Perfil cadastrado com sucesso!");

    }

    @PutMapping("/{id}")
    public ResponseEntity<Perfil> atualizar(@PathVariable Long id, @Valid @RequestBody PerfilInserirDTO perfil) {
        Perfil perfilAtualizado = perfilService.atualizarPerfil(id, perfil);
        if (perfilAtualizado != null) {
            return ResponseEntity.ok(perfilAtualizado);
        }
        return ResponseEntity.notFound().build();
    }
}
