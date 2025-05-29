package org.serratec.TrabalhoFinalAPI.service;

import org.serratec.TrabalhoFinalAPI.domain.Cliente;
import org.serratec.TrabalhoFinalAPI.domain.Endereco;
import org.serratec.TrabalhoFinalAPI.dto.ClienteDTO;
import org.serratec.TrabalhoFinalAPI.dto.ClienteEditarDTO;
import org.serratec.TrabalhoFinalAPI.dto.ClienteInserirDTO;
import org.serratec.TrabalhoFinalAPI.exception.CpfException;
import org.serratec.TrabalhoFinalAPI.exception.EmailException;
import org.serratec.TrabalhoFinalAPI.exception.SenhaException;
import org.serratec.TrabalhoFinalAPI.repository.ClienteRepository;
import org.serratec.TrabalhoFinalAPI.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.TrabalhoFinalAPI.dto.EnderecoInserirDTO;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoService enderecoService;
    
    //Fazer envio de email  
    public ClienteDTO inserir(ClienteInserirDTO clienteInserirDTO) throws CpfException, EmailException, SenhaException {
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
        Endereco endereco = enderecoService.adicionarEndereco(cep);
        enderecoRepository.save(endereco);

        endereco.setNumero(clienteInserirDTO.getNumeroEndereco());
        endereco.setComplemento(clienteInserirDTO.getComplemento());

        Cliente cliente = new Cliente(clienteInserirDTO);
        endereco.setCliente(cliente);

        List<Endereco> listaEnderecosCliente = new ArrayList<>();
        listaEnderecosCliente.add(endereco);
        cliente.setEnderecos(listaEnderecosCliente);

        clienteRepository.save(cliente);

        return new ClienteDTO(cliente);

    }
    //Fazer envio de email  
    public ClienteDTO editarCadastro(ClienteEditarDTO clienteEditarDTO, Long id) throws CpfException, EmailException, SenhaException {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isPresent()) {

            Cliente clienteTemp = clienteRepository.findByEmail(clienteEditarDTO.getEmail());
            if (null != clienteTemp && clienteEditarDTO.getEmail().equals(clienteTemp.getEmail())) {
                if (!id.equals(clienteTemp.getId())) {
                    throw new EmailException("E-mail já cadastrado!");
                }
            }

            if (!clienteEditarDTO.getSenha().equals(clienteEditarDTO.getConfirmaSenha())) {
                throw new SenhaException("Senhas inseridas são diferentes!");
            }

            Cliente cliente = clienteRepository.getReferenceById(id);

            cliente.setNome(clienteEditarDTO.getNome());
            cliente.setSenha(clienteEditarDTO.getSenha());
            cliente.setEmail(clienteEditarDTO.getEmail());
            cliente.setTelefone(clienteEditarDTO.getTelefone());

            clienteRepository.save(cliente);

            return new ClienteDTO(cliente);
        }
        return null;
    }

    public List<ClienteDTO> listarTodos() {
        List<Cliente> clientes= clienteRepository.findAll();
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        for (Cliente c: clientes) {
            ClienteDTO clienteDTO = new ClienteDTO(c);
            clientesDTO.add(clienteDTO);
        }

        return clientesDTO;
    }

    public ClienteDTO exibirUm(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            return new ClienteDTO(clienteOptional.get());
        }
        return null;
    }
    
	public boolean excluir(Long id) {
		if (clienteRepository.existsById(id)) {
			clienteRepository.deleteById(id);
			return true;
		}
		return false;
	}

    public Endereco criarEndereco(Long id, EnderecoInserirDTO enderecoInserirDTO) {
        String cep = enderecoInserirDTO.getCep();
        Endereco endereco = enderecoService.adicionarEndereco(cep);
        endereco.setNumero(enderecoInserirDTO.getNumero());
        endereco.setComplemento(enderecoInserirDTO.getComplemento());
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if(clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            endereco.setCliente(cliente); 
            enderecoRepository.save(endereco);
            return endereco;
        }
        return null;
    } 
}
