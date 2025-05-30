package org.serratec.TrabalhoFinalAPI.domain;
// Essa classe irá fazer a intermediação com as outras classes
// para ser aplicado quantidade/valor de venda/desconto

import java.math.BigDecimal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
@Entity
public class PedidoProduto {
	@EmbeddedId
	private PedidoProdutoId id = new PedidoProdutoId();
	
	private Integer quantidade;
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
	