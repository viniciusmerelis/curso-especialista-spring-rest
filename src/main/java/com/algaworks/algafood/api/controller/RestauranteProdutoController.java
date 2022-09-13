package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.ProdutoAssemblerDTO;
import com.algaworks.algafood.api.assembler.disassembler.ProdutoInputDisassemblerDTO;
import com.algaworks.algafood.api.model.ProdutoDto;
import com.algaworks.algafood.api.model.input.ProdutoDtoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private ProdutoAssemblerDTO produtoAssemblerDTO;

	@Autowired
	private ProdutoInputDisassemblerDTO produtoInputDisassemblerDTO;

	@GetMapping
	public List<ProdutoDto> listar(@PathVariable Long restauranteId,
			@RequestParam(required = false) boolean incluirInativos) {
		
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		List<Produto> todosProdutos = null;
		if (incluirInativos) {
			todosProdutos = produtoRepository.findByRestaurante(restaurante);
		} else {
			todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
		} 
		return produtoAssemblerDTO.toCollectionDto(todosProdutos);
	}

	@GetMapping("/{produtoId}")
	public ProdutoDto buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
		return produtoAssemblerDTO.toDto(produto);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDto adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoDtoInput produtoDtoInput) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		Produto produto = produtoInputDisassemblerDTO.toDomainObject(produtoDtoInput);
		produto.setRestaurante(restaurante);
		produto = produtoService.salvar(produto);
		return produtoAssemblerDTO.toDto(produto);
	}

	@PutMapping("/{produtoId}")
	public ProdutoDto atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@RequestBody @Valid ProdutoDtoInput produtoDtoInput) {
		Produto produtoAtual = produtoService.buscarOuFalhar(restauranteId, produtoId);
		produtoInputDisassemblerDTO.copyToDomainObject(produtoDtoInput, produtoAtual);
		produtoAtual = produtoService.salvar(produtoAtual);
		return produtoAssemblerDTO.toDto(produtoAtual);
	}

}
