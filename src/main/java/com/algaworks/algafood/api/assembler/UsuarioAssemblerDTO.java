package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioAssemblerDTO {

	@Autowired
    private ModelMapper modelMapper;
    
    public UsuarioDTO toDto(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
    
    public List<UsuarioDTO> toCollectionDto(Collection<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> toDto(usuario))
                .collect(Collectors.toList());
    }  
	
}
