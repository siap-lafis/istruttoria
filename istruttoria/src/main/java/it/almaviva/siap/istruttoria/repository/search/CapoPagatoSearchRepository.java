package it.almaviva.siap.istruttoria.repository.search;

import it.almaviva.siap.istruttoria.domain.CapoPagato;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CapoPagato entity.
 */
public interface CapoPagatoSearchRepository extends ElasticsearchRepository<CapoPagato, Long> {
}
