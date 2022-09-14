package com.algaworks.algafood.api.assembler.disassembler;

import com.algaworks.algafood.api.model.input.ProdutoInputDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoInputDisassemblerDTO {

	@Autowired
    private ModelMapper modelMapper;
    
    public Produto toDomainObject(ProdutoInputDTO produtoInput) {
        return modelMapper.map(produtoInput, Produto.class);
    }
    
    public void copyToDomainObject(ProdutoInputDTO produtoInput, Produto produto) {
        modelMapper.map(produtoInput, produto);
    } 
	
}
