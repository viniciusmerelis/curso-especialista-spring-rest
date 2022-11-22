package com.algaworks.algafood.api.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PageModel")
@Getter
@Setter
public class PageModelOpenApi {
    private Long size;
    private Long totalElements;
    private Long totalPages;
    private Long number;
}
