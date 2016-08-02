package it.almaviva.siap.istruttoria.repository;

import it.almaviva.siap.istruttoria.domain.Penalita;
import it.almaviva.siap.istruttoria.domain.Superficie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Penalita entity.
 */
@SuppressWarnings("unused")
public interface PenalitaRepository extends JpaRepository<Penalita,Long> {
	
	Page<Penalita> findByPagamentoId(Long id,Pageable pageable);

}
