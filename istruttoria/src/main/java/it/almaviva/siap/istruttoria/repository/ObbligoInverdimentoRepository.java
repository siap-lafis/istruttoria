package it.almaviva.siap.istruttoria.repository;

import it.almaviva.siap.istruttoria.domain.ObbligoInverdimento;
import it.almaviva.siap.istruttoria.domain.Superficie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ObbligoInverdimento entity.
 */
@SuppressWarnings("unused")
public interface ObbligoInverdimentoRepository extends JpaRepository<ObbligoInverdimento,Long> {

	Page<ObbligoInverdimento> findBySuperficiInverdimentoId(Long id,Pageable pageable);
}
