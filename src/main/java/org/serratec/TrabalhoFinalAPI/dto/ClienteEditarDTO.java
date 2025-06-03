package org.serratec.TrabalhoFinalAPI.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ClienteEditarDTO {
    
    @NotBlank(message="Nome é um campo obrigatório.")
    @Size(max = 100, message="Máximo de ${max} caracteres.")
    private String nome;

     @NotBlank(message="E-mail é um campo obrigatório.")
    @Email(message = "E-mail inválido.")
    private String email;
    
    @Pattern(regexp = "^\\(?[1-9]{2}\\)?\s?(9[0-9]{4})-?[0-9]{4}$", message = "Telefone inválido. Ex: (11) 91234-5678")
    private String telefone;

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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
