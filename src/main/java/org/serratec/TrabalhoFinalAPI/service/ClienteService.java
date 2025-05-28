package org.serratec.TrabalhoFinalAPI.service;

import org.serratec.TrabalhoFinalAPI.domain.Cliente;
import org.serratec.TrabalhoFinalAPI.domain.Endereco;
import org.serratec.TrabalhoFinalAPI.dto.ClienteDTO;
import org.serratec.TrabalhoFinalAPI.dto.ClienteInserirDTO;
import org.serratec.TrabalhoFinalAPI.dto.EnderecoDTO;
import org.serratec.TrabalhoFinalAPI.exception.CpfException;
import org.serratec.TrabalhoFinalAPI.exception.EmailException;
import org.serratec.TrabalhoFinalAPI.exception.SenhaException;
import org.serratec.TrabalhoFinalAPI.repository.ClienteRepository;
import org.serratec.TrabalhoFinalAPI.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired EnderecoService enderecoService;

    public ClienteDTO inserir(ClienteInserirDTO clienteInserirDTO) throws CpfException {
        if ((clienteRepository.findByCpf(clienteInserirDTO.getCpf())) != null) {
            throw new CpfException("CPF já cadastrado!");
        }

        if ((clienteRepository.findByEmail(clienteInserirDTO.getEmail())) != null) {
            throw new EmailException("E-mail já cadastrado!");
        }

        if (!clienteInserirDTO.getSenha().equals(clienteInserirDTO.getConfirmaSenha())) {
            throw new SenhaException("Senhas inseridas são diferentes!");
        }

        String cep = clienteInserirDTO.getCep();

//        enderecoService.buscar(cep);

        Cliente cliente = new Cliente(clienteInserirDTO);

        clienteRepository.save(cliente);

    }
}
