package it.almaviva.siap.istruttoria.web.rest;

import it.almaviva.siap.istruttoria.IstruttoriaApp;
import it.almaviva.siap.istruttoria.domain.Soggetto;
import it.almaviva.siap.istruttoria.repository.SoggettoRepository;
import it.almaviva.siap.istruttoria.repository.search.SoggettoSearchRepository;

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
 * Test class for the SoggettoResource REST controller.
 *
 * @see SoggettoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstruttoriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class SoggettoResourceIntTest {

    private static final String DEFAULT_CUAA = "AAAAA";
    private static final String UPDATED_CUAA = "BBBBB";
    private static final String DEFAULT_DENOMINAZIONE = "AAAAA";
    private static final String UPDATED_DENOMINAZIONE = "BBBBB";

    @Inject
    private SoggettoRepository soggettoRepository;

    @Inject
    private SoggettoSearchRepository soggettoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSoggettoMockMvc;

    private Soggetto soggetto;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SoggettoResource soggettoResource = new SoggettoResource();
        ReflectionTestUtils.setField(soggettoResource, "soggettoSearchRepository", soggettoSearchRepository);
        ReflectionTestUtils.setField(soggettoResource, "soggettoRepository", soggettoRepository);
        this.restSoggettoMockMvc = MockMvcBuilders.standaloneSetup(soggettoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        soggettoSearchRepository.deleteAll();
        soggetto = new Soggetto();
        soggetto.setCuaa(DEFAULT_CUAA);
        soggetto.setDenominazione(DEFAULT_DENOMINAZIONE);
    }

    @Test
    @Transactional
    public void createSoggetto() throws Exception {
        int databaseSizeBeforeCreate = soggettoRepository.findAll().size();

        // Create the Soggetto

        restSoggettoMockMvc.perform(post("/api/soggettos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(soggetto)))
                .andExpect(status().isCreated());

        // Validate the Soggetto in the database
        List<Soggetto> soggettos = soggettoRepository.findAll();
        assertThat(soggettos).hasSize(databaseSizeBeforeCreate + 1);
        Soggetto testSoggetto = soggettos.get(soggettos.size() - 1);
        assertThat(testSoggetto.getCuaa()).isEqualTo(DEFAULT_CUAA);
        assertThat(testSoggetto.getDenominazione()).isEqualTo(DEFAULT_DENOMINAZIONE);

        // Validate the Soggetto in ElasticSearch
        Soggetto soggettoEs = soggettoSearchRepository.findOne(testSoggetto.getId());
        assertThat(soggettoEs).isEqualToComparingFieldByField(testSoggetto);
    }

    @Test
    @Transactional
    public void getAllSoggettos() throws Exception {
        // Initialize the database
        soggettoRepository.saveAndFlush(soggetto);

        // Get all the soggettos
        restSoggettoMockMvc.perform(get("/api/soggettos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(soggetto.getId().intValue())))
                .andExpect(jsonPath("$.[*].cuaa").value(hasItem(DEFAULT_CUAA.toString())))
                .andExpect(jsonPath("$.[*].denominazione").value(hasItem(DEFAULT_DENOMINAZIONE.toString())));
    }

    @Test
    @Transactional
    public void getSoggetto() throws Exception {
        // Initialize the database
        soggettoRepository.saveAndFlush(soggetto);

        // Get the soggetto
        restSoggettoMockMvc.perform(get("/api/soggettos/{id}", soggetto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(soggetto.getId().intValue()))
            .andExpect(jsonPath("$.cuaa").value(DEFAULT_CUAA.toString()))
            .andExpect(jsonPath("$.denominazione").value(DEFAULT_DENOMINAZIONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSoggetto() throws Exception {
        // Get the soggetto
        restSoggettoMockMvc.perform(get("/api/soggettos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSoggetto() throws Exception {
        // Initialize the database
        soggettoRepository.saveAndFlush(soggetto);
        soggettoSearchRepository.save(soggetto);
        int databaseSizeBeforeUpdate = soggettoRepository.findAll().size();

        // Update the soggetto
        Soggetto updatedSoggetto = new Soggetto();
        updatedSoggetto.setId(soggetto.getId());
        updatedSoggetto.setCuaa(UPDATED_CUAA);
        updatedSoggetto.setDenominazione(UPDATED_DENOMINAZIONE);

        restSoggettoMockMvc.perform(put("/api/soggettos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSoggetto)))
                .andExpect(status().isOk());

        // Validate the Soggetto in the database
        List<Soggetto> soggettos = soggettoRepository.findAll();
        assertThat(soggettos).hasSize(databaseSizeBeforeUpdate);
        Soggetto testSoggetto = soggettos.get(soggettos.size() - 1);
        assertThat(testSoggetto.getCuaa()).isEqualTo(UPDATED_CUAA);
        assertThat(testSoggetto.getDenominazione()).isEqualTo(UPDATED_DENOMINAZIONE);

        // Validate the Soggetto in ElasticSearch
        Soggetto soggettoEs = soggettoSearchRepository.findOne(testSoggetto.getId());
        assertThat(soggettoEs).isEqualToComparingFieldByField(testSoggetto);
    }

    @Test
    @Transactional
    public void deleteSoggetto() throws Exception {
        // Initialize the database
        soggettoRepository.saveAndFlush(soggetto);
        soggettoSearchRepository.save(soggetto);
        int databaseSizeBeforeDelete = soggettoRepository.findAll().size();

        // Get the soggetto
        restSoggettoMockMvc.perform(delete("/api/soggettos/{id}", soggetto.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean soggettoExistsInEs = soggettoSearchRepository.exists(soggetto.getId());
        assertThat(soggettoExistsInEs).isFalse();

        // Validate the database is empty
        List<Soggetto> soggettos = soggettoRepository.findAll();
        assertThat(soggettos).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSoggetto() throws Exception {
        // Initialize the database
        soggettoRepository.saveAndFlush(soggetto);
        soggettoSearchRepository.save(soggetto);

        // Search the soggetto
        restSoggettoMockMvc.perform(get("/api/_search/soggettos?query=id:" + soggetto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soggetto.getId().intValue())))
            .andExpect(jsonPath("$.[*].cuaa").value(hasItem(DEFAULT_CUAA.toString())))
            .andExpect(jsonPath("$.[*].denominazione").value(hasItem(DEFAULT_DENOMINAZIONE.toString())));
    }
}
