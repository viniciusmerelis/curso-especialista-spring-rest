package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.LinkFactory;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();
        rootEntryPointModel.add(LinkFactory.linkToCozinhas("cozinhas"));
        rootEntryPointModel.add(LinkFactory.linkToPedidos("pedidos"));
        rootEntryPointModel.add(LinkFactory.linkToRestaurantes("restaurantes"));
        rootEntryPointModel.add(LinkFactory.linkToGrupos("grupos"));
        rootEntryPointModel.add(LinkFactory.linkToUsuarios("usuarios"));
        rootEntryPointModel.add(LinkFactory.linkToPermissoes("permissoes"));
        rootEntryPointModel.add(LinkFactory.linkToFormasPagamento("formas-pagamento"));
        rootEntryPointModel.add(LinkFactory.linkToEstados("estados"));
        rootEntryPointModel.add(LinkFactory.linkToCidades("cidades"));
        rootEntryPointModel.add(LinkFactory.linkToEstatisticas("estatisticas"));
        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> { }
}
