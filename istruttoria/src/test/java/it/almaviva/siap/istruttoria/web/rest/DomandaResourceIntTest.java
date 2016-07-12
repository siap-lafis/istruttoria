package it.almaviva.siap.istruttoria.web.rest;

import it.almaviva.siap.istruttoria.IstruttoriaApp;
import it.almaviva.siap.istruttoria.domain.Domanda;
import it.almaviva.siap.istruttoria.repository.DomandaRepository;
import it.almaviva.siap.istruttoria.repository.search.DomandaSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the DomandaResource REST controller.
 *
 * @see DomandaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstruttoriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class DomandaResourceIntTest {


    private static final Integer DEFAULT_ID_DOMANDA = 1;
    private static final Integer UPDATED_ID_DOMANDA = 2;
    private static final String DEFAULT_DATA_PRES = "AAAAA";
    private static final String UPDATED_DATA_PRES = "BBBBB";
    private static final String DEFAULT_DATA_INSE = "AAAAA";
    private static final String UPDATED_DATA_INSE = "BBBBB";
    private static final String DEFAULT_COD_SETTORE = "AAAAA";
    private static final String UPDATED_COD_SETTORE = "BBBBB";

    private static final Integer DEFAULT_ANNO = 1;
    private static final Integer UPDATED_ANNO = 2;

    @Inject
    private DomandaRepository domandaRepository;

    @Inject
    private DomandaSearchRepository domandaSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDomandaMockMvc;

    private Domanda domanda;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DomandaResource domandaResource = new DomandaResource();
        ReflectionTestUtils.setField(domandaResource, "domandaSearchRepository", domandaSearchRepository);
        ReflectionTestUtils.setField(domandaResource, "domandaRepository", domandaRepository);
        this.restDomandaMockMvc = MockMvcBuilders.standaloneSetup(domandaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        domandaSearchRepository.deleteAll();
        domanda = new Domanda();
        domanda.setIdDomanda(DEFAULT_ID_DOMANDA);
        domanda.setDataPres(DEFAULT_DATA_PRES);
        domanda.setDataInse(DEFAULT_DATA_INSE);
        domanda.setCodSettore(DEFAULT_COD_SETTORE);
        domanda.setAnno(DEFAULT_ANNO);
    }

    @Test
    @Transactional
    public void createDomanda() throws Exception {
        int databaseSizeBeforeCreate = domandaRepository.findAll().size();

        // Create the Domanda

        restDomandaMockMvc.perform(post("/api/domandas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(domanda)))
                .andExpect(status().isCreated());

        // Validate the Domanda in the database
        List<Domanda> domandas = domandaRepository.findAll();
        assertThat(domandas).hasSize(databaseSizeBeforeCreate + 1);
        Domanda testDomanda = domandas.get(domandas.size() - 1);
        assertThat(testDomanda.getIdDomanda()).isEqualTo(DEFAULT_ID_DOMANDA);
        assertThat(testDomanda.getDataPres()).isEqualTo(DEFAULT_DATA_PRES);
        assertThat(testDomanda.getDataInse()).isEqualTo(DEFAULT_DATA_INSE);
        assertThat(testDomanda.getCodSettore()).isEqualTo(DEFAULT_COD_SETTORE);
        assertThat(testDomanda.getAnno()).isEqualTo(DEFAULT_ANNO);

        // Validate the Domanda in ElasticSearch
        Domanda domandaEs = domandaSearchRepository.findOne(testDomanda.getId());
        assertThat(domandaEs).isEqualToComparingFieldByField(testDomanda);
    }

    @Test
    @Transactional
    public void getAllDomandas() throws Exception {
        // Initialize the database
        domandaRepository.saveAndFlush(domanda);

        // Get all the domandas
        restDomandaMockMvc.perform(get("/api/domandas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(domanda.getId().intValue())))
                .andExpect(jsonPath("$.[*].idDomanda").value(hasItem(DEFAULT_ID_DOMANDA)))
                .andExpect(jsonPath("$.[*].dataPres").value(hasItem(DEFAULT_DATA_PRES.toString())))
                .andExpect(jsonPath("$.[*].dataInse").value(hasItem(DEFAULT_DATA_INSE.toString())))
                .andExpect(jsonPath("$.[*].codSettore").value(hasItem(DEFAULT_COD_SETTORE.toString())))
                .andExpect(jsonPath("$.[*].anno").value(hasItem(DEFAULT_ANNO)));
    }

    @Test
    @Transactional
    public void getDomanda() throws Exception {
        // Initialize the database
        domandaRepository.saveAndFlush(domanda);

        // Get the domanda
        restDomandaMockMvc.perform(get("/api/domandas/{id}", domanda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(domanda.getId().intValue()))
            .andExpect(jsonPath("$.idDomanda").value(DEFAULT_ID_DOMANDA))
            .andExpect(jsonPath("$.dataPres").value(DEFAULT_DATA_PRES.toString()))
            .andExpect(jsonPath("$.dataInse").value(DEFAULT_DATA_INSE.toString()))
            .andExpect(jsonPath("$.codSettore").value(DEFAULT_COD_SETTORE.toString()))
            .andExpect(jsonPath("$.anno").value(DEFAULT_ANNO));
    }

    @Test
    @Transactional
    public void getNonExistingDomanda() throws Exception {
        // Get the domanda
        restDomandaMockMvc.perform(get("/api/domandas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDomanda() throws Exception {
        // Initialize the database
        domandaRepository.saveAndFlush(domanda);
        domandaSearchRepository.save(domanda);
        int databaseSizeBeforeUpdate = domandaRepository.findAll().size();

        // Update the domanda
        Domanda updatedDomanda = new Domanda();
        updatedDomanda.setId(domanda.getId());
        updatedDomanda.setIdDomanda(UPDATED_ID_DOMANDA);
        updatedDomanda.setDataPres(UPDATED_DATA_PRES);
        updatedDomanda.setDataInse(UPDATED_DATA_INSE);
        updatedDomanda.setCodSettore(UPDATED_COD_SETTORE);
        updatedDomanda.setAnno(UPDATED_ANNO);

        restDomandaMockMvc.perform(put("/api/domandas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDomanda)))
                .andExpect(status().isOk());

        // Validate the Domanda in the database
        List<Domanda> domandas = domandaRepository.findAll();
        assertThat(domandas).hasSize(databaseSizeBeforeUpdate);
        Domanda testDomanda = domandas.get(domandas.size() - 1);
        assertThat(testDomanda.getIdDomanda()).isEqualTo(UPDATED_ID_DOMANDA);
        assertThat(testDomanda.getDataPres()).isEqualTo(UPDATED_DATA_PRES);
        assertThat(testDomanda.getDataInse()).isEqualTo(UPDATED_DATA_INSE);
        assertThat(testDomanda.getCodSettore()).isEqualTo(UPDATED_COD_SETTORE);
        assertThat(testDomanda.getAnno()).isEqualTo(UPDATED_ANNO);

        // Validate the Domanda in ElasticSearch
        Domanda domandaEs = domandaSearchRepository.findOne(testDomanda.getId());
        assertThat(domandaEs).isEqualToComparingFieldByField(testDomanda);
    }

    @Test
    @Transactional
    public void deleteDomanda() throws Exception {
        // Initialize the database
        domandaRepository.saveAndFlush(domanda);
        domandaSearchRepository.save(domanda);
        int databaseSizeBeforeDelete = domandaRepository.findAll().size();

        // Get the domanda
        restDomandaMockMvc.perform(delete("/api/domandas/{id}", domanda.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean domandaExistsInEs = domandaSearchRepository.exists(domanda.getId());
        assertThat(domandaExistsInEs).isFalse();

        // Validate the database is empty
        List<Domanda> domandas = domandaRepository.findAll();
        assertThat(domandas).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDomanda() throws Exception {
        // Initialize the database
        domandaRepository.saveAndFlush(domanda);
        domandaSearchRepository.save(domanda);

        // Search the domanda
        restDomandaMockMvc.perform(get("/api/_search/domandas?query=id:" + domanda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domanda.getId().intValue())))
            .andExpect(jsonPath("$.[*].idDomanda").value(hasItem(DEFAULT_ID_DOMANDA)))
            .andExpect(jsonPath("$.[*].dataPres").value(hasItem(DEFAULT_DATA_PRES.toString())))
            .andExpect(jsonPath("$.[*].dataInse").value(hasItem(DEFAULT_DATA_INSE.toString())))
            .andExpect(jsonPath("$.[*].codSettore").value(hasItem(DEFAULT_COD_SETTORE.toString())))
            .andExpect(jsonPath("$.[*].anno").value(hasItem(DEFAULT_ANNO)));
    }
}
