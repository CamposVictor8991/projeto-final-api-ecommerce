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
	private BigDecimal valorVenda;
	private BigDecimal desconto;
	
	public PedidoProdutoId getId() {
		return id;
	}
	public void setId(PedidoProdutoId id) {
		this.id = id;
	}
	public Pedido getPedido() {
		return this.id.getPedido();
	}
	public void setPedido(Pedido pedido) {
		this.id.setPedido(pedido);
	}
	public Produto getProduto() {
		return this.id.getProduto();
	}
	public void setProduto(Produto produto) {
		this.id.setProduto(produto);
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getValorVenda() {
		return valorVenda;
	}
	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}
	public BigDecimal getDesconto() {
		return desconto;
	}
	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	
	
}
	