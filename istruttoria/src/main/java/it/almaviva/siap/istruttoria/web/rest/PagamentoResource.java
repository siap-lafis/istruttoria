package it.almaviva.siap.istruttoria.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.mysema.query.types.Predicate;

import it.almaviva.siap.istruttoria.domain.Pagamento;
import it.almaviva.siap.istruttoria.domain.QPagamento;
import it.almaviva.siap.istruttoria.repository.PagamentoRepository;
import it.almaviva.siap.istruttoria.repository.search.PagamentoSearchRepository;
import it.almaviva.siap.istruttoria.web.rest.util.HeaderUtil;
import it.almaviva.siap.istruttoria.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Pagamento.
 */
@RestController
@RequestMapping("/api")
public class PagamentoResource {

    private final Logger log = LoggerFactory.getLogger(PagamentoResource.class);
        
    @Inject
    private PagamentoRepository pagamentoRepository;
    
    @Inject
    private PagamentoSearchRepository pagamentoSearchRepository;
    
    /**
     * POST  /pagamentos : Create a new pagamento.
     *
     * @param pagamento the pagamento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pagamento, or with status 400 (Bad Request) if the pagamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/pagamentos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pagamento> createPagamento(@RequestBody Pagamento pagamento) throws URISyntaxException {
        log.debug("REST request to save Pagamento : {}", pagamento);
        if (pagamento.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pagamento", "idexists", "A new pagamento cannot already have an ID")).body(null);
        }
        Pagamento result = pagamentoRepository.save(pagamento);
        pagamentoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pagamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pagamento", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pagamentos : Updates an existing pagamento.
     *
     * @param pagamento the pagamento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pagamento,
     * or with status 400 (Bad Request) if the pagamento is not valid,
     * or with status 500 (Internal Server Error) if the pagamento couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/pagamentos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pagamento> updatePagamento(@RequestBody Pagamento pagamento) throws URISyntaxException {
        log.debug("REST request to update Pagamento : {}", pagamento);
        if (pagamento.getId() == null) {
            return createPagamento(pagamento);
        }
        Pagamento result = pagamentoRepository.save(pagamento);
        pagamentoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pagamento", pagamento.getId().toString()))
            .body(result);
    }

//    /**
//     * GET  /pagamentos : get all the pagamentos.
//     *
//     * @return the ResponseEntity with status 200 (OK) and the list of pagamentos in body
//     */
//    @RequestMapping(value = "/pagamentos",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public List<Pagamento> getAllPagamentos() {
//        log.debug("REST request to get all Pagamentos");
//        List<Pagamento> pagamentos = pagamentoRepository.findAll();
//        return pagamentos;
//    }
    
    /**
     * GET  /pagamentos : get all the pagamentos. Versione paginata
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pagamentos in body
     */
    @RequestMapping(value = "/pagamentos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Pagamento>> getAllPagamentos(Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get all Pagamentos");       
        Page<Pagamento> page = pagamentoRepository.findAll(pageable);
	    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pagamentos");
	    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pagamentos/:id : get the "id" pagamento.
     *
     * @param id the id of the pagamento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pagamento, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/pagamentos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pagamento> getPagamento(@PathVariable Long id) {
        log.debug("REST request to get Pagamento : {}", id);
        Pagamento pagamento = pagamentoRepository.findOne(id);
        return Optional.ofNullable(pagamento)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pagamentos/:id : delete the "id" pagamento.
     *
     * @param id the id of the pagamento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/pagamentos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id) {
        log.debug("REST request to delete Pagamento : {}", id);
        pagamentoRepository.delete(id);
        pagamentoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pagamento", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pagamentos?query=:query : search for the pagamento corresponding
     * to the query.
     *
     * @param query the query of the pagamento search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/pagamentos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Pagamento>> searchPagamentos(@RequestParam String query, Pageable pageable) throws URISyntaxException {
        log.debug("REST request to search Pagamentos for query {}", query);
	    /*
	     * Using QueryDsl as explained in 
	     * https://jhipster.github.io/tips/003_tip_add_querydsl_support.html
	     */
	    QPagamento pagamento = new QPagamento("pagamento");
	    Predicate predicate;
	    try {
	    	predicate = pagamento.elencoPagamento.domanda.idDomanda.eq(Integer.parseInt(query.trim()))
					.or(pagamento.elencoPagamento.domanda.soggetto.cuaa.eq(query.trim()));
	    }
	    catch (NumberFormatException  nfe) {
	    	predicate = pagamento.elencoPagamento.domanda.soggetto.cuaa.eq(query.trim());
	    }
	    Page<Pagamento> page = pagamentoRepository.findAll(predicate, pageable);
	    HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/pagamentos");
	    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//        return StreamSupport
//            .stream(pagamentoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
//            .collect(Collectors.toList());
    }


    /**
     * GET  /pagamentos/:id : get pagamenti by "id" domanda.
     *
     * @param id the id of the domanda including the pagamentos to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pagamentos, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/pagamentos/domanda/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed    
    public ResponseEntity<List<Pagamento>> getPagamentiDomanda(@PathVariable Long id,Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get Pagamenti : {}", id);
        Page<Pagamento> page = pagamentoRepository.findByElencoPagamentoDomandaId(id,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pagamentos/domanda/{id}");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
