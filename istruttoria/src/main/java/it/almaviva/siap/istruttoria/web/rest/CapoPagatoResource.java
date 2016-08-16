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

import it.almaviva.siap.istruttoria.domain.CapoPagato;
import it.almaviva.siap.istruttoria.domain.Superficie;
import it.almaviva.siap.istruttoria.repository.CapoPagatoRepository;
import it.almaviva.siap.istruttoria.repository.search.CapoPagatoSearchRepository;
import it.almaviva.siap.istruttoria.web.rest.util.HeaderUtil;
import it.almaviva.siap.istruttoria.web.rest.util.PaginationUtil;

import com.mysema.query.types.Predicate;
import it.almaviva.siap.istruttoria.domain.QCapoPagato;

/**
 * REST controller for managing CapoPagato.
 */
@RestController
@RequestMapping("/api")
public class CapoPagatoResource {

    private final Logger log = LoggerFactory.getLogger(CapoPagatoResource.class);
        
    @Inject
    private CapoPagatoRepository capoPagatoRepository;
    
    @Inject
    private CapoPagatoSearchRepository capoPagatoSearchRepository;
    
    /**
     * POST  /capo-pagatoes : Create a new capoPagato.
     *
     * @param capoPagato the capoPagato to create
     * @return the ResponseEntity with status 201 (Created) and with body the new capoPagato, or with status 400 (Bad Request) if the capoPagato has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/capo-pagatoes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CapoPagato> createCapoPagato(@RequestBody CapoPagato capoPagato) throws URISyntaxException {
        log.debug("REST request to save CapoPagato : {}", capoPagato);
        if (capoPagato.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("capoPagato", "idexists", "A new capoPagato cannot already have an ID")).body(null);
        }
        CapoPagato result = capoPagatoRepository.save(capoPagato);
        capoPagatoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/capo-pagatoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("capoPagato", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /capo-pagatoes : Updates an existing capoPagato.
     *
     * @param capoPagato the capoPagato to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated capoPagato,
     * or with status 400 (Bad Request) if the capoPagato is not valid,
     * or with status 500 (Internal Server Error) if the capoPagato couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/capo-pagatoes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CapoPagato> updateCapoPagato(@RequestBody CapoPagato capoPagato) throws URISyntaxException {
        log.debug("REST request to update CapoPagato : {}", capoPagato);
        if (capoPagato.getId() == null) {
            return createCapoPagato(capoPagato);
        }
        CapoPagato result = capoPagatoRepository.save(capoPagato);
        capoPagatoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("capoPagato", capoPagato.getId().toString()))
            .body(result);
    }

//    /**
//     * GET  /capo-pagatoes : get all the capoPagatoes.
//     *
//     * @return the ResponseEntity with status 200 (OK) and the list of capoPagatoes in body
//     */
//    @RequestMapping(value = "/capo-pagatoes",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public List<CapoPagato> getAllCapoPagatoes() {
//        log.debug("REST request to get all CapoPagatoes");
//        List<CapoPagato> capoPagatoes = capoPagatoRepository.findAll();
//        return capoPagatoes;
//    }
    
    /**
     * GET  /capo-pagatoes : get all the capoPagatoes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of capoPagatoes in body
     */
    @RequestMapping(value = "/capo-pagatoes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CapoPagato>>  getAllCapoPagatoes(Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get all CapoPagatoes");      
        Page<CapoPagato> page = capoPagatoRepository.findAll(pageable);
	    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/capo-pagatoes");
	    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /capo-pagatoes/:id : get the "id" capoPagato.
     *
     * @param id the id of the capoPagato to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the capoPagato, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/capo-pagatoes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CapoPagato> getCapoPagato(@PathVariable Long id) {
        log.debug("REST request to get CapoPagato : {}", id);
        CapoPagato capoPagato = capoPagatoRepository.findOne(id);
        return Optional.ofNullable(capoPagato)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /capo-pagatoes/:id : delete the "id" capoPagato.
     *
     * @param id the id of the capoPagato to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/capo-pagatoes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCapoPagato(@PathVariable Long id) {
        log.debug("REST request to delete CapoPagato : {}", id);
        capoPagatoRepository.delete(id);
        capoPagatoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("capoPagato", id.toString())).build();
    }

    /**
     * SEARCH  /_search/capo-pagatoes?query=:query : search for the capoPagato corresponding
     * to the query.
     *
     * @param query the query of the capoPagato search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/capo-pagatoes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CapoPagato>> searchCapoPagatoes(@RequestParam String query, Pageable pageable) throws URISyntaxException {
        log.debug("REST request to search CapoPagatoes for query {}", query);
        QCapoPagato capoPagato = new QCapoPagato("capoPagato");
        Predicate predicate;
        try {
        	predicate = capoPagato.pagamento.idAttoAmmi.eq(Integer.parseInt(query.trim()))
        			.or(capoPagato.marcaCapo.eq(query.trim()))
        				.or(capoPagato.codAsl.eq(query.trim()));
        }
        catch (NumberFormatException  nfe) {
        	predicate = capoPagato.marcaCapo.eq(query.trim())
        					.or(capoPagato.codAsl.eq(query.trim()));
        }
    	
		Page<CapoPagato> page = capoPagatoRepository.findAll(predicate, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/_search/soggettos?query=" + query);
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        /*
        return StreamSupport
            .stream(capoPagatoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
         */
    }
    
    /**
     * GET  /capo-pagatoes/pagamento/:id : get capo-pagato by "id" pagamento.
     *
     * @param id the id of the domanda including the superfici to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the superfici, or with status 404 (Not Found)
     * @throws URISyntaxException 
     */
    @RequestMapping(value = "/capo-pagatoes/pagamento/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CapoPagato>> getCapiPagatiPagamento(@PathVariable Long id,Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get getCapiPagatiPagamento : {}", id);
        Page<CapoPagato> page = capoPagatoRepository.findByPagamentoId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/capo-pagatoes/pagamento/{id}");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
