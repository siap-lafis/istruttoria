package it.almaviva.siap.istruttoria.repository;

import it.almaviva.siap.istruttoria.domain.ElencoPagamento;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ElencoPagamento entity.
 */
@SuppressWarnings("unused")
public interface ElencoPagamentoRepository extends JpaRepository<ElencoPagamento,Long> {

}
