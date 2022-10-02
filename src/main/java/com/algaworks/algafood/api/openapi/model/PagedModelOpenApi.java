package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.CozinhaDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedModelOpenApi<T> {
    private List<CozinhaDTO> content;

    @ApiModelProperty(example = "10", value = "Quantidade de registros por página")
    private Long size;

    @ApiModelProperty(example = "50", value = "Quantidade total de registros")
    private Long totalElements;

    @ApiModelProperty(example = "5", value = "Quantidade total de páginas")
    private Long totalPages;

    @ApiModelProperty(example = "0", value = "Número da página atual (começa em 0)")
    private Long number;
}
