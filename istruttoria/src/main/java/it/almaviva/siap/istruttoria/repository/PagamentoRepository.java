package it.almaviva.siap.istruttoria.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import it.almaviva.siap.istruttoria.domain.Pagamento;

/**
 * Spring Data JPA repository for the Pagamento entity.
 */
@SuppressWarnings("unused")
public interface PagamentoRepository extends JpaRepository<Pagamento,Long>, QueryDslPredicateExecutor<Pagamento> {

	List<Pagamento> findByElencoPagamentoDomandaId(Long id);
	List<Pagamento> findByIdAttoAmmi(Integer idAttoAmmi);
	Page<Pagamento> findByElencoPagamentoDomandaId(Long id,Pageable pageable);
}
