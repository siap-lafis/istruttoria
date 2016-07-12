package it.almaviva.siap.istruttoria.repository;

import it.almaviva.siap.istruttoria.domain.Soggetto;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Soggetto entity.
 */
@SuppressWarnings("unused")
public interface SoggettoRepository extends JpaRepository<Soggetto,Long> {

}
