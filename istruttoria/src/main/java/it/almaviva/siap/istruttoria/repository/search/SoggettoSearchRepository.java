package it.almaviva.siap.istruttoria.repository.search;

import it.almaviva.siap.istruttoria.domain.Soggetto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Soggetto entity.
 */
public interface SoggettoSearchRepository extends ElasticsearchRepository<Soggetto, Long> {
}
