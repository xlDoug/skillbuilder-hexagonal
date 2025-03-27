package br.com.nttdata.skillbuilder.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan("br.com.nttdata.skillbuilder.adapter.out.persistence.entity")
@EnableJpaRepositories("br.com.nttdata.skillbuilder.adapter.out.persistence.repository")
@EnableTransactionManagement
public class JpaConfig {
    // Configuração específica adicional do JPA, se necessário
}