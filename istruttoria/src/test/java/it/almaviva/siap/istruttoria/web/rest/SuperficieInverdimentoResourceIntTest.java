package it.almaviva.siap.istruttoria.web.rest;

import it.almaviva.siap.istruttoria.IstruttoriaApp;
import it.almaviva.siap.istruttoria.domain.SuperficieInverdimento;
import it.almaviva.siap.istruttoria.repository.SuperficieInverdimentoRepository;
import it.almaviva.siap.istruttoria.repository.search.SuperficieInverdimentoSearchRepository;

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
 * Test class for the SuperficieInverdimentoResource REST controller.
 *
 * @see SuperficieInverdimentoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstruttoriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class SuperficieInverdimentoResourceIntTest {


    private static final Integer DEFAULT_SUPE_SEMI = 1;
    private static final Integer UPDATED_SUPE_SEMI = 2;

    private static final Integer DEFAULT_SUPE_PRIM_COLT = 1;
    private static final Integer UPDATED_SUPE_PRIM_COLT = 2;

    private static final Integer DEFAULT_SUPE_SECO_COLT = 1;
    private static final Integer UPDATED_SUPE_SECO_COLT = 2;

    private static final Integer DEFAULT_SUPE_ALTR_COLT = 1;
    private static final Integer UPDATED_SUPE_ALTR_COLT = 2;

    private static final Integer DEFAULT_SUPE_PRIM_MAX = 1;
    private static final Integer UPDATED_SUPE_PRIM_MAX = 2;

    private static final Integer DEFAULT_SUPE_SECO_MAX = 1;
    private static final Integer UPDATED_SUPE_SECO_MAX = 2;

    private static final Integer DEFAULT_SUPE_PRIM_DIFF_1 = 1;
    private static final Integer UPDATED_SUPE_PRIM_DIFF_1 = 2;

    private static final Integer DEFAULT_SUPE_PRIM_DIFF_2 = 1;
    private static final Integer UPDATED_SUPE_PRIM_DIFF_2 = 2;

    private static final Float DEFAULT_TASSO_DIFF_PRIM = 1F;
    private static final Float UPDATED_TASSO_DIFF_PRIM = 2F;

    private static final Integer DEFAULT_SUPE_PRIM_RIDU = 1;
    private static final Integer UPDATED_SUPE_PRIM_RIDU = 2;

    private static final Integer DEFAULT_SUPE_SECO_DIFF_1 = 1;
    private static final Integer UPDATED_SUPE_SECO_DIFF_1 = 2;

    private static final Integer DEFAULT_SUPE_SECO_DIFF_2 = 1;
    private static final Integer UPDATED_SUPE_SECO_DIFF_2 = 2;

    private static final Float DEFAULT_TASSO_DIFF_SECO = 1F;
    private static final Float UPDATED_TASSO_DIFF_SECO = 2F;

    private static final Integer DEFAULT_SUPE_SECO_RIDU = 1;
    private static final Integer UPDATED_SUPE_SECO_RIDU = 2;

    private static final Float DEFAULT_TOTA_TASSO_DIFF = 1F;
    private static final Float UPDATED_TOTA_TASSO_DIFF = 2F;

    private static final Integer DEFAULT_SUPE_RIDU_DIVE = 1;
    private static final Integer UPDATED_SUPE_RIDU_DIVE = 2;

    private static final Integer DEFAULT_SUPE_EFA = 1;
    private static final Integer UPDATED_SUPE_EFA = 2;

    private static final Integer DEFAULT_SUPE_EFA_OBBL = 1;
    private static final Integer UPDATED_SUPE_EFA_OBBL = 2;

    private static final Integer DEFAULT_SUPE_EFA_DIFF = 1;
    private static final Integer UPDATED_SUPE_EFA_DIFF = 2;

    private static final Float DEFAULT_TASSO_DIFF_EFA = 1F;
    private static final Float UPDATED_TASSO_DIFF_EFA = 2F;

    private static final Integer DEFAULT_SUPE_RIDU_EFA = 1;
    private static final Integer UPDATED_SUPE_RIDU_EFA = 2;

    private static final Integer DEFAULT_TOTA_RIDU = 1;
    private static final Integer UPDATED_TOTA_RIDU = 2;

    private static final Integer DEFAULT_SUPE_PAGA_SEMI = 1;
    private static final Integer UPDATED_SUPE_PAGA_SEMI = 2;

    private static final Integer DEFAULT_SUPE_PRAT_SENS = 1;
    private static final Integer UPDATED_SUPE_PRAT_SENS = 2;

    private static final Integer DEFAULT_SUPE_PRAT_NSENS = 1;
    private static final Integer UPDATED_SUPE_PRAT_NSENS = 2;

    private static final Integer DEFAULT_SUPE_PERM = 1;
    private static final Integer UPDATED_SUPE_PERM = 2;

    private static final Integer DEFAULT_SUPE_INVE = 1;
    private static final Integer UPDATED_SUPE_INVE = 2;

    @Inject
    private SuperficieInverdimentoRepository superficieInverdimentoRepository;

    @Inject
    private SuperficieInverdimentoSearchRepository superficieInverdimentoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSuperficieInverdimentoMockMvc;

    private SuperficieInverdimento superficieInverdimento;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SuperficieInverdimentoResource superficieInverdimentoResource = new SuperficieInverdimentoResource();
        ReflectionTestUtils.setField(superficieInverdimentoResource, "superficieInverdimentoSearchRepository", superficieInverdimentoSearchRepository);
        ReflectionTestUtils.setField(superficieInverdimentoResource, "superficieInverdimentoRepository", superficieInverdimentoRepository);
        this.restSuperficieInverdimentoMockMvc = MockMvcBuilders.standaloneSetup(superficieInverdimentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        superficieInverdimentoSearchRepository.deleteAll();
        superficieInverdimento = new SuperficieInverdimento();
        superficieInverdimento.setSupeSemi(DEFAULT_SUPE_SEMI);
        superficieInverdimento.setSupePrimColt(DEFAULT_SUPE_PRIM_COLT);
        superficieInverdimento.setSupeSecoColt(DEFAULT_SUPE_SECO_COLT);
        superficieInverdimento.setSupeAltrColt(DEFAULT_SUPE_ALTR_COLT);
        superficieInverdimento.setSupePrimMax(DEFAULT_SUPE_PRIM_MAX);
        superficieInverdimento.setSupeSecoMax(DEFAULT_SUPE_SECO_MAX);
        superficieInverdimento.setSupePrimDiff1(DEFAULT_SUPE_PRIM_DIFF_1);
        superficieInverdimento.setSupePrimDiff2(DEFAULT_SUPE_PRIM_DIFF_2);
        superficieInverdimento.setTassoDiffPrim(DEFAULT_TASSO_DIFF_PRIM);
        superficieInverdimento.setSupePrimRidu(DEFAULT_SUPE_PRIM_RIDU);
        superficieInverdimento.setSupeSecoDiff1(DEFAULT_SUPE_SECO_DIFF_1);
        superficieInverdimento.setSupeSecoDiff2(DEFAULT_SUPE_SECO_DIFF_2);
        superficieInverdimento.setTassoDiffSeco(DEFAULT_TASSO_DIFF_SECO);
        superficieInverdimento.setSupeSecoRidu(DEFAULT_SUPE_SECO_RIDU);
        superficieInverdimento.setTotaTassoDiff(DEFAULT_TOTA_TASSO_DIFF);
        superficieInverdimento.setSupeRiduDive(DEFAULT_SUPE_RIDU_DIVE);
        superficieInverdimento.setSupeEfa(DEFAULT_SUPE_EFA);
        superficieInverdimento.setSupeEfaObbl(DEFAULT_SUPE_EFA_OBBL);
        superficieInverdimento.setSupeEfaDiff(DEFAULT_SUPE_EFA_DIFF);
        superficieInverdimento.setTassoDiffEfa(DEFAULT_TASSO_DIFF_EFA);
        superficieInverdimento.setSupeRiduEfa(DEFAULT_SUPE_RIDU_EFA);
        superficieInverdimento.setTotaRidu(DEFAULT_TOTA_RIDU);
        superficieInverdimento.setSupePagaSemi(DEFAULT_SUPE_PAGA_SEMI);
        superficieInverdimento.setSupePratSens(DEFAULT_SUPE_PRAT_SENS);
        superficieInverdimento.setSupePratNsens(DEFAULT_SUPE_PRAT_NSENS);
        superficieInverdimento.setSupePerm(DEFAULT_SUPE_PERM);
        superficieInverdimento.setSupeInve(DEFAULT_SUPE_INVE);
    }

    @Test
    @Transactional
    public void createSuperficieInverdimento() throws Exception {
        int databaseSizeBeforeCreate = superficieInverdimentoRepository.findAll().size();

        // Create the SuperficieInverdimento

        restSuperficieInverdimentoMockMvc.perform(post("/api/superficie-inverdimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(superficieInverdimento)))
                .andExpect(status().isCreated());

        // Validate the SuperficieInverdimento in the database
        List<SuperficieInverdimento> superficieInverdimentos = superficieInverdimentoRepository.findAll();
        assertThat(superficieInverdimentos).hasSize(databaseSizeBeforeCreate + 1);
        SuperficieInverdimento testSuperficieInverdimento = superficieInverdimentos.get(superficieInverdimentos.size() - 1);
        assertThat(testSuperficieInverdimento.getSupeSemi()).isEqualTo(DEFAULT_SUPE_SEMI);
        assertThat(testSuperficieInverdimento.getSupePrimColt()).isEqualTo(DEFAULT_SUPE_PRIM_COLT);
        assertThat(testSuperficieInverdimento.getSupeSecoColt()).isEqualTo(DEFAULT_SUPE_SECO_COLT);
        assertThat(testSuperficieInverdimento.getSupeAltrColt()).isEqualTo(DEFAULT_SUPE_ALTR_COLT);
        assertThat(testSuperficieInverdimento.getSupePrimMax()).isEqualTo(DEFAULT_SUPE_PRIM_MAX);
        assertThat(testSuperficieInverdimento.getSupeSecoMax()).isEqualTo(DEFAULT_SUPE_SECO_MAX);
        assertThat(testSuperficieInverdimento.getSupePrimDiff1()).isEqualTo(DEFAULT_SUPE_PRIM_DIFF_1);
        assertThat(testSuperficieInverdimento.getSupePrimDiff2()).isEqualTo(DEFAULT_SUPE_PRIM_DIFF_2);
        assertThat(testSuperficieInverdimento.getTassoDiffPrim()).isEqualTo(DEFAULT_TASSO_DIFF_PRIM);
        assertThat(testSuperficieInverdimento.getSupePrimRidu()).isEqualTo(DEFAULT_SUPE_PRIM_RIDU);
        assertThat(testSuperficieInverdimento.getSupeSecoDiff1()).isEqualTo(DEFAULT_SUPE_SECO_DIFF_1);
        assertThat(testSuperficieInverdimento.getSupeSecoDiff2()).isEqualTo(DEFAULT_SUPE_SECO_DIFF_2);
        assertThat(testSuperficieInverdimento.getTassoDiffSeco()).isEqualTo(DEFAULT_TASSO_DIFF_SECO);
        assertThat(testSuperficieInverdimento.getSupeSecoRidu()).isEqualTo(DEFAULT_SUPE_SECO_RIDU);
        assertThat(testSuperficieInverdimento.getTotaTassoDiff()).isEqualTo(DEFAULT_TOTA_TASSO_DIFF);
        assertThat(testSuperficieInverdimento.getSupeRiduDive()).isEqualTo(DEFAULT_SUPE_RIDU_DIVE);
        assertThat(testSuperficieInverdimento.getSupeEfa()).isEqualTo(DEFAULT_SUPE_EFA);
        assertThat(testSuperficieInverdimento.getSupeEfaObbl()).isEqualTo(DEFAULT_SUPE_EFA_OBBL);
        assertThat(testSuperficieInverdimento.getSupeEfaDiff()).isEqualTo(DEFAULT_SUPE_EFA_DIFF);
        assertThat(testSuperficieInverdimento.getTassoDiffEfa()).isEqualTo(DEFAULT_TASSO_DIFF_EFA);
        assertThat(testSuperficieInverdimento.getSupeRiduEfa()).isEqualTo(DEFAULT_SUPE_RIDU_EFA);
        assertThat(testSuperficieInverdimento.getTotaRidu()).isEqualTo(DEFAULT_TOTA_RIDU);
        assertThat(testSuperficieInverdimento.getSupePagaSemi()).isEqualTo(DEFAULT_SUPE_PAGA_SEMI);
        assertThat(testSuperficieInverdimento.getSupePratSens()).isEqualTo(DEFAULT_SUPE_PRAT_SENS);
        assertThat(testSuperficieInverdimento.getSupePratNsens()).isEqualTo(DEFAULT_SUPE_PRAT_NSENS);
        assertThat(testSuperficieInverdimento.getSupePerm()).isEqualTo(DEFAULT_SUPE_PERM);
        assertThat(testSuperficieInverdimento.getSupeInve()).isEqualTo(DEFAULT_SUPE_INVE);

        // Validate the SuperficieInverdimento in ElasticSearch
        SuperficieInverdimento superficieInverdimentoEs = superficieInverdimentoSearchRepository.findOne(testSuperficieInverdimento.getId());
        assertThat(superficieInverdimentoEs).isEqualToComparingFieldByField(testSuperficieInverdimento);
    }

    @Test
    @Transactional
    public void getAllSuperficieInverdimentos() throws Exception {
        // Initialize the database
        superficieInverdimentoRepository.saveAndFlush(superficieInverdimento);

        // Get all the superficieInverdimentos
        restSuperficieInverdimentoMockMvc.perform(get("/api/superficie-inverdimentos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(superficieInverdimento.getId().intValue())))
                .andExpect(jsonPath("$.[*].supeSemi").value(hasItem(DEFAULT_SUPE_SEMI)))
                .andExpect(jsonPath("$.[*].supePrimColt").value(hasItem(DEFAULT_SUPE_PRIM_COLT)))
                .andExpect(jsonPath("$.[*].supeSecoColt").value(hasItem(DEFAULT_SUPE_SECO_COLT)))
                .andExpect(jsonPath("$.[*].supeAltrColt").value(hasItem(DEFAULT_SUPE_ALTR_COLT)))
                .andExpect(jsonPath("$.[*].supePrimMax").value(hasItem(DEFAULT_SUPE_PRIM_MAX)))
                .andExpect(jsonPath("$.[*].supeSecoMax").value(hasItem(DEFAULT_SUPE_SECO_MAX)))
                .andExpect(jsonPath("$.[*].supePrimDiff1").value(hasItem(DEFAULT_SUPE_PRIM_DIFF_1)))
                .andExpect(jsonPath("$.[*].supePrimDiff2").value(hasItem(DEFAULT_SUPE_PRIM_DIFF_2)))
                .andExpect(jsonPath("$.[*].tassoDiffPrim").value(hasItem(DEFAULT_TASSO_DIFF_PRIM.doubleValue())))
                .andExpect(jsonPath("$.[*].supePrimRidu").value(hasItem(DEFAULT_SUPE_PRIM_RIDU)))
                .andExpect(jsonPath("$.[*].supeSecoDiff1").value(hasItem(DEFAULT_SUPE_SECO_DIFF_1)))
                .andExpect(jsonPath("$.[*].supeSecoDiff2").value(hasItem(DEFAULT_SUPE_SECO_DIFF_2)))
                .andExpect(jsonPath("$.[*].tassoDiffSeco").value(hasItem(DEFAULT_TASSO_DIFF_SECO.doubleValue())))
                .andExpect(jsonPath("$.[*].supeSecoRidu").value(hasItem(DEFAULT_SUPE_SECO_RIDU)))
                .andExpect(jsonPath("$.[*].totaTassoDiff").value(hasItem(DEFAULT_TOTA_TASSO_DIFF.doubleValue())))
                .andExpect(jsonPath("$.[*].supeRiduDive").value(hasItem(DEFAULT_SUPE_RIDU_DIVE)))
                .andExpect(jsonPath("$.[*].supeEfa").value(hasItem(DEFAULT_SUPE_EFA)))
                .andExpect(jsonPath("$.[*].supeEfaObbl").value(hasItem(DEFAULT_SUPE_EFA_OBBL)))
                .andExpect(jsonPath("$.[*].supeEfaDiff").value(hasItem(DEFAULT_SUPE_EFA_DIFF)))
                .andExpect(jsonPath("$.[*].tassoDiffEfa").value(hasItem(DEFAULT_TASSO_DIFF_EFA.doubleValue())))
                .andExpect(jsonPath("$.[*].supeRiduEfa").value(hasItem(DEFAULT_SUPE_RIDU_EFA)))
                .andExpect(jsonPath("$.[*].totaRidu").value(hasItem(DEFAULT_TOTA_RIDU)))
                .andExpect(jsonPath("$.[*].supePagaSemi").value(hasItem(DEFAULT_SUPE_PAGA_SEMI)))
                .andExpect(jsonPath("$.[*].supePratSens").value(hasItem(DEFAULT_SUPE_PRAT_SENS)))
                .andExpect(jsonPath("$.[*].supePratNsens").value(hasItem(DEFAULT_SUPE_PRAT_NSENS)))
                .andExpect(jsonPath("$.[*].supePerm").value(hasItem(DEFAULT_SUPE_PERM)))
                .andExpect(jsonPath("$.[*].supeInve").value(hasItem(DEFAULT_SUPE_INVE)));
    }

    @Test
    @Transactional
    public void getSuperficieInverdimento() throws Exception {
        // Initialize the database
        superficieInverdimentoRepository.saveAndFlush(superficieInverdimento);

        // Get the superficieInverdimento
        restSuperficieInverdimentoMockMvc.perform(get("/api/superficie-inverdimentos/{id}", superficieInverdimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(superficieInverdimento.getId().intValue()))
            .andExpect(jsonPath("$.supeSemi").value(DEFAULT_SUPE_SEMI))
            .andExpect(jsonPath("$.supePrimColt").value(DEFAULT_SUPE_PRIM_COLT))
            .andExpect(jsonPath("$.supeSecoColt").value(DEFAULT_SUPE_SECO_COLT))
            .andExpect(jsonPath("$.supeAltrColt").value(DEFAULT_SUPE_ALTR_COLT))
            .andExpect(jsonPath("$.supePrimMax").value(DEFAULT_SUPE_PRIM_MAX))
            .andExpect(jsonPath("$.supeSecoMax").value(DEFAULT_SUPE_SECO_MAX))
            .andExpect(jsonPath("$.supePrimDiff1").value(DEFAULT_SUPE_PRIM_DIFF_1))
            .andExpect(jsonPath("$.supePrimDiff2").value(DEFAULT_SUPE_PRIM_DIFF_2))
            .andExpect(jsonPath("$.tassoDiffPrim").value(DEFAULT_TASSO_DIFF_PRIM.doubleValue()))
            .andExpect(jsonPath("$.supePrimRidu").value(DEFAULT_SUPE_PRIM_RIDU))
            .andExpect(jsonPath("$.supeSecoDiff1").value(DEFAULT_SUPE_SECO_DIFF_1))
            .andExpect(jsonPath("$.supeSecoDiff2").value(DEFAULT_SUPE_SECO_DIFF_2))
            .andExpect(jsonPath("$.tassoDiffSeco").value(DEFAULT_TASSO_DIFF_SECO.doubleValue()))
            .andExpect(jsonPath("$.supeSecoRidu").value(DEFAULT_SUPE_SECO_RIDU))
            .andExpect(jsonPath("$.totaTassoDiff").value(DEFAULT_TOTA_TASSO_DIFF.doubleValue()))
            .andExpect(jsonPath("$.supeRiduDive").value(DEFAULT_SUPE_RIDU_DIVE))
            .andExpect(jsonPath("$.supeEfa").value(DEFAULT_SUPE_EFA))
            .andExpect(jsonPath("$.supeEfaObbl").value(DEFAULT_SUPE_EFA_OBBL))
            .andExpect(jsonPath("$.supeEfaDiff").value(DEFAULT_SUPE_EFA_DIFF))
            .andExpect(jsonPath("$.tassoDiffEfa").value(DEFAULT_TASSO_DIFF_EFA.doubleValue()))
            .andExpect(jsonPath("$.supeRiduEfa").value(DEFAULT_SUPE_RIDU_EFA))
            .andExpect(jsonPath("$.totaRidu").value(DEFAULT_TOTA_RIDU))
            .andExpect(jsonPath("$.supePagaSemi").value(DEFAULT_SUPE_PAGA_SEMI))
            .andExpect(jsonPath("$.supePratSens").value(DEFAULT_SUPE_PRAT_SENS))
            .andExpect(jsonPath("$.supePratNsens").value(DEFAULT_SUPE_PRAT_NSENS))
            .andExpect(jsonPath("$.supePerm").value(DEFAULT_SUPE_PERM))
            .andExpect(jsonPath("$.supeInve").value(DEFAULT_SUPE_INVE));
    }

    @Test
    @Transactional
    public void getNonExistingSuperficieInverdimento() throws Exception {
        // Get the superficieInverdimento
        restSuperficieInverdimentoMockMvc.perform(get("/api/superficie-inverdimentos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuperficieInverdimento() throws Exception {
        // Initialize the database
        superficieInverdimentoRepository.saveAndFlush(superficieInverdimento);
        superficieInverdimentoSearchRepository.save(superficieInverdimento);
        int databaseSizeBeforeUpdate = superficieInverdimentoRepository.findAll().size();

        // Update the superficieInverdimento
        SuperficieInverdimento updatedSuperficieInverdimento = new SuperficieInverdimento();
        updatedSuperficieInverdimento.setId(superficieInverdimento.getId());
        updatedSuperficieInverdimento.setSupeSemi(UPDATED_SUPE_SEMI);
        updatedSuperficieInverdimento.setSupePrimColt(UPDATED_SUPE_PRIM_COLT);
        updatedSuperficieInverdimento.setSupeSecoColt(UPDATED_SUPE_SECO_COLT);
        updatedSuperficieInverdimento.setSupeAltrColt(UPDATED_SUPE_ALTR_COLT);
        updatedSuperficieInverdimento.setSupePrimMax(UPDATED_SUPE_PRIM_MAX);
        updatedSuperficieInverdimento.setSupeSecoMax(UPDATED_SUPE_SECO_MAX);
        updatedSuperficieInverdimento.setSupePrimDiff1(UPDATED_SUPE_PRIM_DIFF_1);
        updatedSuperficieInverdimento.setSupePrimDiff2(UPDATED_SUPE_PRIM_DIFF_2);
        updatedSuperficieInverdimento.setTassoDiffPrim(UPDATED_TASSO_DIFF_PRIM);
        updatedSuperficieInverdimento.setSupePrimRidu(UPDATED_SUPE_PRIM_RIDU);
        updatedSuperficieInverdimento.setSupeSecoDiff1(UPDATED_SUPE_SECO_DIFF_1);
        updatedSuperficieInverdimento.setSupeSecoDiff2(UPDATED_SUPE_SECO_DIFF_2);
        updatedSuperficieInverdimento.setTassoDiffSeco(UPDATED_TASSO_DIFF_SECO);
        updatedSuperficieInverdimento.setSupeSecoRidu(UPDATED_SUPE_SECO_RIDU);
        updatedSuperficieInverdimento.setTotaTassoDiff(UPDATED_TOTA_TASSO_DIFF);
        updatedSuperficieInverdimento.setSupeRiduDive(UPDATED_SUPE_RIDU_DIVE);
        updatedSuperficieInverdimento.setSupeEfa(UPDATED_SUPE_EFA);
        updatedSuperficieInverdimento.setSupeEfaObbl(UPDATED_SUPE_EFA_OBBL);
        updatedSuperficieInverdimento.setSupeEfaDiff(UPDATED_SUPE_EFA_DIFF);
        updatedSuperficieInverdimento.setTassoDiffEfa(UPDATED_TASSO_DIFF_EFA);
        updatedSuperficieInverdimento.setSupeRiduEfa(UPDATED_SUPE_RIDU_EFA);
        updatedSuperficieInverdimento.setTotaRidu(UPDATED_TOTA_RIDU);
        updatedSuperficieInverdimento.setSupePagaSemi(UPDATED_SUPE_PAGA_SEMI);
        updatedSuperficieInverdimento.setSupePratSens(UPDATED_SUPE_PRAT_SENS);
        updatedSuperficieInverdimento.setSupePratNsens(UPDATED_SUPE_PRAT_NSENS);
        updatedSuperficieInverdimento.setSupePerm(UPDATED_SUPE_PERM);
        updatedSuperficieInverdimento.setSupeInve(UPDATED_SUPE_INVE);

        restSuperficieInverdimentoMockMvc.perform(put("/api/superficie-inverdimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSuperficieInverdimento)))
                .andExpect(status().isOk());

        // Validate the SuperficieInverdimento in the database
        List<SuperficieInverdimento> superficieInverdimentos = superficieInverdimentoRepository.findAll();
        assertThat(superficieInverdimentos).hasSize(databaseSizeBeforeUpdate);
        SuperficieInverdimento testSuperficieInverdimento = superficieInverdimentos.get(superficieInverdimentos.size() - 1);
        assertThat(testSuperficieInverdimento.getSupeSemi()).isEqualTo(UPDATED_SUPE_SEMI);
        assertThat(testSuperficieInverdimento.getSupePrimColt()).isEqualTo(UPDATED_SUPE_PRIM_COLT);
        assertThat(testSuperficieInverdimento.getSupeSecoColt()).isEqualTo(UPDATED_SUPE_SECO_COLT);
        assertThat(testSuperficieInverdimento.getSupeAltrColt()).isEqualTo(UPDATED_SUPE_ALTR_COLT);
        assertThat(testSuperficieInverdimento.getSupePrimMax()).isEqualTo(UPDATED_SUPE_PRIM_MAX);
        assertThat(testSuperficieInverdimento.getSupeSecoMax()).isEqualTo(UPDATED_SUPE_SECO_MAX);
        assertThat(testSuperficieInverdimento.getSupePrimDiff1()).isEqualTo(UPDATED_SUPE_PRIM_DIFF_1);
        assertThat(testSuperficieInverdimento.getSupePrimDiff2()).isEqualTo(UPDATED_SUPE_PRIM_DIFF_2);
        assertThat(testSuperficieInverdimento.getTassoDiffPrim()).isEqualTo(UPDATED_TASSO_DIFF_PRIM);
        assertThat(testSuperficieInverdimento.getSupePrimRidu()).isEqualTo(UPDATED_SUPE_PRIM_RIDU);
        assertThat(testSuperficieInverdimento.getSupeSecoDiff1()).isEqualTo(UPDATED_SUPE_SECO_DIFF_1);
        assertThat(testSuperficieInverdimento.getSupeSecoDiff2()).isEqualTo(UPDATED_SUPE_SECO_DIFF_2);
        assertThat(testSuperficieInverdimento.getTassoDiffSeco()).isEqualTo(UPDATED_TASSO_DIFF_SECO);
        assertThat(testSuperficieInverdimento.getSupeSecoRidu()).isEqualTo(UPDATED_SUPE_SECO_RIDU);
        assertThat(testSuperficieInverdimento.getTotaTassoDiff()).isEqualTo(UPDATED_TOTA_TASSO_DIFF);
        assertThat(testSuperficieInverdimento.getSupeRiduDive()).isEqualTo(UPDATED_SUPE_RIDU_DIVE);
        assertThat(testSuperficieInverdimento.getSupeEfa()).isEqualTo(UPDATED_SUPE_EFA);
        assertThat(testSuperficieInverdimento.getSupeEfaObbl()).isEqualTo(UPDATED_SUPE_EFA_OBBL);
        assertThat(testSuperficieInverdimento.getSupeEfaDiff()).isEqualTo(UPDATED_SUPE_EFA_DIFF);
        assertThat(testSuperficieInverdimento.getTassoDiffEfa()).isEqualTo(UPDATED_TASSO_DIFF_EFA);
        assertThat(testSuperficieInverdimento.getSupeRiduEfa()).isEqualTo(UPDATED_SUPE_RIDU_EFA);
        assertThat(testSuperficieInverdimento.getTotaRidu()).isEqualTo(UPDATED_TOTA_RIDU);
        assertThat(testSuperficieInverdimento.getSupePagaSemi()).isEqualTo(UPDATED_SUPE_PAGA_SEMI);
        assertThat(testSuperficieInverdimento.getSupePratSens()).isEqualTo(UPDATED_SUPE_PRAT_SENS);
        assertThat(testSuperficieInverdimento.getSupePratNsens()).isEqualTo(UPDATED_SUPE_PRAT_NSENS);
        assertThat(testSuperficieInverdimento.getSupePerm()).isEqualTo(UPDATED_SUPE_PERM);
        assertThat(testSuperficieInverdimento.getSupeInve()).isEqualTo(UPDATED_SUPE_INVE);

        // Validate the SuperficieInverdimento in ElasticSearch
        SuperficieInverdimento superficieInverdimentoEs = superficieInverdimentoSearchRepository.findOne(testSuperficieInverdimento.getId());
        assertThat(superficieInverdimentoEs).isEqualToComparingFieldByField(testSuperficieInverdimento);
    }

    @Test
    @Transactional
    public void deleteSuperficieInverdimento() throws Exception {
        // Initialize the database
        superficieInverdimentoRepository.saveAndFlush(superficieInverdimento);
        superficieInverdimentoSearchRepository.save(superficieInverdimento);
        int databaseSizeBeforeDelete = superficieInverdimentoRepository.findAll().size();

        // Get the superficieInverdimento
        restSuperficieInverdimentoMockMvc.perform(delete("/api/superficie-inverdimentos/{id}", superficieInverdimento.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean superficieInverdimentoExistsInEs = superficieInverdimentoSearchRepository.exists(superficieInverdimento.getId());
        assertThat(superficieInverdimentoExistsInEs).isFalse();

        // Validate the database is empty
        List<SuperficieInverdimento> superficieInverdimentos = superficieInverdimentoRepository.findAll();
        assertThat(superficieInverdimentos).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSuperficieInverdimento() throws Exception {
        // Initialize the database
        superficieInverdimentoRepository.saveAndFlush(superficieInverdimento);
        superficieInverdimentoSearchRepository.save(superficieInverdimento);

        // Search the superficieInverdimento
        restSuperficieInverdimentoMockMvc.perform(get("/api/_search/superficie-inverdimentos?query=id:" + superficieInverdimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(superficieInverdimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].supeSemi").value(hasItem(DEFAULT_SUPE_SEMI)))
            .andExpect(jsonPath("$.[*].supePrimColt").value(hasItem(DEFAULT_SUPE_PRIM_COLT)))
            .andExpect(jsonPath("$.[*].supeSecoColt").value(hasItem(DEFAULT_SUPE_SECO_COLT)))
            .andExpect(jsonPath("$.[*].supeAltrColt").value(hasItem(DEFAULT_SUPE_ALTR_COLT)))
            .andExpect(jsonPath("$.[*].supePrimMax").value(hasItem(DEFAULT_SUPE_PRIM_MAX)))
            .andExpect(jsonPath("$.[*].supeSecoMax").value(hasItem(DEFAULT_SUPE_SECO_MAX)))
            .andExpect(jsonPath("$.[*].supePrimDiff1").value(hasItem(DEFAULT_SUPE_PRIM_DIFF_1)))
            .andExpect(jsonPath("$.[*].supePrimDiff2").value(hasItem(DEFAULT_SUPE_PRIM_DIFF_2)))
            .andExpect(jsonPath("$.[*].tassoDiffPrim").value(hasItem(DEFAULT_TASSO_DIFF_PRIM.doubleValue())))
            .andExpect(jsonPath("$.[*].supePrimRidu").value(hasItem(DEFAULT_SUPE_PRIM_RIDU)))
            .andExpect(jsonPath("$.[*].supeSecoDiff1").value(hasItem(DEFAULT_SUPE_SECO_DIFF_1)))
            .andExpect(jsonPath("$.[*].supeSecoDiff2").value(hasItem(DEFAULT_SUPE_SECO_DIFF_2)))
            .andExpect(jsonPath("$.[*].tassoDiffSeco").value(hasItem(DEFAULT_TASSO_DIFF_SECO.doubleValue())))
            .andExpect(jsonPath("$.[*].supeSecoRidu").value(hasItem(DEFAULT_SUPE_SECO_RIDU)))
            .andExpect(jsonPath("$.[*].totaTassoDiff").value(hasItem(DEFAULT_TOTA_TASSO_DIFF.doubleValue())))
            .andExpect(jsonPath("$.[*].supeRiduDive").value(hasItem(DEFAULT_SUPE_RIDU_DIVE)))
            .andExpect(jsonPath("$.[*].supeEfa").value(hasItem(DEFAULT_SUPE_EFA)))
            .andExpect(jsonPath("$.[*].supeEfaObbl").value(hasItem(DEFAULT_SUPE_EFA_OBBL)))
            .andExpect(jsonPath("$.[*].supeEfaDiff").value(hasItem(DEFAULT_SUPE_EFA_DIFF)))
            .andExpect(jsonPath("$.[*].tassoDiffEfa").value(hasItem(DEFAULT_TASSO_DIFF_EFA.doubleValue())))
            .andExpect(jsonPath("$.[*].supeRiduEfa").value(hasItem(DEFAULT_SUPE_RIDU_EFA)))
            .andExpect(jsonPath("$.[*].totaRidu").value(hasItem(DEFAULT_TOTA_RIDU)))
            .andExpect(jsonPath("$.[*].supePagaSemi").value(hasItem(DEFAULT_SUPE_PAGA_SEMI)))
            .andExpect(jsonPath("$.[*].supePratSens").value(hasItem(DEFAULT_SUPE_PRAT_SENS)))
            .andExpect(jsonPath("$.[*].supePratNsens").value(hasItem(DEFAULT_SUPE_PRAT_NSENS)))
            .andExpect(jsonPath("$.[*].supePerm").value(hasItem(DEFAULT_SUPE_PERM)))
            .andExpect(jsonPath("$.[*].supeInve").value(hasItem(DEFAULT_SUPE_INVE)));
    }
}
