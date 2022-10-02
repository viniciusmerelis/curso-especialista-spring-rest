package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaAssemblerDTO;
import com.algaworks.algafood.api.assembler.disassembler.CozinhaInputDisassemblerDTO;
import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.input.CozinhaInputDTO;
import com.algaworks.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import com.fasterxml.jackson.annotation.JsonRootName;
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

import javax.validation.Valid;
import java.util.List;

@JsonRootName("cozinha")
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CozinhaAssemblerDTO cozinhaAssemblerDTO;
	
	@Autowired
	private CozinhaInputDisassemblerDTO cozinhaDisassemblerDTO;

	@GetMapping
	public Page<CozinhaDTO> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPages = cozinhaRepository.findAll(pageable);
		List<CozinhaDTO> cozinhasDTO = cozinhaAssemblerDTO.toCollectionDto(cozinhasPages.getContent());
		Page<CozinhaDTO> cozinhasPagesDTO = new PageImpl<>(cozinhasDTO, pageable, cozinhasPages.getTotalElements());
		return cozinhasPagesDTO;
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
		return cozinhaAssemblerDTO.toDto(cozinha);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
		Cozinha cozinha = cozinhaDisassemblerDTO.toDomainObject(cozinhaInputDTO);
		cozinha = cozinhaService.salvar(cozinha);
		return cozinhaAssemblerDTO.toDto(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	public CozinhaDTO atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
		Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(cozinhaId);
		cozinhaDisassemblerDTO.copyToDomainObject(cozinhaInputDTO, cozinhaAtual);
		cozinhaAtual = cozinhaService.salvar(cozinhaAtual);
		return cozinhaAssemblerDTO.toDto(cozinhaAtual);
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cozinhaService.excluir(cozinhaId);
	}
}
