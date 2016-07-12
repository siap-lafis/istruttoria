package it.almaviva.siap.istruttoria.repository;

import it.almaviva.siap.istruttoria.domain.Domanda;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Domanda entity.
 */
@SuppressWarnings("unused")
public interface DomandaRepository extends JpaRepository<Domanda,Long> {

}
