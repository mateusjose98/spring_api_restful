package io.github.mateusjose98.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.github.mateusjose98.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Data
public class PedidoDTO {

	@NotNull(message = "Informe o id do cliente.")
	private Integer cliente;
	
	@NotNull(message = "Informe o total do cliente.")
	private BigDecimal total;
	
	@NotEmptyList(message = "Pedido precisa ter itens!")
	private List<ItensPedidoDTO> itens;
}
