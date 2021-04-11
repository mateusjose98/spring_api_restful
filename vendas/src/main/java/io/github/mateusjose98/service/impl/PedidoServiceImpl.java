package io.github.mateusjose98.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mateusjose98.domain.entity.Cliente;
import io.github.mateusjose98.domain.entity.ItemPedido;
import io.github.mateusjose98.domain.entity.Pedido;
import io.github.mateusjose98.domain.entity.Produto;
import io.github.mateusjose98.domain.enums.StatusPedido;
import io.github.mateusjose98.domain.repository.Clientes;
import io.github.mateusjose98.domain.repository.ItensPedido;
import io.github.mateusjose98.domain.repository.Pedidos;
import io.github.mateusjose98.domain.repository.Produtos;
import io.github.mateusjose98.exception.PedidoNaoEncontradoException;
import io.github.mateusjose98.exception.RegraNegocioException;
import io.github.mateusjose98.rest.dto.ItensPedidoDTO;
import io.github.mateusjose98.rest.dto.PedidoDTO;
import io.github.mateusjose98.service.PedidoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

	private final Pedidos repository;
	private final Clientes clientesRepository;
	private final Produtos prodRepository;
	private final ItensPedido itemsPedido;

	@Override
	@Transactional
	public Pedido salvar(PedidoDTO dto) {
		Integer idCliente = dto.getCliente();
		Cliente cliente = clientesRepository.findById(idCliente)
				.orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));

		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setDataPedido(LocalDate.now());
		pedido.setTotal(dto.getTotal());
		pedido.setStatus(StatusPedido.REALIZADO);
		
		List<ItemPedido> itens = converterItens(pedido, dto.getItens());
		repository.save(pedido);
		itemsPedido.saveAll(itens);
		pedido.setItems(itens);
		return pedido;
	}

	private List<ItemPedido> converterItens(Pedido pedido, List<ItensPedidoDTO> itens) {
		if (itens.isEmpty()) {
			throw new RegraNegocioException("Lista de pedidos está vazia!");
		}

		return itens.stream().map(dto -> {
			Integer idProduto = dto.getCodigo_produto();
			Produto produto = prodRepository.findById(idProduto)
					.orElseThrow(() -> new RegraNegocioException("Código de produto inválido: " + idProduto));

			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setQuantidade(dto.getQuantidade());
			itemPedido.setPedido(pedido);
			itemPedido.setProduto(produto);
			return itemPedido;
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<Pedido> obterPedidoCompleto(Integer id) {
		Optional<Pedido> pedido = repository.findByIdFetchItems(id);
		return pedido;
	}

	@Override
	@Transactional
	public void atualizaStatus(Integer id, StatusPedido status) {
		repository.findById(id).map(pedido -> {
			pedido.setStatus(status);
			return repository.save(pedido);
		}).orElseThrow( () -> new PedidoNaoEncontradoException() );
		
	}

}
