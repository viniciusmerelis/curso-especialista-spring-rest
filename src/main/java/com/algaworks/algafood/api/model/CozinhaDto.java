package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaDto {
	@JsonView(RestauranteView.ResumoListagem.class)
	private Long id;
	@JsonView(RestauranteView.ResumoListagem.class)
	private String nome;
	
}
