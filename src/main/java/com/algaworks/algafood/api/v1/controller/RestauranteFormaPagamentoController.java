package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.LinkFactory;
import com.algaworks.algafood.api.v1.assembler.FormaPagamentoAssemblerDTO;
import com.algaworks.algafood.api.v1.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
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
@RequestMapping(path = "/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private FormaPagamentoAssemblerDTO formaPagamentoAssemblerDTO;
	
	@Override
	@GetMapping
	public CollectionModel<FormaPagamentoDTO> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		CollectionModel<FormaPagamentoDTO> formaPagamentoDTOS = formaPagamentoAssemblerDTO.toCollectionModel(restaurante.getFormasPagamento())
				.removeLinks()
				.add(LinkFactory.linkToRestauranteFormasPagamento(restauranteId))
				.add(LinkFactory.linkToRestauranteFormaPagamentoAssociar(restauranteId, "associar"));
		formaPagamentoDTOS.getContent().forEach(formaPagamento -> formaPagamento.add(
				LinkFactory.linkToRestauranteFormaPagamentoDesassociar(restauranteId, formaPagamento.getId(), "desassociar")));
		return formaPagamentoDTOS;
	}
	
	@Override
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
		return ResponseEntity.noContent().build();
	}
}
