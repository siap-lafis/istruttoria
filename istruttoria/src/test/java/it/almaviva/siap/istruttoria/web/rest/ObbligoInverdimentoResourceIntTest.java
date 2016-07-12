package it.almaviva.siap.istruttoria.web.rest;

import it.almaviva.siap.istruttoria.IstruttoriaApp;
import it.almaviva.siap.istruttoria.domain.ObbligoInverdimento;
import it.almaviva.siap.istruttoria.repository.ObbligoInverdimentoRepository;
import it.almaviva.siap.istruttoria.repository.search.ObbligoInverdimentoSearchRepository;

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
 * Test class for the ObbligoInverdimentoResource REST controller.
 *
 * @see ObbligoInverdimentoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstruttoriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class ObbligoInverdimentoResourceIntTest {


    private static final Integer DEFAULT_SUPE_SEMI = 1;
    private static final Integer UPDATED_SUPE_SEMI = 2;

    private static final Integer DEFAULT_SUPE_PRAT_PERM = 1;
    private static final Integer UPDATED_SUPE_PRAT_PERM = 2;

    private static final Integer DEFAULT_SUPE_FORA = 1;
    private static final Integer UPDATED_SUPE_FORA = 2;

    private static final Integer DEFAULT_DECO_ESON_DIVE = 1;
    private static final Integer UPDATED_DECO_ESON_DIVE = 2;

    private static final Integer DEFAULT_DECO_ESON_EFA = 1;
    private static final Integer UPDATED_DECO_ESON_EFA = 2;

    private static final Integer DEFAULT_FLAG_RISP_COLT = 1;
    private static final Integer UPDATED_FLAG_RISP_COLT = 2;

    private static final Integer DEFAULT_FLAG_RISP_COLT_RIMA = 1;
    private static final Integer UPDATED_FLAG_RISP_COLT_RIMA = 2;

    private static final Integer DEFAULT_FLAG_RISP_75_P = 1;
    private static final Integer UPDATED_FLAG_RISP_75_P = 2;

    private static final Integer DEFAULT_FLAG_RISP_95_P = 1;
    private static final Integer UPDATED_FLAG_RISP_95_P = 2;

    private static final Integer DEFAULT_FLAG_RISP_EFA = 1;
    private static final Integer UPDATED_FLAG_RISP_EFA = 2;

    @Inject
    private ObbligoInverdimentoRepository obbligoInverdimentoRepository;

    @Inject
    private ObbligoInverdimentoSearchRepository obbligoInverdimentoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restObbligoInverdimentoMockMvc;

    private ObbligoInverdimento obbligoInverdimento;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ObbligoInverdimentoResource obbligoInverdimentoResource = new ObbligoInverdimentoResource();
        ReflectionTestUtils.setField(obbligoInverdimentoResource, "obbligoInverdimentoSearchRepository", obbligoInverdimentoSearchRepository);
        ReflectionTestUtils.setField(obbligoInverdimentoResource, "obbligoInverdimentoRepository", obbligoInverdimentoRepository);
        this.restObbligoInverdimentoMockMvc = MockMvcBuilders.standaloneSetup(obbligoInverdimentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        obbligoInverdimentoSearchRepository.deleteAll();
        obbligoInverdimento = new ObbligoInverdimento();
        obbligoInverdimento.setSupeSemi(DEFAULT_SUPE_SEMI);
        obbligoInverdimento.setSupePratPerm(DEFAULT_SUPE_PRAT_PERM);
        obbligoInverdimento.setSupeFora(DEFAULT_SUPE_FORA);
        obbligoInverdimento.setDecoEsonDive(DEFAULT_DECO_ESON_DIVE);
        obbligoInverdimento.setDecoEsonEfa(DEFAULT_DECO_ESON_EFA);
        obbligoInverdimento.setFlagRispColt(DEFAULT_FLAG_RISP_COLT);
        obbligoInverdimento.setFlagRispColtRima(DEFAULT_FLAG_RISP_COLT_RIMA);
        obbligoInverdimento.setFlagRisp75P(DEFAULT_FLAG_RISP_75_P);
        obbligoInverdimento.setFlagRisp95P(DEFAULT_FLAG_RISP_95_P);
        obbligoInverdimento.setFlagRispEfa(DEFAULT_FLAG_RISP_EFA);
    }

    @Test
    @Transactional
    public void createObbligoInverdimento() throws Exception {
        int databaseSizeBeforeCreate = obbligoInverdimentoRepository.findAll().size();

        // Create the ObbligoInverdimento

        restObbligoInverdimentoMockMvc.perform(post("/api/obbligo-inverdimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(obbligoInverdimento)))
                .andExpect(status().isCreated());

        // Validate the ObbligoInverdimento in the database
        List<ObbligoInverdimento> obbligoInverdimentos = obbligoInverdimentoRepository.findAll();
        assertThat(obbligoInverdimentos).hasSize(databaseSizeBeforeCreate + 1);
        ObbligoInverdimento testObbligoInverdimento = obbligoInverdimentos.get(obbligoInverdimentos.size() - 1);
        assertThat(testObbligoInverdimento.getSupeSemi()).isEqualTo(DEFAULT_SUPE_SEMI);
        assertThat(testObbligoInverdimento.getSupePratPerm()).isEqualTo(DEFAULT_SUPE_PRAT_PERM);
        assertThat(testObbligoInverdimento.getSupeFora()).isEqualTo(DEFAULT_SUPE_FORA);
        assertThat(testObbligoInverdimento.getDecoEsonDive()).isEqualTo(DEFAULT_DECO_ESON_DIVE);
        assertThat(testObbligoInverdimento.getDecoEsonEfa()).isEqualTo(DEFAULT_DECO_ESON_EFA);
        assertThat(testObbligoInverdimento.getFlagRispColt()).isEqualTo(DEFAULT_FLAG_RISP_COLT);
        assertThat(testObbligoInverdimento.getFlagRispColtRima()).isEqualTo(DEFAULT_FLAG_RISP_COLT_RIMA);
        assertThat(testObbligoInverdimento.getFlagRisp75P()).isEqualTo(DEFAULT_FLAG_RISP_75_P);
        assertThat(testObbligoInverdimento.getFlagRisp95P()).isEqualTo(DEFAULT_FLAG_RISP_95_P);
        assertThat(testObbligoInverdimento.getFlagRispEfa()).isEqualTo(DEFAULT_FLAG_RISP_EFA);

        // Validate the ObbligoInverdimento in ElasticSearch
        ObbligoInverdimento obbligoInverdimentoEs = obbligoInverdimentoSearchRepository.findOne(testObbligoInverdimento.getId());
        assertThat(obbligoInverdimentoEs).isEqualToComparingFieldByField(testObbligoInverdimento);
    }

    @Test
    @Transactional
    public void getAllObbligoInverdimentos() throws Exception {
        // Initialize the database
        obbligoInverdimentoRepository.saveAndFlush(obbligoInverdimento);

        // Get all the obbligoInverdimentos
        restObbligoInverdimentoMockMvc.perform(get("/api/obbligo-inverdimentos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(obbligoInverdimento.getId().intValue())))
                .andExpect(jsonPath("$.[*].supeSemi").value(hasItem(DEFAULT_SUPE_SEMI)))
                .andExpect(jsonPath("$.[*].supePratPerm").value(hasItem(DEFAULT_SUPE_PRAT_PERM)))
                .andExpect(jsonPath("$.[*].supeFora").value(hasItem(DEFAULT_SUPE_FORA)))
                .andExpect(jsonPath("$.[*].decoEsonDive").value(hasItem(DEFAULT_DECO_ESON_DIVE)))
                .andExpect(jsonPath("$.[*].decoEsonEfa").value(hasItem(DEFAULT_DECO_ESON_EFA)))
                .andExpect(jsonPath("$.[*].flagRispColt").value(hasItem(DEFAULT_FLAG_RISP_COLT)))
                .andExpect(jsonPath("$.[*].flagRispColtRima").value(hasItem(DEFAULT_FLAG_RISP_COLT_RIMA)))
                .andExpect(jsonPath("$.[*].flagRisp75P").value(hasItem(DEFAULT_FLAG_RISP_75_P)))
                .andExpect(jsonPath("$.[*].flagRisp95P").value(hasItem(DEFAULT_FLAG_RISP_95_P)))
                .andExpect(jsonPath("$.[*].flagRispEfa").value(hasItem(DEFAULT_FLAG_RISP_EFA)));
    }

    @Test
    @Transactional
    public void getObbligoInverdimento() throws Exception {
        // Initialize the database
        obbligoInverdimentoRepository.saveAndFlush(obbligoInverdimento);

        // Get the obbligoInverdimento
        restObbligoInverdimentoMockMvc.perform(get("/api/obbligo-inverdimentos/{id}", obbligoInverdimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(obbligoInverdimento.getId().intValue()))
            .andExpect(jsonPath("$.supeSemi").value(DEFAULT_SUPE_SEMI))
            .andExpect(jsonPath("$.supePratPerm").value(DEFAULT_SUPE_PRAT_PERM))
            .andExpect(jsonPath("$.supeFora").value(DEFAULT_SUPE_FORA))
            .andExpect(jsonPath("$.decoEsonDive").value(DEFAULT_DECO_ESON_DIVE))
            .andExpect(jsonPath("$.decoEsonEfa").value(DEFAULT_DECO_ESON_EFA))
            .andExpect(jsonPath("$.flagRispColt").value(DEFAULT_FLAG_RISP_COLT))
            .andExpect(jsonPath("$.flagRispColtRima").value(DEFAULT_FLAG_RISP_COLT_RIMA))
            .andExpect(jsonPath("$.flagRisp75P").value(DEFAULT_FLAG_RISP_75_P))
            .andExpect(jsonPath("$.flagRisp95P").value(DEFAULT_FLAG_RISP_95_P))
            .andExpect(jsonPath("$.flagRispEfa").value(DEFAULT_FLAG_RISP_EFA));
    }

    @Test
    @Transactional
    public void getNonExistingObbligoInverdimento() throws Exception {
        // Get the obbligoInverdimento
        restObbligoInverdimentoMockMvc.perform(get("/api/obbligo-inverdimentos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObbligoInverdimento() throws Exception {
        // Initialize the database
        obbligoInverdimentoRepository.saveAndFlush(obbligoInverdimento);
        obbligoInverdimentoSearchRepository.save(obbligoInverdimento);
        int databaseSizeBeforeUpdate = obbligoInverdimentoRepository.findAll().size();

        // Update the obbligoInverdimento
        ObbligoInverdimento updatedObbligoInverdimento = new ObbligoInverdimento();
        updatedObbligoInverdimento.setId(obbligoInverdimento.getId());
        updatedObbligoInverdimento.setSupeSemi(UPDATED_SUPE_SEMI);
        updatedObbligoInverdimento.setSupePratPerm(UPDATED_SUPE_PRAT_PERM);
        updatedObbligoInverdimento.setSupeFora(UPDATED_SUPE_FORA);
        updatedObbligoInverdimento.setDecoEsonDive(UPDATED_DECO_ESON_DIVE);
        updatedObbligoInverdimento.setDecoEsonEfa(UPDATED_DECO_ESON_EFA);
        updatedObbligoInverdimento.setFlagRispColt(UPDATED_FLAG_RISP_COLT);
        updatedObbligoInverdimento.setFlagRispColtRima(UPDATED_FLAG_RISP_COLT_RIMA);
        updatedObbligoInverdimento.setFlagRisp75P(UPDATED_FLAG_RISP_75_P);
        updatedObbligoInverdimento.setFlagRisp95P(UPDATED_FLAG_RISP_95_P);
        updatedObbligoInverdimento.setFlagRispEfa(UPDATED_FLAG_RISP_EFA);

        restObbligoInverdimentoMockMvc.perform(put("/api/obbligo-inverdimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedObbligoInverdimento)))
                .andExpect(status().isOk());

        // Validate the ObbligoInverdimento in the database
        List<ObbligoInverdimento> obbligoInverdimentos = obbligoInverdimentoRepository.findAll();
        assertThat(obbligoInverdimentos).hasSize(databaseSizeBeforeUpdate);
        ObbligoInverdimento testObbligoInverdimento = obbligoInverdimentos.get(obbligoInverdimentos.size() - 1);
        assertThat(testObbligoInverdimento.getSupeSemi()).isEqualTo(UPDATED_SUPE_SEMI);
        assertThat(testObbligoInverdimento.getSupePratPerm()).isEqualTo(UPDATED_SUPE_PRAT_PERM);
        assertThat(testObbligoInverdimento.getSupeFora()).isEqualTo(UPDATED_SUPE_FORA);
        assertThat(testObbligoInverdimento.getDecoEsonDive()).isEqualTo(UPDATED_DECO_ESON_DIVE);
        assertThat(testObbligoInverdimento.getDecoEsonEfa()).isEqualTo(UPDATED_DECO_ESON_EFA);
        assertThat(testObbligoInverdimento.getFlagRispColt()).isEqualTo(UPDATED_FLAG_RISP_COLT);
        assertThat(testObbligoInverdimento.getFlagRispColtRima()).isEqualTo(UPDATED_FLAG_RISP_COLT_RIMA);
        assertThat(testObbligoInverdimento.getFlagRisp75P()).isEqualTo(UPDATED_FLAG_RISP_75_P);
        assertThat(testObbligoInverdimento.getFlagRisp95P()).isEqualTo(UPDATED_FLAG_RISP_95_P);
        assertThat(testObbligoInverdimento.getFlagRispEfa()).isEqualTo(UPDATED_FLAG_RISP_EFA);

        // Validate the ObbligoInverdimento in ElasticSearch
        ObbligoInverdimento obbligoInverdimentoEs = obbligoInverdimentoSearchRepository.findOne(testObbligoInverdimento.getId());
        assertThat(obbligoInverdimentoEs).isEqualToComparingFieldByField(testObbligoInverdimento);
    }

    @Test
    @Transactional
    public void deleteObbligoInverdimento() throws Exception {
        // Initialize the database
        obbligoInverdimentoRepository.saveAndFlush(obbligoInverdimento);
        obbligoInverdimentoSearchRepository.save(obbligoInverdimento);
        int databaseSizeBeforeDelete = obbligoInverdimentoRepository.findAll().size();

        // Get the obbligoInverdimento
        restObbligoInverdimentoMockMvc.perform(delete("/api/obbligo-inverdimentos/{id}", obbligoInverdimento.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean obbligoInverdimentoExistsInEs = obbligoInverdimentoSearchRepository.exists(obbligoInverdimento.getId());
        assertThat(obbligoInverdimentoExistsInEs).isFalse();

        // Validate the database is empty
        List<ObbligoInverdimento> obbligoInverdimentos = obbligoInverdimentoRepository.findAll();
        assertThat(obbligoInverdimentos).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchObbligoInverdimento() throws Exception {
        // Initialize the database
        obbligoInverdimentoRepository.saveAndFlush(obbligoInverdimento);
        obbligoInverdimentoSearchRepository.save(obbligoInverdimento);

        // Search the obbligoInverdimento
        restObbligoInverdimentoMockMvc.perform(get("/api/_search/obbligo-inverdimentos?query=id:" + obbligoInverdimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obbligoInverdimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].supeSemi").value(hasItem(DEFAULT_SUPE_SEMI)))
            .andExpect(jsonPath("$.[*].supePratPerm").value(hasItem(DEFAULT_SUPE_PRAT_PERM)))
            .andExpect(jsonPath("$.[*].supeFora").value(hasItem(DEFAULT_SUPE_FORA)))
            .andExpect(jsonPath("$.[*].decoEsonDive").value(hasItem(DEFAULT_DECO_ESON_DIVE)))
            .andExpect(jsonPath("$.[*].decoEsonEfa").value(hasItem(DEFAULT_DECO_ESON_EFA)))
            .andExpect(jsonPath("$.[*].flagRispColt").value(hasItem(DEFAULT_FLAG_RISP_COLT)))
            .andExpect(jsonPath("$.[*].flagRispColtRima").value(hasItem(DEFAULT_FLAG_RISP_COLT_RIMA)))
            .andExpect(jsonPath("$.[*].flagRisp75P").value(hasItem(DEFAULT_FLAG_RISP_75_P)))
            .andExpect(jsonPath("$.[*].flagRisp95P").value(hasItem(DEFAULT_FLAG_RISP_95_P)))
            .andExpect(jsonPath("$.[*].flagRispEfa").value(hasItem(DEFAULT_FLAG_RISP_EFA)));
    }
}
