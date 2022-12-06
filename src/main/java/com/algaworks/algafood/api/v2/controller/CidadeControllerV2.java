package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v2.assembler.CidadeAssemblerDTOV2;
import com.algaworks.algafood.api.v2.assembler.CidadeInputDisassemblerDTOV2;
import com.algaworks.algafood.api.v2.model.CidadeDTOV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputDTOV2;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 {
    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeAssemblerDTOV2 cidadeAssemblerDTO;

    @Autowired
    private CidadeInputDisassemblerDTOV2 cidadeDisassemblerDTO;

    @GetMapping
    public CollectionModel<CidadeDTOV2> listar() {
        List<Cidade> cidades = cidadeRepository.findAll();
        return cidadeAssemblerDTO.toCollectionModel(cidades);
    }

    @GetMapping("/{cidadeId}")
    public CidadeDTOV2 buscar(@PathVariable Long cidadeId) {
        Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);
        return cidadeAssemblerDTO.toModel(cidade);
    }

    @PostMapping
    public CidadeDTOV2 adicionar(@RequestBody @Valid CidadeInputDTOV2 cidadeInputDTO) {
        try {
            Cidade cidade = cidadeDisassemblerDTO.toDomainObject(cidadeInputDTO);
            cidade = cidadeService.salvar(cidade);
            CidadeDTOV2 cidadeDTOV2 = cidadeAssemblerDTO.toModel(cidade);
            ResourceUriHelper.addUriInResponseHeader(cidadeDTOV2.getIdCidade());
            return cidadeDTOV2;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeDTOV2 atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInputDTOV2 cidadeInputDTO) {
        try {
            Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);
            cidadeDisassemblerDTO.copyToDomainObject(cidadeInputDTO, cidadeAtual);
            cidadeAtual = cidadeService.salvar(cidadeAtual);
            return cidadeAssemblerDTO.toModel(cidadeAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    public void remover(@PathVariable Long cidadeId) {
        cidadeService.excluir(cidadeId);
    }
}
