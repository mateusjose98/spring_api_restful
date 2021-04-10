package io.github.mateusjose98.rest.dto;
//
//{
//    "cliente": 1,
//    "total": 100,
//    "itens": [
//        {
//            "produto": 1,
//            "quantidade": 100
//        }
//    ]
//}

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor @NoArgsConstructor
@Data
public class ItensPedidoDTO {
	
	private Integer codigo_produto;
	private BigDecimal quantidade;


	
}
