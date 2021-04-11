package io.github.mateusjose98.exception;

public class PedidoNaoEncontradoException extends RuntimeException{

	
	public PedidoNaoEncontradoException() {
		super("Pedido não encontrado!");
	}
}
