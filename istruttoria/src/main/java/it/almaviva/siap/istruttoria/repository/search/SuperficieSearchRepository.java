package it.almaviva.siap.istruttoria.repository.search;

import it.almaviva.siap.istruttoria.domain.Superficie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Superficie entity.
 */
public interface SuperficieSearchRepository extends ElasticsearchRepository<Superficie, Long> {
}
