package it.almaviva.siap.istruttoria.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

import it.almaviva.siap.istruttoria.domain.Penalita;
import it.almaviva.siap.istruttoria.domain.Superficie;
import it.almaviva.siap.istruttoria.repository.PenalitaRepository;
import it.almaviva.siap.istruttoria.repository.search.PenalitaSearchRepository;
import it.almaviva.siap.istruttoria.web.rest.util.HeaderUtil;
import it.almaviva.siap.istruttoria.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Penalita.
 */
@RestController
@RequestMapping("/api")
public class PenalitaResource {

    private final Logger log = LoggerFactory.getLogger(PenalitaResource.class);
        
    @Inject
    private PenalitaRepository penalitaRepository;
    
    @Inject
    private PenalitaSearchRepository penalitaSearchRepository;
    
    /**
     * POST  /penalitas : Create a new penalita.
     *
     * @param penalita the penalita to create
     * @return the ResponseEntity with status 201 (Created) and with body the new penalita, or with status 400 (Bad Request) if the penalita has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/penalitas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Penalita> createPenalita(@RequestBody Penalita penalita) throws URISyntaxException {
        log.debug("REST request to save Penalita : {}", penalita);
        if (penalita.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("penalita", "idexists", "A new penalita cannot already have an ID")).body(null);
        }
        Penalita result = penalitaRepository.save(penalita);
        penalitaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/penalitas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("penalita", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /penalitas : Updates an existing penalita.
     *
     * @param penalita the penalita to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated penalita,
     * or with status 400 (Bad Request) if the penalita is not valid,
     * or with status 500 (Internal Server Error) if the penalita couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/penalitas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Penalita> updatePenalita(@RequestBody Penalita penalita) throws URISyntaxException {
        log.debug("REST request to update Penalita : {}", penalita);
        if (penalita.getId() == null) {
            return createPenalita(penalita);
        }
        Penalita result = penalitaRepository.save(penalita);
        penalitaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("penalita", penalita.getId().toString()))
            .body(result);
    }

//    /**
//     * GET  /penalitas : get all the penalitas.
//     *
//     * @return the ResponseEntity with status 200 (OK) and the list of penalitas in body
//     */
//    @RequestMapping(value = "/penalitas",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public List<Penalita> getAllPenalitas() {
//        log.debug("REST request to get all Penalitas");
//        List<Penalita> penalitas = penalitaRepository.findAll();
//        return penalitas;
//    }
    

    /**
     * GET  /penalitas : get all the penalitas. Versione paginata
     *
     * @return the ResponseEntity with status 200 (OK) and the list of penalitas in body
     */
    @RequestMapping(value = "/penalitas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Penalita>> getAllPenalitas(Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get all Penalitas");
        Page<Penalita> page = penalitaRepository.findAll(pageable);
	    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/penalitas");
	    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);       
    }

    /**
     * GET  /penalitas/:id : get the "id" penalita.
     *
     * @param id the id of the penalita to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the penalita, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/penalitas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Penalita> getPenalita(@PathVariable Long id) {
        log.debug("REST request to get Penalita : {}", id);
        Penalita penalita = penalitaRepository.findOne(id);
        return Optional.ofNullable(penalita)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /penalitas/:id : delete the "id" penalita.
     *
     * @param id the id of the penalita to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/penalitas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePenalita(@PathVariable Long id) {
        log.debug("REST request to delete Penalita : {}", id);
        penalitaRepository.delete(id);
        penalitaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("penalita", id.toString())).build();
    }

    /**
     * SEARCH  /_search/penalitas?query=:query : search for the penalita corresponding
     * to the query.
     *
     * @param query the query of the penalita search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/penalitas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Penalita> searchPenalitas(@RequestParam String query) {
        log.debug("REST request to search Penalitas for query {}", query);
        return StreamSupport
            .stream(penalitaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
    
    /**
     * GET  /penalitas/pagamento:id : get penalità by "id" pagamento.
     *
     * @param id the id of the domanda including the superfici to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the superfici, or with status 404 (Not Found)
     * @throws URISyntaxException 
     */
    @RequestMapping(value = "/penalitas/pagamento/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Penalita>> getPenalitaPagamento(@PathVariable Long id,Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get getPenalitaPagamento : {}", id);
        Page<Penalita> page = penalitaRepository.findByPagamentoId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/penalitas/pagamento/{id}");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
