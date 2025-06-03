package org.serratec.TrabalhoFinalAPI.dto;

import org.serratec.TrabalhoFinalAPI.domain.Cliente;

public class ClienteResumoDTO {

    private Long id;
    private String nome;

    public ClienteResumoDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
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
}
