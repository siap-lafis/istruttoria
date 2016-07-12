package it.almaviva.siap.istruttoria.web.rest;

import it.almaviva.siap.istruttoria.IstruttoriaApp;
import it.almaviva.siap.istruttoria.domain.Superficie;
import it.almaviva.siap.istruttoria.repository.SuperficieRepository;
import it.almaviva.siap.istruttoria.repository.search.SuperficieSearchRepository;

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
 * Test class for the SuperficieResource REST controller.
 *
 * @see SuperficieResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstruttoriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class SuperficieResourceIntTest {

    private static final String DEFAULT_COD_NAZIONALE = "AAAAA";
    private static final String UPDATED_COD_NAZIONALE = "BBBBB";

    private static final Integer DEFAULT_FOGLIO = 1;
    private static final Integer UPDATED_FOGLIO = 2;
    private static final String DEFAULT_COD_INTERVENTO = "AAAAA";
    private static final String UPDATED_COD_INTERVENTO = "BBBBB";
    private static final String DEFAULT_COD_COLTURA = "AAAAA";
    private static final String UPDATED_COD_COLTURA = "BBBBB";

    private static final Integer DEFAULT_SUPE_DICH = 1;
    private static final Integer UPDATED_SUPE_DICH = 2;

    private static final Integer DEFAULT_SUPE_AMMI = 1;
    private static final Integer UPDATED_SUPE_AMMI = 2;

    private static final Integer DEFAULT_SUPE_AMMI_NETTA = 1;
    private static final Integer UPDATED_SUPE_AMMI_NETTA = 2;

    @Inject
    private SuperficieRepository superficieRepository;

    @Inject
    private SuperficieSearchRepository superficieSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSuperficieMockMvc;

    private Superficie superficie;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SuperficieResource superficieResource = new SuperficieResource();
        ReflectionTestUtils.setField(superficieResource, "superficieSearchRepository", superficieSearchRepository);
        ReflectionTestUtils.setField(superficieResource, "superficieRepository", superficieRepository);
        this.restSuperficieMockMvc = MockMvcBuilders.standaloneSetup(superficieResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        superficieSearchRepository.deleteAll();
        superficie = new Superficie();
        superficie.setCodNazionale(DEFAULT_COD_NAZIONALE);
        superficie.setFoglio(DEFAULT_FOGLIO);
        superficie.setCodIntervento(DEFAULT_COD_INTERVENTO);
        superficie.setCodColtura(DEFAULT_COD_COLTURA);
        superficie.setSupeDich(DEFAULT_SUPE_DICH);
        superficie.setSupeAmmi(DEFAULT_SUPE_AMMI);
        superficie.setSupeAmmiNetta(DEFAULT_SUPE_AMMI_NETTA);
    }

    @Test
    @Transactional
    public void createSuperficie() throws Exception {
        int databaseSizeBeforeCreate = superficieRepository.findAll().size();

        // Create the Superficie

        restSuperficieMockMvc.perform(post("/api/superficies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(superficie)))
                .andExpect(status().isCreated());

        // Validate the Superficie in the database
        List<Superficie> superficies = superficieRepository.findAll();
        assertThat(superficies).hasSize(databaseSizeBeforeCreate + 1);
        Superficie testSuperficie = superficies.get(superficies.size() - 1);
        assertThat(testSuperficie.getCodNazionale()).isEqualTo(DEFAULT_COD_NAZIONALE);
        assertThat(testSuperficie.getFoglio()).isEqualTo(DEFAULT_FOGLIO);
        assertThat(testSuperficie.getCodIntervento()).isEqualTo(DEFAULT_COD_INTERVENTO);
        assertThat(testSuperficie.getCodColtura()).isEqualTo(DEFAULT_COD_COLTURA);
        assertThat(testSuperficie.getSupeDich()).isEqualTo(DEFAULT_SUPE_DICH);
        assertThat(testSuperficie.getSupeAmmi()).isEqualTo(DEFAULT_SUPE_AMMI);
        assertThat(testSuperficie.getSupeAmmiNetta()).isEqualTo(DEFAULT_SUPE_AMMI_NETTA);

        // Validate the Superficie in ElasticSearch
        Superficie superficieEs = superficieSearchRepository.findOne(testSuperficie.getId());
        assertThat(superficieEs).isEqualToComparingFieldByField(testSuperficie);
    }

    @Test
    @Transactional
    public void getAllSuperficies() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);

        // Get all the superficies
        restSuperficieMockMvc.perform(get("/api/superficies?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(superficie.getId().intValue())))
                .andExpect(jsonPath("$.[*].codNazionale").value(hasItem(DEFAULT_COD_NAZIONALE.toString())))
                .andExpect(jsonPath("$.[*].foglio").value(hasItem(DEFAULT_FOGLIO)))
                .andExpect(jsonPath("$.[*].codIntervento").value(hasItem(DEFAULT_COD_INTERVENTO.toString())))
                .andExpect(jsonPath("$.[*].codColtura").value(hasItem(DEFAULT_COD_COLTURA.toString())))
                .andExpect(jsonPath("$.[*].supeDich").value(hasItem(DEFAULT_SUPE_DICH)))
                .andExpect(jsonPath("$.[*].supeAmmi").value(hasItem(DEFAULT_SUPE_AMMI)))
                .andExpect(jsonPath("$.[*].supeAmmiNetta").value(hasItem(DEFAULT_SUPE_AMMI_NETTA)));
    }

    @Test
    @Transactional
    public void getSuperficie() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);

        // Get the superficie
        restSuperficieMockMvc.perform(get("/api/superficies/{id}", superficie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(superficie.getId().intValue()))
            .andExpect(jsonPath("$.codNazionale").value(DEFAULT_COD_NAZIONALE.toString()))
            .andExpect(jsonPath("$.foglio").value(DEFAULT_FOGLIO))
            .andExpect(jsonPath("$.codIntervento").value(DEFAULT_COD_INTERVENTO.toString()))
            .andExpect(jsonPath("$.codColtura").value(DEFAULT_COD_COLTURA.toString()))
            .andExpect(jsonPath("$.supeDich").value(DEFAULT_SUPE_DICH))
            .andExpect(jsonPath("$.supeAmmi").value(DEFAULT_SUPE_AMMI))
            .andExpect(jsonPath("$.supeAmmiNetta").value(DEFAULT_SUPE_AMMI_NETTA));
    }

    @Test
    @Transactional
    public void getNonExistingSuperficie() throws Exception {
        // Get the superficie
        restSuperficieMockMvc.perform(get("/api/superficies/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuperficie() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);
        superficieSearchRepository.save(superficie);
        int databaseSizeBeforeUpdate = superficieRepository.findAll().size();

        // Update the superficie
        Superficie updatedSuperficie = new Superficie();
        updatedSuperficie.setId(superficie.getId());
        updatedSuperficie.setCodNazionale(UPDATED_COD_NAZIONALE);
        updatedSuperficie.setFoglio(UPDATED_FOGLIO);
        updatedSuperficie.setCodIntervento(UPDATED_COD_INTERVENTO);
        updatedSuperficie.setCodColtura(UPDATED_COD_COLTURA);
        updatedSuperficie.setSupeDich(UPDATED_SUPE_DICH);
        updatedSuperficie.setSupeAmmi(UPDATED_SUPE_AMMI);
        updatedSuperficie.setSupeAmmiNetta(UPDATED_SUPE_AMMI_NETTA);

        restSuperficieMockMvc.perform(put("/api/superficies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSuperficie)))
                .andExpect(status().isOk());

        // Validate the Superficie in the database
        List<Superficie> superficies = superficieRepository.findAll();
        assertThat(superficies).hasSize(databaseSizeBeforeUpdate);
        Superficie testSuperficie = superficies.get(superficies.size() - 1);
        assertThat(testSuperficie.getCodNazionale()).isEqualTo(UPDATED_COD_NAZIONALE);
        assertThat(testSuperficie.getFoglio()).isEqualTo(UPDATED_FOGLIO);
        assertThat(testSuperficie.getCodIntervento()).isEqualTo(UPDATED_COD_INTERVENTO);
        assertThat(testSuperficie.getCodColtura()).isEqualTo(UPDATED_COD_COLTURA);
        assertThat(testSuperficie.getSupeDich()).isEqualTo(UPDATED_SUPE_DICH);
        assertThat(testSuperficie.getSupeAmmi()).isEqualTo(UPDATED_SUPE_AMMI);
        assertThat(testSuperficie.getSupeAmmiNetta()).isEqualTo(UPDATED_SUPE_AMMI_NETTA);

        // Validate the Superficie in ElasticSearch
        Superficie superficieEs = superficieSearchRepository.findOne(testSuperficie.getId());
        assertThat(superficieEs).isEqualToComparingFieldByField(testSuperficie);
    }

    @Test
    @Transactional
    public void deleteSuperficie() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);
        superficieSearchRepository.save(superficie);
        int databaseSizeBeforeDelete = superficieRepository.findAll().size();

        // Get the superficie
        restSuperficieMockMvc.perform(delete("/api/superficies/{id}", superficie.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean superficieExistsInEs = superficieSearchRepository.exists(superficie.getId());
        assertThat(superficieExistsInEs).isFalse();

        // Validate the database is empty
        List<Superficie> superficies = superficieRepository.findAll();
        assertThat(superficies).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSuperficie() throws Exception {
        // Initialize the database
        superficieRepository.saveAndFlush(superficie);
        superficieSearchRepository.save(superficie);

        // Search the superficie
        restSuperficieMockMvc.perform(get("/api/_search/superficies?query=id:" + superficie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(superficie.getId().intValue())))
            .andExpect(jsonPath("$.[*].codNazionale").value(hasItem(DEFAULT_COD_NAZIONALE.toString())))
            .andExpect(jsonPath("$.[*].foglio").value(hasItem(DEFAULT_FOGLIO)))
            .andExpect(jsonPath("$.[*].codIntervento").value(hasItem(DEFAULT_COD_INTERVENTO.toString())))
            .andExpect(jsonPath("$.[*].codColtura").value(hasItem(DEFAULT_COD_COLTURA.toString())))
            .andExpect(jsonPath("$.[*].supeDich").value(hasItem(DEFAULT_SUPE_DICH)))
            .andExpect(jsonPath("$.[*].supeAmmi").value(hasItem(DEFAULT_SUPE_AMMI)))
            .andExpect(jsonPath("$.[*].supeAmmiNetta").value(hasItem(DEFAULT_SUPE_AMMI_NETTA)));
    }
}
