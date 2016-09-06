package it.almaviva.siap.istruttoria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import it.almaviva.siap.istruttoria.domain.ObbligoInverdimento;

/**
 * Spring Data JPA repository for the ObbligoInverdimento entity.
 */
@SuppressWarnings("unused")
public interface ObbligoInverdimentoRepository extends JpaRepository<ObbligoInverdimento,Long>, QueryDslPredicateExecutor<ObbligoInverdimento> {

	Page<ObbligoInverdimento> findBySuperficiInverdimentoId(Long id,Pageable pageable);
}
