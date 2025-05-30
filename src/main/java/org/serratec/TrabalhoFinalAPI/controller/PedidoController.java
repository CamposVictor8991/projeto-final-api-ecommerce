package org.serratec.TrabalhoFinalAPI.controller;

import org.serratec.TrabalhoFinalAPI.domain.Pedido;
import org.serratec.TrabalhoFinalAPI.dto.PedidoDTO;
import org.serratec.TrabalhoFinalAPI.dto.PedidoInserirDTO;
import org.serratec.TrabalhoFinalAPI.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> inserirPedido (@RequestBody PedidoInserirDTO pedidoInserirDTO) {
        Pedido pedido = pedidoService.inserirPedido(pedidoInserirDTO);
        return ResponseEntity.ok(pedido);
    }







}
