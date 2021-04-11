package io.github.mateusjose98.domain.rest.controller;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.mateusjose98.domain.entity.ItemPedido;
import io.github.mateusjose98.domain.entity.Pedido;
import io.github.mateusjose98.domain.enums.StatusPedido;
import io.github.mateusjose98.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.mateusjose98.rest.dto.InformacaoItemPedidoDTO;
import io.github.mateusjose98.rest.dto.InformacoesPedidoDTO;
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
	
	
	@GetMapping("{id}")
	public InformacoesPedidoDTO getById(@PathVariable Integer id) {
		return service.obterPedidoCompleto(id).map(p -> converter(p))
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado!"));
		
	}
	
	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateStatus(@PathVariable("id") Integer id,@RequestBody AtualizacaoStatusPedidoDTO novoStatus) {
		
		service.atualizaStatus(id, StatusPedido.valueOf(novoStatus.getNovoStatus()));
	}
	
	
	
	public InformacoesPedidoDTO converter(Pedido pedido) {
		return InformacoesPedidoDTO.builder()
				.codigo(pedido.getId())
				.dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
				.cpf(pedido.getCliente().getCpf()).nomeCliente(pedido.getCliente().getNome())
				.total(pedido.getTotal())
				.status(pedido.getStatus().name())
				.itens(converter(pedido.getItems()))
				.build();
	}
	
	
	public List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
		if( CollectionUtils.isEmpty(itens) ) {
			return Collections.emptyList();
		}
	
		
		return itens.stream().
				map(i -> 
					InformacaoItemPedidoDTO.builder()
					.descricao(i.getProduto().getDescricao())
					.precoUnitario(i.getProduto().getPreco())
					.quantidade(i.getQuantidade()).build())
				.collect(Collectors.toList());
				
	}
	
	
	
	


}
