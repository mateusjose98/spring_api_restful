package io.github.mateusjose98.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mateusjose98.domain.entity.Pedido;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

}
