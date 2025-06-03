package org.serratec.TrabalhoFinalAPI.controller;

import java.util.List;
import java.util.Set;

import org.serratec.TrabalhoFinalAPI.domain.ClientePerfil;
import org.serratec.TrabalhoFinalAPI.domain.Endereco;
import org.serratec.TrabalhoFinalAPI.domain.Categoria;
import org.serratec.TrabalhoFinalAPI.domain.Cliente;
import org.serratec.TrabalhoFinalAPI.dto.ClienteDTO;
import org.serratec.TrabalhoFinalAPI.dto.ClienteEditarDTO;
import org.serratec.TrabalhoFinalAPI.dto.ClienteInserirDTO;
import org.serratec.TrabalhoFinalAPI.dto.EditarStatusDTO;
import org.serratec.TrabalhoFinalAPI.dto.EnderecoInserirDTO;
import org.serratec.TrabalhoFinalAPI.dto.PedidoDTO;
import org.serratec.TrabalhoFinalAPI.dto.PedidoInserirDTO;
import org.serratec.TrabalhoFinalAPI.exception.CpfException;
import org.serratec.TrabalhoFinalAPI.exception.EmailException;
import org.serratec.TrabalhoFinalAPI.exception.SenhaException;
import org.serratec.TrabalhoFinalAPI.service.ClienteService;
import org.serratec.TrabalhoFinalAPI.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Listar todos os clientes", description = "A resposta lista os dados de todos os clientes, com seus endereços associados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna todos os clientes"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Exibir um cliente", description = "A resposta exibe os dados de um cliente específico, com seus endereços associados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna o cliente com o ID especificado"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Object> exibirPorId(@PathVariable Long id) {

        // Obtém o nome de usuário autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Aqui você pega o usuário autenticado (do banco) e verifica o ID
        Cliente usuarioAutenticado = clienteService.acharCliente(username);

        boolean isAdmin = usuarioAutenticado.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMINISTRADOR"));

        if (!usuarioAutenticado.getId().equals(id) && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para acessar este usuário.");
        }

        ClienteDTO clienteDTO = clienteService.exibirUm(id);
        if (clienteDTO != null) {
            return ResponseEntity.ok(clienteDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Criar um cliente", description = "A resposta cria um novo cliente e retorna seus dados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna o cliente criado"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Object> criar(@Valid @RequestBody ClienteInserirDTO clienteInserirDTO) {
        try {
            ClienteDTO clienteDTO = clienteService.inserir(clienteInserirDTO);
            return ResponseEntity.ok(clienteDTO);
        } catch (CpfException | EmailException | SenhaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edita um cliente", description = "A resposta atualiza os dados de um cliente específico e retorna os dados atualizados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna o cliente atualizado"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Object> editar(
            @Valid @RequestBody ClienteEditarDTO clienteEditarDTO, @PathVariable Long id) {
//        try {

        // Obtém o nome de usuário autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Aqui você pega o usuário autenticado (do banco) e verifica o ID
        Cliente usuarioAutenticado = clienteService.acharCliente(username);

        boolean isAdmin = usuarioAutenticado.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMINISTRADOR"));

        if (!usuarioAutenticado.getId().equals(id) && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para acessar este usuário.");
        }

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
    @Operation(summary = "Deleta um cliente", description = "A resposta deleta um cliente específico e retorna o status da operação.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna o status da exclusão do cliente"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Object> excluir(@PathVariable Long id) {

        // Obtém o nome de usuário autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Aqui você pega o usuário autenticado (do banco) e verifica o ID
        Cliente usuarioAutenticado = clienteService.acharCliente(username);

        boolean isAdmin = usuarioAutenticado.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMINISTRADOR"));

        if (!usuarioAutenticado.getId().equals(id) && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para acessar este usuário.");
        }

        boolean removido = clienteService.excluir(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/enderecos")
    @Operation(summary = "Cria um endereço no cliente", description = "A resposta cria um novo endereço para um cliente específico e retorna os dados do endereço criado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna o endereço criado"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Object> criarEndereco(@PathVariable Long id, @Valid @RequestBody EnderecoInserirDTO enderecoInserirDTO) {

        // Obtém o nome de usuário autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Aqui você pega o usuário autenticado (do banco) e verifica o ID
        Cliente usuarioAutenticado = clienteService.acharCliente(username);

        boolean isAdmin = usuarioAutenticado.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMINISTRADOR"));

        if (!usuarioAutenticado.getId().equals(id) && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para alterar este usuário.");
        }

        Endereco endereco = clienteService.criarEndereco(id, enderecoInserirDTO);
        if (endereco == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(endereco);
    }

    // Pedidos
    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/{id}/pedidos")
    @Operation(summary = "Listar pedidos", description = "A resposta retorna os pedidos associados a um cliente específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna os pedidos do cliente com o ID especificado"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Object> listarPedidos(@PathVariable Long id) {

        // Obtém o nome de usuário autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Aqui você pega o usuário autenticado (do banco) e verifica o ID
        Cliente usuarioAutenticado = clienteService.acharCliente(username);

        boolean isAdmin = usuarioAutenticado.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMINISTRADOR"));

        if (!usuarioAutenticado.getId().equals(id) && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para acessar este usuário.");
        }

        List<PedidoDTO> pedidoDTO = pedidoService.listarPedidos(id);

        if (null != pedidoDTO) {
            return ResponseEntity.ok(pedidoDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/pedidos")
    @Operation(summary = "Inserir um pedido", description = " A resposta cria um novo pedido para um cliente específico e retorna os dados do pedido criado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna o pedido criado"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Object> inserirPedido(@PathVariable Long id, @RequestBody PedidoInserirDTO pedidoInserirDTO) {
        // Obtém o nome de usuário autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Aqui você pega o usuário autenticado (do banco) e verifica o ID
        Cliente usuarioAutenticado = clienteService.acharCliente(username);

        boolean isAdmin = usuarioAutenticado.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMINISTRADOR"));

        if (!usuarioAutenticado.getId().equals(id) && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para acessar este usuário.");
        }

        PedidoDTO pedidoDTO = pedidoService.inserirPedido(id, pedidoInserirDTO);
        return ResponseEntity.ok(pedidoDTO);
    }

    @PutMapping("/{id}/pedidos/{id_pedido}")
    @Operation(summary = "Edita pedido", description = "A resposta atualiza os dados de um pedido específico de um cliente e retorna os dados atualizados do pedido.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna pedido atualizado"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Object> editarPedido(@PathVariable Long id, @PathVariable Long id_pedido, @Valid @RequestBody PedidoInserirDTO pedidoInserirDTO) {
        // Obtém o nome de usuário autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Aqui você pega o usuário autenticado (do banco) e verifica o ID
        Cliente usuarioAutenticado = clienteService.acharCliente(username);

        boolean isAdmin = usuarioAutenticado.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMINISTRADOR"));

        if (!usuarioAutenticado.getId().equals(id) && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para acessar este usuário.");
        }

        PedidoDTO pedidoDTO = pedidoService.editarPedido(id, id_pedido, pedidoInserirDTO);
        return ResponseEntity.ok(pedidoDTO);
    }

    // Status do pedido
    @PutMapping("/{id}/pedidos/{id_pedido}/status")
    @Operation(summary = "Edita status do pedido", description = "A resposta atualiza o status de um pedido específico de um cliente e retorna os dados atualizados do pedido.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = Categoria.class), mediaType = "application/json")},
                description = "Retorna o pedido atualizado"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso o recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
    })
    public ResponseEntity<Object> editarStatus(@PathVariable Long id, @PathVariable Long id_pedido, @Valid @RequestBody EditarStatusDTO editarStatusDTO) {
        // Obtém o nome de usuário autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Aqui você pega o usuário autenticado (do banco) e verifica o ID
        Cliente usuarioAutenticado = clienteService.acharCliente(username);

        boolean isAdmin = usuarioAutenticado.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMINISTRADOR"));

        if (!usuarioAutenticado.getId().equals(id) && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para acessar este usuário.");
        }

        PedidoDTO pedidoDTO = pedidoService.editarStatus(id, id_pedido, editarStatusDTO);
        return ResponseEntity.ok(pedidoDTO);
    }

}
