package org.serratec.TrabalhoFinalAPI.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente_perfil")
public class ClientePerfil {

    @EmbeddedId
    private ClientePerfilPK id = new ClientePerfilPK();

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    public ClientePerfil() {
    }

    public ClientePerfil(Cliente cliente, Perfil perfil, LocalDate dataCriacao) {
        this.id.setCliente(cliente);
        this.id.setPerfil(perfil);
        this.dataCriacao = dataCriacao;
    }

    public ClientePerfilPK getId() {
        return id;
    }

    public void setId(ClientePerfilPK id) {
        this.id = id;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setCliente(Cliente cliente) {
        this.id.setCliente(cliente);
    }

    public void setPerfil(Perfil perfil) {
        this.id.setPerfil(perfil);
    }

}
