package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.LinkFactory;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RestauranteAssemblerDTO extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO> {
	
	@Autowired
	private ModelMapper mapper;

	public RestauranteAssemblerDTO() {
		super(RestauranteController.class, RestauranteDTO.class);
	}

	@Override
	public RestauranteDTO toModel(Restaurante restaurante) {
		RestauranteDTO restauranteDTO = createModelWithId(restaurante.getId(), restaurante);
		mapper.map(restaurante, restauranteDTO);
		restauranteDTO.add(LinkFactory.linkToRestaurantes("restaurantes"));
		restauranteDTO.getCozinha().add(LinkFactory.linkToCozinha(restauranteDTO.getCozinha().getId()));
		if (Objects.nonNull(restauranteDTO.getEndereco())) {
			restauranteDTO.getEndereco().getCidade().add(LinkFactory.linkToCidade(restauranteDTO.getEndereco().getCidade().getId()));
		}
		restauranteDTO.add(LinkFactory.linkToRestauranteFormasPagamento(restauranteDTO.getId(), "formas-pagamento"));
		restauranteDTO.add(LinkFactory.linkToRestauranteResponsaveis(restauranteDTO.getId(), "responsaveis"));
		return restauranteDTO;
	}

	@Override
	public CollectionModel<RestauranteDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities).add(LinkFactory.linkToRestaurantes());
	}
}
