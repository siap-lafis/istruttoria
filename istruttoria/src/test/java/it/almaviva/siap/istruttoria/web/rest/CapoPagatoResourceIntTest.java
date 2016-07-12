package it.almaviva.siap.istruttoria.web.rest;

import it.almaviva.siap.istruttoria.IstruttoriaApp;
import it.almaviva.siap.istruttoria.domain.CapoPagato;
import it.almaviva.siap.istruttoria.repository.CapoPagatoRepository;
import it.almaviva.siap.istruttoria.repository.search.CapoPagatoSearchRepository;

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
 * Test class for the CapoPagatoResource REST controller.
 *
 * @see CapoPagatoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstruttoriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class CapoPagatoResourceIntTest {

    private static final String DEFAULT_MARCA_CAPO = "AAAAA";
    private static final String UPDATED_MARCA_CAPO = "BBBBB";
    private static final String DEFAULT_AMMISSIBILE = "AAAAA";
    private static final String UPDATED_AMMISSIBILE = "BBBBB";

    private static final Integer DEFAULT_NUM_UBA = 1;
    private static final Integer UPDATED_NUM_UBA = 2;
    private static final String DEFAULT_MANCANZA_ANALISI_LATTE = "AAAAA";
    private static final String UPDATED_MANCANZA_ANALISI_LATTE = "BBBBB";

    private static final Integer DEFAULT_MEDIE_LATTE_SOMA = 1;
    private static final Integer UPDATED_MEDIE_LATTE_SOMA = 2;

    private static final Integer DEFAULT_MEDIE_LATTE_GERM = 1;
    private static final Integer UPDATED_MEDIE_LATTE_GERM = 2;

    private static final Float DEFAULT_MEDIE_LATTE_PROT = 1F;
    private static final Float UPDATED_MEDIE_LATTE_PROT = 2F;
    private static final String DEFAULT_COD_ASL = "AAAAA";
    private static final String UPDATED_COD_ASL = "BBBBB";
    private static final String DEFAULT_FLAG_SESS = "AAAAA";
    private static final String UPDATED_FLAG_SESS = "BBBBB";
    private static final String DEFAULT_DATA_NASC = "AAAAA";
    private static final String UPDATED_DATA_NASC = "BBBBB";
    private static final String DEFAULT_CODI_RAZZ = "AAAAA";
    private static final String UPDATED_CODI_RAZZ = "BBBBB";
    private static final String DEFAULT_DATA_INIZ_DETE = "AAAAA";
    private static final String UPDATED_DATA_INIZ_DETE = "BBBBB";
    private static final String DEFAULT_DATA_FINE_DETE = "AAAAA";
    private static final String UPDATED_DATA_FINE_DETE = "BBBBB";

    @Inject
    private CapoPagatoRepository capoPagatoRepository;

    @Inject
    private CapoPagatoSearchRepository capoPagatoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCapoPagatoMockMvc;

    private CapoPagato capoPagato;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CapoPagatoResource capoPagatoResource = new CapoPagatoResource();
        ReflectionTestUtils.setField(capoPagatoResource, "capoPagatoSearchRepository", capoPagatoSearchRepository);
        ReflectionTestUtils.setField(capoPagatoResource, "capoPagatoRepository", capoPagatoRepository);
        this.restCapoPagatoMockMvc = MockMvcBuilders.standaloneSetup(capoPagatoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        capoPagatoSearchRepository.deleteAll();
        capoPagato = new CapoPagato();
        capoPagato.setMarcaCapo(DEFAULT_MARCA_CAPO);
        capoPagato.setAmmissibile(DEFAULT_AMMISSIBILE);
        capoPagato.setNumUba(DEFAULT_NUM_UBA);
        capoPagato.setMancanzaAnalisiLatte(DEFAULT_MANCANZA_ANALISI_LATTE);
        capoPagato.setMedieLatteSoma(DEFAULT_MEDIE_LATTE_SOMA);
        capoPagato.setMedieLatteGerm(DEFAULT_MEDIE_LATTE_GERM);
        capoPagato.setMedieLatteProt(DEFAULT_MEDIE_LATTE_PROT);
        capoPagato.setCodAsl(DEFAULT_COD_ASL);
        capoPagato.setFlagSess(DEFAULT_FLAG_SESS);
        capoPagato.setDataNasc(DEFAULT_DATA_NASC);
        capoPagato.setCodiRazz(DEFAULT_CODI_RAZZ);
        capoPagato.setDataInizDete(DEFAULT_DATA_INIZ_DETE);
        capoPagato.setDataFineDete(DEFAULT_DATA_FINE_DETE);
    }

    @Test
    @Transactional
    public void createCapoPagato() throws Exception {
        int databaseSizeBeforeCreate = capoPagatoRepository.findAll().size();

        // Create the CapoPagato

        restCapoPagatoMockMvc.perform(post("/api/capo-pagatoes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(capoPagato)))
                .andExpect(status().isCreated());

        // Validate the CapoPagato in the database
        List<CapoPagato> capoPagatoes = capoPagatoRepository.findAll();
        assertThat(capoPagatoes).hasSize(databaseSizeBeforeCreate + 1);
        CapoPagato testCapoPagato = capoPagatoes.get(capoPagatoes.size() - 1);
        assertThat(testCapoPagato.getMarcaCapo()).isEqualTo(DEFAULT_MARCA_CAPO);
        assertThat(testCapoPagato.getAmmissibile()).isEqualTo(DEFAULT_AMMISSIBILE);
        assertThat(testCapoPagato.getNumUba()).isEqualTo(DEFAULT_NUM_UBA);
        assertThat(testCapoPagato.getMancanzaAnalisiLatte()).isEqualTo(DEFAULT_MANCANZA_ANALISI_LATTE);
        assertThat(testCapoPagato.getMedieLatteSoma()).isEqualTo(DEFAULT_MEDIE_LATTE_SOMA);
        assertThat(testCapoPagato.getMedieLatteGerm()).isEqualTo(DEFAULT_MEDIE_LATTE_GERM);
        assertThat(testCapoPagato.getMedieLatteProt()).isEqualTo(DEFAULT_MEDIE_LATTE_PROT);
        assertThat(testCapoPagato.getCodAsl()).isEqualTo(DEFAULT_COD_ASL);
        assertThat(testCapoPagato.getFlagSess()).isEqualTo(DEFAULT_FLAG_SESS);
        assertThat(testCapoPagato.getDataNasc()).isEqualTo(DEFAULT_DATA_NASC);
        assertThat(testCapoPagato.getCodiRazz()).isEqualTo(DEFAULT_CODI_RAZZ);
        assertThat(testCapoPagato.getDataInizDete()).isEqualTo(DEFAULT_DATA_INIZ_DETE);
        assertThat(testCapoPagato.getDataFineDete()).isEqualTo(DEFAULT_DATA_FINE_DETE);

        // Validate the CapoPagato in ElasticSearch
        CapoPagato capoPagatoEs = capoPagatoSearchRepository.findOne(testCapoPagato.getId());
        assertThat(capoPagatoEs).isEqualToComparingFieldByField(testCapoPagato);
    }

    @Test
    @Transactional
    public void getAllCapoPagatoes() throws Exception {
        // Initialize the database
        capoPagatoRepository.saveAndFlush(capoPagato);

        // Get all the capoPagatoes
        restCapoPagatoMockMvc.perform(get("/api/capo-pagatoes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(capoPagato.getId().intValue())))
                .andExpect(jsonPath("$.[*].marcaCapo").value(hasItem(DEFAULT_MARCA_CAPO.toString())))
                .andExpect(jsonPath("$.[*].ammissibile").value(hasItem(DEFAULT_AMMISSIBILE.toString())))
                .andExpect(jsonPath("$.[*].numUba").value(hasItem(DEFAULT_NUM_UBA)))
                .andExpect(jsonPath("$.[*].mancanzaAnalisiLatte").value(hasItem(DEFAULT_MANCANZA_ANALISI_LATTE.toString())))
                .andExpect(jsonPath("$.[*].medieLatteSoma").value(hasItem(DEFAULT_MEDIE_LATTE_SOMA)))
                .andExpect(jsonPath("$.[*].medieLatteGerm").value(hasItem(DEFAULT_MEDIE_LATTE_GERM)))
                .andExpect(jsonPath("$.[*].medieLatteProt").value(hasItem(DEFAULT_MEDIE_LATTE_PROT.doubleValue())))
                .andExpect(jsonPath("$.[*].codAsl").value(hasItem(DEFAULT_COD_ASL.toString())))
                .andExpect(jsonPath("$.[*].flagSess").value(hasItem(DEFAULT_FLAG_SESS.toString())))
                .andExpect(jsonPath("$.[*].dataNasc").value(hasItem(DEFAULT_DATA_NASC.toString())))
                .andExpect(jsonPath("$.[*].codiRazz").value(hasItem(DEFAULT_CODI_RAZZ.toString())))
                .andExpect(jsonPath("$.[*].dataInizDete").value(hasItem(DEFAULT_DATA_INIZ_DETE.toString())))
                .andExpect(jsonPath("$.[*].dataFineDete").value(hasItem(DEFAULT_DATA_FINE_DETE.toString())));
    }

    @Test
    @Transactional
    public void getCapoPagato() throws Exception {
        // Initialize the database
        capoPagatoRepository.saveAndFlush(capoPagato);

        // Get the capoPagato
        restCapoPagatoMockMvc.perform(get("/api/capo-pagatoes/{id}", capoPagato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(capoPagato.getId().intValue()))
            .andExpect(jsonPath("$.marcaCapo").value(DEFAULT_MARCA_CAPO.toString()))
            .andExpect(jsonPath("$.ammissibile").value(DEFAULT_AMMISSIBILE.toString()))
            .andExpect(jsonPath("$.numUba").value(DEFAULT_NUM_UBA))
            .andExpect(jsonPath("$.mancanzaAnalisiLatte").value(DEFAULT_MANCANZA_ANALISI_LATTE.toString()))
            .andExpect(jsonPath("$.medieLatteSoma").value(DEFAULT_MEDIE_LATTE_SOMA))
            .andExpect(jsonPath("$.medieLatteGerm").value(DEFAULT_MEDIE_LATTE_GERM))
            .andExpect(jsonPath("$.medieLatteProt").value(DEFAULT_MEDIE_LATTE_PROT.doubleValue()))
            .andExpect(jsonPath("$.codAsl").value(DEFAULT_COD_ASL.toString()))
            .andExpect(jsonPath("$.flagSess").value(DEFAULT_FLAG_SESS.toString()))
            .andExpect(jsonPath("$.dataNasc").value(DEFAULT_DATA_NASC.toString()))
            .andExpect(jsonPath("$.codiRazz").value(DEFAULT_CODI_RAZZ.toString()))
            .andExpect(jsonPath("$.dataInizDete").value(DEFAULT_DATA_INIZ_DETE.toString()))
            .andExpect(jsonPath("$.dataFineDete").value(DEFAULT_DATA_FINE_DETE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCapoPagato() throws Exception {
        // Get the capoPagato
        restCapoPagatoMockMvc.perform(get("/api/capo-pagatoes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCapoPagato() throws Exception {
        // Initialize the database
        capoPagatoRepository.saveAndFlush(capoPagato);
        capoPagatoSearchRepository.save(capoPagato);
        int databaseSizeBeforeUpdate = capoPagatoRepository.findAll().size();

        // Update the capoPagato
        CapoPagato updatedCapoPagato = new CapoPagato();
        updatedCapoPagato.setId(capoPagato.getId());
        updatedCapoPagato.setMarcaCapo(UPDATED_MARCA_CAPO);
        updatedCapoPagato.setAmmissibile(UPDATED_AMMISSIBILE);
        updatedCapoPagato.setNumUba(UPDATED_NUM_UBA);
        updatedCapoPagato.setMancanzaAnalisiLatte(UPDATED_MANCANZA_ANALISI_LATTE);
        updatedCapoPagato.setMedieLatteSoma(UPDATED_MEDIE_LATTE_SOMA);
        updatedCapoPagato.setMedieLatteGerm(UPDATED_MEDIE_LATTE_GERM);
        updatedCapoPagato.setMedieLatteProt(UPDATED_MEDIE_LATTE_PROT);
        updatedCapoPagato.setCodAsl(UPDATED_COD_ASL);
        updatedCapoPagato.setFlagSess(UPDATED_FLAG_SESS);
        updatedCapoPagato.setDataNasc(UPDATED_DATA_NASC);
        updatedCapoPagato.setCodiRazz(UPDATED_CODI_RAZZ);
        updatedCapoPagato.setDataInizDete(UPDATED_DATA_INIZ_DETE);
        updatedCapoPagato.setDataFineDete(UPDATED_DATA_FINE_DETE);

        restCapoPagatoMockMvc.perform(put("/api/capo-pagatoes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCapoPagato)))
                .andExpect(status().isOk());

        // Validate the CapoPagato in the database
        List<CapoPagato> capoPagatoes = capoPagatoRepository.findAll();
        assertThat(capoPagatoes).hasSize(databaseSizeBeforeUpdate);
        CapoPagato testCapoPagato = capoPagatoes.get(capoPagatoes.size() - 1);
        assertThat(testCapoPagato.getMarcaCapo()).isEqualTo(UPDATED_MARCA_CAPO);
        assertThat(testCapoPagato.getAmmissibile()).isEqualTo(UPDATED_AMMISSIBILE);
        assertThat(testCapoPagato.getNumUba()).isEqualTo(UPDATED_NUM_UBA);
        assertThat(testCapoPagato.getMancanzaAnalisiLatte()).isEqualTo(UPDATED_MANCANZA_ANALISI_LATTE);
        assertThat(testCapoPagato.getMedieLatteSoma()).isEqualTo(UPDATED_MEDIE_LATTE_SOMA);
        assertThat(testCapoPagato.getMedieLatteGerm()).isEqualTo(UPDATED_MEDIE_LATTE_GERM);
        assertThat(testCapoPagato.getMedieLatteProt()).isEqualTo(UPDATED_MEDIE_LATTE_PROT);
        assertThat(testCapoPagato.getCodAsl()).isEqualTo(UPDATED_COD_ASL);
        assertThat(testCapoPagato.getFlagSess()).isEqualTo(UPDATED_FLAG_SESS);
        assertThat(testCapoPagato.getDataNasc()).isEqualTo(UPDATED_DATA_NASC);
        assertThat(testCapoPagato.getCodiRazz()).isEqualTo(UPDATED_CODI_RAZZ);
        assertThat(testCapoPagato.getDataInizDete()).isEqualTo(UPDATED_DATA_INIZ_DETE);
        assertThat(testCapoPagato.getDataFineDete()).isEqualTo(UPDATED_DATA_FINE_DETE);

        // Validate the CapoPagato in ElasticSearch
        CapoPagato capoPagatoEs = capoPagatoSearchRepository.findOne(testCapoPagato.getId());
        assertThat(capoPagatoEs).isEqualToComparingFieldByField(testCapoPagato);
    }

    @Test
    @Transactional
    public void deleteCapoPagato() throws Exception {
        // Initialize the database
        capoPagatoRepository.saveAndFlush(capoPagato);
        capoPagatoSearchRepository.save(capoPagato);
        int databaseSizeBeforeDelete = capoPagatoRepository.findAll().size();

        // Get the capoPagato
        restCapoPagatoMockMvc.perform(delete("/api/capo-pagatoes/{id}", capoPagato.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean capoPagatoExistsInEs = capoPagatoSearchRepository.exists(capoPagato.getId());
        assertThat(capoPagatoExistsInEs).isFalse();

        // Validate the database is empty
        List<CapoPagato> capoPagatoes = capoPagatoRepository.findAll();
        assertThat(capoPagatoes).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCapoPagato() throws Exception {
        // Initialize the database
        capoPagatoRepository.saveAndFlush(capoPagato);
        capoPagatoSearchRepository.save(capoPagato);

        // Search the capoPagato
        restCapoPagatoMockMvc.perform(get("/api/_search/capo-pagatoes?query=id:" + capoPagato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(capoPagato.getId().intValue())))
            .andExpect(jsonPath("$.[*].marcaCapo").value(hasItem(DEFAULT_MARCA_CAPO.toString())))
            .andExpect(jsonPath("$.[*].ammissibile").value(hasItem(DEFAULT_AMMISSIBILE.toString())))
            .andExpect(jsonPath("$.[*].numUba").value(hasItem(DEFAULT_NUM_UBA)))
            .andExpect(jsonPath("$.[*].mancanzaAnalisiLatte").value(hasItem(DEFAULT_MANCANZA_ANALISI_LATTE.toString())))
            .andExpect(jsonPath("$.[*].medieLatteSoma").value(hasItem(DEFAULT_MEDIE_LATTE_SOMA)))
            .andExpect(jsonPath("$.[*].medieLatteGerm").value(hasItem(DEFAULT_MEDIE_LATTE_GERM)))
            .andExpect(jsonPath("$.[*].medieLatteProt").value(hasItem(DEFAULT_MEDIE_LATTE_PROT.doubleValue())))
            .andExpect(jsonPath("$.[*].codAsl").value(hasItem(DEFAULT_COD_ASL.toString())))
            .andExpect(jsonPath("$.[*].flagSess").value(hasItem(DEFAULT_FLAG_SESS.toString())))
            .andExpect(jsonPath("$.[*].dataNasc").value(hasItem(DEFAULT_DATA_NASC.toString())))
            .andExpect(jsonPath("$.[*].codiRazz").value(hasItem(DEFAULT_CODI_RAZZ.toString())))
            .andExpect(jsonPath("$.[*].dataInizDete").value(hasItem(DEFAULT_DATA_INIZ_DETE.toString())))
            .andExpect(jsonPath("$.[*].dataFineDete").value(hasItem(DEFAULT_DATA_FINE_DETE.toString())));
    }
}
