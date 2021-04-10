package io.github.mateusjose98.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.mateusjose98.domain.entity.Pedido;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

	@Query(" select p from Pedido p left join fetch p.items where p.id = :id ")
	Optional<Pedido> findByIdFetchItems(@Param("id") Integer id);
}
