package com.algaworks.algafood.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDtoInput {

	@Valid
	@NotNull
	private RestauranteDtoIdInput restaurante;
	
	@Valid
	@NotNull
	private EnderecoDtoInput enderecoEntrega;
	
	@Valid
	@NotNull
	private FormaPagamentoDtoIdInput formaPagamento;
	
	@Valid
	@Size(min = 1)
	@NotNull
	private List<ItemPedidoDtoInput> itens;
}
