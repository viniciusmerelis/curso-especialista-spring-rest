package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.LinkFactory;
import com.algaworks.algafood.api.controller.GrupoController;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GrupoAssemblerDTO extends RepresentationModelAssemblerSupport<Grupo, GrupoDTO> {

	@Autowired
    private ModelMapper mapper;
    
    public GrupoAssemblerDTO() {
        super(GrupoController.class, GrupoDTO.class);
    }

    @Override
    public GrupoDTO toModel(Grupo grupo) {
        GrupoDTO grupoDTO = createModelWithId(grupo.getId(), grupo);
        mapper.map(grupo, grupoDTO);
        grupoDTO.add(LinkFactory.linkToGrupos("grupos"));
        grupoDTO.add(LinkFactory.linkToGrupoPermissoes(grupoDTO.getId(), "permissoes"));
        return grupoDTO;
    }

    @Override
    public CollectionModel<GrupoDTO> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities).add(LinkFactory.linkToGrupos());
    }
}
