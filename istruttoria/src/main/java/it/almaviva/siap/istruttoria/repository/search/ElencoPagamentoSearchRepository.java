package it.almaviva.siap.istruttoria.repository.search;

import it.almaviva.siap.istruttoria.domain.ElencoPagamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ElencoPagamento entity.
 */
public interface ElencoPagamentoSearchRepository extends ElasticsearchRepository<ElencoPagamento, Long> {
}
