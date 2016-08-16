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

import it.almaviva.siap.istruttoria.domain.Soggetto;
import it.almaviva.siap.istruttoria.repository.SoggettoRepository;
import it.almaviva.siap.istruttoria.repository.search.SoggettoSearchRepository;
import it.almaviva.siap.istruttoria.web.rest.util.HeaderUtil;
import it.almaviva.siap.istruttoria.web.rest.util.PaginationUtil;

import com.mysema.query.types.Predicate;
import it.almaviva.siap.istruttoria.domain.QSoggetto;

/**
 * REST controller for managing Soggetto.
 */
@RestController
@RequestMapping("/api")
public class SoggettoResource {

    private final Logger log = LoggerFactory.getLogger(SoggettoResource.class);
        
    @Inject
    private SoggettoRepository soggettoRepository;
    
    @Inject
    private SoggettoSearchRepository soggettoSearchRepository;
    
    /**
     * POST  /soggettos : Create a new soggetto.
     *
     * @param soggetto the soggetto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new soggetto, or with status 400 (Bad Request) if the soggetto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/soggettos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Soggetto> createSoggetto(@RequestBody Soggetto soggetto) throws URISyntaxException {
        log.debug("REST request to save Soggetto : {}", soggetto);
        if (soggetto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("soggetto", "idexists", "A new soggetto cannot already have an ID")).body(null);
        }
        Soggetto result = soggettoRepository.save(soggetto);
        soggettoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/soggettos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("soggetto", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /soggettos : Updates an existing soggetto.
     *
     * @param soggetto the soggetto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated soggetto,
     * or with status 400 (Bad Request) if the soggetto is not valid,
     * or with status 500 (Internal Server Error) if the soggetto couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/soggettos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Soggetto> updateSoggetto(@RequestBody Soggetto soggetto) throws URISyntaxException {
        log.debug("REST request to update Soggetto : {}", soggetto);
        if (soggetto.getId() == null) {
            return createSoggetto(soggetto);
        }
        Soggetto result = soggettoRepository.save(soggetto);
        soggettoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("soggetto", soggetto.getId().toString()))
            .body(result);
    }

//    /**
//     * GET  /soggettos : get all the soggettos.
//     *
//     * @return the ResponseEntity with status 200 (OK) and the list of soggettos in body
//     */
//    @RequestMapping(value = "/soggettos",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public List<Soggetto> getAllSoggettos() {
//        log.debug("REST request to get all Soggettos");
//        List<Soggetto> soggettos = soggettoRepository.findAll();
//        return soggettos;
//    }
    
    
    /**
     * GET  /soggettos : get all the soggettos. Versione paginata
     *
     * @return the ResponseEntity with status 200 (OK) and the list of soggettos in body
     */
    @RequestMapping(value = "/soggettos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Soggetto>> getAllSoggettos(Pageable pageable)  throws URISyntaxException {
        log.debug("REST request to get all Soggettos");
        Page<Soggetto> page = soggettoRepository.findAll(pageable);
	    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/soggettos");
	    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /soggettos/:id : get the "id" soggetto.
     *
     * @param id the id of the soggetto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the soggetto, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/soggettos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Soggetto> getSoggetto(@PathVariable Long id) {
        log.debug("REST request to get Soggetto : {}", id);
        Soggetto soggetto = soggettoRepository.findOne(id);
        return Optional.ofNullable(soggetto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /soggettos/:id : delete the "id" soggetto.
     *
     * @param id the id of the soggetto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/soggettos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSoggetto(@PathVariable Long id) {
        log.debug("REST request to delete Soggetto : {}", id);
        soggettoRepository.delete(id);
        soggettoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("soggetto", id.toString())).build();
    }

    /**
     * SEARCH  /_search/soggettos?query=:query : search for the soggetto corresponding
     * to the query.
     *
     * @param query the query of the soggetto search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/soggettos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Soggetto>> searchSoggettos(@RequestParam String query, Pageable pageable) throws URISyntaxException {
        log.debug("REST request to search Soggettos for query {}", query);
        QSoggetto soggetto = new QSoggetto("soggetto");

        Predicate predicate = soggetto.cuaa.eq(query.trim())
    			.or(soggetto.partitaIva.eq(query.trim()))
    			.or(soggetto.denominazione.containsIgnoreCase(query.trim()));
    	
		Page<Soggetto> page = soggettoRepository.findAll(predicate, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/_search/soggettos?query=" + query);
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        /*
        return StreamSupport
            .stream(soggettoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
         */
    }


}
