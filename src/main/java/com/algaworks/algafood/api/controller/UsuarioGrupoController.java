package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.LinkFactory;
import com.algaworks.algafood.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GrupoAssemblerDTO;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;

@RestController
@RequestMapping(path = "/usuarios/{usuarioId}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private GrupoAssemblerDTO grupoAssemblerDTO;
	
	@Override
	@GetMapping
	public CollectionModel<GrupoDTO> listar(@PathVariable Long usuarioId) {
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		CollectionModel<GrupoDTO> gruposDTO = grupoAssemblerDTO.toCollectionModel(usuario.getGrupos())
				.removeLinks()
				.add(LinkFactory.linkToUsuarioGrupoAssociar(usuarioId, "associar"));
		gruposDTO.getContent().forEach(grupo -> grupo.add(LinkFactory.linkToUsuarioGrupoDesassociar(usuarioId, grupo.getId(), "desassociar")));
		return gruposDTO;
	}
	
	@Override
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		usuarioService.associoarGrupo(usuarioId, grupoId);
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		usuarioService.desassocioarGrupo(usuarioId, grupoId);
		return ResponseEntity.noContent().build();
	}
	
}
