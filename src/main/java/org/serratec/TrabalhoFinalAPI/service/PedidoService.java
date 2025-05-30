package org.serratec.TrabalhoFinalAPI.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.TrabalhoFinalAPI.domain.Pedido;
import org.serratec.TrabalhoFinalAPI.domain.PedidoProduto;
import org.serratec.TrabalhoFinalAPI.domain.Produto;
import org.serratec.TrabalhoFinalAPI.dto.PedidoDTO;
import org.serratec.TrabalhoFinalAPI.dto.PedidoInserirDTO;
import org.serratec.TrabalhoFinalAPI.dto.PedidoProdutoDTO;
import org.serratec.TrabalhoFinalAPI.enuns.Status;
import org.serratec.TrabalhoFinalAPI.repository.ClienteRepository;
import org.serratec.TrabalhoFinalAPI.repository.EnderecoRepository;
import org.serratec.TrabalhoFinalAPI.repository.PedidoRepository;
import org.serratec.TrabalhoFinalAPI.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    public Pedido inserirPedido(Long id, PedidoInserirDTO pedidoInserirDTO) {

        Pedido pedido = new Pedido();
        pedido.setCliente(clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado")));
        pedido.setEndereco(enderecoRepository.findById(pedidoInserirDTO.getEnderecoId())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado")));

        List<PedidoProdutoDTO> pedidoProdutosDTO = pedidoInserirDTO.getPedidoProdutos();
        List<PedidoProduto> pedidoProdutos = new ArrayList<>();
        for (PedidoProdutoDTO p: pedidoProdutosDTO) {
            Optional<Produto> produtoOpd = produtoRepository.findById(p.getProdutoId());
            if (produtoOpd.isPresent()) {
                Produto produto = produtoOpd.get();
                int quantidade = p.getQuantidade();
                PedidoProduto pedidoProduto = new PedidoProduto();
                pedidoProduto.setProduto(produto);
                pedidoProduto.setQuantidade(quantidade);
                pedidoProdutos.add(pedidoProduto);
            }
        }

        pedido.setPedidoProdutos(pedidoProdutos);
        double valorTotal = 0.0;
        for (PedidoProduto pedidoProduto : pedidoProdutos) {
            double valor = pedidoProduto.getProduto().getPreco() * pedidoProduto.getQuantidade();
            valorTotal += valor;
        }
        pedido.setValorVenda(valorTotal);

        pedidoRepository.save(pedido);
        return pedido;
    }
}
