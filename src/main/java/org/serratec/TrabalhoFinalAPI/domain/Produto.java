package org.serratec.TrabalhoFinalAPI.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NotBlank(message = "Nome do produto é obrigatória.")
    @Size(min = 3, max = 30, message="Mínimo de ${min} e máximo de ${max} caracteres")
    private String nomeProduto;

    @NotBlank(message = "Descrição é obrigatória.")
    @Size(max = 100)
    private String descricao;

    @NotBlank(message = "Valor é obrigatório.")
    private Double preco;

    @ManyToMany
    @JoinColumn(name="id_pedido")
    private Pedido pedido;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

}
