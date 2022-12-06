package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.GrupoAssemblerDTO;
import com.algaworks.algafood.api.v1.assembler.disassembler.GrupoInputDisassemblerDTO;
import com.algaworks.algafood.api.v1.model.GrupoDTO;
import com.algaworks.algafood.api.v1.model.input.GrupoInputDTO;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.GrupoService;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private GrupoAssemblerDTO grupoAssemblerDTO;
	
	@Autowired
	private GrupoInputDisassemblerDTO grupoDisassemblerDTO;
	
	@Override
	@GetMapping
	public CollectionModel<GrupoDTO> listar() {
		List<Grupo> todosGrupos = grupoRepository.findAll();
		return grupoAssemblerDTO.toCollectionModel(todosGrupos);
	}
	
	@Override
	@GetMapping("/{grupoId}")
	public GrupoDTO buscar(@PathVariable Long grupoId) {
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		return grupoAssemblerDTO.toModel(grupo);
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO adicionar(@RequestBody GrupoInputDTO grupoInputDTO) {
		Grupo grupo = grupoDisassemblerDTO.toDomainObject(grupoInputDTO);
		grupo = grupoService.salvar(grupo);
		return grupoAssemblerDTO.toModel(grupo);
	}
	
	@Override
	@PutMapping("/{grupoId}")
	public GrupoDTO atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInputDTO grupoInputDTO) {
		Grupo grupoAtual = grupoService.buscarOuFalhar(grupoId);
		grupoDisassemblerDTO.copyToDomainObject(grupoInputDTO, grupoAtual);
		grupoAtual = grupoService.salvar(grupoAtual);
		return grupoAssemblerDTO.toModel(grupoAtual);
	}
	
	@Override
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long grupoId) {
		grupoService.excluir(grupoId);
	}
	
}
