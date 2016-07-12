package it.almaviva.siap.istruttoria.web.rest;

import it.almaviva.siap.istruttoria.IstruttoriaApp;
import it.almaviva.siap.istruttoria.domain.Penalita;
import it.almaviva.siap.istruttoria.repository.PenalitaRepository;
import it.almaviva.siap.istruttoria.repository.search.PenalitaSearchRepository;

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
 * Test class for the PenalitaResource REST controller.
 *
 * @see PenalitaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstruttoriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class PenalitaResourceIntTest {

    private static final String DEFAULT_DECO_TIPO_PENA = "AAAAA";
    private static final String UPDATED_DECO_TIPO_PENA = "BBBBB";

    private static final Float DEFAULT_QNTA_PENA = 1F;
    private static final Float UPDATED_QNTA_PENA = 2F;

    private static final Float DEFAULT_IMPO_PENA = 1F;
    private static final Float UPDATED_IMPO_PENA = 2F;
    private static final String DEFAULT_UNIT_MISU = "AAAAA";
    private static final String UPDATED_UNIT_MISU = "BBBBB";

    @Inject
    private PenalitaRepository penalitaRepository;

    @Inject
    private PenalitaSearchRepository penalitaSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPenalitaMockMvc;

    private Penalita penalita;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PenalitaResource penalitaResource = new PenalitaResource();
        ReflectionTestUtils.setField(penalitaResource, "penalitaSearchRepository", penalitaSearchRepository);
        ReflectionTestUtils.setField(penalitaResource, "penalitaRepository", penalitaRepository);
        this.restPenalitaMockMvc = MockMvcBuilders.standaloneSetup(penalitaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        penalitaSearchRepository.deleteAll();
        penalita = new Penalita();
        penalita.setDecoTipoPena(DEFAULT_DECO_TIPO_PENA);
        penalita.setQntaPena(DEFAULT_QNTA_PENA);
        penalita.setImpoPena(DEFAULT_IMPO_PENA);
        penalita.setUnitMisu(DEFAULT_UNIT_MISU);
    }

    @Test
    @Transactional
    public void createPenalita() throws Exception {
        int databaseSizeBeforeCreate = penalitaRepository.findAll().size();

        // Create the Penalita

        restPenalitaMockMvc.perform(post("/api/penalitas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(penalita)))
                .andExpect(status().isCreated());

        // Validate the Penalita in the database
        List<Penalita> penalitas = penalitaRepository.findAll();
        assertThat(penalitas).hasSize(databaseSizeBeforeCreate + 1);
        Penalita testPenalita = penalitas.get(penalitas.size() - 1);
        assertThat(testPenalita.getDecoTipoPena()).isEqualTo(DEFAULT_DECO_TIPO_PENA);
        assertThat(testPenalita.getQntaPena()).isEqualTo(DEFAULT_QNTA_PENA);
        assertThat(testPenalita.getImpoPena()).isEqualTo(DEFAULT_IMPO_PENA);
        assertThat(testPenalita.getUnitMisu()).isEqualTo(DEFAULT_UNIT_MISU);

        // Validate the Penalita in ElasticSearch
        Penalita penalitaEs = penalitaSearchRepository.findOne(testPenalita.getId());
        assertThat(penalitaEs).isEqualToComparingFieldByField(testPenalita);
    }

    @Test
    @Transactional
    public void getAllPenalitas() throws Exception {
        // Initialize the database
        penalitaRepository.saveAndFlush(penalita);

        // Get all the penalitas
        restPenalitaMockMvc.perform(get("/api/penalitas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(penalita.getId().intValue())))
                .andExpect(jsonPath("$.[*].decoTipoPena").value(hasItem(DEFAULT_DECO_TIPO_PENA.toString())))
                .andExpect(jsonPath("$.[*].qntaPena").value(hasItem(DEFAULT_QNTA_PENA.doubleValue())))
                .andExpect(jsonPath("$.[*].impoPena").value(hasItem(DEFAULT_IMPO_PENA.doubleValue())))
                .andExpect(jsonPath("$.[*].unitMisu").value(hasItem(DEFAULT_UNIT_MISU.toString())));
    }

    @Test
    @Transactional
    public void getPenalita() throws Exception {
        // Initialize the database
        penalitaRepository.saveAndFlush(penalita);

        // Get the penalita
        restPenalitaMockMvc.perform(get("/api/penalitas/{id}", penalita.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(penalita.getId().intValue()))
            .andExpect(jsonPath("$.decoTipoPena").value(DEFAULT_DECO_TIPO_PENA.toString()))
            .andExpect(jsonPath("$.qntaPena").value(DEFAULT_QNTA_PENA.doubleValue()))
            .andExpect(jsonPath("$.impoPena").value(DEFAULT_IMPO_PENA.doubleValue()))
            .andExpect(jsonPath("$.unitMisu").value(DEFAULT_UNIT_MISU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPenalita() throws Exception {
        // Get the penalita
        restPenalitaMockMvc.perform(get("/api/penalitas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePenalita() throws Exception {
        // Initialize the database
        penalitaRepository.saveAndFlush(penalita);
        penalitaSearchRepository.save(penalita);
        int databaseSizeBeforeUpdate = penalitaRepository.findAll().size();

        // Update the penalita
        Penalita updatedPenalita = new Penalita();
        updatedPenalita.setId(penalita.getId());
        updatedPenalita.setDecoTipoPena(UPDATED_DECO_TIPO_PENA);
        updatedPenalita.setQntaPena(UPDATED_QNTA_PENA);
        updatedPenalita.setImpoPena(UPDATED_IMPO_PENA);
        updatedPenalita.setUnitMisu(UPDATED_UNIT_MISU);

        restPenalitaMockMvc.perform(put("/api/penalitas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPenalita)))
                .andExpect(status().isOk());

        // Validate the Penalita in the database
        List<Penalita> penalitas = penalitaRepository.findAll();
        assertThat(penalitas).hasSize(databaseSizeBeforeUpdate);
        Penalita testPenalita = penalitas.get(penalitas.size() - 1);
        assertThat(testPenalita.getDecoTipoPena()).isEqualTo(UPDATED_DECO_TIPO_PENA);
        assertThat(testPenalita.getQntaPena()).isEqualTo(UPDATED_QNTA_PENA);
        assertThat(testPenalita.getImpoPena()).isEqualTo(UPDATED_IMPO_PENA);
        assertThat(testPenalita.getUnitMisu()).isEqualTo(UPDATED_UNIT_MISU);

        // Validate the Penalita in ElasticSearch
        Penalita penalitaEs = penalitaSearchRepository.findOne(testPenalita.getId());
        assertThat(penalitaEs).isEqualToComparingFieldByField(testPenalita);
    }

    @Test
    @Transactional
    public void deletePenalita() throws Exception {
        // Initialize the database
        penalitaRepository.saveAndFlush(penalita);
        penalitaSearchRepository.save(penalita);
        int databaseSizeBeforeDelete = penalitaRepository.findAll().size();

        // Get the penalita
        restPenalitaMockMvc.perform(delete("/api/penalitas/{id}", penalita.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean penalitaExistsInEs = penalitaSearchRepository.exists(penalita.getId());
        assertThat(penalitaExistsInEs).isFalse();

        // Validate the database is empty
        List<Penalita> penalitas = penalitaRepository.findAll();
        assertThat(penalitas).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPenalita() throws Exception {
        // Initialize the database
        penalitaRepository.saveAndFlush(penalita);
        penalitaSearchRepository.save(penalita);

        // Search the penalita
        restPenalitaMockMvc.perform(get("/api/_search/penalitas?query=id:" + penalita.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(penalita.getId().intValue())))
            .andExpect(jsonPath("$.[*].decoTipoPena").value(hasItem(DEFAULT_DECO_TIPO_PENA.toString())))
            .andExpect(jsonPath("$.[*].qntaPena").value(hasItem(DEFAULT_QNTA_PENA.doubleValue())))
            .andExpect(jsonPath("$.[*].impoPena").value(hasItem(DEFAULT_IMPO_PENA.doubleValue())))
            .andExpect(jsonPath("$.[*].unitMisu").value(hasItem(DEFAULT_UNIT_MISU.toString())));
    }
}
