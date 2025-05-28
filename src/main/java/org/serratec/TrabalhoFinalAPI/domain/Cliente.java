package org.serratec.TrabalhoFinalAPI.domain;

import java.util.List;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Nome é um campo obrigatório.")
    @Size(max = 100, message="Máximo de ${max} caracteres.")
    private String nome;

    @NotBlank(message="Telefone é um campo obrigatório.")
    // @Pattern(regexp = "^\(?[1-9]{2}\)?\s?(9[0-9]{4})-?[0-9]{4}$", message = "Telefone inválido. Ex: (11) 91234-5678")
    private String telefone;

    @NotBlank(message="E-mail é um campo obrigatório.")
    @Email(message = "E-mail inválido.")
    private String email;

    @NotBlank(message="CPF é um campo obrigatório.")
    @CPF(message="CPF inválido.")
    private String cpf;

    @NotBlank(message="A senha é um campo obrigatório.")
    @Size(min = 8, max = 100)
    private String senha;
    
   /* Aqui estamos exibindo uma lista de endereço no cliente */
    @JsonManagedReference
    @OneToMany(mappedBy = "cliente")
    List<Endereco>enderecos;
    
    /* Aqui estamos exibindo uma lista de pedidos no cliente */
    @JsonManagedReference
    @OneToMany(mappedBy = "cliente")
    List<Pedido>pedidos;

    //construtor

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

}
