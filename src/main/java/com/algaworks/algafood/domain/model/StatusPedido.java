package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum StatusPedido {
	
	CRIADO("Criado"),
	CONFIRMADO("Confirmado", CRIADO),
	ENTREGUE("Entregue", CONFIRMADO),
	CANCELADO("Cancelado", CRIADO);
	
	private String descricao;
	private List<StatusPedido> statusAntecessores;
	
	StatusPedido(String descricao, StatusPedido... statusAntecessores) {
		this.descricao = descricao;
		this.statusAntecessores = Arrays.asList(statusAntecessores);
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public boolean naoPodeAlterarPara(StatusPedido status) {
		return !status.statusAntecessores.contains(this);
	}

	public boolean podeAlterarPara(StatusPedido status) {
		return !naoPodeAlterarPara(status);
	}

	public List<StatusPedido> statusDisponiveisParaAlteracao() {
		return Arrays.stream(StatusPedido.values())
				.filter(status -> !status.equals(this))
				.filter(status -> podeAlterarPara(status))
				.collect(Collectors.toList());
	}
}
