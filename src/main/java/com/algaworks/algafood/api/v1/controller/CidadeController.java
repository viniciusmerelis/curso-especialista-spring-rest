package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v1.assembler.CidadeAssemblerDTO;
import com.algaworks.algafood.api.v1.assembler.disassembler.CidadeInputDisassemblerDTO;
import com.algaworks.algafood.api.v1.model.CidadeDTO;
import com.algaworks.algafood.api.v1.model.input.CidadeInputDTO;
import com.algaworks.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
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
@RequestMapping(path = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {
    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeAssemblerDTO cidadeAssemblerDTO;

    @Autowired
    private CidadeInputDisassemblerDTO cidadeDisassemblerDTO;

    @Override
    @CheckSecurity.Cidades.PodeConsultar
    @GetMapping
    public CollectionModel<CidadeDTO> listar() {
        List<Cidade> cidades = cidadeRepository.findAll();
        return cidadeAssemblerDTO.toCollectionModel(cidades);
    }

    @Override
    @CheckSecurity.Cidades.PodeConsultar
    @GetMapping("/{cidadeId}")
    public CidadeDTO buscar(@PathVariable Long cidadeId) {
        Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);
        return cidadeAssemblerDTO.toModel(cidade);
    }

    @Override
    @CheckSecurity.Cidades.PodeEditar
    @PostMapping
    public CidadeDTO adicionar(@RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
        try {
            Cidade cidade = cidadeDisassemblerDTO.toDomainObject(cidadeInputDTO);
            cidade = cidadeService.salvar(cidade);
            ResourceUriHelper.addUriInResponseHeader(cidade.getId());
            return cidadeAssemblerDTO.toModel(cidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @CheckSecurity.Cidades.PodeEditar
    @PutMapping("/{cidadeId}")
    public CidadeDTO atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
        try {
            Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);
            cidadeDisassemblerDTO.copyToDomainObject(cidadeInputDTO, cidadeAtual);
            cidadeAtual = cidadeService.salvar(cidadeAtual);
            return cidadeAssemblerDTO.toModel(cidadeAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @CheckSecurity.Cidades.PodeEditar
    @DeleteMapping("/{cidadeId}")
    public void remover(@PathVariable Long cidadeId) {
        cidadeService.excluir(cidadeId);
    }
}
