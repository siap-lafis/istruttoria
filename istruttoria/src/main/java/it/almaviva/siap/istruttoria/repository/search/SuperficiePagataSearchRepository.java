package it.almaviva.siap.istruttoria.repository.search;

import it.almaviva.siap.istruttoria.domain.SuperficiePagata;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SuperficiePagata entity.
 */
public interface SuperficiePagataSearchRepository extends ElasticsearchRepository<SuperficiePagata, Long> {
}
