package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.LinkFactory;
import com.algaworks.algafood.api.v1.controller.GrupoController;
import com.algaworks.algafood.api.v1.model.GrupoDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
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

    @Autowired
    private AlgaSecurity algaSecurity;
    
    public GrupoAssemblerDTO() {
        super(GrupoController.class, GrupoDTO.class);
    }

    @Override
    public GrupoDTO toModel(Grupo grupo) {
        GrupoDTO grupoDTO = createModelWithId(grupo.getId(), grupo);
        mapper.map(grupo, grupoDTO);
        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            grupoDTO.add(LinkFactory.linkToGrupos("grupos"));
            grupoDTO.add(LinkFactory.linkToGrupoPermissoes(grupoDTO.getId(), "permissoes"));
        }
        return grupoDTO;
    }

    @Override
    public CollectionModel<GrupoDTO> toCollectionModel(Iterable<? extends Grupo> entities) {
        CollectionModel<GrupoDTO> collectionModel = super.toCollectionModel(entities);
        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            collectionModel.add(LinkFactory.linkToGrupos());
        }
        return collectionModel;
    }
}
