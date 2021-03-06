package io.github.mateusjose98.domain.rest.controller;


import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.mateusjose98.domain.entity.Cliente;
import io.github.mateusjose98.domain.repository.Clientes;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private Clientes clientes;
    
    
    @GetMapping("{id}")
    public Cliente helloCliente(@PathVariable("id")Integer id){
    		if (clientes.findById(id).isPresent()) {
    			return clientes.findById(id).get();
    			
    		}
    		throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Cliente não encontrado.");
    }
    
    
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody Cliente cliente) {
    	Cliente clienteSalvo = clientes.save(cliente);
    	return clienteSalvo;
    }
    
    
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id")Integer id){
    	clientes.findById(id).map(cliente ->{
	    			clientes.delete(cliente);
	    			return cliente;
    			}).orElseThrow(
    					() ->  new ResponseStatusException( HttpStatus.NOT_FOUND, "Cliente não encontrado."));

    }
    
    
    //atualizacao integral
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id")Integer id, 
    		@RequestBody Cliente cliente) {
    	
    	return clientes
    			.findById(id)
    			.map(
    					clienteExistente -> 
    						{
    							cliente.setId(clienteExistente.getId());
    							clientes.save(cliente);
    							return ResponseEntity.noContent().build();
    						}
    					).orElseGet( () -> ResponseEntity.notFound().build());

    	
    }
    
    
    @GetMapping
    public ResponseEntity find(Cliente filtro) {
    	ExampleMatcher matcher = ExampleMatcher.matching()
    			.withIgnoreCase()
    			.withStringMatcher(StringMatcher.CONTAINING);
    	Example example = Example.of(filtro, matcher);
    	List<Cliente> lista = clientes.findAll(example);
    	return ResponseEntity.ok(lista);
    }
    

    



 

}
