package org.serratec.TrabalhoFinalAPI.domain;

import org.serratec.TrabalhoFinalAPI.dto.ProdutoInserirDTO;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do produto é obrigatória.")
    @Size(min = 3, max = 30, message="Mínimo de ${min} e máximo de ${max} caracteres")
    private String nomeProduto;

    @NotBlank(message = "Descrição é obrigatória.")
    @Size(max = 100)
    private String descricao;

    private Double preco;
    
    private int quantidade;

    
    /* Aqui estamos exibindo ID desta categoria no PRODUTO! */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    
    
    
    
    public Produto() {
		super();
	}
    
    
    public Produto(ProdutoInserirDTO produtoInserirDTO) {
		this.nomeProduto = produtoInserirDTO.getNomeProduto();
		this.descricao = produtoInserirDTO.getDescricaoProduto();
		this.preco = produtoInserirDTO.getPrecoProduto();
		this.quantidade = produtoInserirDTO.getQuantidadeProduto();
	}

	public Long getId() {
        return id;
    }

    public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public void setId(Long id) {
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
