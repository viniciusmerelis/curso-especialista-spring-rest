package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.LinkFactory;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.RestauranteApenasNomeDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteApenasNomeAssemblerDTO extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeDTO> {

    @Autowired
    private ModelMapper mapper;

    public RestauranteApenasNomeAssemblerDTO() {
        super(RestauranteController.class, RestauranteApenasNomeDTO.class);
    }

    @Override
    public RestauranteApenasNomeDTO toModel(Restaurante restaurante) {
        RestauranteApenasNomeDTO restauranteDTO = createModelWithId(restaurante.getId(), restaurante);
        mapper.map(restaurante, restauranteDTO);
        restauranteDTO.add(LinkFactory.linkToRestaurantes("restaurantes"));
        return restauranteDTO;
    }

    @Override
    public CollectionModel<RestauranteApenasNomeDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(LinkFactory.linkToRestaurantes());
    }
}
