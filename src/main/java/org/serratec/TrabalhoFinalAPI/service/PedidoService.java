package org.serratec.TrabalhoFinalAPI.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.TrabalhoFinalAPI.config.MailConfig;
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
    private MailConfig mailConfig;

    public PedidoDTO inserirPedido(Long id, PedidoInserirDTO pedidoInserirDTO) {

        Pedido pedido = new Pedido();
        pedido.setCliente(clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado")));
        pedido.setEndereco(enderecoRepository.findById(pedidoInserirDTO.getEnderecoId())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado")));

        List<PedidoProdutoDTO> pedidoProdutosDTO = pedidoInserirDTO.getPedidoProdutos();
        List<PedidoProduto> pedidoProdutos = new ArrayList<>();
        for (PedidoProdutoDTO p : pedidoProdutosDTO) {
            Produto produto = produtoRepository.findById(p.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

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
        double valorTotal = 0.0;
        for (PedidoProduto pedidoProduto : pedidoProdutos) {
            double valor = pedidoProduto.getProduto().getPreco() * pedidoProduto.getQuantidade();
            valorTotal += valor;
        }
        pedido.setValorVenda(valorTotal);

        pedidoRepository.save(pedido);

        // Envia o e-mail de confirmação
        String emailCliente = pedido.getCliente().getEmail();
        String assuntoPedidoConfirmado = "Seu pedido n° " + pedido.getId() + " foi realizado! 🛍️";
        String mensagemPedidoConfirmado = "Olá, " + pedido.getCliente().getNome() + "!\n\n" +
                "Recebemos seu pedido realizado em " + pedido.getDataPedido() + " e já estamos preparando ele com todo carinho." +
                "\nAproveitando esse contato, vamos te dar um resumo do que você comprou:" +
                "\n\n📝 Seu código de pedido é o n° " + pedido.getId() + "." +
                "\n📍 Endereço de entrega: " + pedido.getEndereco() +
                "\n📦 Itens do seu pedido: \n\n";

                for (PedidoProduto pp : pedido.getPedidoProdutos()) {
                    mensagemPedidoConfirmado += "    ‣ " + pp.getProduto().getNomeProduto() +
                            ": " + pp.getQuantidade() + " por " +
                            " R$ " + String.format("%.2f", pp.getProduto().getPreco()) + " cada.\n";
                }

        mensagemPedidoConfirmado += "\nTotal do seu pedido: R$ " + String.format("%.2f", pedido.getValorVenda()) +
                "\nAgradecemos pela sua compra e esperamos que você aproveite seus produtos!\n" +
                "\n\nAtenciosamente,\n" +
                "Grupo 5 🩵💙";

        mailConfig.enviarEmail(emailCliente, assuntoPedidoConfirmado, mensagemPedidoConfirmado);

        PedidoDTO pedidoDTO = new PedidoDTO(pedido);

        return pedidoDTO;
    }

    public PedidoDTO editarPedido(Long id, Long id_pedido, PedidoInserirDTO pedidoInserirDTO) {

        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id_pedido);
        Pedido pedido = pedidoOpt.get();

        pedido.setCliente(clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado")));
        pedido.setEndereco(enderecoRepository.findById(pedidoInserirDTO.getEnderecoId())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado")));

        List<PedidoProdutoDTO> pedidoProdutosDTO = pedidoInserirDTO.getPedidoProdutos();
        List<PedidoProduto> pedidoProdutos = new ArrayList<>();
        for (PedidoProdutoDTO p : pedidoProdutosDTO) {
            Produto produto = produtoRepository.findById(p.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

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
        double valorTotal = 0.0;
        for (PedidoProduto pedidoProduto : pedidoProdutos) {
            double valor = pedidoProduto.getProduto().getPreco() * pedidoProduto.getQuantidade();
            valorTotal += valor;
        }
        pedido.setValorVenda(valorTotal);

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

        pedidoRepository.save(pedido);
        PedidoDTO pedidoDTO = new PedidoDTO(pedido);

        return pedidoDTO;
    }
}