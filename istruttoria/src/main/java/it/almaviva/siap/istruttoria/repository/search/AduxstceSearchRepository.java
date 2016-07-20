package it.almaviva.siap.istruttoria.repository.search;

import it.almaviva.siap.istruttoria.domain.Aduxstce;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Aduxstce entity.
 */
public interface AduxstceSearchRepository extends ElasticsearchRepository<Aduxstce, Long> {
}
