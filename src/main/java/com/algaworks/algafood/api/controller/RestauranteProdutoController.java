package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.algaworks.algafood.api.model.ProdutoDTO;
import com.algaworks.algafood.api.model.input.ProdutoInputDTO;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

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

	@Override
	@GetMapping
	public List<ProdutoDTO> listar(@PathVariable Long restauranteId,
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

	@Override
	@GetMapping("/{produtoId}")
	public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
		return produtoAssemblerDTO.toDto(produto);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		Produto produto = produtoInputDisassemblerDTO.toDomainObject(produtoInputDTO);
		produto.setRestaurante(restaurante);
		produto = produtoService.salvar(produto);
		return produtoAssemblerDTO.toDto(produto);
	}

	@Override
	@PutMapping("/{produtoId}")
	public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
								@RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
		Produto produtoAtual = produtoService.buscarOuFalhar(restauranteId, produtoId);
		produtoInputDisassemblerDTO.copyToDomainObject(produtoInputDTO, produtoAtual);
		produtoAtual = produtoService.salvar(produtoAtual);
		return produtoAssemblerDTO.toDto(produtoAtual);
	}

}
