package it.almaviva.siap.istruttoria.repository.search;

import it.almaviva.siap.istruttoria.domain.SuperficieInverdimento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SuperficieInverdimento entity.
 */
public interface SuperficieInverdimentoSearchRepository extends ElasticsearchRepository<SuperficieInverdimento, Long> {
}
