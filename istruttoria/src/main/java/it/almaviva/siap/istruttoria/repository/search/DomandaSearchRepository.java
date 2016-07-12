package it.almaviva.siap.istruttoria.repository.search;

import it.almaviva.siap.istruttoria.domain.Domanda;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Domanda entity.
 */
public interface DomandaSearchRepository extends ElasticsearchRepository<Domanda, Long> {
}
