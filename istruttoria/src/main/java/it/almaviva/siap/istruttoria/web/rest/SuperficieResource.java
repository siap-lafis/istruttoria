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
import it.almaviva.siap.istruttoria.repository.SuperficieRepository;
import it.almaviva.siap.istruttoria.repository.search.SuperficieSearchRepository;
import it.almaviva.siap.istruttoria.web.rest.util.HeaderUtil;
import it.almaviva.siap.istruttoria.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Superficie.
 */
@RestController
@RequestMapping("/api")
public class SuperficieResource {

    private final Logger log = LoggerFactory.getLogger(SuperficieResource.class);
        
    @Inject
    private SuperficieRepository superficieRepository;
    
    @Inject
    private SuperficieSearchRepository superficieSearchRepository;
    
    /**
     * POST  /superficies : Create a new superficie.
     *
     * @param superficie the superficie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new superficie, or with status 400 (Bad Request) if the superficie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/superficies",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Superficie> createSuperficie(@RequestBody Superficie superficie) throws URISyntaxException {
        log.debug("REST request to save Superficie : {}", superficie);
        if (superficie.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("superficie", "idexists", "A new superficie cannot already have an ID")).body(null);
        }
        Superficie result = superficieRepository.save(superficie);
        superficieSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/superficies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("superficie", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /superficies : Updates an existing superficie.
     *
     * @param superficie the superficie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated superficie,
     * or with status 400 (Bad Request) if the superficie is not valid,
     * or with status 500 (Internal Server Error) if the superficie couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/superficies",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Superficie> updateSuperficie(@RequestBody Superficie superficie) throws URISyntaxException {
        log.debug("REST request to update Superficie : {}", superficie);
        if (superficie.getId() == null) {
            return createSuperficie(superficie);
        }
        Superficie result = superficieRepository.save(superficie);
        superficieSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("superficie", superficie.getId().toString()))
            .body(result);
    }

//    /**
//     * GET  /superficies : get all the superficies.
//     *
//     * @return the ResponseEntity with status 200 (OK) and the list of superficies in body
//     */
//    @RequestMapping(value = "/superficies",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public List<Superficie> getAllSuperficies() {
//        log.debug("REST request to get all Superficies");
//        List<Superficie> superficies = superficieRepository.findAll();
//        return superficies;
//    }
    
    
    /**
     * GET  /superficies : recupera tutte le superfici paginate
     *
     * @return the ResponseEntity with status 200 (OK) and the list of superficies in body
     */
    @RequestMapping(value = "/superficies",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Superficie>> getAllSuperficies(Pageable pageable)
    throws URISyntaxException {
        log.debug("REST request to get all Superficies");
        Page<Superficie> page = superficieRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/superficies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

    }
    

    /**
     * GET  /superficies/:id : get the "id" superficie.
     *
     * @param id the id of the superficie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the superficie, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/superficies/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Superficie> getSuperficie(@PathVariable Long id) {
        log.debug("REST request to get Superficie : {}", id);
        Superficie superficie = superficieRepository.findOne(id);
        return Optional.ofNullable(superficie)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /superficies/:id : delete the "id" superficie.
     *
     * @param id the id of the superficie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/superficies/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSuperficie(@PathVariable Long id) {
        log.debug("REST request to delete Superficie : {}", id);
        superficieRepository.delete(id);
        superficieSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("superficie", id.toString())).build();
    }

    /**
     * SEARCH  /_search/superficies?query=:query : search for the superficie corresponding
     * to the query.
     *
     * @param query the query of the superficie search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/superficies",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Superficie> searchSuperficies(@RequestParam String query) {
        log.debug("REST request to search Superficies for query {}", query);
        return StreamSupport
            .stream(superficieSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

//    /**
//     * GET  /superficies/:id : get superfici by "id" domanda.
//     *
//     * @param id the id of the domanda including the superfici to retrieve
//     * @return the ResponseEntity with status 200 (OK) and with body the superfici, or with status 404 (Not Found)
//     */
//    @RequestMapping(value = "/superficies/domanda/{id}",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public List<Superficie> getSuperficiDomanda(@PathVariable Long id) {
//        log.debug("REST request to get Superfici : {}", id);
//        List<Superficie> superficis = superficieRepository.findByDomandaId(id);
//        return superficis;
//    }
    
    /**
     * GET  /superficies/:id : get superfici by "id" domanda.
     *
     * @param id the id of the domanda including the superfici to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the superfici, or with status 404 (Not Found)
     * @throws URISyntaxException 
     */
    @RequestMapping(value = "/superficies/domanda/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Superficie>> getSuperficiDomanda(@PathVariable Long id,Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get Superfici : {}", id);
        Page<Superficie> page = superficieRepository.findByDomandaId(id,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/superficies/domanda/{id}");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
