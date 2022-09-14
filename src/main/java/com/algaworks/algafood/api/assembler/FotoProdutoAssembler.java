package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.FotoProdutoDTO;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoDTO toDto(FotoProduto foto) {
        return modelMapper.map(foto, FotoProdutoDTO.class);
    }
}
