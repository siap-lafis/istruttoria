package it.almaviva.siap.istruttoria.repository;

import it.almaviva.siap.istruttoria.domain.Penalita;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Penalita entity.
 */
@SuppressWarnings("unused")
public interface PenalitaRepository extends JpaRepository<Penalita,Long> {

}
