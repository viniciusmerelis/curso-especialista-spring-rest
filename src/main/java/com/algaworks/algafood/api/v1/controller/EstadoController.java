package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.v1.openapi.controller.EstadoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.EstadoAssemblerDTO;
import com.algaworks.algafood.api.v1.assembler.disassembler.EstadoInputDisassemblerDTO;
import com.algaworks.algafood.api.v1.model.EstadoDTO;
import com.algaworks.algafood.api.v1.model.input.EstadoInputDTO;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.EstadoService;

@RestController
@RequestMapping(value = "/v1/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private EstadoService estadoService;
	@Autowired
	private EstadoAssemblerDTO estadoAssemblerDTO;
	private EstadoInputDisassemblerDTO estadoDisassemblerDTO;

	@Override
	@GetMapping
	public CollectionModel<EstadoDTO> listar() {
		List<Estado> estados = estadoRepository.findAll();
		return estadoAssemblerDTO.toCollectionModel(estados);
	}

	@Override
	@GetMapping("/{estadoId}")
	public EstadoDTO buscar(@PathVariable Long estadoId) {
		Estado estado = estadoService.buscarOuFalhar(estadoId);
		return estadoAssemblerDTO.toModel(estado);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@RequestBody @Valid EstadoInputDTO estadoInputDTO) {
		Estado estado = estadoDisassemblerDTO.toDomainObject(estadoInputDTO);
		estado = estadoService.salvar(estado);
		return estadoAssemblerDTO.toModel(estado);
	}

	@Override
	@PutMapping("/{estadoId}")
	public EstadoDTO atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInputDTO estadoInputDTO) {
		Estado estadoAtual = estadoService.buscarOuFalhar(estadoId);
		estadoDisassemblerDTO.copyToDomainObject(estadoInputDTO, estadoAtual);
		estadoAtual = estadoService.salvar(estadoAtual);
		return estadoAssemblerDTO.toModel(estadoAtual);
	}

	@Override
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
		estadoService.excluir(estadoId);
	}
}
