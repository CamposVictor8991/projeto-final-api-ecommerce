package org.serratec.TrabalhoFinalAPI.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ClienteInserirDTO {

    @NotBlank(message="Nome é um campo obrigatório.")
    @Size(max = 100, message="Máximo de ${max} caracteres.")
    private String nome;

     @NotBlank(message="E-mail é um campo obrigatório.")
    @Email(message = "E-mail inválido.")
    private String email;

    @NotBlank(message="CPF é um campo obrigatório.")
    @CPF(message="CPF inválido.")
    private String cpf;

    @NotBlank(message="Telefone é um campo obrigatório.")
    @Pattern(regexp = "^\\(?[1-9]{2}\\)?\s?(9[0-9]{4})-?[0-9]{4}$", message = "Telefone inválido. Ex: (11) 91234-5678")
    private String telefone;

    @NotBlank(message="CEP é um campo obrigatório.")
    private String cep;

    @NotBlank(message="Número de endereço é um campo obrigatório.")
    private String numeroEndereco;
    @Size(max= 100)
    private String complemento;

    @NotBlank(message="A senha é um campo obrigatório.")
    @Size(min = 8, max = 100)
    private String senha;
    private String confirmaSenha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumeroEndereco() {
        return numeroEndereco;
    }

    public void setNumeroEndereco(String numeroEndereco) {
        this.numeroEndereco = numeroEndereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }
}
