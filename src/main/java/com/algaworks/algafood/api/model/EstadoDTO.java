package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
public class EstadoDTO extends RepresentationModel<EstadoDTO> {
    @ApiModelProperty(example = "1")
	private Long id;
    @ApiModelProperty(example = "Minas Gerais")
    private String nome;
}
