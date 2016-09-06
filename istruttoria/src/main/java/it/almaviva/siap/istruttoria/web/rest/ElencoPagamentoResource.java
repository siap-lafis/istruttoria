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

import it.almaviva.siap.istruttoria.domain.ElencoPagamento;
import it.almaviva.siap.istruttoria.domain.QElencoPagamento;
import it.almaviva.siap.istruttoria.repository.ElencoPagamentoRepository;
import it.almaviva.siap.istruttoria.repository.search.ElencoPagamentoSearchRepository;
import it.almaviva.siap.istruttoria.web.rest.util.HeaderUtil;
import it.almaviva.siap.istruttoria.web.rest.util.PaginationUtil;

/**
 * REST controller for managing ElencoPagamento.
 */
@RestController
@RequestMapping("/api")
public class ElencoPagamentoResource {

    private final Logger log = LoggerFactory.getLogger(ElencoPagamentoResource.class);
        
    @Inject
    private ElencoPagamentoRepository elencoPagamentoRepository;
    
    @Inject
    private ElencoPagamentoSearchRepository elencoPagamentoSearchRepository;
    
    /**
     * POST  /elenco-pagamentos : Create a new elencoPagamento.
     *
     * @param elencoPagamento the elencoPagamento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new elencoPagamento, or with status 400 (Bad Request) if the elencoPagamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/elenco-pagamentos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ElencoPagamento> createElencoPagamento(@RequestBody ElencoPagamento elencoPagamento) throws URISyntaxException {
        log.debug("REST request to save ElencoPagamento : {}", elencoPagamento);
        if (elencoPagamento.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("elencoPagamento", "idexists", "A new elencoPagamento cannot already have an ID")).body(null);
        }
        ElencoPagamento result = elencoPagamentoRepository.save(elencoPagamento);
        elencoPagamentoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/elenco-pagamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("elencoPagamento", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /elenco-pagamentos : Updates an existing elencoPagamento.
     *
     * @param elencoPagamento the elencoPagamento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated elencoPagamento,
     * or with status 400 (Bad Request) if the elencoPagamento is not valid,
     * or with status 500 (Internal Server Error) if the elencoPagamento couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/elenco-pagamentos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ElencoPagamento> updateElencoPagamento(@RequestBody ElencoPagamento elencoPagamento) throws URISyntaxException {
        log.debug("REST request to update ElencoPagamento : {}", elencoPagamento);
        if (elencoPagamento.getId() == null) {
            return createElencoPagamento(elencoPagamento);
        }
        ElencoPagamento result = elencoPagamentoRepository.save(elencoPagamento);
        elencoPagamentoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("elencoPagamento", elencoPagamento.getId().toString()))
            .body(result);
    }

//    /**
//     * GET  /elenco-pagamentos : get all the elencoPagamentos.
//     *
//     * @return the ResponseEntity with status 200 (OK) and the list of elencoPagamentos in body
//     */
//    @RequestMapping(value = "/elenco-pagamentos",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public List<ElencoPagamento> getAllElencoPagamentos() {
//        log.debug("REST request to get all ElencoPagamentos");
//        List<ElencoPagamento> elencoPagamentos = elencoPagamentoRepository.findAll();
//        return elencoPagamentos;
//    }
    
    /**
     * GET  /elenco-pagamentos : get all the elencoPagamentos. Versione paginata
     *
     * @return the ResponseEntity with status 200 (OK) and the list of elencoPagamentos in body
     */
    @RequestMapping(value = "/elenco-pagamentos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ElencoPagamento>> getAllElencoPagamentos(Pageable pageable)  throws URISyntaxException{
        log.debug("REST request to get all ElencoPagamentos");          
        Page<ElencoPagamento> page = elencoPagamentoRepository.findAll(pageable);
	    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/elenco-pagamentos");
	    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /elenco-pagamentos/:id : get the "id" elencoPagamento.
     *
     * @param id the id of the elencoPagamento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the elencoPagamento, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/elenco-pagamentos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ElencoPagamento> getElencoPagamento(@PathVariable Long id) {
        log.debug("REST request to get ElencoPagamento : {}", id);
        ElencoPagamento elencoPagamento = elencoPagamentoRepository.findOne(id);
        return Optional.ofNullable(elencoPagamento)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /elenco-pagamentos/:id : delete the "id" elencoPagamento.
     *
     * @param id the id of the elencoPagamento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/elenco-pagamentos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteElencoPagamento(@PathVariable Long id) {
        log.debug("REST request to delete ElencoPagamento : {}", id);
        elencoPagamentoRepository.delete(id);
        elencoPagamentoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("elencoPagamento", id.toString())).build();
    }

    /**
     * SEARCH  /_search/elenco-pagamentos?query=:query : search for the elencoPagamento corresponding
     * to the query.
     *
     * @param query the query of the elencoPagamento search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/elenco-pagamentos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ElencoPagamento>> searchElencoPagamentos(@RequestParam String query, Pageable pageable) throws URISyntaxException {
        log.debug("REST request to search ElencoPagamentos for query {}", query);
        /*
         * Using QueryDsl as explained in 
         * https://jhipster.github.io/tips/003_tip_add_querydsl_support.html
         */
        QElencoPagamento elencoPagamento = new QElencoPagamento("elencoPagamento");
        Predicate predicate;
        try {
        	predicate = elencoPagamento.domanda.idDomanda.eq(Integer.parseInt(query.trim()))
					.or(elencoPagamento.domanda.soggetto.cuaa.eq(query.trim()));
        }
        catch (NumberFormatException  nfe) {
        	predicate = elencoPagamento.domanda.soggetto.cuaa.eq(query.trim());
        }
        Page<ElencoPagamento> page = elencoPagamentoRepository.findAll(predicate, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/elenco-pagamentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        //  return StreamSupport
        //   .stream(elencoPagamentoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        // .collect(Collectors.toList());
    }


}
