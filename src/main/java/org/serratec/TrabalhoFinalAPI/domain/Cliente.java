package org.serratec.TrabalhoFinalAPI.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.serratec.TrabalhoFinalAPI.dto.ClienteInserirDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cliente implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String cpf;
    private String senha;

    /* Aqui estamos exibindo uma lista de endereço no cliente */
    @JsonManagedReference
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Endereco> enderecos;

    /* Aqui estamos exibindo uma lista de pedidos no cliente */
    @JsonManagedReference
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Pedido> pedidos;

    /* Insere perfil no cliente */
    @OneToMany(mappedBy = "id.cliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClientePerfil> clientesPerfis = new HashSet<>();
    //Aqui esta a lista de favoritos do cliente
    @JsonManagedReference
    @OneToMany(mappedBy="cliente", fetch = FetchType.EAGER)
    private List<Favorito> produtosFavoritos = new ArrayList<>();

    //construtor
    public Cliente() {
    }

    public Cliente(ClienteInserirDTO clienteInserirDTO) {
        this.nome = clienteInserirDTO.getNome();
        this.telefone = clienteInserirDTO.getTelefone();
        this.email = clienteInserirDTO.getEmail();
        this.cpf = clienteInserirDTO.getCpf();
        this.senha = clienteInserirDTO.getSenha();
        this.enderecos = new ArrayList<>();
    }

    public Cliente(Long id, String nome, String telefone, String email, String cpf, String senha,
            List<Endereco> enderecos, List<Pedido> pedidos, Set<ClientePerfil> clientesPerfis) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
        this.enderecos = enderecos;
        this.pedidos = pedidos;
        this.clientesPerfis = clientesPerfis;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Set<ClientePerfil> getClientesPerfis() {
        return clientesPerfis;
    }

    public void setClientesPerfis(Set<ClientePerfil> clientesPerfis) {
        this.clientesPerfis = clientesPerfis;
    }

    @Override
    public String toString() {
        return "Olá, " + nome + "!" + "\n\nSeu cadastro foi realizado com sucesso!";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.nome);
        hash = 89 * hash + Objects.hashCode(this.telefone);
        hash = 89 * hash + Objects.hashCode(this.email);
        hash = 89 * hash + Objects.hashCode(this.cpf);
        hash = 89 * hash + Objects.hashCode(this.senha);
        hash = 89 * hash + Objects.hashCode(this.enderecos);
        hash = 89 * hash + Objects.hashCode(this.pedidos);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.telefone, other.telefone)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        if (!Objects.equals(this.senha, other.senha)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.enderecos, other.enderecos)) {
            return false;
        }
        return Objects.equals(this.pedidos, other.pedidos);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (ClientePerfil clientePerfil : getClientesPerfis()) {
            authorities.add(new SimpleGrantedAuthority(clientePerfil.getId().getPerfil().getNome()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    public List<Favorito> getProdutosFavoritos() {
        return produtosFavoritos;
    }

    public void setProdutosFavoritos(List<Favorito> produtosFavoritos) {
        this.produtosFavoritos = produtosFavoritos;
    }



}
