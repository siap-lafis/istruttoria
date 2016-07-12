package it.almaviva.siap.istruttoria.repository.search;

import it.almaviva.siap.istruttoria.domain.Pagamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Pagamento entity.
 */
public interface PagamentoSearchRepository extends ElasticsearchRepository<Pagamento, Long> {
}
