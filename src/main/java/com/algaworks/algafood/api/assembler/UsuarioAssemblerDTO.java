package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.LinkFactory;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAssemblerDTO extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {

	@Autowired
    private ModelMapper mapper;

    public UsuarioAssemblerDTO() {
        super(UsuarioController.class, UsuarioDTO.class);
    }

    @Override
    public UsuarioDTO toModel(Usuario usuario) {
        UsuarioDTO usuarioDTO = createModelWithId(usuario.getId(), usuario);
        mapper.map(usuario, usuarioDTO);
        usuarioDTO.add(LinkFactory.linkToUsuarios("usuarios"));
        usuarioDTO.add(LinkFactory.linkToGruposUsuario(usuarioDTO.getId(), "grupos-usuario"));
        return usuarioDTO;
    }

    @Override
    public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities).add(LinkFactory.linkToUsuarios());
    }
}
