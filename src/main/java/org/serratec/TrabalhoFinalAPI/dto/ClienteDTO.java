package org.serratec.TrabalhoFinalAPI.dto;

import org.serratec.TrabalhoFinalAPI.domain.Cliente;
import org.serratec.TrabalhoFinalAPI.domain.Endereco;

import java.util.List;

public class ClienteDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private List<Endereco> enderecos;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente cliente) {
        this.nome = cliente.getNome();
        this.id = cliente.getId();
        this.enderecos = cliente.getEnderecos();
        this.cpf = cliente.getCpf();
        this.email = cliente.getEmail();
        this.telefone = cliente.getTelefone();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
