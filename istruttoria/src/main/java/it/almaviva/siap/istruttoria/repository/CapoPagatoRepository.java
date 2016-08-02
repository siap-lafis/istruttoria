package it.almaviva.siap.istruttoria.repository;

import it.almaviva.siap.istruttoria.domain.CapoPagato;
import it.almaviva.siap.istruttoria.domain.Superficie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CapoPagato entity.
 */
@SuppressWarnings("unused")
public interface CapoPagatoRepository extends JpaRepository<CapoPagato,Long> {
	
	Page<CapoPagato> findByPagamentoId(Long id,Pageable pageable);

}
