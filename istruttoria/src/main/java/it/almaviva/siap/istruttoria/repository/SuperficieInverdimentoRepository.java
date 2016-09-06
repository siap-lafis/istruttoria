package it.almaviva.siap.istruttoria.repository;

import it.almaviva.siap.istruttoria.domain.Superficie;
import it.almaviva.siap.istruttoria.domain.SuperficieInverdimento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

/**
 * Spring Data JPA repository for the SuperficieInverdimento entity.
 */
@SuppressWarnings("unused")
public interface SuperficieInverdimentoRepository extends JpaRepository<SuperficieInverdimento,Long>, QueryDslPredicateExecutor<SuperficieInverdimento> {
	
	Page<SuperficieInverdimento> findByDomandaId(Long id,Pageable pageable);

}
