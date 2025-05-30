package org.serratec.TrabalhoFinalAPI.controller;

import org.serratec.TrabalhoFinalAPI.domain.Pedido;
import org.serratec.TrabalhoFinalAPI.dto.PedidoDTO;
import org.serratec.TrabalhoFinalAPI.dto.PedidoInserirDTO;
import org.serratec.TrabalhoFinalAPI.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/{id}")
    public ResponseEntity<Pedido> inserirPedido (@PathVariable Long id, @RequestBody PedidoInserirDTO pedidoInserirDTO) {
        Pedido pedido = pedidoService.inserirPedido(id, pedidoInserirDTO);
        return ResponseEntity.ok(pedido);
    }







}
