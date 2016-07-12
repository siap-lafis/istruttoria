package it.almaviva.siap.istruttoria.web.rest;

import it.almaviva.siap.istruttoria.IstruttoriaApp;
import it.almaviva.siap.istruttoria.domain.ElencoPagamento;
import it.almaviva.siap.istruttoria.repository.ElencoPagamentoRepository;
import it.almaviva.siap.istruttoria.repository.search.ElencoPagamentoSearchRepository;

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
 * Test class for the ElencoPagamentoResource REST controller.
 *
 * @see ElencoPagamentoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstruttoriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class ElencoPagamentoResourceIntTest {


    private static final Integer DEFAULT_ID_DECR = 1;
    private static final Integer UPDATED_ID_DECR = 2;
    private static final String DEFAULT_DATA_DECR = "AAAAA";
    private static final String UPDATED_DATA_DECR = "BBBBB";

    @Inject
    private ElencoPagamentoRepository elencoPagamentoRepository;

    @Inject
    private ElencoPagamentoSearchRepository elencoPagamentoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restElencoPagamentoMockMvc;

    private ElencoPagamento elencoPagamento;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ElencoPagamentoResource elencoPagamentoResource = new ElencoPagamentoResource();
        ReflectionTestUtils.setField(elencoPagamentoResource, "elencoPagamentoSearchRepository", elencoPagamentoSearchRepository);
        ReflectionTestUtils.setField(elencoPagamentoResource, "elencoPagamentoRepository", elencoPagamentoRepository);
        this.restElencoPagamentoMockMvc = MockMvcBuilders.standaloneSetup(elencoPagamentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        elencoPagamentoSearchRepository.deleteAll();
        elencoPagamento = new ElencoPagamento();
        elencoPagamento.setIdDecr(DEFAULT_ID_DECR);
        elencoPagamento.setDataDecr(DEFAULT_DATA_DECR);
    }

    @Test
    @Transactional
    public void createElencoPagamento() throws Exception {
        int databaseSizeBeforeCreate = elencoPagamentoRepository.findAll().size();

        // Create the ElencoPagamento

        restElencoPagamentoMockMvc.perform(post("/api/elenco-pagamentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(elencoPagamento)))
                .andExpect(status().isCreated());

        // Validate the ElencoPagamento in the database
        List<ElencoPagamento> elencoPagamentos = elencoPagamentoRepository.findAll();
        assertThat(elencoPagamentos).hasSize(databaseSizeBeforeCreate + 1);
        ElencoPagamento testElencoPagamento = elencoPagamentos.get(elencoPagamentos.size() - 1);
        assertThat(testElencoPagamento.getIdDecr()).isEqualTo(DEFAULT_ID_DECR);
        assertThat(testElencoPagamento.getDataDecr()).isEqualTo(DEFAULT_DATA_DECR);

        // Validate the ElencoPagamento in ElasticSearch
        ElencoPagamento elencoPagamentoEs = elencoPagamentoSearchRepository.findOne(testElencoPagamento.getId());
        assertThat(elencoPagamentoEs).isEqualToComparingFieldByField(testElencoPagamento);
    }

    @Test
    @Transactional
    public void getAllElencoPagamentos() throws Exception {
        // Initialize the database
        elencoPagamentoRepository.saveAndFlush(elencoPagamento);

        // Get all the elencoPagamentos
        restElencoPagamentoMockMvc.perform(get("/api/elenco-pagamentos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(elencoPagamento.getId().intValue())))
                .andExpect(jsonPath("$.[*].idDecr").value(hasItem(DEFAULT_ID_DECR)))
                .andExpect(jsonPath("$.[*].dataDecr").value(hasItem(DEFAULT_DATA_DECR.toString())));
    }

    @Test
    @Transactional
    public void getElencoPagamento() throws Exception {
        // Initialize the database
        elencoPagamentoRepository.saveAndFlush(elencoPagamento);

        // Get the elencoPagamento
        restElencoPagamentoMockMvc.perform(get("/api/elenco-pagamentos/{id}", elencoPagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(elencoPagamento.getId().intValue()))
            .andExpect(jsonPath("$.idDecr").value(DEFAULT_ID_DECR))
            .andExpect(jsonPath("$.dataDecr").value(DEFAULT_DATA_DECR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingElencoPagamento() throws Exception {
        // Get the elencoPagamento
        restElencoPagamentoMockMvc.perform(get("/api/elenco-pagamentos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElencoPagamento() throws Exception {
        // Initialize the database
        elencoPagamentoRepository.saveAndFlush(elencoPagamento);
        elencoPagamentoSearchRepository.save(elencoPagamento);
        int databaseSizeBeforeUpdate = elencoPagamentoRepository.findAll().size();

        // Update the elencoPagamento
        ElencoPagamento updatedElencoPagamento = new ElencoPagamento();
        updatedElencoPagamento.setId(elencoPagamento.getId());
        updatedElencoPagamento.setIdDecr(UPDATED_ID_DECR);
        updatedElencoPagamento.setDataDecr(UPDATED_DATA_DECR);

        restElencoPagamentoMockMvc.perform(put("/api/elenco-pagamentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedElencoPagamento)))
                .andExpect(status().isOk());

        // Validate the ElencoPagamento in the database
        List<ElencoPagamento> elencoPagamentos = elencoPagamentoRepository.findAll();
        assertThat(elencoPagamentos).hasSize(databaseSizeBeforeUpdate);
        ElencoPagamento testElencoPagamento = elencoPagamentos.get(elencoPagamentos.size() - 1);
        assertThat(testElencoPagamento.getIdDecr()).isEqualTo(UPDATED_ID_DECR);
        assertThat(testElencoPagamento.getDataDecr()).isEqualTo(UPDATED_DATA_DECR);

        // Validate the ElencoPagamento in ElasticSearch
        ElencoPagamento elencoPagamentoEs = elencoPagamentoSearchRepository.findOne(testElencoPagamento.getId());
        assertThat(elencoPagamentoEs).isEqualToComparingFieldByField(testElencoPagamento);
    }

    @Test
    @Transactional
    public void deleteElencoPagamento() throws Exception {
        // Initialize the database
        elencoPagamentoRepository.saveAndFlush(elencoPagamento);
        elencoPagamentoSearchRepository.save(elencoPagamento);
        int databaseSizeBeforeDelete = elencoPagamentoRepository.findAll().size();

        // Get the elencoPagamento
        restElencoPagamentoMockMvc.perform(delete("/api/elenco-pagamentos/{id}", elencoPagamento.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean elencoPagamentoExistsInEs = elencoPagamentoSearchRepository.exists(elencoPagamento.getId());
        assertThat(elencoPagamentoExistsInEs).isFalse();

        // Validate the database is empty
        List<ElencoPagamento> elencoPagamentos = elencoPagamentoRepository.findAll();
        assertThat(elencoPagamentos).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchElencoPagamento() throws Exception {
        // Initialize the database
        elencoPagamentoRepository.saveAndFlush(elencoPagamento);
        elencoPagamentoSearchRepository.save(elencoPagamento);

        // Search the elencoPagamento
        restElencoPagamentoMockMvc.perform(get("/api/_search/elenco-pagamentos?query=id:" + elencoPagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elencoPagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].idDecr").value(hasItem(DEFAULT_ID_DECR)))
            .andExpect(jsonPath("$.[*].dataDecr").value(hasItem(DEFAULT_DATA_DECR.toString())));
    }
}
