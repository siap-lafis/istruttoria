package it.almaviva.siap.istruttoria.repository;

import it.almaviva.siap.istruttoria.domain.CapoPagato;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CapoPagato entity.
 */
@SuppressWarnings("unused")
public interface CapoPagatoRepository extends JpaRepository<CapoPagato,Long> {

}
