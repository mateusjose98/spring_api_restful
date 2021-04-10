package io.github.mateusjose98.domain.repository;

import io.github.mateusjose98.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
