package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class FakeEnvioEmailService implements EnvioEmailService {
	@Autowired
	private ProcessadorTemplateEmail processadorTemplateEmail;
	@Override
	public void enviar(Mensagem mensagem) {
		String corpo = processadorTemplateEmail.processarTemplate(mensagem);
		log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
	}
}
