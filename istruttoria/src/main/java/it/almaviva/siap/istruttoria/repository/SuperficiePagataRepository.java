package it.almaviva.siap.istruttoria.repository;

import it.almaviva.siap.istruttoria.domain.SuperficiePagata;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SuperficiePagata entity.
 */
@SuppressWarnings("unused")
public interface SuperficiePagataRepository extends JpaRepository<SuperficiePagata,Long> {

}
