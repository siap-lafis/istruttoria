package it.almaviva.siap.istruttoria.repository;

import it.almaviva.siap.istruttoria.domain.Aduxstce;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Aduxstce entity.
 */
@SuppressWarnings("unused")
public interface AduxstceRepository extends JpaRepository<Aduxstce,Long> {

}
