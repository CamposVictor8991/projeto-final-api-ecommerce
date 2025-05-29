package org.serratec.TrabalhoFinalAPI.controller;

import java.util.List;

import org.serratec.TrabalhoFinalAPI.domain.Endereco;
import org.serratec.TrabalhoFinalAPI.dto.ClienteDTO;
import org.serratec.TrabalhoFinalAPI.dto.ClienteEditarDTO;
import org.serratec.TrabalhoFinalAPI.dto.ClienteInserirDTO;
import org.serratec.TrabalhoFinalAPI.dto.EnderecoInserirDTO;
import org.serratec.TrabalhoFinalAPI.exception.CpfException;
import org.serratec.TrabalhoFinalAPI.exception.EmailException;
import org.serratec.TrabalhoFinalAPI.exception.SenhaException;
import org.serratec.TrabalhoFinalAPI.repository.ClienteRepository;
import org.serratec.TrabalhoFinalAPI.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> exibirPorId(@PathVariable Long id) {
        ClienteDTO clienteDTO = clienteService.exibirUm(id);
        if (clienteDTO != null) {
            return ResponseEntity.ok(clienteDTO);
        };
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Object> criar(@Valid @RequestBody ClienteInserirDTO clienteInserirDTO) {
        try {
            ClienteDTO clienteDTO = clienteService.inserir(clienteInserirDTO);
            return ResponseEntity.ok(clienteDTO);
        } catch (CpfException | EmailException | SenhaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> editar(
        @Valid @RequestBody ClienteEditarDTO clienteEditarDTO, @PathVariable Long id) {
//        try {
            ClienteDTO clienteDTO = clienteService.editarCadastro(clienteEditarDTO, id);
            if (clienteDTO == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(clienteDTO);
//        } catch (CpfException | EmailException | SenhaException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
    	boolean removido = clienteService.excluir(id);
		if (removido) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

    @PostMapping("/{id}/enderecos")
    public ResponseEntity<Endereco> criarEndereco(@PathVariable Long id, @Valid @RequestBody EnderecoInserirDTO enderecoInserirDTO){
        Endereco endereco = clienteService.criarEndereco(id, enderecoInserirDTO);
        if(endereco == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(endereco);
    }
        
      
    
}
