package io.github.mateusjose98.domain.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mateusjose98.service.PedidoService;

@RestController
@RequestMapping("/api/")
public class PedidoController {
	
	
	private PedidoService service;
	
	public PedidoController(PedidoService service) {
		this.service = service;
	}

}
