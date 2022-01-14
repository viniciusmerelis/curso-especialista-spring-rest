package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.CozinhaDtoInput;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaDtoInputDisassembler {

	@Autowired
    private ModelMapper modelMapper;
    
    public Cozinha toDomainObject(CozinhaDtoInput cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }
    
    public void copyToDomainObject(CozinhaDtoInput cozinhaInput, Cozinha cozinha) {
        modelMapper.map(cozinhaInput, cozinha);
    }
	
}
