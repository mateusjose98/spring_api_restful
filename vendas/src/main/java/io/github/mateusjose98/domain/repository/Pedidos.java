package io.github.mateusjose98.domain.repository;

import io.github.mateusjose98.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

}
