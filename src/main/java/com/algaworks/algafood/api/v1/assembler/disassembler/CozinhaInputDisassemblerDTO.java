package com.algaworks.algafood.api.v1.assembler.disassembler;

import com.algaworks.algafood.api.v1.model.input.CozinhaInputDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaInputDisassemblerDTO {

	@Autowired
    private ModelMapper modelMapper;
    
    public Cozinha toDomainObject(CozinhaInputDTO cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }
    
    public void copyToDomainObject(CozinhaInputDTO cozinhaInput, Cozinha cozinha) {
        modelMapper.map(cozinhaInput, cozinha);
    }
	
}
