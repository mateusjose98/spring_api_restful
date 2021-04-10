package io.github.mateusjose98.domain.entity;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_cliente")
@AllArgsConstructor @NoArgsConstructor
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;


    @Column(name = "nome", length = 100)
    private String nome;
    
    @Column(name = "cpf")
    private String cpf;

    //um cliente pode fazer vários pedidos
    @JsonIgnore
    @OneToMany(mappedBy = "cliente") //atributo do lado Many
    private Set<Pedido> pedidos;




  
}
