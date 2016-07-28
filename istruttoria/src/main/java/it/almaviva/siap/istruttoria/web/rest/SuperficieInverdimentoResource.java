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

import it.almaviva.siap.istruttoria.domain.SuperficieInverdimento;
import it.almaviva.siap.istruttoria.repository.SuperficieInverdimentoRepository;
import it.almaviva.siap.istruttoria.repository.search.SuperficieInverdimentoSearchRepository;
import it.almaviva.siap.istruttoria.web.rest.util.HeaderUtil;
import it.almaviva.siap.istruttoria.web.rest.util.PaginationUtil;

/**
 * REST controller for managing SuperficieInverdimento.
 */
@RestController
@RequestMapping("/api")
public class SuperficieInverdimentoResource {

    private final Logger log = LoggerFactory.getLogger(SuperficieInverdimentoResource.class);
        
    @Inject
    private SuperficieInverdimentoRepository superficieInverdimentoRepository;
    
    @Inject
    private SuperficieInverdimentoSearchRepository superficieInverdimentoSearchRepository;
    
    /**
     * POST  /superficie-inverdimentos : Create a new superficieInverdimento.
     *
     * @param superficieInverdimento the superficieInverdimento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new superficieInverdimento, or with status 400 (Bad Request) if the superficieInverdimento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/superficie-inverdimentos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SuperficieInverdimento> createSuperficieInverdimento(@RequestBody SuperficieInverdimento superficieInverdimento) throws URISyntaxException {
        log.debug("REST request to save SuperficieInverdimento : {}", superficieInverdimento);
        if (superficieInverdimento.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("superficieInverdimento", "idexists", "A new superficieInverdimento cannot already have an ID")).body(null);
        }
        SuperficieInverdimento result = superficieInverdimentoRepository.save(superficieInverdimento);
        superficieInverdimentoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/superficie-inverdimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("superficieInverdimento", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /superficie-inverdimentos : Updates an existing superficieInverdimento.
     *
     * @param superficieInverdimento the superficieInverdimento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated superficieInverdimento,
     * or with status 400 (Bad Request) if the superficieInverdimento is not valid,
     * or with status 500 (Internal Server Error) if the superficieInverdimento couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/superficie-inverdimentos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SuperficieInverdimento> updateSuperficieInverdimento(@RequestBody SuperficieInverdimento superficieInverdimento) throws URISyntaxException {
        log.debug("REST request to update SuperficieInverdimento : {}", superficieInverdimento);
        if (superficieInverdimento.getId() == null) {
            return createSuperficieInverdimento(superficieInverdimento);
        }
        SuperficieInverdimento result = superficieInverdimentoRepository.save(superficieInverdimento);
        superficieInverdimentoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("superficieInverdimento", superficieInverdimento.getId().toString()))
            .body(result);
    }

//    /**
//     * GET  /superficie-inverdimentos : get all the superficieInverdimentos.
//     *
//     * @return the ResponseEntity with status 200 (OK) and the list of superficieInverdimentos in body
//     */
//    @RequestMapping(value = "/superficie-inverdimentos",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public List<SuperficieInverdimento> getAllSuperficieInverdimentos() {
//        log.debug("REST request to get all SuperficieInverdimentos");
//        List<SuperficieInverdimento> superficieInverdimentos = superficieInverdimentoRepository.findAll();
//        return superficieInverdimentos;
//    }
    
    /**
     * GET  /superficie-inverdimentos : get all the superficieInverdimentos. Versione paginata
     *
     * @return the ResponseEntity with status 200 (OK) and the list of superficieInverdimentos in body
     */
    @RequestMapping(value = "/superficie-inverdimentos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public  ResponseEntity<List<SuperficieInverdimento>> getAllSuperficieInverdimentos(Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get all SuperficieInverdimentos");
    	Page<SuperficieInverdimento> page = superficieInverdimentoRepository.findAll(pageable);
    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/superficie-inverdimentos");
    	return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /superficie-inverdimentos/:id : get the "id" superficieInverdimento.
     *
     * @param id the id of the superficieInverdimento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the superficieInverdimento, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/superficie-inverdimentos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SuperficieInverdimento> getSuperficieInverdimento(@PathVariable Long id) {
        log.debug("REST request to get SuperficieInverdimento : {}", id);
        SuperficieInverdimento superficieInverdimento = superficieInverdimentoRepository.findOne(id);
        return Optional.ofNullable(superficieInverdimento)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /superficie-inverdimentos/:id : delete the "id" superficieInverdimento.
     *
     * @param id the id of the superficieInverdimento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/superficie-inverdimentos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSuperficieInverdimento(@PathVariable Long id) {
        log.debug("REST request to delete SuperficieInverdimento : {}", id);
        superficieInverdimentoRepository.delete(id);
        superficieInverdimentoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("superficieInverdimento", id.toString())).build();
    }

    /**
     * SEARCH  /_search/superficie-inverdimentos?query=:query : search for the superficieInverdimento corresponding
     * to the query.
     *
     * @param query the query of the superficieInverdimento search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/superficie-inverdimentos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SuperficieInverdimento> searchSuperficieInverdimentos(@RequestParam String query) {
        log.debug("REST request to search SuperficieInverdimentos for query {}", query);
        return StreamSupport
            .stream(superficieInverdimentoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
