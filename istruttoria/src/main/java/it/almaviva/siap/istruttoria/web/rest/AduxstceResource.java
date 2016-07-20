package it.almaviva.siap.istruttoria.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.almaviva.siap.istruttoria.domain.Aduxstce;
import it.almaviva.siap.istruttoria.repository.AduxstceRepository;
import it.almaviva.siap.istruttoria.repository.search.AduxstceSearchRepository;
import it.almaviva.siap.istruttoria.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Aduxstce.
 */
@RestController
@RequestMapping("/api")
public class AduxstceResource {

    private final Logger log = LoggerFactory.getLogger(AduxstceResource.class);
        
    @Inject
    private AduxstceRepository aduxstceRepository;
    
    @Inject
    private AduxstceSearchRepository aduxstceSearchRepository;
    
    /**
     * POST  /aduxstces : Create a new aduxstce.
     *
     * @param aduxstce the aduxstce to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aduxstce, or with status 400 (Bad Request) if the aduxstce has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/aduxstces",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Aduxstce> createAduxstce(@RequestBody Aduxstce aduxstce) throws URISyntaxException {
        log.debug("REST request to save Aduxstce : {}", aduxstce);
        if (aduxstce.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("aduxstce", "idexists", "A new aduxstce cannot already have an ID")).body(null);
        }
        Aduxstce result = aduxstceRepository.save(aduxstce);
        aduxstceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/aduxstces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("aduxstce", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aduxstces : Updates an existing aduxstce.
     *
     * @param aduxstce the aduxstce to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aduxstce,
     * or with status 400 (Bad Request) if the aduxstce is not valid,
     * or with status 500 (Internal Server Error) if the aduxstce couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/aduxstces",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Aduxstce> updateAduxstce(@RequestBody Aduxstce aduxstce) throws URISyntaxException {
        log.debug("REST request to update Aduxstce : {}", aduxstce);
        if (aduxstce.getId() == null) {
            return createAduxstce(aduxstce);
        }
        Aduxstce result = aduxstceRepository.save(aduxstce);
        aduxstceSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("aduxstce", aduxstce.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aduxstces : get all the aduxstces.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of aduxstces in body
     */
    @RequestMapping(value = "/aduxstces",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Aduxstce> getAllAduxstces() {
        log.debug("REST request to get all Aduxstces");
        List<Aduxstce> aduxstces = aduxstceRepository.findAll();
        return aduxstces;
    }

    /**
     * GET  /aduxstces/:id : get the "id" aduxstce.
     *
     * @param id the id of the aduxstce to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aduxstce, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/aduxstces/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Aduxstce> getAduxstce(@PathVariable Long id) {
        log.debug("REST request to get Aduxstce : {}", id);
        Aduxstce aduxstce = aduxstceRepository.findOne(id);
        return Optional.ofNullable(aduxstce)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /aduxstces/:id : delete the "id" aduxstce.
     *
     * @param id the id of the aduxstce to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/aduxstces/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAduxstce(@PathVariable Long id) {
        log.debug("REST request to delete Aduxstce : {}", id);
        aduxstceRepository.delete(id);
        aduxstceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("aduxstce", id.toString())).build();
    }

    /**
     * SEARCH  /_search/aduxstces?query=:query : search for the aduxstce corresponding
     * to the query.
     *
     * @param query the query of the aduxstce search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/aduxstces",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Aduxstce> searchAduxstces(@RequestParam String query) {
        log.debug("REST request to search Aduxstces for query {}", query);
        return StreamSupport
            .stream(aduxstceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
