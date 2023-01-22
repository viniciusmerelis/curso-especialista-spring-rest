package com.algaworks.algafood;

import java.util.TimeZone;

import com.algaworks.algafood.core.io.Base64ProtocolResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.algaworks.algafood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgafoodApiJpaApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		var app = new SpringApplication(AlgafoodApiJpaApplication.class);
		app.addListeners(new Base64ProtocolResolver());
		app.run(args);
//		SpringApplication.run(AlgafoodApiJpaApplication.class, args);
	}

}
