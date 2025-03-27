package br.com.nttdata.skillbuilder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.nttdata.skillbuilder.adapter.out.notification.NotificacaoGatewayAdapter;
import br.com.nttdata.skillbuilder.adapter.out.persistence.AtividadeConcluidaRepositoryAdapter;
import br.com.nttdata.skillbuilder.adapter.out.persistence.AtividadeRepositoryAdapter;
import br.com.nttdata.skillbuilder.adapter.out.persistence.CredencialUsuarioRepositoryAdapter;
import br.com.nttdata.skillbuilder.adapter.out.persistence.CursoRepositoryAdapter;
import br.com.nttdata.skillbuilder.adapter.out.persistence.InformacaoProfessorRepositoryAdapter;
import br.com.nttdata.skillbuilder.adapter.out.persistence.NotificacaoRepositoryAdapter;
import br.com.nttdata.skillbuilder.adapter.out.persistence.PontuacaoRepositoryAdapter;
import br.com.nttdata.skillbuilder.adapter.out.persistence.ProgressoRepositoryAdapter;
import br.com.nttdata.skillbuilder.adapter.out.persistence.RankingRepositoryAdapter;
import br.com.nttdata.skillbuilder.adapter.out.persistence.RecompensaRepositoryAdapter;
import br.com.nttdata.skillbuilder.adapter.out.persistence.RelatorioRepositoryAdapter;
import br.com.nttdata.skillbuilder.adapter.out.persistence.ResgateRepositoryAdapter;
import br.com.nttdata.skillbuilder.adapter.out.persistence.SessaoRepositoryAdapter;
import br.com.nttdata.skillbuilder.adapter.out.persistence.UsuarioRepositoryAdapter;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.AtividadeConcluidaJpaRepository;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.AtividadeJpaRepository;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.CredencialUsuarioJpaRepository;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.CursoJpaRepository;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.InformacaoProfessorJpaRepository;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.NotificacaoJpaRepository;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.PontuacaoJpaRepository;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.ProgressoJpaRepository;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.RankingJpaRepository;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.RecompensaJpaRepository;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.ResgateJpaRepository;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.SessaoJpaRepository;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.UsuarioJpaRepository;
import br.com.nttdata.skillbuilder.application.port.out.AtividadeConcluidaRepository;
import br.com.nttdata.skillbuilder.application.port.out.AtividadeRepository;
import br.com.nttdata.skillbuilder.application.port.out.CredencialUsuarioRepository;
import br.com.nttdata.skillbuilder.application.port.out.CursoRepository;
import br.com.nttdata.skillbuilder.application.port.out.InformacaoProfessorRepository;
import br.com.nttdata.skillbuilder.application.port.out.NotificacaoGateway;
import br.com.nttdata.skillbuilder.application.port.out.NotificacaoRepository;
import br.com.nttdata.skillbuilder.application.port.out.PontuacaoRepository;
import br.com.nttdata.skillbuilder.application.port.out.ProgressoRepository;
import br.com.nttdata.skillbuilder.application.port.out.RankingRepository;
import br.com.nttdata.skillbuilder.application.port.out.RecompensaRepository;
import br.com.nttdata.skillbuilder.application.port.out.RelatorioRepository;
import br.com.nttdata.skillbuilder.application.port.out.ResgateRepository;
import br.com.nttdata.skillbuilder.application.port.out.SessaoRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;

/**
 * Configuração dos adaptadores de saída para injeção de dependência
 */
@Configuration
public class PersistenceAdapterModule {

    @Bean
    public UsuarioRepository usuarioRepository(UsuarioJpaRepository usuarioJpaRepository) {
        return new UsuarioRepositoryAdapter(usuarioJpaRepository);
    }

    @Bean
    public CredencialUsuarioRepository credencialUsuarioRepository(CredencialUsuarioJpaRepository credencialUsuarioJpaRepository) {
        return new CredencialUsuarioRepositoryAdapter(credencialUsuarioJpaRepository);
    }

    @Bean
    public CursoRepository cursoRepository(CursoJpaRepository cursoJpaRepository) {
        return new CursoRepositoryAdapter(cursoJpaRepository);
    }

    @Bean
    public AtividadeRepository atividadeRepository(AtividadeJpaRepository atividadeJpaRepository) {
        return new AtividadeRepositoryAdapter(atividadeJpaRepository);
    }

    @Bean
    public AtividadeConcluidaRepository atividadeConcluidaRepository(AtividadeConcluidaJpaRepository atividadeConcluidaJpaRepository) {
        return new AtividadeConcluidaRepositoryAdapter(atividadeConcluidaJpaRepository);
    }

    @Bean
    public ProgressoRepository progressoRepository(ProgressoJpaRepository progressoJpaRepository) {
        return new ProgressoRepositoryAdapter(progressoJpaRepository);
    }

    @Bean
    public PontuacaoRepository pontuacaoRepository(PontuacaoJpaRepository pontuacaoJpaRepository) {
        return new PontuacaoRepositoryAdapter(pontuacaoJpaRepository);
    }

    @Bean
    public RankingRepository rankingRepository(RankingJpaRepository rankingJpaRepository, PontuacaoJpaRepository pontuacaoJpaRepository) {
        return new RankingRepositoryAdapter(rankingJpaRepository, pontuacaoJpaRepository);
    }

    @Bean
    public RecompensaRepository recompensaRepository(RecompensaJpaRepository recompensaJpaRepository) {
        return new RecompensaRepositoryAdapter(recompensaJpaRepository);
    }

    @Bean
    public ResgateRepository resgateRepository(ResgateJpaRepository resgateJpaRepository) {
        return new ResgateRepositoryAdapter(resgateJpaRepository);
    }

    @Bean
    public NotificacaoRepository notificacaoRepository(NotificacaoJpaRepository notificacaoJpaRepository) {
        return new NotificacaoRepositoryAdapter(notificacaoJpaRepository);
    }

    @Bean
    public InformacaoProfessorRepository informacaoProfessorRepository(InformacaoProfessorJpaRepository informacaoProfessorJpaRepository) {
        return new InformacaoProfessorRepositoryAdapter(informacaoProfessorJpaRepository);
    }

    @Bean
    public SessaoRepository sessaoRepository(SessaoJpaRepository sessaoJpaRepository) {
        return new SessaoRepositoryAdapter(sessaoJpaRepository);
    }

    @Bean
    public NotificacaoGateway notificacaoGateway() {
        return new NotificacaoGatewayAdapter();
    }

    @Bean
    public RelatorioRepository relatorioRepository(
            UsuarioRepository usuarioRepository,
            CursoRepository cursoRepository,
            AtividadeRepository atividadeRepository,
            ProgressoRepository progressoRepository,
            PontuacaoRepository pontuacaoRepository,
            RankingRepository rankingRepository) {
        return new RelatorioRepositoryAdapter(
                usuarioRepository,
                cursoRepository,
                atividadeRepository,
                progressoRepository,
                pontuacaoRepository,
                rankingRepository);
    }
}