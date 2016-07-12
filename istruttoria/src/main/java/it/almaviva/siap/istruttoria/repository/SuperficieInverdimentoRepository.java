package it.almaviva.siap.istruttoria.repository;

import it.almaviva.siap.istruttoria.domain.SuperficieInverdimento;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SuperficieInverdimento entity.
 */
@SuppressWarnings("unused")
public interface SuperficieInverdimentoRepository extends JpaRepository<SuperficieInverdimento,Long> {

}
