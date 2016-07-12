package it.almaviva.siap.istruttoria.repository;

import it.almaviva.siap.istruttoria.domain.Superficie;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Superficie entity.
 */
@SuppressWarnings("unused")
public interface SuperficieRepository extends JpaRepository<Superficie,Long> {

	List<Superficie> findByDomandaId(Long id);
}
