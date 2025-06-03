package org.serratec.TrabalhoFinalAPI.service;

import org.serratec.TrabalhoFinalAPI.config.MailConfig;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MailConfig mailConfig;

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
        cliente.setSenha(bCryptPasswordEncoder.encode(clienteInserirDTO.getSenha())); // criptografando a senha
        endereco.setCliente(cliente);

        List<Endereco> listaEnderecosCliente = new ArrayList<>();
        listaEnderecosCliente.add(endereco);
        cliente.setEnderecos(listaEnderecosCliente);

        clienteRepository.save(cliente);

        String mensagemCadastroCriado = "Olá, " + cliente.getNome() + "!"
                + "\n\nEstamos passando bem rapidinho pra dar as boas-vindas e confirmar que seu cadastro foi criado com sucesso! "
                + "\nAgora que você faz parte da nossa comunidade e pode aproveitar ofertas exclusivas, acompanhar seus pedidos e muito mais! "
                + "\nLembre-se de que você pode acessar sua conta usando o e-mail: " + cliente.getEmail() + " e a senha que você escolheu."
                + "\nConta com a gente para qualquer dúvida ou ajuda que você precisar!"
                + "\n\nAtenciosamente, \nGrupo 5 - Serratec  🩵💙";

        mailConfig.enviarEmail(cliente.getEmail(), "Cadastro de Cliente Criado!", mensagemCadastroCriado);

        return new ClienteDTO(cliente);

    }

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
            cliente.setSenha(bCryptPasswordEncoder.encode(clienteEditarDTO.getSenha())); // criptografando a senha
            cliente.setEmail(clienteEditarDTO.getEmail());
            cliente.setTelefone(clienteEditarDTO.getTelefone());

            clienteRepository.save(cliente);

            String mensagemCadastroEditado = "Olá, " + cliente.getNome()
                    + "\n\nPassando rapidinho pra confirmar que as informações da sua conta foram atualizadas com sucesso! ✨"
                    + "\nLembre-se de sempre manter seus dados atualizados pra não perder acesso à sua conta e continuar por dentro das novidades."
                    + "\nSeus dados atuais são:"
                    + "\n\nNome: " + cliente.getNome()
                    + "\nE-mail: " + cliente.getEmail()
                    + "\nTelefone: " + cliente.getTelefone()
                    + "\n\nSe não foi você quem fez essa alteração, entre em contato com a gente imediatamente."
                    + "\nConta com a gente pra qualquer dúvida ou ajuda que precisar!"
                    + "\n\nAtenciosamente, \nGrupo 5 - Serratec 🩵💙";

            mailConfig.enviarEmail(cliente.getEmail(), "Atualização cadastral!", mensagemCadastroEditado);

            return new ClienteDTO(cliente);
        }
        return null;
    }

    public List<ClienteDTO> listarTodos() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        for (Cliente c : clientes) {
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
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            endereco.setCliente(cliente);
            enderecoRepository.save(endereco);
            return endereco;
        }
        return null;
    }

    public Cliente acharCliente(String email) {
        return clienteRepository.findByEmail(email);
    }

}
