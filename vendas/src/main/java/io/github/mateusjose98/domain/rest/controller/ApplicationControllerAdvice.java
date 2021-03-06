package io.github.mateusjose98.domain.rest.controller;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.mateusjose98.domain.rest.ApiErrors;
import io.github.mateusjose98.exception.PedidoNaoEncontradoException;
import io.github.mateusjose98.exception.RegraNegocioException;

@RestControllerAdvice
public class ApplicationControllerAdvice {
	
	@ExceptionHandler(RegraNegocioException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleRegraNegocioException (RegraNegocioException ex) {
		String mensagem = ex.getMessage();
		return new ApiErrors(mensagem);
	}

	
	@ExceptionHandler(PedidoNaoEncontradoException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrors handlePedidoNotFoundException (PedidoNaoEncontradoException ex) {
		return new ApiErrors(ex.getMessage());
	}
	
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleMethodNotValid(MethodArgumentNotValidException ex) {
		return new ApiErrors(ex.getBindingResult()
				.getAllErrors().stream().map( erro -> erro.getDefaultMessage()).collect(Collectors.toList()));
	}
	
}
