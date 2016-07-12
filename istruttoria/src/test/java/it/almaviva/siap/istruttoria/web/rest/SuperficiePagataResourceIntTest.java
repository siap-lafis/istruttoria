package it.almaviva.siap.istruttoria.web.rest;

import it.almaviva.siap.istruttoria.IstruttoriaApp;
import it.almaviva.siap.istruttoria.domain.SuperficiePagata;
import it.almaviva.siap.istruttoria.repository.SuperficiePagataRepository;
import it.almaviva.siap.istruttoria.repository.search.SuperficiePagataSearchRepository;

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
 * Test class for the SuperficiePagataResource REST controller.
 *
 * @see SuperficiePagataResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstruttoriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class SuperficiePagataResourceIntTest {


    private static final Integer DEFAULT_SUPE_DICH = 1;
    private static final Integer UPDATED_SUPE_DICH = 2;

    private static final Integer DEFAULT_SUPE_AMMI = 1;
    private static final Integer UPDATED_SUPE_AMMI = 2;

    private static final Integer DEFAULT_SUPE_REFR = 1;
    private static final Integer UPDATED_SUPE_REFR = 2;

    private static final Integer DEFAULT_SUPE_DETE = 1;
    private static final Integer UPDATED_SUPE_DETE = 2;

    private static final Integer DEFAULT_SUPE_NSAN = 1;
    private static final Integer UPDATED_SUPE_NSAN = 2;

    private static final Integer DEFAULT_SUPE_ACCE = 1;
    private static final Integer UPDATED_SUPE_ACCE = 2;

    private static final Integer DEFAULT_NUM_TITO_DICH = 1;
    private static final Integer UPDATED_NUM_TITO_DICH = 2;

    private static final Integer DEFAULT_NUM_TITO_DETE = 1;
    private static final Integer UPDATED_NUM_TITO_DETE = 2;

    @Inject
    private SuperficiePagataRepository superficiePagataRepository;

    @Inject
    private SuperficiePagataSearchRepository superficiePagataSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSuperficiePagataMockMvc;

    private SuperficiePagata superficiePagata;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SuperficiePagataResource superficiePagataResource = new SuperficiePagataResource();
        ReflectionTestUtils.setField(superficiePagataResource, "superficiePagataSearchRepository", superficiePagataSearchRepository);
        ReflectionTestUtils.setField(superficiePagataResource, "superficiePagataRepository", superficiePagataRepository);
        this.restSuperficiePagataMockMvc = MockMvcBuilders.standaloneSetup(superficiePagataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        superficiePagataSearchRepository.deleteAll();
        superficiePagata = new SuperficiePagata();
        superficiePagata.setSupeDich(DEFAULT_SUPE_DICH);
        superficiePagata.setSupeAmmi(DEFAULT_SUPE_AMMI);
        superficiePagata.setSupeRefr(DEFAULT_SUPE_REFR);
        superficiePagata.setSupeDete(DEFAULT_SUPE_DETE);
        superficiePagata.setSupeNsan(DEFAULT_SUPE_NSAN);
        superficiePagata.setSupeAcce(DEFAULT_SUPE_ACCE);
        superficiePagata.setNumTitoDich(DEFAULT_NUM_TITO_DICH);
        superficiePagata.setNumTitoDete(DEFAULT_NUM_TITO_DETE);
    }

    @Test
    @Transactional
    public void createSuperficiePagata() throws Exception {
        int databaseSizeBeforeCreate = superficiePagataRepository.findAll().size();

        // Create the SuperficiePagata

        restSuperficiePagataMockMvc.perform(post("/api/superficie-pagatas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(superficiePagata)))
                .andExpect(status().isCreated());

        // Validate the SuperficiePagata in the database
        List<SuperficiePagata> superficiePagatas = superficiePagataRepository.findAll();
        assertThat(superficiePagatas).hasSize(databaseSizeBeforeCreate + 1);
        SuperficiePagata testSuperficiePagata = superficiePagatas.get(superficiePagatas.size() - 1);
        assertThat(testSuperficiePagata.getSupeDich()).isEqualTo(DEFAULT_SUPE_DICH);
        assertThat(testSuperficiePagata.getSupeAmmi()).isEqualTo(DEFAULT_SUPE_AMMI);
        assertThat(testSuperficiePagata.getSupeRefr()).isEqualTo(DEFAULT_SUPE_REFR);
        assertThat(testSuperficiePagata.getSupeDete()).isEqualTo(DEFAULT_SUPE_DETE);
        assertThat(testSuperficiePagata.getSupeNsan()).isEqualTo(DEFAULT_SUPE_NSAN);
        assertThat(testSuperficiePagata.getSupeAcce()).isEqualTo(DEFAULT_SUPE_ACCE);
        assertThat(testSuperficiePagata.getNumTitoDich()).isEqualTo(DEFAULT_NUM_TITO_DICH);
        assertThat(testSuperficiePagata.getNumTitoDete()).isEqualTo(DEFAULT_NUM_TITO_DETE);

        // Validate the SuperficiePagata in ElasticSearch
        SuperficiePagata superficiePagataEs = superficiePagataSearchRepository.findOne(testSuperficiePagata.getId());
        assertThat(superficiePagataEs).isEqualToComparingFieldByField(testSuperficiePagata);
    }

    @Test
    @Transactional
    public void getAllSuperficiePagatas() throws Exception {
        // Initialize the database
        superficiePagataRepository.saveAndFlush(superficiePagata);

        // Get all the superficiePagatas
        restSuperficiePagataMockMvc.perform(get("/api/superficie-pagatas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(superficiePagata.getId().intValue())))
                .andExpect(jsonPath("$.[*].supeDich").value(hasItem(DEFAULT_SUPE_DICH)))
                .andExpect(jsonPath("$.[*].supeAmmi").value(hasItem(DEFAULT_SUPE_AMMI)))
                .andExpect(jsonPath("$.[*].supeRefr").value(hasItem(DEFAULT_SUPE_REFR)))
                .andExpect(jsonPath("$.[*].supeDete").value(hasItem(DEFAULT_SUPE_DETE)))
                .andExpect(jsonPath("$.[*].supeNsan").value(hasItem(DEFAULT_SUPE_NSAN)))
                .andExpect(jsonPath("$.[*].supeAcce").value(hasItem(DEFAULT_SUPE_ACCE)))
                .andExpect(jsonPath("$.[*].numTitoDich").value(hasItem(DEFAULT_NUM_TITO_DICH)))
                .andExpect(jsonPath("$.[*].numTitoDete").value(hasItem(DEFAULT_NUM_TITO_DETE)));
    }

    @Test
    @Transactional
    public void getSuperficiePagata() throws Exception {
        // Initialize the database
        superficiePagataRepository.saveAndFlush(superficiePagata);

        // Get the superficiePagata
        restSuperficiePagataMockMvc.perform(get("/api/superficie-pagatas/{id}", superficiePagata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(superficiePagata.getId().intValue()))
            .andExpect(jsonPath("$.supeDich").value(DEFAULT_SUPE_DICH))
            .andExpect(jsonPath("$.supeAmmi").value(DEFAULT_SUPE_AMMI))
            .andExpect(jsonPath("$.supeRefr").value(DEFAULT_SUPE_REFR))
            .andExpect(jsonPath("$.supeDete").value(DEFAULT_SUPE_DETE))
            .andExpect(jsonPath("$.supeNsan").value(DEFAULT_SUPE_NSAN))
            .andExpect(jsonPath("$.supeAcce").value(DEFAULT_SUPE_ACCE))
            .andExpect(jsonPath("$.numTitoDich").value(DEFAULT_NUM_TITO_DICH))
            .andExpect(jsonPath("$.numTitoDete").value(DEFAULT_NUM_TITO_DETE));
    }

    @Test
    @Transactional
    public void getNonExistingSuperficiePagata() throws Exception {
        // Get the superficiePagata
        restSuperficiePagataMockMvc.perform(get("/api/superficie-pagatas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuperficiePagata() throws Exception {
        // Initialize the database
        superficiePagataRepository.saveAndFlush(superficiePagata);
        superficiePagataSearchRepository.save(superficiePagata);
        int databaseSizeBeforeUpdate = superficiePagataRepository.findAll().size();

        // Update the superficiePagata
        SuperficiePagata updatedSuperficiePagata = new SuperficiePagata();
        updatedSuperficiePagata.setId(superficiePagata.getId());
        updatedSuperficiePagata.setSupeDich(UPDATED_SUPE_DICH);
        updatedSuperficiePagata.setSupeAmmi(UPDATED_SUPE_AMMI);
        updatedSuperficiePagata.setSupeRefr(UPDATED_SUPE_REFR);
        updatedSuperficiePagata.setSupeDete(UPDATED_SUPE_DETE);
        updatedSuperficiePagata.setSupeNsan(UPDATED_SUPE_NSAN);
        updatedSuperficiePagata.setSupeAcce(UPDATED_SUPE_ACCE);
        updatedSuperficiePagata.setNumTitoDich(UPDATED_NUM_TITO_DICH);
        updatedSuperficiePagata.setNumTitoDete(UPDATED_NUM_TITO_DETE);

        restSuperficiePagataMockMvc.perform(put("/api/superficie-pagatas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSuperficiePagata)))
                .andExpect(status().isOk());

        // Validate the SuperficiePagata in the database
        List<SuperficiePagata> superficiePagatas = superficiePagataRepository.findAll();
        assertThat(superficiePagatas).hasSize(databaseSizeBeforeUpdate);
        SuperficiePagata testSuperficiePagata = superficiePagatas.get(superficiePagatas.size() - 1);
        assertThat(testSuperficiePagata.getSupeDich()).isEqualTo(UPDATED_SUPE_DICH);
        assertThat(testSuperficiePagata.getSupeAmmi()).isEqualTo(UPDATED_SUPE_AMMI);
        assertThat(testSuperficiePagata.getSupeRefr()).isEqualTo(UPDATED_SUPE_REFR);
        assertThat(testSuperficiePagata.getSupeDete()).isEqualTo(UPDATED_SUPE_DETE);
        assertThat(testSuperficiePagata.getSupeNsan()).isEqualTo(UPDATED_SUPE_NSAN);
        assertThat(testSuperficiePagata.getSupeAcce()).isEqualTo(UPDATED_SUPE_ACCE);
        assertThat(testSuperficiePagata.getNumTitoDich()).isEqualTo(UPDATED_NUM_TITO_DICH);
        assertThat(testSuperficiePagata.getNumTitoDete()).isEqualTo(UPDATED_NUM_TITO_DETE);

        // Validate the SuperficiePagata in ElasticSearch
        SuperficiePagata superficiePagataEs = superficiePagataSearchRepository.findOne(testSuperficiePagata.getId());
        assertThat(superficiePagataEs).isEqualToComparingFieldByField(testSuperficiePagata);
    }

    @Test
    @Transactional
    public void deleteSuperficiePagata() throws Exception {
        // Initialize the database
        superficiePagataRepository.saveAndFlush(superficiePagata);
        superficiePagataSearchRepository.save(superficiePagata);
        int databaseSizeBeforeDelete = superficiePagataRepository.findAll().size();

        // Get the superficiePagata
        restSuperficiePagataMockMvc.perform(delete("/api/superficie-pagatas/{id}", superficiePagata.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean superficiePagataExistsInEs = superficiePagataSearchRepository.exists(superficiePagata.getId());
        assertThat(superficiePagataExistsInEs).isFalse();

        // Validate the database is empty
        List<SuperficiePagata> superficiePagatas = superficiePagataRepository.findAll();
        assertThat(superficiePagatas).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSuperficiePagata() throws Exception {
        // Initialize the database
        superficiePagataRepository.saveAndFlush(superficiePagata);
        superficiePagataSearchRepository.save(superficiePagata);

        // Search the superficiePagata
        restSuperficiePagataMockMvc.perform(get("/api/_search/superficie-pagatas?query=id:" + superficiePagata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(superficiePagata.getId().intValue())))
            .andExpect(jsonPath("$.[*].supeDich").value(hasItem(DEFAULT_SUPE_DICH)))
            .andExpect(jsonPath("$.[*].supeAmmi").value(hasItem(DEFAULT_SUPE_AMMI)))
            .andExpect(jsonPath("$.[*].supeRefr").value(hasItem(DEFAULT_SUPE_REFR)))
            .andExpect(jsonPath("$.[*].supeDete").value(hasItem(DEFAULT_SUPE_DETE)))
            .andExpect(jsonPath("$.[*].supeNsan").value(hasItem(DEFAULT_SUPE_NSAN)))
            .andExpect(jsonPath("$.[*].supeAcce").value(hasItem(DEFAULT_SUPE_ACCE)))
            .andExpect(jsonPath("$.[*].numTitoDich").value(hasItem(DEFAULT_NUM_TITO_DICH)))
            .andExpect(jsonPath("$.[*].numTitoDete").value(hasItem(DEFAULT_NUM_TITO_DETE)));
    }
}
