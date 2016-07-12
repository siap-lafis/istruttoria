package it.almaviva.siap.istruttoria.repository;

import it.almaviva.siap.istruttoria.domain.ObbligoInverdimento;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ObbligoInverdimento entity.
 */
@SuppressWarnings("unused")
public interface ObbligoInverdimentoRepository extends JpaRepository<ObbligoInverdimento,Long> {

}
