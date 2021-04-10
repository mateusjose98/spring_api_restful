package io.github.mateusjose98.service;

import java.util.Optional;

import io.github.mateusjose98.domain.entity.Pedido;
import io.github.mateusjose98.rest.dto.PedidoDTO;

public interface PedidoService {
	
	
	Pedido salvar(PedidoDTO dto);
	
	Optional<Pedido> obterPedidoCompleto(Integer id);

}
