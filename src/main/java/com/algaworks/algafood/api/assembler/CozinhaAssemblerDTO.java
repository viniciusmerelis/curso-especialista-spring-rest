package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.LinkFactory;
import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaAssemblerDTO extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO> {

	@Autowired
    private ModelMapper modelMapper;

    public CozinhaAssemblerDTO() {
        super(CozinhaController.class, CozinhaDTO.class);
    }

    @Override
    public CozinhaDTO toModel(Cozinha cozinha) {
        CozinhaDTO cozinhaDTO = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaDTO);
        cozinhaDTO.add(LinkFactory.linkToCozinhas("cozinhas"));
        return  cozinhaDTO;
    }
}
