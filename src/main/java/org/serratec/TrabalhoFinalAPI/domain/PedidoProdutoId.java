package org.serratec.TrabalhoFinalAPI.domain;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Embeddable;
// Essa é uma classe que garante que o produto vai aparecer 1x por pedido
// ou seja, é uma classe auxiliar; 
// Ex: Aparece um só produto , mas aparece quantidade dele..
// Produto A 3 unidades.
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class PedidoProdutoId implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonBackReference
    @JoinColumn(name = "pedido_id")
    @ManyToOne
    private Pedido pedido;

    @JsonBackReference
    @JoinColumn(name = "produto_id")
    @ManyToOne
    private Produto produto;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pedido, produto);
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
        PedidoProdutoId other = (PedidoProdutoId) obj;
        return Objects.equals(pedido, other.pedido) && Objects.equals(produto, other.produto);
    }

}
