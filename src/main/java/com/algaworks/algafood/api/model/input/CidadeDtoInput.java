package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

@Component
public class CidadeDtoInput {

	@NotBlank
    private String nome;
    
    @Valid
    @NotNull
    private EstadoDtoIdInput estado;
	
}
