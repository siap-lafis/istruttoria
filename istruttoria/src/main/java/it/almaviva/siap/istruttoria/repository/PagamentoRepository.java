package it.almaviva.siap.istruttoria.repository;

import it.almaviva.siap.istruttoria.domain.Pagamento;
import it.almaviva.siap.istruttoria.domain.Superficie;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pagamento entity.
 */
@SuppressWarnings("unused")
public interface PagamentoRepository extends JpaRepository<Pagamento,Long> {

	List<Pagamento> findByElencoPagamentoDomandaId(Long id);
	List<Pagamento> findByIdAttoAmmi(Integer idAttoAmmi);
}
