package io.github.mateusjose98.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mateusjose98.domain.entity.Produto;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
