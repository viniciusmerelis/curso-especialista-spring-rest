package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.LinkFactory;
import com.algaworks.algafood.core.security.AlgaSecurity;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AlgaSecurity algaSecurity;

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();
        if (algaSecurity.podeConsultarCozinhas()) {
            rootEntryPointModel.add(LinkFactory.linkToCozinhas("cozinhas"));
        }
        if (algaSecurity.podePesquisarPedidos()) {
            rootEntryPointModel.add(LinkFactory.linkToPedidos("pedidos"));
        }
        if (algaSecurity.podeConsultarRestaurantes()) {
            rootEntryPointModel.add(LinkFactory.linkToRestaurantes("restaurantes"));
        }
        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            rootEntryPointModel.add(LinkFactory.linkToGrupos("grupos"));
            rootEntryPointModel.add(LinkFactory.linkToUsuarios("usuarios"));
            rootEntryPointModel.add(LinkFactory.linkToPermissoes("permissoes"));
        }
        if (algaSecurity.podeConsultarFormasPagamento()) {
            rootEntryPointModel.add(LinkFactory.linkToFormasPagamento("formas-pagamento"));
        }
        if (algaSecurity.podeConsultarEstados()) {
            rootEntryPointModel.add(LinkFactory.linkToEstados("estados"));
        }
        if (algaSecurity.podeConsultarCidades()) {
            rootEntryPointModel.add(LinkFactory.linkToCidades("cidades"));
        }
        if (algaSecurity.podeConsultarEstatisticas()) {
            rootEntryPointModel.add(LinkFactory.linkToEstatisticas("estatisticas"));
        }
        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> { }
}
