package com.algaworks.algafood.api.v2;

import com.algaworks.algafood.api.v2.controller.CidadeControllerV2;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class LinkFactoryV2 {

    public static Link linkToCidades(String rel) {
        return linkTo(CidadeControllerV2.class).withRel(rel);
    }

    public static Link linkToCidades() {
        return linkToCidades(IanaLinkRelations.SELF.value());
    }
}
