package io.github.mateusjose98.domain.entity;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

@Entity
@Table(name="tb_cliente")
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

    public Cliente(){ }

    public Cliente(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }


    public Cliente(String nome) {
        this.nome = nome;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
