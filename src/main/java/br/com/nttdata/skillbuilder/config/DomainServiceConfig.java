package br.com.nttdata.skillbuilder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.nttdata.skillbuilder.domain.service.AvaliadorService;
import br.com.nttdata.skillbuilder.domain.service.NotificacaoService;
import br.com.nttdata.skillbuilder.domain.service.PontuacaoService;
import br.com.nttdata.skillbuilder.domain.service.ProgressoService;
import br.com.nttdata.skillbuilder.domain.service.RecompensaService;
import br.com.nttdata.skillbuilder.domain.service.ValidacaoDominioService;

@Configuration
public class DomainServiceConfig {

    @Bean
    public AvaliadorService avaliadorService() {
        return new AvaliadorService();
    }

    @Bean
    public NotificacaoService notificacaoService() {
        return new NotificacaoService();
    }

    @Bean
    public PontuacaoService pontuacaoService() {
        return new PontuacaoService();
    }

    @Bean
    public ProgressoService progressoService() {
        return new ProgressoService();
    }

    @Bean
    public RecompensaService recompensaService() {
        return new RecompensaService();
    }

    @Bean
    public ValidacaoDominioService validacaoDominioService() {
        return new ValidacaoDominioService();
    }
}