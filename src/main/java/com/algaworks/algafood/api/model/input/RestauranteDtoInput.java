package com.algaworks.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDtoInput {
	@NotBlank
	private String nome;

	@NotNull
	@PositiveOrZero
	private BigDecimal taxaFrete;

	@Valid
	@NotNull
	private CozinhaDtoIdInput cozinha;

	@Valid
	@NotNull
	private EnderecoDtoInput endereco;
}
