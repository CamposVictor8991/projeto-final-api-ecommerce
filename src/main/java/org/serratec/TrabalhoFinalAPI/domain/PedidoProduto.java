package org.serratec.TrabalhoFinalAPI.domain;
// Essa classe irá fazer a intermediação com as outras classes
// para ser aplicado quantidade/valor de venda/desconto

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
public class PedidoProduto {
	@EmbeddedId
	private PedidoProdutoId id = new PedidoProdutoId();

	private Integer quantidade;

	@OneToOne
    @JoinColumn(name = "produto_individual_id")
    private Produto produto;

	public PedidoProdutoId getId() {
		return id;
	}
	public void setId(PedidoProdutoId id) {
		this.id = id;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	
	
	
	
}
	