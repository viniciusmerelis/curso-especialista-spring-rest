package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.LinkFactory;
import com.algaworks.algafood.api.v1.controller.CidadeController;
import com.algaworks.algafood.api.v1.model.CidadeDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeAssemblerDTO extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {

	@Autowired
    private ModelMapper mapper;

    @Autowired
    private AlgaSecurity algaSecurity;

    public CidadeAssemblerDTO() {
        super(CidadeController.class, CidadeDTO.class);
    }

    @Override
    public CidadeDTO toModel(Cidade cidade) {
        CidadeDTO cidadeDTO = createModelWithId(cidade.getId(), cidade);
        mapper.map(cidade, cidadeDTO);
        if (algaSecurity.podeConsultarCidades()) {
            cidadeDTO.add(LinkFactory.linkToCidades("cidades"));
        }
        if (algaSecurity.podeConsultarEstados()) {
            cidadeDTO.getEstado().add(LinkFactory.linkToEstado(cidadeDTO.getEstado().getId()));
        }
        return cidadeDTO;
    }

    @Override
    public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
        CollectionModel<CidadeDTO> collectionModel = super.toCollectionModel(entities);
        if (algaSecurity.podeConsultarCidades()) {
            collectionModel.add(LinkFactory.linkToCidades());
        }
        return collectionModel;
    }
}
