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

import it.almaviva.siap.istruttoria.domain.Domanda;
import it.almaviva.siap.istruttoria.domain.QDomanda;
import it.almaviva.siap.istruttoria.repository.DomandaRepository;
import it.almaviva.siap.istruttoria.repository.search.DomandaSearchRepository;
import it.almaviva.siap.istruttoria.web.rest.util.HeaderUtil;
import it.almaviva.siap.istruttoria.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Domanda.
 */
@RestController
@RequestMapping("/api")
public class DomandaResource {

    private final Logger log = LoggerFactory.getLogger(DomandaResource.class);
        
    @Inject
    private DomandaRepository domandaRepository;
    
    @Inject
    private DomandaSearchRepository domandaSearchRepository;
    
    /**
     * POST  /domandas : Create a new domanda.
     *
     * @param domanda the domanda to create
     * @return the ResponseEntity with status 201 (Created) and with body the new domanda, or with status 400 (Bad Request) if the domanda has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/domandas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Domanda> createDomanda(@RequestBody Domanda domanda) throws URISyntaxException {
        log.debug("REST request to save Domanda : {}", domanda);
        if (domanda.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("domanda", "idexists", "A new domanda cannot already have an ID")).body(null);
        }
        Domanda result = domandaRepository.save(domanda);
        domandaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/domandas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("domanda", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /domandas : Updates an existing domanda.
     *
     * @param domanda the domanda to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated domanda,
     * or with status 400 (Bad Request) if the domanda is not valid,
     * or with status 500 (Internal Server Error) if the domanda couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/domandas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Domanda> updateDomanda(@RequestBody Domanda domanda) throws URISyntaxException {
        log.debug("REST request to update Domanda : {}", domanda);
        if (domanda.getId() == null) {
            return createDomanda(domanda);
        }
        Domanda result = domandaRepository.save(domanda);
        domandaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("domanda", domanda.getId().toString()))
            .body(result);
    }

//    /**
//     * GET  /domandas : get all the domandas.
//     *
//     * @return the ResponseEntity with status 200 (OK) and the list of domandas in body
//     */
//    @RequestMapping(value = "/domandas",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public List<Domanda> getAllDomandas() {
//        log.debug("REST request to get all Domandas");
//        List<Domanda> domandas = domandaRepository.findAll();
//        return domandas;
//    }
    
	  /**
	  * GET  /domandas : get all the domandas. Versione paginata
	  *
	  * @return the ResponseEntity with status 200 (OK) and the list of domandas in body
	  */
	 @RequestMapping(value = "/domandas",
	     method = RequestMethod.GET,
	     produces = MediaType.APPLICATION_JSON_VALUE)
	 @Timed
	 public ResponseEntity<List<Domanda>> getAllDomandas(Pageable pageable) throws URISyntaxException {
	     log.debug("REST request to get all Domandas");	    	     
	     Page<Domanda> page = domandaRepository.findAll(pageable);
	     HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/domandas");
	     return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	 }

    /**
     * GET  /domandas/:id : get the "id" domanda.
     *
     * @param id the id of the domanda to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the domanda, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/domandas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Domanda> getDomanda(@PathVariable Long id) {
        log.debug("REST request to get Domanda : {}", id);
        Domanda domanda = domandaRepository.findOne(id);
        return Optional.ofNullable(domanda)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /domandas/:id : delete the "id" domanda.
     *
     * @param id the id of the domanda to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/domandas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDomanda(@PathVariable Long id) {
        log.debug("REST request to delete Domanda : {}", id);
        domandaRepository.delete(id);
        domandaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("domanda", id.toString())).build();
    }

    /**
     * SEARCH  /_search/domandas?query=:query : search for the domanda corresponding
     * to the query.
     *
     * @param query the query of the domanda search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/domandas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Domanda>> searchDomandas(@RequestParam String query, Pageable pageable) throws URISyntaxException {
        log.debug("REST request to search Domandas for query {}", query);
        /*
         * Using QueryDsl as explained in 
         * https://jhipster.github.io/tips/003_tip_add_querydsl_support.html
         */
        QDomanda domanda = new QDomanda("domanda");
        Predicate predicate;
        try {
        	predicate = domanda.idDomanda.eq(Integer.parseInt(query.trim()))
        			.or(domanda.soggetto.cuaa.eq(query.trim()));
        }
        catch (NumberFormatException  nfe) {
        	predicate = domanda.soggetto.cuaa.eq(query.trim());
        }
		Page<Domanda> page = domandaRepository.findAll(predicate, pageable);
		HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query,page, "/api/_search/domandas");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        /*
        return StreamSupport
            .stream(domandaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
         */
    }
    
    /**
     * GET  /domandas/soggetto/:id : get superfici by "id" domanda.
     *
     * @param id the id of the domanda including the superfici to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the superfici, or with status 404 (Not Found)
     * @throws URISyntaxException 
     */
    @RequestMapping(value = "/domandas/soggetto/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Domanda>> getDomandeSoggetto(@PathVariable Long id,Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get DomandeSoggetto : {}", id);
        Page<Domanda> page = domandaRepository.findBySoggettoId(id,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/domandas/soggetto/{id}");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
