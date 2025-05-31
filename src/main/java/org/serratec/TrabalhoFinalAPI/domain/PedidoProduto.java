package org.serratec.TrabalhoFinalAPI.domain;
// Essa classe irá fazer a intermediação com as outras classes
// para ser aplicado quantidade/valor de venda/desconto

import jakarta.persistence.*;

@Entity
public class PedidoProduto {
	@EmbeddedId
	private PedidoProdutoId id = new PedidoProdutoId();

	private Integer quantidade;

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


    //perguntar ao professor
    @Transient
    public Produto getProduto() {
        return this.id.getProduto();
    }

    @Transient
    public Pedido getPedido() {
        return this.id.getPedido();
    }

    // Se quiser setters também
    public void setProduto(Produto produto) {
        this.id.setProduto(produto);
    }

    public void setPedido(Pedido pedido) {
        this.id.setPedido(pedido);
    }
}