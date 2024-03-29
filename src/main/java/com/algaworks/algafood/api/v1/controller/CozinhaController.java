package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.CozinhaAssemblerDTO;
import com.algaworks.algafood.api.v1.assembler.disassembler.CozinhaInputDisassemblerDTO;
import com.algaworks.algafood.api.v1.model.CozinhaDTO;
import com.algaworks.algafood.api.v1.model.input.CozinhaInputDTO;
import com.algaworks.algafood.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

@Slf4j
@RestController
@RequestMapping(path = "/v1/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CozinhaService cozinhaService;

	@Autowired
	private CozinhaAssemblerDTO cozinhaAssemblerDTO;

	@Autowired
	private CozinhaInputDisassemblerDTO cozinhaDisassemblerDTO;

	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

	@Override
	@CheckSecurity.Cozinhas.PodeConsultar
	@GetMapping
	public PagedModel<CozinhaDTO> listar(@PageableDefault(size = 10) Pageable pageable) {
		log.info("Consultando cozinhas com páginas de {} registros...", pageable.getPageSize());
		Page<Cozinha> cozinhasPages = cozinhaRepository.findAll(pageable);
		PagedModel cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhasPages, cozinhaAssemblerDTO);
		return cozinhasPagedModel;
	}

	@Override
	@CheckSecurity.Cozinhas.PodeConsultar
	@GetMapping("/{cozinhaId}")
	public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
		return cozinhaAssemblerDTO.toModel(cozinha);
	}

	@Override
	@CheckSecurity.Cozinhas.PodeEditar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
		Cozinha cozinha = cozinhaDisassemblerDTO.toDomainObject(cozinhaInputDTO);
		cozinha = cozinhaService.salvar(cozinha);
		return cozinhaAssemblerDTO.toModel(cozinha);
	}

	@Override
	@CheckSecurity.Cozinhas.PodeEditar
	@PutMapping("/{cozinhaId}")
	public CozinhaDTO atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
		Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(cozinhaId);
		cozinhaDisassemblerDTO.copyToDomainObject(cozinhaInputDTO, cozinhaAtual);
		cozinhaAtual = cozinhaService.salvar(cozinhaAtual);
		return cozinhaAssemblerDTO.toModel(cozinhaAtual);
	}

	@Override
	@CheckSecurity.Cozinhas.PodeEditar
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cozinhaService.excluir(cozinhaId);
	}
}
