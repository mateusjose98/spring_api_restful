package io.github.mateusjose98.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Data
public class PedidoDTO {

	private Integer cliente;
	private BigDecimal total;
	private List<ItensPedidoDTO> itens;
}
