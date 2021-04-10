package io.github.mateusjose98.rest.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class InformacaoItemPedidoDTO {
	
	private String descricao;
	private BigDecimal precoUnitario;
	private BigDecimal quantidade;

}
