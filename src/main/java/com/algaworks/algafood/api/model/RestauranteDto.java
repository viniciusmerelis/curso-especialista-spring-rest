package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDto {
	
	@JsonView(RestauranteView.ResumoListagem.class)
	private Long id;
	@JsonView(RestauranteView.ResumoListagem.class)
	private String nome;
	@JsonView(RestauranteView.ResumoListagem.class)
	private BigDecimal precoFrete;
	@JsonView(RestauranteView.ResumoListagem.class)
	private CozinhaDto cozinha;
	private Boolean ativo;
	private Boolean aberto;
	private EnderecoDto endereco;
	
}
