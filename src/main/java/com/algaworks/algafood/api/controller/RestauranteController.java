package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.RestauranteAssemblerDTO;
import com.algaworks.algafood.api.assembler.disassembler.RestauranteInputDisassemblerDTO;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteDtoInput;
import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteAssemblerDTO restauranteAssemblerDTO;

    @Autowired
    private RestauranteInputDisassemblerDTO restauranteDisassemblerDTO;

    @JsonView(RestauranteView.ResumoListagem.class)
    @GetMapping
    public List<RestauranteDTO> listar() {
        return restauranteAssemblerDTO.toCollectionDto(restauranteRepository.findAll());
    }

    @JsonView(RestauranteView.ApenasIdENome.class)
    @GetMapping(params = "projecao=apenas-id-e-nome")
    public List<RestauranteDTO> listarResumido() {
        return listar();
    }

    // Forma dinâmica
//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
//		List<Restaurante> restaurantes = restauranteRepository.findAll();
//		List<RestauranteDto> restaurantesDto = restauranteDtoAssembler.toCollectionDto(restaurantes);
//		MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesDto);
//		restaurantesWrapper.setSerializationView(RestauranteView.ResumoListagem.class);
//		if ("apenas-id-e-nome".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(RestauranteView.ApenasIdENome.class);
//		} else if ("completo".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(null);
//		}
//		return restaurantesWrapper;
//	}

    @GetMapping("/{restauranteId}")
    public RestauranteDTO buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        return restauranteAssemblerDTO.toDto(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(@RequestBody @Valid RestauranteDtoInput restauranteInputDTO) {
        try {
            Restaurante restaurante = restauranteDisassemblerDTO.toDomainObject(restauranteInputDTO);
            return restauranteAssemblerDTO.toDto(restauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteDTO atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteDtoInput restauranteInputDTO) {
        try {
            Restaurante restauranteAtual = restauranteService.buscarOuFalhar(restauranteId);
            restauranteDisassemblerDTO.copyToDomainObject(restauranteInputDTO, restauranteAtual);
            return restauranteAssemblerDTO.toDto(restauranteService.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {
        restauranteService.ativar(restauranteId);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restaurantesIds) {
        try {
            restauranteService.ativar(restaurantesIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId) {
        restauranteService.inativar(restauranteId);
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restaurantesIds) {
        try {
            restauranteService.inativar(restaurantesIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long restauranteId) {
        restauranteService.abrir(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long restauranteId) {
        restauranteService.fechar(restauranteId);
    }

}
