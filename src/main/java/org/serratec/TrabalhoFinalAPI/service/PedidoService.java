package org.serratec.TrabalhoFinalAPI.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.TrabalhoFinalAPI.domain.Cliente;
import org.serratec.TrabalhoFinalAPI.domain.Pedido;
import org.serratec.TrabalhoFinalAPI.domain.PedidoProduto;
import org.serratec.TrabalhoFinalAPI.domain.PedidoProdutoId;
import org.serratec.TrabalhoFinalAPI.domain.Produto;
import org.serratec.TrabalhoFinalAPI.dto.EditarStatusDTO;
import org.serratec.TrabalhoFinalAPI.dto.PedidoDTO;
import org.serratec.TrabalhoFinalAPI.dto.PedidoInserirDTO;
import org.serratec.TrabalhoFinalAPI.dto.PedidoProdutoDTO;
import org.serratec.TrabalhoFinalAPI.enuns.Status;
import org.serratec.TrabalhoFinalAPI.exception.RuntimeMensagemException;
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
    @Autowired
    ProdutoService produtoService;

    public PedidoDTO inserirPedido(Long id, PedidoInserirDTO pedidoInserirDTO) {

        Pedido pedido = new Pedido();
        //confere se o cliente e o id existem
        pedido.setCliente(clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeMensagemException("Cliente não encontrado")));
        pedido.setEndereco(enderecoRepository.findById(pedidoInserirDTO.getEnderecoId())
                .orElseThrow(() -> new RuntimeMensagemException("Endereço não encontrado")));

        //cria lista temporaria dos pedido/produtos inseridos
        List<PedidoProdutoDTO> pedidoProdutosDTO = pedidoInserirDTO.getPedidoProdutos();
        List<PedidoProduto> pedidoProdutos = new ArrayList<>();
        //lista temporaria de produtos relacionados
        List<Produto> relacionados = new ArrayList<>();
        for (PedidoProdutoDTO p : pedidoProdutosDTO) {
            //confere se exuste o produto no estoque
            Produto produto = produtoRepository.findById(p.getProdutoId())
                    .orElseThrow(() -> new RuntimeMensagemException("Produto não disponível."));

            //confere se existem unidades suficientes no estoque
            if (produto.getQuantidade() < p.getQuantidade()) {
                throw new RuntimeMensagemException("Estoque insificiente, atualmente existem " + produto.getQuantidade() + " unidades de " + produto.getNomeProduto() + " no estoque.");
            }
            PedidoProduto pedidoProduto = new PedidoProduto();
            pedidoProduto.setQuantidade(p.getQuantidade());

            // Cria o ID composto e seta corretamente
            PedidoProdutoId ppId = new PedidoProdutoId();
            ppId.setPedido(pedido);
            ppId.setProduto(produto);
            pedidoProduto.setId(ppId);

            List<Produto> listarRelacionados = produtoService.listarRelacionados(p.getProdutoId());

            for (Produto r: listarRelacionados) {
                if (!relacionados.contains(r)) {
                    relacionados.add(r);
                }
            }

            pedidoProdutos.add(pedidoProduto);
        }

        pedido.setPedidoProdutos(pedidoProdutos);

        //calcula o valor da venda
        double valorTotal = 0.0;
        for (PedidoProduto pedidoProduto : pedidoProdutos) {
            double valor = pedidoProduto.getProduto().getPreco() * pedidoProduto.getQuantidade();
            valorTotal += valor;
        }
        //calcula total com desconto
        double totalComDesconto = valorTotal - (pedido.getDesconto() / 100 * valorTotal);

        pedido.setValorVenda(valorTotal);
        pedido.setTotal(totalComDesconto);

        pedidoRepository.save(pedido);

        PedidoDTO pedidoDTO = new PedidoDTO(pedido);

        pedidoDTO.setRelacionados(relacionados);

        return pedidoDTO;
    }

    public PedidoDTO editarPedido(Long id, Long id_pedido, PedidoInserirDTO pedidoInserirDTO) {

        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id_pedido);
        Pedido pedido = pedidoOpt.get();
        //confere se o cliente e o id existem
        pedido.setCliente(clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeMensagemException("Cliente não encontrado")));
        pedido.setEndereco(enderecoRepository.findById(pedidoInserirDTO.getEnderecoId())
                .orElseThrow(() -> new RuntimeMensagemException("Endereço não disponível.")));

        //cria lista temporaria dos pedido/produtos inseridos
        List<PedidoProdutoDTO> pedidoProdutosDTO = pedidoInserirDTO.getPedidoProdutos();
        List<PedidoProduto> pedidoProdutos = new ArrayList<>();
        for (PedidoProdutoDTO p : pedidoProdutosDTO) {
            //confere se exuste o produto no estoque
            Produto produto = produtoRepository.findById(p.getProdutoId())
                    .orElseThrow(() -> new RuntimeMensagemException("Produto não encontrado"));

            //confere se existem unidades suficientes no estoque
            if (produto.getQuantidade() < p.getQuantidade()) {
                throw new RuntimeMensagemException("Estoque insificiente, atualmente existem " + produto.getQuantidade() + " unidades de " + produto.getNomeProduto() + " no estoque.");
            }

            PedidoProduto pedidoProduto = new PedidoProduto();
            pedidoProduto.setQuantidade(p.getQuantidade());

            // Cria o ID composto e seta corretamente
            PedidoProdutoId ppId = new PedidoProdutoId();
            ppId.setPedido(pedido);
            ppId.setProduto(produto);
            pedidoProduto.setId(ppId);

            pedidoProdutos.add(pedidoProduto);
        }

        pedido.setPedidoProdutos(pedidoProdutos);
        //calcula o valor da venda
        double valorTotal = 0.0;
        for (PedidoProduto pedidoProduto : pedidoProdutos) {
            double valor = pedidoProduto.getProduto().getPreco() * pedidoProduto.getQuantidade();
            valorTotal += valor;
        }
        //calcula total com desconto
        double totalComDesconto = valorTotal - (pedido.getDesconto() / 100 * valorTotal);

        pedido.setValorVenda(valorTotal);
        pedido.setTotal(totalComDesconto);

        pedidoRepository.save(pedido);
        PedidoDTO pedidoDTO = new PedidoDTO(pedido);

        return pedidoDTO;
    }

    public List<PedidoDTO> listarPedidos(Long id) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            List<Pedido> pedidosCliente = pedidoRepository.findByCliente(cliente);
            List<PedidoDTO> pedidoDTO = new ArrayList<>();

            for (Pedido p : pedidosCliente) {
                PedidoDTO pedidoDTO2 = new PedidoDTO(p);
                pedidoDTO.add(pedidoDTO2);
            }

            return pedidoDTO;
        }
        return null;
    }

    public PedidoDTO editarStatus(Long id, Long id_pedido, EditarStatusDTO editarStatusDTO) {

        Status status = editarStatusDTO.getStatus();
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id_pedido);
        Pedido pedido = pedidoOpt.get();

        pedido.setStatus(status);
        pedido.setDesconto(editarStatusDTO.getDesconto());

        //calcula total com desconto
        double totalComDesconto = pedido.getTotal() - (editarStatusDTO.getDesconto() / 100 * pedido.getTotal());
        pedido.setTotal(totalComDesconto);

        List<PedidoProduto> pedidosProdutos = pedido.getPedidoProdutos();

        //confere se o pedido foi concluido e se foi retirar os itens do estoque
        if (editarStatusDTO.getStatus().equals(Status.CONCLUIDO)) {
            for (PedidoProduto pp : pedidosProdutos) {
                Produto produto = pp.getProduto();
                int quantidadeVendida = pp.getQuantidade();
                produto.setQuantidade(produto.getQuantidade() - quantidadeVendida);
            }
        }

        pedidoRepository.save(pedido);
        PedidoDTO pedidoDTO = new PedidoDTO(pedido);

        return pedidoDTO;
    }
}
