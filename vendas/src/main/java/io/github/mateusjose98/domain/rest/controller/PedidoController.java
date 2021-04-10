package io.github.mateusjose98.domain.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.mateusjose98.domain.entity.Pedido;
import io.github.mateusjose98.rest.dto.PedidoDTO;
import io.github.mateusjose98.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
	
	
	private PedidoService service;
	
	public PedidoController(PedidoService service) {
		this.service = service;
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Integer save(@RequestBody PedidoDTO pedidoDto) {
		
		Pedido pedido = service.salvar(pedidoDto);
		return pedido.getId();
	}

}
