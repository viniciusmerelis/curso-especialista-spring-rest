package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.LinkFactory;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.v1.model.FotoProdutoDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO> {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AlgaSecurity algaSecurity;

    public FotoProdutoAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoDTO.class);
    }

    @Override
    public FotoProdutoDTO toModel(FotoProduto fotoProduto) {
        FotoProdutoDTO fotoProdutoDTO = mapper.map(fotoProduto, FotoProdutoDTO.class);
        if (algaSecurity.podeConsultarRestaurantes()) {
            fotoProdutoDTO.add(LinkFactory.linkToProdutoFoto(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId()));
            fotoProdutoDTO.add(LinkFactory.linkToProduto(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId(), "produto"));
        }
        return fotoProdutoDTO;
    }
}
