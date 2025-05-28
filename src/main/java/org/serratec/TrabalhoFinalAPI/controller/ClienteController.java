package org.serratec.TrabalhoFinalAPI.controller;

import jakarta.validation.Valid;
import org.serratec.TrabalhoFinalAPI.dto.ClienteInserirDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @PostMapping
    public ResponseEntity<ClienteDTO> criar(@Valid @RequestBody ClienteInserirDTO clienteInserirDTO) {
        inserir(clienteInserirDTO);
    }
}
