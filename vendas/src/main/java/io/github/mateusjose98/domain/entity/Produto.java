package io.github.mateusjose98.domain.entity;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "produto")
@AllArgsConstructor @NoArgsConstructor
@Data
public class Produto {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "preco_unitario") @NotNull(message = "Campo preço obrigatório!")
    private BigDecimal preco;

    @Column(name = "descricao") @NotEmpty(message = "Campo descrição mandatório!")
    private String descricao;

    
}
