package it.almaviva.siap.istruttoria.repository;

import it.almaviva.siap.istruttoria.domain.ElencoPagamento;
import it.almaviva.siap.istruttoria.domain.SuperficieInverdimento;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

/**
 * Spring Data JPA repository for the ElencoPagamento entity.
 */
@SuppressWarnings("unused")
public interface ElencoPagamentoRepository extends JpaRepository<ElencoPagamento,Long>, QueryDslPredicateExecutor<ElencoPagamento> {

	public List<ElencoPagamento> findByDomandaId(Long id);
}
