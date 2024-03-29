package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.LinkFactory;
import com.algaworks.algafood.api.v1.controller.UsuarioController;
import com.algaworks.algafood.api.v1.model.UsuarioDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
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

    @Autowired
    private AlgaSecurity algaSecurity;

    public UsuarioAssemblerDTO() {
        super(UsuarioController.class, UsuarioDTO.class);
    }

    @Override
    public UsuarioDTO toModel(Usuario usuario) {
        UsuarioDTO usuarioDTO = createModelWithId(usuario.getId(), usuario);
        mapper.map(usuario, usuarioDTO);
        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            usuarioDTO.add(LinkFactory.linkToUsuarios("usuarios"));
            usuarioDTO.add(LinkFactory.linkToUsuarioGrupos(usuarioDTO.getId(), "grupos-usuario"));
        }
        return usuarioDTO;
    }

    @Override
    public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities).add(LinkFactory.linkToUsuarios());
    }
}
