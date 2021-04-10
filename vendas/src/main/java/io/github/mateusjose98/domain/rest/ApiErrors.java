package io.github.mateusjose98.domain.rest;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;


public class ApiErrors {
	
	@Getter
	private List<String> erros;

	public ApiErrors(String mensagemErro) {
		this.erros = Arrays.asList(mensagemErro);
	}
}
