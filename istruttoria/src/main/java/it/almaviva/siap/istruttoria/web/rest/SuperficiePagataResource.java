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

import it.almaviva.siap.istruttoria.domain.Superficie;
import it.almaviva.siap.istruttoria.domain.SuperficiePagata;
import it.almaviva.siap.istruttoria.repository.SuperficiePagataRepository;
import it.almaviva.siap.istruttoria.repository.search.SuperficiePagataSearchRepository;
import it.almaviva.siap.istruttoria.web.rest.util.HeaderUtil;
import it.almaviva.siap.istruttoria.web.rest.util.PaginationUtil;

import com.mysema.query.types.Predicate;
import it.almaviva.siap.istruttoria.domain.QSuperficiePagata;

/**
 * REST controller for managing SuperficiePagata.
 */
@RestController
@RequestMapping("/api")
public class SuperficiePagataResource {

    private final Logger log = LoggerFactory.getLogger(SuperficiePagataResource.class);
        
    @Inject
    private SuperficiePagataRepository superficiePagataRepository;
    
    @Inject
    private SuperficiePagataSearchRepository superficiePagataSearchRepository;
    
    /**
     * POST  /superficie-pagatas : Create a new superficiePagata.
     *
     * @param superficiePagata the superficiePagata to create
     * @return the ResponseEntity with status 201 (Created) and with body the new superficiePagata, or with status 400 (Bad Request) if the superficiePagata has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/superficie-pagatas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SuperficiePagata> createSuperficiePagata(@RequestBody SuperficiePagata superficiePagata) throws URISyntaxException {
        log.debug("REST request to save SuperficiePagata : {}", superficiePagata);
        if (superficiePagata.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("superficiePagata", "idexists", "A new superficiePagata cannot already have an ID")).body(null);
        }
        SuperficiePagata result = superficiePagataRepository.save(superficiePagata);
        superficiePagataSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/superficie-pagatas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("superficiePagata", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /superficie-pagatas : Updates an existing superficiePagata.
     *
     * @param superficiePagata the superficiePagata to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated superficiePagata,
     * or with status 400 (Bad Request) if the superficiePagata is not valid,
     * or with status 500 (Internal Server Error) if the superficiePagata couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/superficie-pagatas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SuperficiePagata> updateSuperficiePagata(@RequestBody SuperficiePagata superficiePagata) throws URISyntaxException {
        log.debug("REST request to update SuperficiePagata : {}", superficiePagata);
        if (superficiePagata.getId() == null) {
            return createSuperficiePagata(superficiePagata);
        }
        SuperficiePagata result = superficiePagataRepository.save(superficiePagata);
        superficiePagataSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("superficiePagata", superficiePagata.getId().toString()))
            .body(result);
    }

//    /**
//     * GET  /superficie-pagatas : get all the superficiePagatas.
//     *
//     * @return the ResponseEntity with status 200 (OK) and the list of superficiePagatas in body
//     */
//    @RequestMapping(value = "/superficie-pagatas",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public List<SuperficiePagata> getAllSuperficiePagatas() {
//        log.debug("REST request to get all SuperficiePagatas");
//        List<SuperficiePagata> superficiePagatas = superficiePagataRepository.findAll();
//        return superficiePagatas;
//    }
    
    /**
     * GET  /superficie-pagatas : get all the superficiePagatas. Versione paginata
     *
     * @return the ResponseEntity with status 200 (OK) and the list of superficiePagatas in body
     */
    @RequestMapping(value = "/superficie-pagatas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public  ResponseEntity<List<SuperficiePagata>> getAllSuperficiePagatas(Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get all SuperficiePagatas");
        Page<SuperficiePagata> page = superficiePagataRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/superficie-pagatas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    

    /**
     * GET  /superficie-pagatas/:id : get the "id" superficiePagata.
     *
     * @param id the id of the superficiePagata to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the superficiePagata, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/superficie-pagatas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SuperficiePagata> getSuperficiePagata(@PathVariable Long id) {
        log.debug("REST request to get SuperficiePagata : {}", id);
        SuperficiePagata superficiePagata = superficiePagataRepository.findOne(id);
        return Optional.ofNullable(superficiePagata)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /superficie-pagatas/:id : delete the "id" superficiePagata.
     *
     * @param id the id of the superficiePagata to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/superficie-pagatas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSuperficiePagata(@PathVariable Long id) {
        log.debug("REST request to delete SuperficiePagata : {}", id);
        superficiePagataRepository.delete(id);
        superficiePagataSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("superficiePagata", id.toString())).build();
    }

    /**
     * SEARCH  /_search/superficie-pagatas?query=:query : search for the superficiePagata corresponding
     * to the query.
     *
     * @param query the query of the superficiePagata search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/superficie-pagatas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SuperficiePagata>> searchSuperficiePagatas(@RequestParam String query, Pageable pageable) throws URISyntaxException {
        log.debug("REST request to search SuperficiePagatas for query {}", query);
        QSuperficiePagata superficiePagata = new QSuperficiePagata("superficiePagata");
        Predicate predicate;
        try {
        	predicate = superficiePagata.pagamento.idAttoAmmi.eq(Integer.parseInt(query.trim()));
        }
        catch (NumberFormatException  nfe) {
        	//Non torna nulla
        	predicate = superficiePagata.id.eq(new Long(0));
        }
    	
		Page<SuperficiePagata> page = superficiePagataRepository.findAll(predicate, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/_search/soggettos?query=" + query);
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        /*
        return StreamSupport
            .stream(superficiePagataSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
         */
    }
    
    /**
     * GET  /superficie-pagatas/pagamento/:id : get superfici pagate by "id" pagamento.
     *
     * @param id the id of the domanda including the superfici to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the superfici, or with status 404 (Not Found)
     * @throws URISyntaxException 
     */
    @RequestMapping(value = "/superficie-pagatas/pagamento/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SuperficiePagata>> getSuperficiPagatePagamento(@PathVariable Long id,Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get SuperficiPagate by id pagamento : {}", id);
        Page<SuperficiePagata> page = superficiePagataRepository.findByPagamentoId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/superficie-pagatas/pagamento/{id}");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
