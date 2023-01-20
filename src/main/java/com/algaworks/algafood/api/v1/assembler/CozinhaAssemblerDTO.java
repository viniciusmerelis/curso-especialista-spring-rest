package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.LinkFactory;
import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.model.CozinhaDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaAssemblerDTO extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO> {

	@Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaSecurity algaSecurity;

    public CozinhaAssemblerDTO() {
        super(CozinhaController.class, CozinhaDTO.class);
    }

    @Override
    public CozinhaDTO toModel(Cozinha cozinha) {
        CozinhaDTO cozinhaDTO = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaDTO);
        if (algaSecurity.podeConsultarCozinhas()) {
            cozinhaDTO.add(LinkFactory.linkToCozinhas("cozinhas"));
        }
        return  cozinhaDTO;
    }
}
