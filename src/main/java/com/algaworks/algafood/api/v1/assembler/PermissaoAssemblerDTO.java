package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.LinkFactory;
import com.algaworks.algafood.api.v1.controller.PermissaoController;
import com.algaworks.algafood.api.v1.model.PermissaoDTO;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PermissaoAssemblerDTO extends RepresentationModelAssemblerSupport<Permissao, PermissaoDTO> {

	@Autowired
	private ModelMapper mapper;
	
	public PermissaoAssemblerDTO() {
		super(PermissaoController.class, PermissaoDTO.class);
	}

	@Override
	public PermissaoDTO toModel(Permissao permissao) {
		return mapper.map(permissao, PermissaoDTO.class);
	}

	@Override
	public CollectionModel<PermissaoDTO> toCollectionModel(Iterable<? extends Permissao> entities) {
		return super.toCollectionModel(entities).add(LinkFactory.linkToPermissoes());
	}
}
