package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.LinkFactory;
import com.algaworks.algafood.api.assembler.UsuarioAssemblerDTO;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
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

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private UsuarioAssemblerDTO usuarioAssemblerDTO;
	
	@Override
	@GetMapping
	public CollectionModel<UsuarioDTO> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		CollectionModel<UsuarioDTO> usuariosDTO = usuarioAssemblerDTO.toCollectionModel(restaurante.getResponsaveis())
				.removeLinks()
				.add(LinkFactory.linkToRestauranteResponsaveis(restauranteId))
				.add(LinkFactory.linkToRestauranteResponsavelAssociar(restauranteId, "assciar"));
		usuariosDTO.getContent().forEach(usuario -> usuario.add(LinkFactory.linkToRestauranteResponsavelDesassociar(restauranteId, usuario.getId(), "desassociar")));
		return usuariosDTO;
	}
	
	@Override
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.associarResponsavel(restauranteId, usuarioId);
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.desassociarResponsavel(restauranteId, usuarioId);
		return ResponseEntity.noContent().build();
	}
	
}
