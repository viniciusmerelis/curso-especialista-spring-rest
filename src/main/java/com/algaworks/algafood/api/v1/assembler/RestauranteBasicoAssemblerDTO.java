package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.LinkFactory;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoAssemblerDTO extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoDTO> {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AlgaSecurity algaSecurity;

    public RestauranteBasicoAssemblerDTO() {
        super(RestauranteController.class, RestauranteBasicoDTO.class);
    }

    @Override
    public RestauranteBasicoDTO toModel(Restaurante restaurante) {
        RestauranteBasicoDTO restauranteDTO = createModelWithId(restaurante.getId(), restaurante);
        mapper.map(restaurante, restauranteDTO);
        if (algaSecurity.podeConsultarRestaurantes()) {
            restauranteDTO.add(LinkFactory.linkToRestaurantes("restaurantes"));
        }
        if (algaSecurity.podeConsultarCozinhas()) {
            restauranteDTO.getCozinha().add(LinkFactory.linkToCozinha(restauranteDTO.getCozinha().getId()));
        }
        return restauranteDTO;
    }

    @Override
    public CollectionModel<RestauranteBasicoDTO> toCollectionModel(Iterable<? extends Restaurante> restaurantes) {
        return super.toCollectionModel(restaurantes).add(LinkFactory.linkToRestaurantes());
    }
}
