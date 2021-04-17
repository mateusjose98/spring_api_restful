package io.github.mateusjose98.domain.rest.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.mateusjose98.domain.entity.Produto;
import io.github.mateusjose98.domain.repository.Produtos;


@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
	
	@Autowired
	private Produtos produtos;
	
	
	//GET ALL
	@GetMapping
	public ResponseEntity findAll(Produto filtro) {
		
		ExampleMatcher matcher = ExampleMatcher.matching()
											   .withIgnoreCase()
											   .withStringMatcher(StringMatcher.CONTAINING);
		Example exemple = Example.of(filtro, matcher);
		System.out.println(produtos.findAll(exemple));
		return ResponseEntity.ok(produtos.findAll(exemple));
	}
	
	//GET BY ID
	@GetMapping("/{id}")
	public Produto findById(@PathVariable("id") Integer id) {
		Optional<Produto> produtoBuscado = produtos.findById(id);
		if (produtoBuscado.isPresent()) {
			return produtoBuscado.get();
		}
		throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Produto não encontrado.");
	}
	
	//POST
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Produto save(@RequestBody @Valid Produto produto) {
		Produto produtoSalvo = produtos.save(produto);
		return produtoSalvo;
	}
	
	//DELETE
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete (@PathVariable Integer id) {
		produtos
			.findById(id)
			.map(produto -> {
					produtos.delete(produto);
					return produto;
			}).orElseThrow(
					() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!"));
	}
	
	//PUT
	@PutMapping("{id}")
	public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody @Valid Produto produto) {
		return produtos.findById(id)
				.map(produtoBuscado 
						-> { produto.setId(produtoBuscado.getId());
						     produtos.save(produtoBuscado);
						     return ResponseEntity.noContent().build();
						}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	

}
