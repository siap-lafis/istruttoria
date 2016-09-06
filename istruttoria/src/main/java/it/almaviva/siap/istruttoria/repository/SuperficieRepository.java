package it.almaviva.siap.istruttoria.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import it.almaviva.siap.istruttoria.domain.Domanda;
import it.almaviva.siap.istruttoria.domain.Superficie;

/**
 * Spring Data JPA repository for the Superficie entity.
 */
@SuppressWarnings("unused")
public interface SuperficieRepository extends JpaRepository<Superficie,Long>, QueryDslPredicateExecutor<Superficie>  {

	List<Superficie> findByDomandaId(Long id);
	Page<Superficie> findByDomandaId(Long id,Pageable pageable);
}
