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

import it.almaviva.siap.istruttoria.domain.ObbligoInverdimento;
import it.almaviva.siap.istruttoria.domain.SuperficieInverdimento;
import it.almaviva.siap.istruttoria.repository.ObbligoInverdimentoRepository;
import it.almaviva.siap.istruttoria.repository.search.ObbligoInverdimentoSearchRepository;
import it.almaviva.siap.istruttoria.web.rest.util.HeaderUtil;
import it.almaviva.siap.istruttoria.web.rest.util.PaginationUtil;

/**
 * REST controller for managing ObbligoInverdimento.
 */
@RestController
@RequestMapping("/api")
public class ObbligoInverdimentoResource {

    private final Logger log = LoggerFactory.getLogger(ObbligoInverdimentoResource.class);
        
    @Inject
    private ObbligoInverdimentoRepository obbligoInverdimentoRepository;
    
    @Inject
    private ObbligoInverdimentoSearchRepository obbligoInverdimentoSearchRepository;
    
    /**
     * POST  /obbligo-inverdimentos : Create a new obbligoInverdimento.
     *
     * @param obbligoInverdimento the obbligoInverdimento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new obbligoInverdimento, or with status 400 (Bad Request) if the obbligoInverdimento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/obbligo-inverdimentos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ObbligoInverdimento> createObbligoInverdimento(@RequestBody ObbligoInverdimento obbligoInverdimento) throws URISyntaxException {
        log.debug("REST request to save ObbligoInverdimento : {}", obbligoInverdimento);
        if (obbligoInverdimento.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("obbligoInverdimento", "idexists", "A new obbligoInverdimento cannot already have an ID")).body(null);
        }
        ObbligoInverdimento result = obbligoInverdimentoRepository.save(obbligoInverdimento);
        obbligoInverdimentoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/obbligo-inverdimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("obbligoInverdimento", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /obbligo-inverdimentos : Updates an existing obbligoInverdimento.
     *
     * @param obbligoInverdimento the obbligoInverdimento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated obbligoInverdimento,
     * or with status 400 (Bad Request) if the obbligoInverdimento is not valid,
     * or with status 500 (Internal Server Error) if the obbligoInverdimento couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/obbligo-inverdimentos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ObbligoInverdimento> updateObbligoInverdimento(@RequestBody ObbligoInverdimento obbligoInverdimento) throws URISyntaxException {
        log.debug("REST request to update ObbligoInverdimento : {}", obbligoInverdimento);
        if (obbligoInverdimento.getId() == null) {
            return createObbligoInverdimento(obbligoInverdimento);
        }
        ObbligoInverdimento result = obbligoInverdimentoRepository.save(obbligoInverdimento);
        obbligoInverdimentoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("obbligoInverdimento", obbligoInverdimento.getId().toString()))
            .body(result);
    }

//    /**
//     * GET  /obbligo-inverdimentos : get all the obbligoInverdimentos.
//     *
//     * @return the ResponseEntity with status 200 (OK) and the list of obbligoInverdimentos in body
//     */
//    @RequestMapping(value = "/obbligo-inverdimentos",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public List<ObbligoInverdimento> getAllObbligoInverdimentos() {
//        log.debug("REST request to get all ObbligoInverdimentos");
//        List<ObbligoInverdimento> obbligoInverdimentos = obbligoInverdimentoRepository.findAll();
//        return obbligoInverdimentos;
//    }
    
    /**
     * GET  /obbligo-inverdimentos : get all the obbligoInverdimentos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of obbligoInverdimentos in body
     */
    @RequestMapping(value = "/obbligo-inverdimentos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ObbligoInverdimento>> getAllObbligoInverdimentos(Pageable pageable)  throws URISyntaxException {
        log.debug("REST request to get all ObbligoInverdimentos");
        Page<ObbligoInverdimento> page = obbligoInverdimentoRepository.findAll(pageable);
	    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/obbligo-inverdimentos");
	    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /obbligo-inverdimentos/:id : get the "id" obbligoInverdimento.
     *
     * @param id the id of the obbligoInverdimento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the obbligoInverdimento, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/obbligo-inverdimentos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ObbligoInverdimento> getObbligoInverdimento(@PathVariable Long id) {
        log.debug("REST request to get ObbligoInverdimento : {}", id);
        ObbligoInverdimento obbligoInverdimento = obbligoInverdimentoRepository.findOne(id);
        return Optional.ofNullable(obbligoInverdimento)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /obbligo-inverdimentos/:id : delete the "id" obbligoInverdimento.
     *
     * @param id the id of the obbligoInverdimento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/obbligo-inverdimentos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteObbligoInverdimento(@PathVariable Long id) {
        log.debug("REST request to delete ObbligoInverdimento : {}", id);
        obbligoInverdimentoRepository.delete(id);
        obbligoInverdimentoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("obbligoInverdimento", id.toString())).build();
    }

    /**
     * SEARCH  /_search/obbligo-inverdimentos?query=:query : search for the obbligoInverdimento corresponding
     * to the query.
     *
     * @param query the query of the obbligoInverdimento search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/obbligo-inverdimentos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ObbligoInverdimento> searchObbligoInverdimentos(@RequestParam String query) {
        log.debug("REST request to search ObbligoInverdimentos for query {}", query);
        return StreamSupport
            .stream(obbligoInverdimentoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
    
    /**
     * GET  /obbligo-inverdimentos/superficie-inverdimento/:id : get superfici by "id" domanda.
     *
     * @param id the id of the SuperficiInverdimento including the superfici to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the superfici, or with status 404 (Not Found)
     * @throws URISyntaxException 
     */
    @RequestMapping(value = "/obbligo-inverdimentos/superficie-inverdimento/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ObbligoInverdimento>> getObblighiInverdimentoSuperficiInverdimento(@PathVariable Long id,Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get Superfici : {}", id);
        Page<ObbligoInverdimento> page = obbligoInverdimentoRepository.findBySuperficiInverdimentoId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/obbligo-inverdimentos/superficie-inverdimento/{id}");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
