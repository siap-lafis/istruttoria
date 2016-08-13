package it.almaviva.siap.istruttoria.repository;

import it.almaviva.siap.istruttoria.domain.Domanda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

/**
 * Spring Data JPA repository for the Domanda entity.
 */
@SuppressWarnings("unused")
public interface DomandaRepository extends JpaRepository<Domanda,Long>, QueryDslPredicateExecutor<Domanda> {
	
	Domanda findById(Long id);
	Page<Domanda> findBySoggettoId(Long id,Pageable pageable);
	
}
