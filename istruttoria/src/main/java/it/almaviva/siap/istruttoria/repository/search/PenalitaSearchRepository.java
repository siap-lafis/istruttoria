package it.almaviva.siap.istruttoria.repository.search;

import it.almaviva.siap.istruttoria.domain.Penalita;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Penalita entity.
 */
public interface PenalitaSearchRepository extends ElasticsearchRepository<Penalita, Long> {
}
