package io.github.mateusjose98.service.impl;

import org.springframework.stereotype.Service;

import io.github.mateusjose98.domain.repository.Pedidos;
import io.github.mateusjose98.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService{

	private Pedidos repository;

	public PedidoServiceImpl(Pedidos repository) {
		this.repository = repository;
	}
	
	
}
