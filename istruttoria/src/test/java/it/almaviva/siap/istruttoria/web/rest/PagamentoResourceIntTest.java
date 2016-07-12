package it.almaviva.siap.istruttoria.web.rest;

import it.almaviva.siap.istruttoria.IstruttoriaApp;
import it.almaviva.siap.istruttoria.domain.Pagamento;
import it.almaviva.siap.istruttoria.repository.PagamentoRepository;
import it.almaviva.siap.istruttoria.repository.search.PagamentoSearchRepository;

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
 * Test class for the PagamentoResource REST controller.
 *
 * @see PagamentoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstruttoriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class PagamentoResourceIntTest {

    private static final String DEFAULT_COD_INTERVENTO = "AAAAA";
    private static final String UPDATED_COD_INTERVENTO = "BBBBB";

    private static final Float DEFAULT_QNTA_DICH = 1F;
    private static final Float UPDATED_QNTA_DICH = 2F;

    private static final Float DEFAULT_QNTA_AMME = 1F;
    private static final Float UPDATED_QNTA_AMME = 2F;

    private static final Float DEFAULT_QNTA_LIQU = 1F;
    private static final Float UPDATED_QNTA_LIQU = 2F;

    private static final Float DEFAULT_IMPO_DICH = 1F;
    private static final Float UPDATED_IMPO_DICH = 2F;

    private static final Float DEFAULT_IMPO_AMME = 1F;
    private static final Float UPDATED_IMPO_AMME = 2F;

    private static final Float DEFAULT_IMPO_LIQU = 1F;
    private static final Float UPDATED_IMPO_LIQU = 2F;

    private static final Integer DEFAULT_STAT_LIQU = 1;
    private static final Integer UPDATED_STAT_LIQU = 2;
    private static final String DEFAULT_UNIT_MISU = "AAAAA";
    private static final String UPDATED_UNIT_MISU = "BBBBB";
    private static final String DEFAULT_CODI_NUME_CAPI_SPES = "AAAAA";
    private static final String UPDATED_CODI_NUME_CAPI_SPES = "BBBBB";
    private static final String DEFAULT_DATA_ELAB = "AAAAA";
    private static final String UPDATED_DATA_ELAB = "BBBBB";

    private static final Integer DEFAULT_CODI_ESI_GCOL = 1;
    private static final Integer UPDATED_CODI_ESI_GCOL = 2;

    private static final Float DEFAULT_PERC_SANZ_GCOL = 1F;
    private static final Float UPDATED_PERC_SANZ_GCOL = 2F;

    private static final Float DEFAULT_PERC_SANZ_AZIE = 1F;
    private static final Float UPDATED_PERC_SANZ_AZIE = 2F;

    private static final Float DEFAULT_VALO_MEDI_TITO = 1F;
    private static final Float UPDATED_VALO_MEDI_TITO = 2F;

    private static final Float DEFAULT_IMPO_TRAT_MODU = 1F;
    private static final Float UPDATED_IMPO_TRAT_MODU = 2F;

    private static final Integer DEFAULT_FASC_MODU = 1;
    private static final Integer UPDATED_FASC_MODU = 2;

    private static final Float DEFAULT_IMPO_TRAT_FINA = 1F;
    private static final Float UPDATED_IMPO_TRAT_FINA = 2F;

    @Inject
    private PagamentoRepository pagamentoRepository;

    @Inject
    private PagamentoSearchRepository pagamentoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPagamentoMockMvc;

    private Pagamento pagamento;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PagamentoResource pagamentoResource = new PagamentoResource();
        ReflectionTestUtils.setField(pagamentoResource, "pagamentoSearchRepository", pagamentoSearchRepository);
        ReflectionTestUtils.setField(pagamentoResource, "pagamentoRepository", pagamentoRepository);
        this.restPagamentoMockMvc = MockMvcBuilders.standaloneSetup(pagamentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pagamentoSearchRepository.deleteAll();
        pagamento = new Pagamento();
        pagamento.setCodIntervento(DEFAULT_COD_INTERVENTO);
        pagamento.setQntaDich(DEFAULT_QNTA_DICH);
        pagamento.setQntaAmme(DEFAULT_QNTA_AMME);
        pagamento.setQntaLiqu(DEFAULT_QNTA_LIQU);
        pagamento.setImpoDich(DEFAULT_IMPO_DICH);
        pagamento.setImpoAmme(DEFAULT_IMPO_AMME);
        pagamento.setImpoLiqu(DEFAULT_IMPO_LIQU);
        pagamento.setStatLiqu(DEFAULT_STAT_LIQU);
        pagamento.setUnitMisu(DEFAULT_UNIT_MISU);
        pagamento.setCodiNumeCapiSpes(DEFAULT_CODI_NUME_CAPI_SPES);
        pagamento.setDataElab(DEFAULT_DATA_ELAB);
        pagamento.setCodiEsiGcol(DEFAULT_CODI_ESI_GCOL);
        pagamento.setPercSanzGcol(DEFAULT_PERC_SANZ_GCOL);
        pagamento.setPercSanzAzie(DEFAULT_PERC_SANZ_AZIE);
        pagamento.setValoMediTito(DEFAULT_VALO_MEDI_TITO);
        pagamento.setImpoTratModu(DEFAULT_IMPO_TRAT_MODU);
        pagamento.setFascModu(DEFAULT_FASC_MODU);
        pagamento.setImpoTratFina(DEFAULT_IMPO_TRAT_FINA);
    }

    @Test
    @Transactional
    public void createPagamento() throws Exception {
        int databaseSizeBeforeCreate = pagamentoRepository.findAll().size();

        // Create the Pagamento

        restPagamentoMockMvc.perform(post("/api/pagamentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pagamento)))
                .andExpect(status().isCreated());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentos = pagamentoRepository.findAll();
        assertThat(pagamentos).hasSize(databaseSizeBeforeCreate + 1);
        Pagamento testPagamento = pagamentos.get(pagamentos.size() - 1);
        assertThat(testPagamento.getCodIntervento()).isEqualTo(DEFAULT_COD_INTERVENTO);
        assertThat(testPagamento.getQntaDich()).isEqualTo(DEFAULT_QNTA_DICH);
        assertThat(testPagamento.getQntaAmme()).isEqualTo(DEFAULT_QNTA_AMME);
        assertThat(testPagamento.getQntaLiqu()).isEqualTo(DEFAULT_QNTA_LIQU);
        assertThat(testPagamento.getImpoDich()).isEqualTo(DEFAULT_IMPO_DICH);
        assertThat(testPagamento.getImpoAmme()).isEqualTo(DEFAULT_IMPO_AMME);
        assertThat(testPagamento.getImpoLiqu()).isEqualTo(DEFAULT_IMPO_LIQU);
        assertThat(testPagamento.getStatLiqu()).isEqualTo(DEFAULT_STAT_LIQU);
        assertThat(testPagamento.getUnitMisu()).isEqualTo(DEFAULT_UNIT_MISU);
        assertThat(testPagamento.getCodiNumeCapiSpes()).isEqualTo(DEFAULT_CODI_NUME_CAPI_SPES);
        assertThat(testPagamento.getDataElab()).isEqualTo(DEFAULT_DATA_ELAB);
        assertThat(testPagamento.getCodiEsiGcol()).isEqualTo(DEFAULT_CODI_ESI_GCOL);
        assertThat(testPagamento.getPercSanzGcol()).isEqualTo(DEFAULT_PERC_SANZ_GCOL);
        assertThat(testPagamento.getPercSanzAzie()).isEqualTo(DEFAULT_PERC_SANZ_AZIE);
        assertThat(testPagamento.getValoMediTito()).isEqualTo(DEFAULT_VALO_MEDI_TITO);
        assertThat(testPagamento.getImpoTratModu()).isEqualTo(DEFAULT_IMPO_TRAT_MODU);
        assertThat(testPagamento.getFascModu()).isEqualTo(DEFAULT_FASC_MODU);
        assertThat(testPagamento.getImpoTratFina()).isEqualTo(DEFAULT_IMPO_TRAT_FINA);

        // Validate the Pagamento in ElasticSearch
        Pagamento pagamentoEs = pagamentoSearchRepository.findOne(testPagamento.getId());
        assertThat(pagamentoEs).isEqualToComparingFieldByField(testPagamento);
    }

    @Test
    @Transactional
    public void getAllPagamentos() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentos
        restPagamentoMockMvc.perform(get("/api/pagamentos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pagamento.getId().intValue())))
                .andExpect(jsonPath("$.[*].codIntervento").value(hasItem(DEFAULT_COD_INTERVENTO.toString())))
                .andExpect(jsonPath("$.[*].qntaDich").value(hasItem(DEFAULT_QNTA_DICH.doubleValue())))
                .andExpect(jsonPath("$.[*].qntaAmme").value(hasItem(DEFAULT_QNTA_AMME.doubleValue())))
                .andExpect(jsonPath("$.[*].qntaLiqu").value(hasItem(DEFAULT_QNTA_LIQU.doubleValue())))
                .andExpect(jsonPath("$.[*].impoDich").value(hasItem(DEFAULT_IMPO_DICH.doubleValue())))
                .andExpect(jsonPath("$.[*].impoAmme").value(hasItem(DEFAULT_IMPO_AMME.doubleValue())))
                .andExpect(jsonPath("$.[*].impoLiqu").value(hasItem(DEFAULT_IMPO_LIQU.doubleValue())))
                .andExpect(jsonPath("$.[*].statLiqu").value(hasItem(DEFAULT_STAT_LIQU)))
                .andExpect(jsonPath("$.[*].unitMisu").value(hasItem(DEFAULT_UNIT_MISU.toString())))
                .andExpect(jsonPath("$.[*].codiNumeCapiSpes").value(hasItem(DEFAULT_CODI_NUME_CAPI_SPES.toString())))
                .andExpect(jsonPath("$.[*].dataElab").value(hasItem(DEFAULT_DATA_ELAB.toString())))
                .andExpect(jsonPath("$.[*].codiEsiGcol").value(hasItem(DEFAULT_CODI_ESI_GCOL)))
                .andExpect(jsonPath("$.[*].percSanzGcol").value(hasItem(DEFAULT_PERC_SANZ_GCOL.doubleValue())))
                .andExpect(jsonPath("$.[*].percSanzAzie").value(hasItem(DEFAULT_PERC_SANZ_AZIE.doubleValue())))
                .andExpect(jsonPath("$.[*].valoMediTito").value(hasItem(DEFAULT_VALO_MEDI_TITO.doubleValue())))
                .andExpect(jsonPath("$.[*].impoTratModu").value(hasItem(DEFAULT_IMPO_TRAT_MODU.doubleValue())))
                .andExpect(jsonPath("$.[*].fascModu").value(hasItem(DEFAULT_FASC_MODU)))
                .andExpect(jsonPath("$.[*].impoTratFina").value(hasItem(DEFAULT_IMPO_TRAT_FINA.doubleValue())));
    }

    @Test
    @Transactional
    public void getPagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get the pagamento
        restPagamentoMockMvc.perform(get("/api/pagamentos/{id}", pagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pagamento.getId().intValue()))
            .andExpect(jsonPath("$.codIntervento").value(DEFAULT_COD_INTERVENTO.toString()))
            .andExpect(jsonPath("$.qntaDich").value(DEFAULT_QNTA_DICH.doubleValue()))
            .andExpect(jsonPath("$.qntaAmme").value(DEFAULT_QNTA_AMME.doubleValue()))
            .andExpect(jsonPath("$.qntaLiqu").value(DEFAULT_QNTA_LIQU.doubleValue()))
            .andExpect(jsonPath("$.impoDich").value(DEFAULT_IMPO_DICH.doubleValue()))
            .andExpect(jsonPath("$.impoAmme").value(DEFAULT_IMPO_AMME.doubleValue()))
            .andExpect(jsonPath("$.impoLiqu").value(DEFAULT_IMPO_LIQU.doubleValue()))
            .andExpect(jsonPath("$.statLiqu").value(DEFAULT_STAT_LIQU))
            .andExpect(jsonPath("$.unitMisu").value(DEFAULT_UNIT_MISU.toString()))
            .andExpect(jsonPath("$.codiNumeCapiSpes").value(DEFAULT_CODI_NUME_CAPI_SPES.toString()))
            .andExpect(jsonPath("$.dataElab").value(DEFAULT_DATA_ELAB.toString()))
            .andExpect(jsonPath("$.codiEsiGcol").value(DEFAULT_CODI_ESI_GCOL))
            .andExpect(jsonPath("$.percSanzGcol").value(DEFAULT_PERC_SANZ_GCOL.doubleValue()))
            .andExpect(jsonPath("$.percSanzAzie").value(DEFAULT_PERC_SANZ_AZIE.doubleValue()))
            .andExpect(jsonPath("$.valoMediTito").value(DEFAULT_VALO_MEDI_TITO.doubleValue()))
            .andExpect(jsonPath("$.impoTratModu").value(DEFAULT_IMPO_TRAT_MODU.doubleValue()))
            .andExpect(jsonPath("$.fascModu").value(DEFAULT_FASC_MODU))
            .andExpect(jsonPath("$.impoTratFina").value(DEFAULT_IMPO_TRAT_FINA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPagamento() throws Exception {
        // Get the pagamento
        restPagamentoMockMvc.perform(get("/api/pagamentos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);
        pagamentoSearchRepository.save(pagamento);
        int databaseSizeBeforeUpdate = pagamentoRepository.findAll().size();

        // Update the pagamento
        Pagamento updatedPagamento = new Pagamento();
        updatedPagamento.setId(pagamento.getId());
        updatedPagamento.setCodIntervento(UPDATED_COD_INTERVENTO);
        updatedPagamento.setQntaDich(UPDATED_QNTA_DICH);
        updatedPagamento.setQntaAmme(UPDATED_QNTA_AMME);
        updatedPagamento.setQntaLiqu(UPDATED_QNTA_LIQU);
        updatedPagamento.setImpoDich(UPDATED_IMPO_DICH);
        updatedPagamento.setImpoAmme(UPDATED_IMPO_AMME);
        updatedPagamento.setImpoLiqu(UPDATED_IMPO_LIQU);
        updatedPagamento.setStatLiqu(UPDATED_STAT_LIQU);
        updatedPagamento.setUnitMisu(UPDATED_UNIT_MISU);
        updatedPagamento.setCodiNumeCapiSpes(UPDATED_CODI_NUME_CAPI_SPES);
        updatedPagamento.setDataElab(UPDATED_DATA_ELAB);
        updatedPagamento.setCodiEsiGcol(UPDATED_CODI_ESI_GCOL);
        updatedPagamento.setPercSanzGcol(UPDATED_PERC_SANZ_GCOL);
        updatedPagamento.setPercSanzAzie(UPDATED_PERC_SANZ_AZIE);
        updatedPagamento.setValoMediTito(UPDATED_VALO_MEDI_TITO);
        updatedPagamento.setImpoTratModu(UPDATED_IMPO_TRAT_MODU);
        updatedPagamento.setFascModu(UPDATED_FASC_MODU);
        updatedPagamento.setImpoTratFina(UPDATED_IMPO_TRAT_FINA);

        restPagamentoMockMvc.perform(put("/api/pagamentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPagamento)))
                .andExpect(status().isOk());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentos = pagamentoRepository.findAll();
        assertThat(pagamentos).hasSize(databaseSizeBeforeUpdate);
        Pagamento testPagamento = pagamentos.get(pagamentos.size() - 1);
        assertThat(testPagamento.getCodIntervento()).isEqualTo(UPDATED_COD_INTERVENTO);
        assertThat(testPagamento.getQntaDich()).isEqualTo(UPDATED_QNTA_DICH);
        assertThat(testPagamento.getQntaAmme()).isEqualTo(UPDATED_QNTA_AMME);
        assertThat(testPagamento.getQntaLiqu()).isEqualTo(UPDATED_QNTA_LIQU);
        assertThat(testPagamento.getImpoDich()).isEqualTo(UPDATED_IMPO_DICH);
        assertThat(testPagamento.getImpoAmme()).isEqualTo(UPDATED_IMPO_AMME);
        assertThat(testPagamento.getImpoLiqu()).isEqualTo(UPDATED_IMPO_LIQU);
        assertThat(testPagamento.getStatLiqu()).isEqualTo(UPDATED_STAT_LIQU);
        assertThat(testPagamento.getUnitMisu()).isEqualTo(UPDATED_UNIT_MISU);
        assertThat(testPagamento.getCodiNumeCapiSpes()).isEqualTo(UPDATED_CODI_NUME_CAPI_SPES);
        assertThat(testPagamento.getDataElab()).isEqualTo(UPDATED_DATA_ELAB);
        assertThat(testPagamento.getCodiEsiGcol()).isEqualTo(UPDATED_CODI_ESI_GCOL);
        assertThat(testPagamento.getPercSanzGcol()).isEqualTo(UPDATED_PERC_SANZ_GCOL);
        assertThat(testPagamento.getPercSanzAzie()).isEqualTo(UPDATED_PERC_SANZ_AZIE);
        assertThat(testPagamento.getValoMediTito()).isEqualTo(UPDATED_VALO_MEDI_TITO);
        assertThat(testPagamento.getImpoTratModu()).isEqualTo(UPDATED_IMPO_TRAT_MODU);
        assertThat(testPagamento.getFascModu()).isEqualTo(UPDATED_FASC_MODU);
        assertThat(testPagamento.getImpoTratFina()).isEqualTo(UPDATED_IMPO_TRAT_FINA);

        // Validate the Pagamento in ElasticSearch
        Pagamento pagamentoEs = pagamentoSearchRepository.findOne(testPagamento.getId());
        assertThat(pagamentoEs).isEqualToComparingFieldByField(testPagamento);
    }

    @Test
    @Transactional
    public void deletePagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);
        pagamentoSearchRepository.save(pagamento);
        int databaseSizeBeforeDelete = pagamentoRepository.findAll().size();

        // Get the pagamento
        restPagamentoMockMvc.perform(delete("/api/pagamentos/{id}", pagamento.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean pagamentoExistsInEs = pagamentoSearchRepository.exists(pagamento.getId());
        assertThat(pagamentoExistsInEs).isFalse();

        // Validate the database is empty
        List<Pagamento> pagamentos = pagamentoRepository.findAll();
        assertThat(pagamentos).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);
        pagamentoSearchRepository.save(pagamento);

        // Search the pagamento
        restPagamentoMockMvc.perform(get("/api/_search/pagamentos?query=id:" + pagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].codIntervento").value(hasItem(DEFAULT_COD_INTERVENTO.toString())))
            .andExpect(jsonPath("$.[*].qntaDich").value(hasItem(DEFAULT_QNTA_DICH.doubleValue())))
            .andExpect(jsonPath("$.[*].qntaAmme").value(hasItem(DEFAULT_QNTA_AMME.doubleValue())))
            .andExpect(jsonPath("$.[*].qntaLiqu").value(hasItem(DEFAULT_QNTA_LIQU.doubleValue())))
            .andExpect(jsonPath("$.[*].impoDich").value(hasItem(DEFAULT_IMPO_DICH.doubleValue())))
            .andExpect(jsonPath("$.[*].impoAmme").value(hasItem(DEFAULT_IMPO_AMME.doubleValue())))
            .andExpect(jsonPath("$.[*].impoLiqu").value(hasItem(DEFAULT_IMPO_LIQU.doubleValue())))
            .andExpect(jsonPath("$.[*].statLiqu").value(hasItem(DEFAULT_STAT_LIQU)))
            .andExpect(jsonPath("$.[*].unitMisu").value(hasItem(DEFAULT_UNIT_MISU.toString())))
            .andExpect(jsonPath("$.[*].codiNumeCapiSpes").value(hasItem(DEFAULT_CODI_NUME_CAPI_SPES.toString())))
            .andExpect(jsonPath("$.[*].dataElab").value(hasItem(DEFAULT_DATA_ELAB.toString())))
            .andExpect(jsonPath("$.[*].codiEsiGcol").value(hasItem(DEFAULT_CODI_ESI_GCOL)))
            .andExpect(jsonPath("$.[*].percSanzGcol").value(hasItem(DEFAULT_PERC_SANZ_GCOL.doubleValue())))
            .andExpect(jsonPath("$.[*].percSanzAzie").value(hasItem(DEFAULT_PERC_SANZ_AZIE.doubleValue())))
            .andExpect(jsonPath("$.[*].valoMediTito").value(hasItem(DEFAULT_VALO_MEDI_TITO.doubleValue())))
            .andExpect(jsonPath("$.[*].impoTratModu").value(hasItem(DEFAULT_IMPO_TRAT_MODU.doubleValue())))
            .andExpect(jsonPath("$.[*].fascModu").value(hasItem(DEFAULT_FASC_MODU)))
            .andExpect(jsonPath("$.[*].impoTratFina").value(hasItem(DEFAULT_IMPO_TRAT_FINA.doubleValue())));
    }
}
