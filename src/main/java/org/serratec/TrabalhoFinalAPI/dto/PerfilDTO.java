package org.serratec.TrabalhoFinalAPI.dto;

public class PerfilDTO {

    private Long id;
    private String nome;

    public PerfilDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
