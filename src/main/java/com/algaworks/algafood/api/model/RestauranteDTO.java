package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {
	
	@JsonView(RestauranteView.ResumoListagem.class)
	private Long id;
	@JsonView(RestauranteView.ResumoListagem.class)
	private String nome;
	@JsonView(RestauranteView.ResumoListagem.class)
	private BigDecimal taxaFrete;
	@JsonView(RestauranteView.ResumoListagem.class)
	private CozinhaDTO cozinha;
	private Boolean ativo;
	private Boolean aberto;
	private EnderecoDTO endereco;
	
}
