package org.serratec.TrabalhoFinalAPI.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.TrabalhoFinalAPI.config.MailConfig;
import org.serratec.TrabalhoFinalAPI.util.DescontoQuantidadeUtil;
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
    @Autowired
    private MailConfig mailConfig;

    public PedidoDTO inserirPedido(Long id, PedidoInserirDTO pedidoInserirDTO) throws RuntimeMensagemException {

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
        int quantidadeTotalItens = 0;
        for (PedidoProduto pedidoProduto : pedidoProdutos) {
            double valor = pedidoProduto.getProduto().getPreco() * pedidoProduto.getQuantidade();
            valorTotal += valor;
            quantidadeTotalItens += pedidoProduto.getQuantidade();
        }

        //calcula total com desconto
        // aplica o desconto baseado na quantidade total
        double desconto = DescontoQuantidadeUtil.calcularDesconto(quantidadeTotalItens);
        double totalComDesconto = valorTotal - (desconto * valorTotal);
        if(totalComDesconto >= 50.00) {
        	pedido.setTemCupomFreteGratis(true);
        } else {
        	pedido.setTemCupomFreteGratis(false);
        }
        pedido.setValorVenda(valorTotal);
        pedido.setDesconto(desconto * 100); // exemplo: 0.10 vira 10.0%
        pedido.setTotal(totalComDesconto);
        pedidoRepository.save(pedido);

        // Envia o e-mail de confirmação
        String emailCliente = pedido.getCliente().getEmail();
        String assuntoPedidoConfirmado = "Seu pedido n° " + pedido.getId() + " foi realizado! ";
        String mensagemPedidoConfirmado = "Olá, " + pedido.getCliente().getNome() + "!\n\n" +
            "Recebemos seu pedido realizado em " + pedido.getDataPedido() + " e já estamos preparando ele com todo carinho." +
            "\nAproveitando esse contato, vamos te dar um resumo do que você comprou:" +
            "\n\n Seu código de pedido é o n° " + pedido.getId() + "." +
            "\n Endereço de entrega: " + pedido.getEndereco() +
            "\n Itens do seu pedido: \n\n";

        for (PedidoProduto pp : pedido.getPedidoProdutos()) {
            mensagemPedidoConfirmado += "    ‣ " + pp.getProduto().getNomeProduto() +
                ": " + pp.getQuantidade() + " por " +
                " R$ " + String.format("%.2f", pp.getProduto().getPreco()) + " cada.\n";
        }

        mensagemPedidoConfirmado += "\nTotal do seu pedido: R$ " + String.format("%.2f", pedido.getValorVenda()) +
            "\nAgradecemos pela sua compra e esperamos que você aproveite seus produtos!\n" +
            "\n\nAtenciosamente,\n" +
            "Grupo 5  🩵💙";

        mailConfig.enviarEmail(emailCliente, assuntoPedidoConfirmado, mensagemPedidoConfirmado);

        PedidoDTO pedidoDTO = new PedidoDTO(pedido);

        pedidoDTO.setRelacionados(relacionados);

        return pedidoDTO;
    }

    public PedidoDTO editarPedido(Long id, Long id_pedido, PedidoInserirDTO pedidoInserirDTO) throws RuntimeMensagemException {

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
        int quantidadeTotalItens = 0;
        for (PedidoProduto pedidoProduto : pedidoProdutos) {
            double valor = pedidoProduto.getProduto().getPreco() * pedidoProduto.getQuantidade();
            valorTotal += valor;
            quantidadeTotalItens += pedidoProduto.getQuantidade();
        }
        //calcula total com desconto
        // aplica o desconto baseado na quantidade total
        double desconto = DescontoQuantidadeUtil.calcularDesconto(quantidadeTotalItens);
        double totalComDesconto = valorTotal - (desconto * valorTotal);
        if(totalComDesconto >= 50.00) {
        	pedido.setTemCupomFreteGratis(true);
        } else {
        	pedido.setTemCupomFreteGratis(false);
        }
        pedido.setValorVenda(valorTotal);
        pedido.setDesconto(desconto * 100);
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
