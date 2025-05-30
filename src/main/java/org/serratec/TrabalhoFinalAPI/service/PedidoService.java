package org.serratec.TrabalhoFinalAPI.service;

import java.util.List;

import org.serratec.TrabalhoFinalAPI.domain.Pedido;
import org.serratec.TrabalhoFinalAPI.domain.PedidoProduto;
import org.serratec.TrabalhoFinalAPI.dto.PedidoDTO;
import org.serratec.TrabalhoFinalAPI.dto.PedidoInserirDTO;
import org.serratec.TrabalhoFinalAPI.enuns.Status;
import org.serratec.TrabalhoFinalAPI.repository.ClienteRepository;
import org.serratec.TrabalhoFinalAPI.repository.EnderecoRepository;
import org.serratec.TrabalhoFinalAPI.repository.PedidoRepository;
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

    public Pedido inserirPedido(PedidoInserirDTO pedidoInserirDTO) {
        Pedido pedido = new Pedido();
        pedido.setCliente(clienteRepository.findById(pedidoInserirDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado")));
        pedido.setEndereco(enderecoRepository.findById(pedidoInserirDTO.getEnderecoId())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado")));

        pedido.setPedidoProdutos(pedidoInserirDTO.getPedidoProdutos());
        List<PedidoProduto> pedidoProdutos = pedidoInserirDTO.getPedidoProdutos();
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
