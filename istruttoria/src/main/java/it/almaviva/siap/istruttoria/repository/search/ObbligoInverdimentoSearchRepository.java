package it.almaviva.siap.istruttoria.repository.search;

import it.almaviva.siap.istruttoria.domain.ObbligoInverdimento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ObbligoInverdimento entity.
 */
public interface ObbligoInverdimentoSearchRepository extends ElasticsearchRepository<ObbligoInverdimento, Long> {
}
