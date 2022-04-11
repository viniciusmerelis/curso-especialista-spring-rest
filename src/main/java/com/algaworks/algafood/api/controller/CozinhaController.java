package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CozinhaDtoAssembler;
import com.algaworks.algafood.api.assembler.disassembler.CozinhaDtoInputDisassembler;
import com.algaworks.algafood.api.model.CozinhaDto;
import com.algaworks.algafood.api.model.input.CozinhaDtoInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("cozinha")
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CozinhaDtoAssembler cozinhaDtoAssembler;
	
	@Autowired
	private CozinhaDtoInputDisassembler cozinhaDtoDisassembler;

	@GetMapping
	public Page<CozinhaDto> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPages = cozinhaRepository.findAll(pageable);
		List<CozinhaDto> cozinhasDto = cozinhaDtoAssembler.toCollectionDto(cozinhasPages.getContent());
		Page<CozinhaDto> cozinhasDtoPages = new PageImpl<>(cozinhasDto, pageable, cozinhasPages.getTotalElements());
		return cozinhasDtoPages;
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaDto buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
		return cozinhaDtoAssembler.toDto(cozinha);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDto adicionar(@RequestBody @Valid CozinhaDtoInput cozinhaDtoInput) {
		Cozinha cozinha = cozinhaDtoDisassembler.toDomainObject(cozinhaDtoInput);
		cozinha = cozinhaService.salvar(cozinha);
		return cozinhaDtoAssembler.toDto(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	public CozinhaDto atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaDtoInput cozinhaDtoInput) {
		Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(cozinhaId);
		cozinhaDtoDisassembler.copyToDomainObject(cozinhaDtoInput, cozinhaAtual);
		cozinhaAtual = cozinhaService.salvar(cozinhaAtual);
		return cozinhaDtoAssembler.toDto(cozinhaAtual);
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cozinhaService.excluir(cozinhaId);
	}
}
