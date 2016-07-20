package it.almaviva.siap.istruttoria.web.rest;

import it.almaviva.siap.istruttoria.IstruttoriaApp;
import it.almaviva.siap.istruttoria.domain.Aduxstce;
import it.almaviva.siap.istruttoria.repository.AduxstceRepository;
import it.almaviva.siap.istruttoria.repository.search.AduxstceSearchRepository;

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
 * Test class for the AduxstceResource REST controller.
 *
 * @see AduxstceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstruttoriaApp.class)
@WebAppConfiguration
@IntegrationTest
public class AduxstceResourceIntTest {


    private static final Long DEFAULT_ID_ATTO_AMMI = 1L;
    private static final Long UPDATED_ID_ATTO_AMMI = 2L;

    private static final Long DEFAULT_ID_DECR = 1L;
    private static final Long UPDATED_ID_DECR = 2L;

    private static final Long DEFAULT_NUME_DECR = 1L;
    private static final Long UPDATED_NUME_DECR = 2L;

    private static final Long DEFAULT_ID_INTE = 1L;
    private static final Long UPDATED_ID_ENTE = 2L;
    private static final String DEFAULT_CODI_INTE = "AAAAA";
    private static final String UPDATED_CODI_INTE = "BBBBB";
    private static final String DEFAULT_DATA_STOR = "AAAAA";
    private static final String UPDATED_DATA_STOR = "BBBBB";
    private static final String DEFAULT_F_100 = "AAAAA";
    private static final String UPDATED_F_100 = "BBBBB";
    private static final String DEFAULT_C_110 = "AAAAA";
    private static final String UPDATED_C_110 = "BBBBB";
    private static final String DEFAULT_C_109 = "AAAAA";
    private static final String UPDATED_C_109 = "BBBBB";
    private static final String DEFAULT_F_200 = "AAAAA";
    private static final String UPDATED_F_200 = "BBBBB";
    private static final String DEFAULT_F_201 = "AAAAA";
    private static final String UPDATED_F_201 = "BBBBB";
    private static final String DEFAULT_F_202_A = "AAAAA";
    private static final String UPDATED_F_202_A = "BBBBB";
    private static final String DEFAULT_F_202_B = "AAAAA";
    private static final String UPDATED_F_202_B = "BBBBB";
    private static final String DEFAULT_F_202_C = "AAAAA";
    private static final String UPDATED_F_202_C = "BBBBB";
    private static final String DEFAULT_F_207 = "AAAAA";
    private static final String UPDATED_F_207 = "BBBBB";
    private static final String DEFAULT_F_300 = "AAAAA";
    private static final String UPDATED_F_300 = "BBBBB";
    private static final String DEFAULT_C_300_A = "AAAAA";
    private static final String UPDATED_C_300_A = "BBBBB";
    private static final String DEFAULT_F_300_B = "AAAAA";
    private static final String UPDATED_F_300_B = "BBBBB";

    private static final Float DEFAULT_C_551 = 1F;
    private static final Float UPDATED_C_551 = 2F;

    private static final Float DEFAULT_C_552 = 1F;
    private static final Float UPDATED_C_552 = 2F;
    private static final String DEFAULT_C_553 = "AAAAA";
    private static final String UPDATED_C_553 = "BBBBB";

    private static final Float DEFAULT_C_554 = 1F;
    private static final Float UPDATED_C_554 = 2F;

    private static final Float DEFAULT_C_558 = 1F;
    private static final Float UPDATED_C_558 = 2F;

    private static final Float DEFAULT_C_559 = 1F;
    private static final Float UPDATED_C_559 = 2F;

    private static final Float DEFAULT_C_560 = 1F;
    private static final Float UPDATED_C_560 = 2F;

    private static final Float DEFAULT_C_561 = 1F;
    private static final Float UPDATED_C_561 = 2F;
    private static final String DEFAULT_C_600 = "AAAAA";
    private static final String UPDATED_C_600 = "BBBBB";
    private static final String DEFAULT_C_611 = "AAAAA";
    private static final String UPDATED_C_611 = "BBBBB";
    private static final String DEFAULT_C_621 = "AAAAA";
    private static final String UPDATED_C_621 = "BBBBB";
    private static final String DEFAULT_C_633 = "AAAAA";
    private static final String UPDATED_C_633 = "BBBBB";
    private static final String DEFAULT_C_634 = "AAAAA";
    private static final String UPDATED_C_634 = "BBBBB";

    private static final Float DEFAULT_C_640 = 1F;
    private static final Float UPDATED_C_640 = 2F;
    private static final String DEFAULT_UNIT_MISU = "AAAAA";
    private static final String UPDATED_UNIT_MISU = "BBBBB";
    private static final String DEFAULT_DATA_CONT_OGGE = "AAAAA";
    private static final String UPDATED_DATA_CONT_OGGE = "BBBBB";

    private static final Float DEFAULT_QNTA_LIQU = 1F;
    private static final Float UPDATED_QNTA_LIQU = 2F;

    private static final Float DEFAULT_IMPO_LIQU = 1F;
    private static final Float UPDATED_IMPO_LIQU = 2F;

    private static final Float DEFAULT_RIDU_RITA_DEPO = 1F;
    private static final Float UPDATED_RIDU_RITA_DEPO = 2F;

    private static final Float DEFAULT_RIDU_MODU = 1F;
    private static final Float UPDATED_RIDU_MODU = 2F;

    private static final Float DEFAULT_DISC_FINA = 1F;
    private static final Float UPDATED_DISC_FINA = 2F;

    private static final Float DEFAULT_SANZ_COND = 1F;
    private static final Float UPDATED_SANZ_COND = 2F;

    private static final Long DEFAULT_STAT_ISTR = 1L;
    private static final Long UPDATED_STAT_ISTR = 2L;

    private static final Long DEFAULT_LIVE_AMMI = 1L;
    private static final Long UPDATED_LIVE_AMMI = 2L;
    private static final String DEFAULT_USER_NAME = "AAAAA";
    private static final String UPDATED_USER_NAME = "BBBBB";
    private static final String DEFAULT_DATA_AGGI = "AAAAA";
    private static final String UPDATED_DATA_AGGI = "BBBBB";

    private static final Float DEFAULT_C_640_QNTA = 1F;
    private static final Float UPDATED_C_640_QNTA = 2F;

    private static final Long DEFAULT_DECO_STAT = 1L;
    private static final Long UPDATED_DECO_STAT = 2L;
    private static final String DEFAULT_FLAG_ESIT = "AAAAA";
    private static final String UPDATED_FLAG_ESIT = "BBBBB";
    private static final String DEFAULT_DATA_SCAR = "AAAAA";
    private static final String UPDATED_DATA_SCAR = "BBBBB";

    private static final Float DEFAULT_C_557 = 1F;
    private static final Float UPDATED_C_557 = 2F;
    private static final String DEFAULT_C_109_A = "AAAAA";
    private static final String UPDATED_C_109_A = "BBBBB";

    private static final Integer DEFAULT_C_400 = 1;
    private static final Integer UPDATED_C_400 = 2;
    private static final String DEFAULT_C_401 = "AAAAA";
    private static final String UPDATED_C_401 = "BBBBB";

    private static final Float DEFAULT_C_402 = 1F;
    private static final Float UPDATED_C_402 = 2F;
    private static final String DEFAULT_C_403 = "AAAAA";
    private static final String UPDATED_C_403 = "BBBBB";
    private static final String DEFAULT_C_404 = "AAAAA";
    private static final String UPDATED_C_404 = "BBBBB";

    private static final Integer DEFAULT_C_405 = 1;
    private static final Integer UPDATED_C_405 = 2;

    private static final Float DEFAULT_C_406 = 1F;
    private static final Float UPDATED_C_406 = 2F;
    private static final String DEFAULT_C_407 = "AAAAA";
    private static final String UPDATED_C_407 = "BBBBB";

    private static final Float DEFAULT_C_558_A = 1F;
    private static final Float UPDATED_C_558_A = 2F;

    private static final Float DEFAULT_C_558_B = 1F;
    private static final Float UPDATED_C_558_B = 2F;

    private static final Float DEFAULT_C_558_C = 1F;
    private static final Float UPDATED_C_558_C = 2F;

    private static final Float DEFAULT_C_558_D = 1F;
    private static final Float UPDATED_C_558_D = 2F;

    private static final Float DEFAULT_C_558_E = 1F;
    private static final Float UPDATED_C_558_E = 2F;

    private static final Float DEFAULT_C_558_F = 1F;
    private static final Float UPDATED_C_558_F = 2F;
    private static final String DEFAULT_C_620 = "AAAAA";
    private static final String UPDATED_C_620 = "BBBBB";

    @Inject
    private AduxstceRepository aduxstceRepository;

    @Inject
    private AduxstceSearchRepository aduxstceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAduxstceMockMvc;

    private Aduxstce aduxstce;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AduxstceResource aduxstceResource = new AduxstceResource();
        ReflectionTestUtils.setField(aduxstceResource, "aduxstceSearchRepository", aduxstceSearchRepository);
        ReflectionTestUtils.setField(aduxstceResource, "aduxstceRepository", aduxstceRepository);
        this.restAduxstceMockMvc = MockMvcBuilders.standaloneSetup(aduxstceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        aduxstceSearchRepository.deleteAll();
        aduxstce = new Aduxstce();
        aduxstce.setIdAttoAmmi(DEFAULT_ID_ATTO_AMMI);
        aduxstce.setIdDecr(DEFAULT_ID_DECR);
        aduxstce.setNumeDecr(DEFAULT_NUME_DECR);
        aduxstce.setIdInte(DEFAULT_ID_INTE);
        aduxstce.setCodiInte(DEFAULT_CODI_INTE);
        aduxstce.setDataStor(DEFAULT_DATA_STOR);
        aduxstce.setf100(DEFAULT_F_100);
        aduxstce.setc110(DEFAULT_C_110);
        aduxstce.setc109(DEFAULT_C_109);
        aduxstce.setf200(DEFAULT_F_200);
        aduxstce.setf201(DEFAULT_F_201);
        aduxstce.setf202a(DEFAULT_F_202_A);
        aduxstce.setf202b(DEFAULT_F_202_B);
        aduxstce.setf202c(DEFAULT_F_202_C);
        aduxstce.setf207(DEFAULT_F_207);
        aduxstce.setf300(DEFAULT_F_300);
        aduxstce.setc300a(DEFAULT_C_300_A);
        aduxstce.setf300b(DEFAULT_F_300_B);
        aduxstce.setc551(DEFAULT_C_551);
        aduxstce.setc552(DEFAULT_C_552);
        aduxstce.setc553(DEFAULT_C_553);
        aduxstce.setc554(DEFAULT_C_554);
        aduxstce.setc558(DEFAULT_C_558);
        aduxstce.setc559(DEFAULT_C_559);
        aduxstce.setc560(DEFAULT_C_560);
        aduxstce.setc561(DEFAULT_C_561);
        aduxstce.setc600(DEFAULT_C_600);
        aduxstce.setc611(DEFAULT_C_611);
        aduxstce.setc621(DEFAULT_C_621);
        aduxstce.setc633(DEFAULT_C_633);
        aduxstce.setc634(DEFAULT_C_634);
        aduxstce.setc640(DEFAULT_C_640);
        aduxstce.setUnitMisu(DEFAULT_UNIT_MISU);
        aduxstce.setDataContOgge(DEFAULT_DATA_CONT_OGGE);
        aduxstce.setQntaLiqu(DEFAULT_QNTA_LIQU);
        aduxstce.setImpoLiqu(DEFAULT_IMPO_LIQU);
        aduxstce.setRiduRitaDepo(DEFAULT_RIDU_RITA_DEPO);
        aduxstce.setRiduModu(DEFAULT_RIDU_MODU);
        aduxstce.setDiscFina(DEFAULT_DISC_FINA);
        aduxstce.setSanzCond(DEFAULT_SANZ_COND);
        aduxstce.setStatIstr(DEFAULT_STAT_ISTR);
        aduxstce.setLiveAmmi(DEFAULT_LIVE_AMMI);
        aduxstce.setUserName(DEFAULT_USER_NAME);
        aduxstce.setDataAggi(DEFAULT_DATA_AGGI);
        aduxstce.setc640Qnta(DEFAULT_C_640_QNTA);
        aduxstce.setDecoStat(DEFAULT_DECO_STAT);
        aduxstce.setFlagEsit(DEFAULT_FLAG_ESIT);
        aduxstce.setDataScar(DEFAULT_DATA_SCAR);
        aduxstce.setc557(DEFAULT_C_557);
        aduxstce.setc109a(DEFAULT_C_109_A);
        aduxstce.setc400(DEFAULT_C_400);
        aduxstce.setc401(DEFAULT_C_401);
        aduxstce.setc402(DEFAULT_C_402);
        aduxstce.setc403(DEFAULT_C_403);
        aduxstce.setc404(DEFAULT_C_404);
        aduxstce.setc405(DEFAULT_C_405);
        aduxstce.setc406(DEFAULT_C_406);
        aduxstce.setc407(DEFAULT_C_407);
        aduxstce.setc558a(DEFAULT_C_558_A);
        aduxstce.setc558b(DEFAULT_C_558_B);
        aduxstce.setc558c(DEFAULT_C_558_C);
        aduxstce.setc558d(DEFAULT_C_558_D);
        aduxstce.setc558e(DEFAULT_C_558_E);
        aduxstce.setc558f(DEFAULT_C_558_F);
        aduxstce.setc620(DEFAULT_C_620);
    }

    @Test
    @Transactional
    public void createAduxstce() throws Exception {
        int databaseSizeBeforeCreate = aduxstceRepository.findAll().size();

        // Create the Aduxstce

        restAduxstceMockMvc.perform(post("/api/aduxstces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aduxstce)))
                .andExpect(status().isCreated());

        // Validate the Aduxstce in the database
        List<Aduxstce> aduxstces = aduxstceRepository.findAll();
        assertThat(aduxstces).hasSize(databaseSizeBeforeCreate + 1);
        Aduxstce testAduxstce = aduxstces.get(aduxstces.size() - 1);
        assertThat(testAduxstce.getIdAttoAmmi()).isEqualTo(DEFAULT_ID_ATTO_AMMI);
        assertThat(testAduxstce.getIdDecr()).isEqualTo(DEFAULT_ID_DECR);
        assertThat(testAduxstce.getNumeDecr()).isEqualTo(DEFAULT_NUME_DECR);
        assertThat(testAduxstce.getIdInte()).isEqualTo(DEFAULT_ID_INTE);
        assertThat(testAduxstce.getCodiInte()).isEqualTo(DEFAULT_CODI_INTE);
        assertThat(testAduxstce.getDataStor()).isEqualTo(DEFAULT_DATA_STOR);
        assertThat(testAduxstce.getf100()).isEqualTo(DEFAULT_F_100);
        assertThat(testAduxstce.getc110()).isEqualTo(DEFAULT_C_110);
        assertThat(testAduxstce.getc109()).isEqualTo(DEFAULT_C_109);
        assertThat(testAduxstce.getf200()).isEqualTo(DEFAULT_F_200);
        assertThat(testAduxstce.getf201()).isEqualTo(DEFAULT_F_201);
        assertThat(testAduxstce.getf202a()).isEqualTo(DEFAULT_F_202_A);
        assertThat(testAduxstce.getf202b()).isEqualTo(DEFAULT_F_202_B);
        assertThat(testAduxstce.getf202c()).isEqualTo(DEFAULT_F_202_C);
        assertThat(testAduxstce.getf207()).isEqualTo(DEFAULT_F_207);
        assertThat(testAduxstce.getf300()).isEqualTo(DEFAULT_F_300);
        assertThat(testAduxstce.getc300a()).isEqualTo(DEFAULT_C_300_A);
        assertThat(testAduxstce.getf300b()).isEqualTo(DEFAULT_F_300_B);
        assertThat(testAduxstce.getc551()).isEqualTo(DEFAULT_C_551);
        assertThat(testAduxstce.getc552()).isEqualTo(DEFAULT_C_552);
        assertThat(testAduxstce.getc553()).isEqualTo(DEFAULT_C_553);
        assertThat(testAduxstce.getc554()).isEqualTo(DEFAULT_C_554);
        assertThat(testAduxstce.getc558()).isEqualTo(DEFAULT_C_558);
        assertThat(testAduxstce.getc559()).isEqualTo(DEFAULT_C_559);
        assertThat(testAduxstce.getc560()).isEqualTo(DEFAULT_C_560);
        assertThat(testAduxstce.getc561()).isEqualTo(DEFAULT_C_561);
        assertThat(testAduxstce.getc600()).isEqualTo(DEFAULT_C_600);
        assertThat(testAduxstce.getc611()).isEqualTo(DEFAULT_C_611);
        assertThat(testAduxstce.getc621()).isEqualTo(DEFAULT_C_621);
        assertThat(testAduxstce.getc633()).isEqualTo(DEFAULT_C_633);
        assertThat(testAduxstce.getc634()).isEqualTo(DEFAULT_C_634);
        assertThat(testAduxstce.getc640()).isEqualTo(DEFAULT_C_640);
        assertThat(testAduxstce.getUnitMisu()).isEqualTo(DEFAULT_UNIT_MISU);
        assertThat(testAduxstce.getDataContOgge()).isEqualTo(DEFAULT_DATA_CONT_OGGE);
        assertThat(testAduxstce.getQntaLiqu()).isEqualTo(DEFAULT_QNTA_LIQU);
        assertThat(testAduxstce.getImpoLiqu()).isEqualTo(DEFAULT_IMPO_LIQU);
        assertThat(testAduxstce.getRiduRitaDepo()).isEqualTo(DEFAULT_RIDU_RITA_DEPO);
        assertThat(testAduxstce.getRiduModu()).isEqualTo(DEFAULT_RIDU_MODU);
        assertThat(testAduxstce.getDiscFina()).isEqualTo(DEFAULT_DISC_FINA);
        assertThat(testAduxstce.getSanzCond()).isEqualTo(DEFAULT_SANZ_COND);
        assertThat(testAduxstce.getStatIstr()).isEqualTo(DEFAULT_STAT_ISTR);
        assertThat(testAduxstce.getLiveAmmi()).isEqualTo(DEFAULT_LIVE_AMMI);
        assertThat(testAduxstce.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testAduxstce.getDataAggi()).isEqualTo(DEFAULT_DATA_AGGI);
        assertThat(testAduxstce.getc640Qnta()).isEqualTo(DEFAULT_C_640_QNTA);
        assertThat(testAduxstce.getDecoStat()).isEqualTo(DEFAULT_DECO_STAT);
        assertThat(testAduxstce.getFlagEsit()).isEqualTo(DEFAULT_FLAG_ESIT);
        assertThat(testAduxstce.getDataScar()).isEqualTo(DEFAULT_DATA_SCAR);
        assertThat(testAduxstce.getc557()).isEqualTo(DEFAULT_C_557);
        assertThat(testAduxstce.getc109a()).isEqualTo(DEFAULT_C_109_A);
        assertThat(testAduxstce.getc400()).isEqualTo(DEFAULT_C_400);
        assertThat(testAduxstce.getc401()).isEqualTo(DEFAULT_C_401);
        assertThat(testAduxstce.getc402()).isEqualTo(DEFAULT_C_402);
        assertThat(testAduxstce.getc403()).isEqualTo(DEFAULT_C_403);
        assertThat(testAduxstce.getc404()).isEqualTo(DEFAULT_C_404);
        assertThat(testAduxstce.getc405()).isEqualTo(DEFAULT_C_405);
        assertThat(testAduxstce.getc406()).isEqualTo(DEFAULT_C_406);
        assertThat(testAduxstce.getc407()).isEqualTo(DEFAULT_C_407);
        assertThat(testAduxstce.getc558a()).isEqualTo(DEFAULT_C_558_A);
        assertThat(testAduxstce.getc558b()).isEqualTo(DEFAULT_C_558_B);
        assertThat(testAduxstce.getc558c()).isEqualTo(DEFAULT_C_558_C);
        assertThat(testAduxstce.getc558d()).isEqualTo(DEFAULT_C_558_D);
        assertThat(testAduxstce.getc558e()).isEqualTo(DEFAULT_C_558_E);
        assertThat(testAduxstce.getc558f()).isEqualTo(DEFAULT_C_558_F);
        assertThat(testAduxstce.getc620()).isEqualTo(DEFAULT_C_620);

        // Validate the Aduxstce in ElasticSearch
        Aduxstce aduxstceEs = aduxstceSearchRepository.findOne(testAduxstce.getId());
        assertThat(aduxstceEs).isEqualToComparingFieldByField(testAduxstce);
    }

    @Test
    @Transactional
    public void getAllAduxstces() throws Exception {
        // Initialize the database
        aduxstceRepository.saveAndFlush(aduxstce);

        // Get all the aduxstces
        restAduxstceMockMvc.perform(get("/api/aduxstces?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aduxstce.getId().intValue())))
                .andExpect(jsonPath("$.[*].idAttoAmmi").value(hasItem(DEFAULT_ID_ATTO_AMMI.intValue())))
                .andExpect(jsonPath("$.[*].idDecr").value(hasItem(DEFAULT_ID_DECR.intValue())))
                .andExpect(jsonPath("$.[*].numeDecr").value(hasItem(DEFAULT_NUME_DECR.intValue())))
                .andExpect(jsonPath("$.[*].idEnte").value(hasItem(DEFAULT_ID_INTE.intValue())))
                .andExpect(jsonPath("$.[*].codiInte").value(hasItem(DEFAULT_CODI_INTE.toString())))
                .andExpect(jsonPath("$.[*].dataStor").value(hasItem(DEFAULT_DATA_STOR.toString())))
                .andExpect(jsonPath("$.[*].f100").value(hasItem(DEFAULT_F_100.toString())))
                .andExpect(jsonPath("$.[*].c110").value(hasItem(DEFAULT_C_110.toString())))
                .andExpect(jsonPath("$.[*].c109").value(hasItem(DEFAULT_C_109.toString())))
                .andExpect(jsonPath("$.[*].f200").value(hasItem(DEFAULT_F_200.toString())))
                .andExpect(jsonPath("$.[*].f201").value(hasItem(DEFAULT_F_201.toString())))
                .andExpect(jsonPath("$.[*].f202a").value(hasItem(DEFAULT_F_202_A.toString())))
                .andExpect(jsonPath("$.[*].f202b").value(hasItem(DEFAULT_F_202_B.toString())))
                .andExpect(jsonPath("$.[*].f202c").value(hasItem(DEFAULT_F_202_C.toString())))
                .andExpect(jsonPath("$.[*].f207").value(hasItem(DEFAULT_F_207.toString())))
                .andExpect(jsonPath("$.[*].f300").value(hasItem(DEFAULT_F_300.toString())))
                .andExpect(jsonPath("$.[*].c300a").value(hasItem(DEFAULT_C_300_A.toString())))
                .andExpect(jsonPath("$.[*].f300b").value(hasItem(DEFAULT_F_300_B.toString())))
                .andExpect(jsonPath("$.[*].c551").value(hasItem(DEFAULT_C_551.doubleValue())))
                .andExpect(jsonPath("$.[*].c552").value(hasItem(DEFAULT_C_552.doubleValue())))
                .andExpect(jsonPath("$.[*].c553").value(hasItem(DEFAULT_C_553.toString())))
                .andExpect(jsonPath("$.[*].c554").value(hasItem(DEFAULT_C_554.doubleValue())))
                .andExpect(jsonPath("$.[*].c558").value(hasItem(DEFAULT_C_558.doubleValue())))
                .andExpect(jsonPath("$.[*].c559").value(hasItem(DEFAULT_C_559.doubleValue())))
                .andExpect(jsonPath("$.[*].c560").value(hasItem(DEFAULT_C_560.doubleValue())))
                .andExpect(jsonPath("$.[*].c561").value(hasItem(DEFAULT_C_561.doubleValue())))
                .andExpect(jsonPath("$.[*].c600").value(hasItem(DEFAULT_C_600.toString())))
                .andExpect(jsonPath("$.[*].c611").value(hasItem(DEFAULT_C_611.toString())))
                .andExpect(jsonPath("$.[*].c621").value(hasItem(DEFAULT_C_621.toString())))
                .andExpect(jsonPath("$.[*].c633").value(hasItem(DEFAULT_C_633.toString())))
                .andExpect(jsonPath("$.[*].c634").value(hasItem(DEFAULT_C_634.toString())))
                .andExpect(jsonPath("$.[*].c640").value(hasItem(DEFAULT_C_640.doubleValue())))
                .andExpect(jsonPath("$.[*].unitMisu").value(hasItem(DEFAULT_UNIT_MISU.toString())))
                .andExpect(jsonPath("$.[*].dataContOgge").value(hasItem(DEFAULT_DATA_CONT_OGGE.toString())))
                .andExpect(jsonPath("$.[*].qntaLiqu").value(hasItem(DEFAULT_QNTA_LIQU.doubleValue())))
                .andExpect(jsonPath("$.[*].impoLiqu").value(hasItem(DEFAULT_IMPO_LIQU.doubleValue())))
                .andExpect(jsonPath("$.[*].riduRitaDepo").value(hasItem(DEFAULT_RIDU_RITA_DEPO.doubleValue())))
                .andExpect(jsonPath("$.[*].riduModu").value(hasItem(DEFAULT_RIDU_MODU.doubleValue())))
                .andExpect(jsonPath("$.[*].discFina").value(hasItem(DEFAULT_DISC_FINA.doubleValue())))
                .andExpect(jsonPath("$.[*].sanzCond").value(hasItem(DEFAULT_SANZ_COND.doubleValue())))
                .andExpect(jsonPath("$.[*].statIstr").value(hasItem(DEFAULT_STAT_ISTR.intValue())))
                .andExpect(jsonPath("$.[*].liveAmmi").value(hasItem(DEFAULT_LIVE_AMMI.intValue())))
                .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
                .andExpect(jsonPath("$.[*].dataAggi").value(hasItem(DEFAULT_DATA_AGGI.toString())))
                .andExpect(jsonPath("$.[*].c640Qnta").value(hasItem(DEFAULT_C_640_QNTA.doubleValue())))
                .andExpect(jsonPath("$.[*].decoStat").value(hasItem(DEFAULT_DECO_STAT.intValue())))
                .andExpect(jsonPath("$.[*].flagEsit").value(hasItem(DEFAULT_FLAG_ESIT.toString())))
                .andExpect(jsonPath("$.[*].dataScar").value(hasItem(DEFAULT_DATA_SCAR.toString())))
                .andExpect(jsonPath("$.[*].c557").value(hasItem(DEFAULT_C_557.doubleValue())))
                .andExpect(jsonPath("$.[*].c109a").value(hasItem(DEFAULT_C_109_A.toString())))
                .andExpect(jsonPath("$.[*].c400").value(hasItem(DEFAULT_C_400)))
                .andExpect(jsonPath("$.[*].c401").value(hasItem(DEFAULT_C_401.toString())))
                .andExpect(jsonPath("$.[*].c402").value(hasItem(DEFAULT_C_402.doubleValue())))
                .andExpect(jsonPath("$.[*].c403").value(hasItem(DEFAULT_C_403.toString())))
                .andExpect(jsonPath("$.[*].c404").value(hasItem(DEFAULT_C_404.toString())))
                .andExpect(jsonPath("$.[*].c405").value(hasItem(DEFAULT_C_405)))
                .andExpect(jsonPath("$.[*].c406").value(hasItem(DEFAULT_C_406.doubleValue())))
                .andExpect(jsonPath("$.[*].c407").value(hasItem(DEFAULT_C_407.toString())))
                .andExpect(jsonPath("$.[*].c558a").value(hasItem(DEFAULT_C_558_A.doubleValue())))
                .andExpect(jsonPath("$.[*].c558b").value(hasItem(DEFAULT_C_558_B.doubleValue())))
                .andExpect(jsonPath("$.[*].c558c").value(hasItem(DEFAULT_C_558_C.doubleValue())))
                .andExpect(jsonPath("$.[*].c558d").value(hasItem(DEFAULT_C_558_D.doubleValue())))
                .andExpect(jsonPath("$.[*].c558e").value(hasItem(DEFAULT_C_558_E.doubleValue())))
                .andExpect(jsonPath("$.[*].c558f").value(hasItem(DEFAULT_C_558_F.doubleValue())))
                .andExpect(jsonPath("$.[*].c620").value(hasItem(DEFAULT_C_620.toString())));
    }

    @Test
    @Transactional
    public void getAduxstce() throws Exception {
        // Initialize the database
        aduxstceRepository.saveAndFlush(aduxstce);

        // Get the aduxstce
        restAduxstceMockMvc.perform(get("/api/aduxstces/{id}", aduxstce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(aduxstce.getId().intValue()))
            .andExpect(jsonPath("$.idAttoAmmi").value(DEFAULT_ID_ATTO_AMMI.intValue()))
            .andExpect(jsonPath("$.idDecr").value(DEFAULT_ID_DECR.intValue()))
            .andExpect(jsonPath("$.numeDecr").value(DEFAULT_NUME_DECR.intValue()))
            .andExpect(jsonPath("$.idEnte").value(DEFAULT_ID_INTE.intValue()))
            .andExpect(jsonPath("$.codiInte").value(DEFAULT_CODI_INTE.toString()))
            .andExpect(jsonPath("$.dataStor").value(DEFAULT_DATA_STOR.toString()))
            .andExpect(jsonPath("$.f100").value(DEFAULT_F_100.toString()))
            .andExpect(jsonPath("$.c110").value(DEFAULT_C_110.toString()))
            .andExpect(jsonPath("$.c109").value(DEFAULT_C_109.toString()))
            .andExpect(jsonPath("$.f200").value(DEFAULT_F_200.toString()))
            .andExpect(jsonPath("$.f201").value(DEFAULT_F_201.toString()))
            .andExpect(jsonPath("$.f202a").value(DEFAULT_F_202_A.toString()))
            .andExpect(jsonPath("$.f202b").value(DEFAULT_F_202_B.toString()))
            .andExpect(jsonPath("$.f202c").value(DEFAULT_F_202_C.toString()))
            .andExpect(jsonPath("$.f207").value(DEFAULT_F_207.toString()))
            .andExpect(jsonPath("$.f300").value(DEFAULT_F_300.toString()))
            .andExpect(jsonPath("$.c300a").value(DEFAULT_C_300_A.toString()))
            .andExpect(jsonPath("$.f300b").value(DEFAULT_F_300_B.toString()))
            .andExpect(jsonPath("$.c551").value(DEFAULT_C_551.doubleValue()))
            .andExpect(jsonPath("$.c552").value(DEFAULT_C_552.doubleValue()))
            .andExpect(jsonPath("$.c553").value(DEFAULT_C_553.toString()))
            .andExpect(jsonPath("$.c554").value(DEFAULT_C_554.doubleValue()))
            .andExpect(jsonPath("$.c558").value(DEFAULT_C_558.doubleValue()))
            .andExpect(jsonPath("$.c559").value(DEFAULT_C_559.doubleValue()))
            .andExpect(jsonPath("$.c560").value(DEFAULT_C_560.doubleValue()))
            .andExpect(jsonPath("$.c561").value(DEFAULT_C_561.doubleValue()))
            .andExpect(jsonPath("$.c600").value(DEFAULT_C_600.toString()))
            .andExpect(jsonPath("$.c611").value(DEFAULT_C_611.toString()))
            .andExpect(jsonPath("$.c621").value(DEFAULT_C_621.toString()))
            .andExpect(jsonPath("$.c633").value(DEFAULT_C_633.toString()))
            .andExpect(jsonPath("$.c634").value(DEFAULT_C_634.toString()))
            .andExpect(jsonPath("$.c640").value(DEFAULT_C_640.doubleValue()))
            .andExpect(jsonPath("$.unitMisu").value(DEFAULT_UNIT_MISU.toString()))
            .andExpect(jsonPath("$.dataContOgge").value(DEFAULT_DATA_CONT_OGGE.toString()))
            .andExpect(jsonPath("$.qntaLiqu").value(DEFAULT_QNTA_LIQU.doubleValue()))
            .andExpect(jsonPath("$.impoLiqu").value(DEFAULT_IMPO_LIQU.doubleValue()))
            .andExpect(jsonPath("$.riduRitaDepo").value(DEFAULT_RIDU_RITA_DEPO.doubleValue()))
            .andExpect(jsonPath("$.riduModu").value(DEFAULT_RIDU_MODU.doubleValue()))
            .andExpect(jsonPath("$.discFina").value(DEFAULT_DISC_FINA.doubleValue()))
            .andExpect(jsonPath("$.sanzCond").value(DEFAULT_SANZ_COND.doubleValue()))
            .andExpect(jsonPath("$.statIstr").value(DEFAULT_STAT_ISTR.intValue()))
            .andExpect(jsonPath("$.liveAmmi").value(DEFAULT_LIVE_AMMI.intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.dataAggi").value(DEFAULT_DATA_AGGI.toString()))
            .andExpect(jsonPath("$.c640Qnta").value(DEFAULT_C_640_QNTA.doubleValue()))
            .andExpect(jsonPath("$.decoStat").value(DEFAULT_DECO_STAT.intValue()))
            .andExpect(jsonPath("$.flagEsit").value(DEFAULT_FLAG_ESIT.toString()))
            .andExpect(jsonPath("$.dataScar").value(DEFAULT_DATA_SCAR.toString()))
            .andExpect(jsonPath("$.c557").value(DEFAULT_C_557.doubleValue()))
            .andExpect(jsonPath("$.c109a").value(DEFAULT_C_109_A.toString()))
            .andExpect(jsonPath("$.c400").value(DEFAULT_C_400))
            .andExpect(jsonPath("$.c401").value(DEFAULT_C_401.toString()))
            .andExpect(jsonPath("$.c402").value(DEFAULT_C_402.doubleValue()))
            .andExpect(jsonPath("$.c403").value(DEFAULT_C_403.toString()))
            .andExpect(jsonPath("$.c404").value(DEFAULT_C_404.toString()))
            .andExpect(jsonPath("$.c405").value(DEFAULT_C_405))
            .andExpect(jsonPath("$.c406").value(DEFAULT_C_406.doubleValue()))
            .andExpect(jsonPath("$.c407").value(DEFAULT_C_407.toString()))
            .andExpect(jsonPath("$.c558a").value(DEFAULT_C_558_A.doubleValue()))
            .andExpect(jsonPath("$.c558b").value(DEFAULT_C_558_B.doubleValue()))
            .andExpect(jsonPath("$.c558c").value(DEFAULT_C_558_C.doubleValue()))
            .andExpect(jsonPath("$.c558d").value(DEFAULT_C_558_D.doubleValue()))
            .andExpect(jsonPath("$.c558e").value(DEFAULT_C_558_E.doubleValue()))
            .andExpect(jsonPath("$.c558f").value(DEFAULT_C_558_F.doubleValue()))
            .andExpect(jsonPath("$.c620").value(DEFAULT_C_620.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAduxstce() throws Exception {
        // Get the aduxstce
        restAduxstceMockMvc.perform(get("/api/aduxstces/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAduxstce() throws Exception {
        // Initialize the database
        aduxstceRepository.saveAndFlush(aduxstce);
        aduxstceSearchRepository.save(aduxstce);
        int databaseSizeBeforeUpdate = aduxstceRepository.findAll().size();

        // Update the aduxstce
        Aduxstce updatedAduxstce = new Aduxstce();
        updatedAduxstce.setId(aduxstce.getId());
        updatedAduxstce.setIdAttoAmmi(UPDATED_ID_ATTO_AMMI);
        updatedAduxstce.setIdDecr(UPDATED_ID_DECR);
        updatedAduxstce.setNumeDecr(UPDATED_NUME_DECR);
        updatedAduxstce.setIdInte(UPDATED_ID_ENTE);
        updatedAduxstce.setCodiInte(UPDATED_CODI_INTE);
        updatedAduxstce.setDataStor(UPDATED_DATA_STOR);
        updatedAduxstce.setf100(UPDATED_F_100);
        updatedAduxstce.setc110(UPDATED_C_110);
        updatedAduxstce.setc109(UPDATED_C_109);
        updatedAduxstce.setf200(UPDATED_F_200);
        updatedAduxstce.setf201(UPDATED_F_201);
        updatedAduxstce.setf202a(UPDATED_F_202_A);
        updatedAduxstce.setf202b(UPDATED_F_202_B);
        updatedAduxstce.setf202c(UPDATED_F_202_C);
        updatedAduxstce.setf207(UPDATED_F_207);
        updatedAduxstce.setf300(UPDATED_F_300);
        updatedAduxstce.setc300a(UPDATED_C_300_A);
        updatedAduxstce.setf300b(UPDATED_F_300_B);
        updatedAduxstce.setc551(UPDATED_C_551);
        updatedAduxstce.setc552(UPDATED_C_552);
        updatedAduxstce.setc553(UPDATED_C_553);
        updatedAduxstce.setc554(UPDATED_C_554);
        updatedAduxstce.setc558(UPDATED_C_558);
        updatedAduxstce.setc559(UPDATED_C_559);
        updatedAduxstce.setc560(UPDATED_C_560);
        updatedAduxstce.setc561(UPDATED_C_561);
        updatedAduxstce.setc600(UPDATED_C_600);
        updatedAduxstce.setc611(UPDATED_C_611);
        updatedAduxstce.setc621(UPDATED_C_621);
        updatedAduxstce.setc633(UPDATED_C_633);
        updatedAduxstce.setc634(UPDATED_C_634);
        updatedAduxstce.setc640(UPDATED_C_640);
        updatedAduxstce.setUnitMisu(UPDATED_UNIT_MISU);
        updatedAduxstce.setDataContOgge(UPDATED_DATA_CONT_OGGE);
        updatedAduxstce.setQntaLiqu(UPDATED_QNTA_LIQU);
        updatedAduxstce.setImpoLiqu(UPDATED_IMPO_LIQU);
        updatedAduxstce.setRiduRitaDepo(UPDATED_RIDU_RITA_DEPO);
        updatedAduxstce.setRiduModu(UPDATED_RIDU_MODU);
        updatedAduxstce.setDiscFina(UPDATED_DISC_FINA);
        updatedAduxstce.setSanzCond(UPDATED_SANZ_COND);
        updatedAduxstce.setStatIstr(UPDATED_STAT_ISTR);
        updatedAduxstce.setLiveAmmi(UPDATED_LIVE_AMMI);
        updatedAduxstce.setUserName(UPDATED_USER_NAME);
        updatedAduxstce.setDataAggi(UPDATED_DATA_AGGI);
        updatedAduxstce.setc640Qnta(UPDATED_C_640_QNTA);
        updatedAduxstce.setDecoStat(UPDATED_DECO_STAT);
        updatedAduxstce.setFlagEsit(UPDATED_FLAG_ESIT);
        updatedAduxstce.setDataScar(UPDATED_DATA_SCAR);
        updatedAduxstce.setc557(UPDATED_C_557);
        updatedAduxstce.setc109a(UPDATED_C_109_A);
        updatedAduxstce.setc400(UPDATED_C_400);
        updatedAduxstce.setc401(UPDATED_C_401);
        updatedAduxstce.setc402(UPDATED_C_402);
        updatedAduxstce.setc403(UPDATED_C_403);
        updatedAduxstce.setc404(UPDATED_C_404);
        updatedAduxstce.setc405(UPDATED_C_405);
        updatedAduxstce.setc406(UPDATED_C_406);
        updatedAduxstce.setc407(UPDATED_C_407);
        updatedAduxstce.setc558a(UPDATED_C_558_A);
        updatedAduxstce.setc558b(UPDATED_C_558_B);
        updatedAduxstce.setc558c(UPDATED_C_558_C);
        updatedAduxstce.setc558d(UPDATED_C_558_D);
        updatedAduxstce.setc558e(UPDATED_C_558_E);
        updatedAduxstce.setc558f(UPDATED_C_558_F);
        updatedAduxstce.setc620(UPDATED_C_620);

        restAduxstceMockMvc.perform(put("/api/aduxstces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAduxstce)))
                .andExpect(status().isOk());

        // Validate the Aduxstce in the database
        List<Aduxstce> aduxstces = aduxstceRepository.findAll();
        assertThat(aduxstces).hasSize(databaseSizeBeforeUpdate);
        Aduxstce testAduxstce = aduxstces.get(aduxstces.size() - 1);
        assertThat(testAduxstce.getIdAttoAmmi()).isEqualTo(UPDATED_ID_ATTO_AMMI);
        assertThat(testAduxstce.getIdDecr()).isEqualTo(UPDATED_ID_DECR);
        assertThat(testAduxstce.getNumeDecr()).isEqualTo(UPDATED_NUME_DECR);
        assertThat(testAduxstce.getIdInte()).isEqualTo(UPDATED_ID_ENTE);
        assertThat(testAduxstce.getCodiInte()).isEqualTo(UPDATED_CODI_INTE);
        assertThat(testAduxstce.getDataStor()).isEqualTo(UPDATED_DATA_STOR);
        assertThat(testAduxstce.getf100()).isEqualTo(UPDATED_F_100);
        assertThat(testAduxstce.getc110()).isEqualTo(UPDATED_C_110);
        assertThat(testAduxstce.getc109()).isEqualTo(UPDATED_C_109);
        assertThat(testAduxstce.getf200()).isEqualTo(UPDATED_F_200);
        assertThat(testAduxstce.getf201()).isEqualTo(UPDATED_F_201);
        assertThat(testAduxstce.getf202a()).isEqualTo(UPDATED_F_202_A);
        assertThat(testAduxstce.getf202b()).isEqualTo(UPDATED_F_202_B);
        assertThat(testAduxstce.getf202c()).isEqualTo(UPDATED_F_202_C);
        assertThat(testAduxstce.getf207()).isEqualTo(UPDATED_F_207);
        assertThat(testAduxstce.getf300()).isEqualTo(UPDATED_F_300);
        assertThat(testAduxstce.getc300a()).isEqualTo(UPDATED_C_300_A);
        assertThat(testAduxstce.getf300b()).isEqualTo(UPDATED_F_300_B);
        assertThat(testAduxstce.getc551()).isEqualTo(UPDATED_C_551);
        assertThat(testAduxstce.getc552()).isEqualTo(UPDATED_C_552);
        assertThat(testAduxstce.getc553()).isEqualTo(UPDATED_C_553);
        assertThat(testAduxstce.getc554()).isEqualTo(UPDATED_C_554);
        assertThat(testAduxstce.getc558()).isEqualTo(UPDATED_C_558);
        assertThat(testAduxstce.getc559()).isEqualTo(UPDATED_C_559);
        assertThat(testAduxstce.getc560()).isEqualTo(UPDATED_C_560);
        assertThat(testAduxstce.getc561()).isEqualTo(UPDATED_C_561);
        assertThat(testAduxstce.getc600()).isEqualTo(UPDATED_C_600);
        assertThat(testAduxstce.getc611()).isEqualTo(UPDATED_C_611);
        assertThat(testAduxstce.getc621()).isEqualTo(UPDATED_C_621);
        assertThat(testAduxstce.getc633()).isEqualTo(UPDATED_C_633);
        assertThat(testAduxstce.getc634()).isEqualTo(UPDATED_C_634);
        assertThat(testAduxstce.getc640()).isEqualTo(UPDATED_C_640);
        assertThat(testAduxstce.getUnitMisu()).isEqualTo(UPDATED_UNIT_MISU);
        assertThat(testAduxstce.getDataContOgge()).isEqualTo(UPDATED_DATA_CONT_OGGE);
        assertThat(testAduxstce.getQntaLiqu()).isEqualTo(UPDATED_QNTA_LIQU);
        assertThat(testAduxstce.getImpoLiqu()).isEqualTo(UPDATED_IMPO_LIQU);
        assertThat(testAduxstce.getRiduRitaDepo()).isEqualTo(UPDATED_RIDU_RITA_DEPO);
        assertThat(testAduxstce.getRiduModu()).isEqualTo(UPDATED_RIDU_MODU);
        assertThat(testAduxstce.getDiscFina()).isEqualTo(UPDATED_DISC_FINA);
        assertThat(testAduxstce.getSanzCond()).isEqualTo(UPDATED_SANZ_COND);
        assertThat(testAduxstce.getStatIstr()).isEqualTo(UPDATED_STAT_ISTR);
        assertThat(testAduxstce.getLiveAmmi()).isEqualTo(UPDATED_LIVE_AMMI);
        assertThat(testAduxstce.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testAduxstce.getDataAggi()).isEqualTo(UPDATED_DATA_AGGI);
        assertThat(testAduxstce.getc640Qnta()).isEqualTo(UPDATED_C_640_QNTA);
        assertThat(testAduxstce.getDecoStat()).isEqualTo(UPDATED_DECO_STAT);
        assertThat(testAduxstce.getFlagEsit()).isEqualTo(UPDATED_FLAG_ESIT);
        assertThat(testAduxstce.getDataScar()).isEqualTo(UPDATED_DATA_SCAR);
        assertThat(testAduxstce.getc557()).isEqualTo(UPDATED_C_557);
        assertThat(testAduxstce.getc109a()).isEqualTo(UPDATED_C_109_A);
        assertThat(testAduxstce.getc400()).isEqualTo(UPDATED_C_400);
        assertThat(testAduxstce.getc401()).isEqualTo(UPDATED_C_401);
        assertThat(testAduxstce.getc402()).isEqualTo(UPDATED_C_402);
        assertThat(testAduxstce.getc403()).isEqualTo(UPDATED_C_403);
        assertThat(testAduxstce.getc404()).isEqualTo(UPDATED_C_404);
        assertThat(testAduxstce.getc405()).isEqualTo(UPDATED_C_405);
        assertThat(testAduxstce.getc406()).isEqualTo(UPDATED_C_406);
        assertThat(testAduxstce.getc407()).isEqualTo(UPDATED_C_407);
        assertThat(testAduxstce.getc558a()).isEqualTo(UPDATED_C_558_A);
        assertThat(testAduxstce.getc558b()).isEqualTo(UPDATED_C_558_B);
        assertThat(testAduxstce.getc558c()).isEqualTo(UPDATED_C_558_C);
        assertThat(testAduxstce.getc558d()).isEqualTo(UPDATED_C_558_D);
        assertThat(testAduxstce.getc558e()).isEqualTo(UPDATED_C_558_E);
        assertThat(testAduxstce.getc558f()).isEqualTo(UPDATED_C_558_F);
        assertThat(testAduxstce.getc620()).isEqualTo(UPDATED_C_620);

        // Validate the Aduxstce in ElasticSearch
        Aduxstce aduxstceEs = aduxstceSearchRepository.findOne(testAduxstce.getId());
        assertThat(aduxstceEs).isEqualToComparingFieldByField(testAduxstce);
    }

    @Test
    @Transactional
    public void deleteAduxstce() throws Exception {
        // Initialize the database
        aduxstceRepository.saveAndFlush(aduxstce);
        aduxstceSearchRepository.save(aduxstce);
        int databaseSizeBeforeDelete = aduxstceRepository.findAll().size();

        // Get the aduxstce
        restAduxstceMockMvc.perform(delete("/api/aduxstces/{id}", aduxstce.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean aduxstceExistsInEs = aduxstceSearchRepository.exists(aduxstce.getId());
        assertThat(aduxstceExistsInEs).isFalse();

        // Validate the database is empty
        List<Aduxstce> aduxstces = aduxstceRepository.findAll();
        assertThat(aduxstces).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAduxstce() throws Exception {
        // Initialize the database
        aduxstceRepository.saveAndFlush(aduxstce);
        aduxstceSearchRepository.save(aduxstce);

        // Search the aduxstce
        restAduxstceMockMvc.perform(get("/api/_search/aduxstces?query=id:" + aduxstce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aduxstce.getId().intValue())))
            .andExpect(jsonPath("$.[*].idAttoAmmi").value(hasItem(DEFAULT_ID_ATTO_AMMI.intValue())))
            .andExpect(jsonPath("$.[*].idDecr").value(hasItem(DEFAULT_ID_DECR.intValue())))
            .andExpect(jsonPath("$.[*].numeDecr").value(hasItem(DEFAULT_NUME_DECR.intValue())))
            .andExpect(jsonPath("$.[*].idEnte").value(hasItem(DEFAULT_ID_INTE.intValue())))
            .andExpect(jsonPath("$.[*].codiInte").value(hasItem(DEFAULT_CODI_INTE.toString())))
            .andExpect(jsonPath("$.[*].dataStor").value(hasItem(DEFAULT_DATA_STOR.toString())))
            .andExpect(jsonPath("$.[*].f100").value(hasItem(DEFAULT_F_100.toString())))
            .andExpect(jsonPath("$.[*].c110").value(hasItem(DEFAULT_C_110.toString())))
            .andExpect(jsonPath("$.[*].c109").value(hasItem(DEFAULT_C_109.toString())))
            .andExpect(jsonPath("$.[*].f200").value(hasItem(DEFAULT_F_200.toString())))
            .andExpect(jsonPath("$.[*].f201").value(hasItem(DEFAULT_F_201.toString())))
            .andExpect(jsonPath("$.[*].f202a").value(hasItem(DEFAULT_F_202_A.toString())))
            .andExpect(jsonPath("$.[*].f202b").value(hasItem(DEFAULT_F_202_B.toString())))
            .andExpect(jsonPath("$.[*].f202c").value(hasItem(DEFAULT_F_202_C.toString())))
            .andExpect(jsonPath("$.[*].f207").value(hasItem(DEFAULT_F_207.toString())))
            .andExpect(jsonPath("$.[*].f300").value(hasItem(DEFAULT_F_300.toString())))
            .andExpect(jsonPath("$.[*].c300a").value(hasItem(DEFAULT_C_300_A.toString())))
            .andExpect(jsonPath("$.[*].f300b").value(hasItem(DEFAULT_F_300_B.toString())))
            .andExpect(jsonPath("$.[*].c551").value(hasItem(DEFAULT_C_551.doubleValue())))
            .andExpect(jsonPath("$.[*].c552").value(hasItem(DEFAULT_C_552.doubleValue())))
            .andExpect(jsonPath("$.[*].c553").value(hasItem(DEFAULT_C_553.toString())))
            .andExpect(jsonPath("$.[*].c554").value(hasItem(DEFAULT_C_554.doubleValue())))
            .andExpect(jsonPath("$.[*].c558").value(hasItem(DEFAULT_C_558.doubleValue())))
            .andExpect(jsonPath("$.[*].c559").value(hasItem(DEFAULT_C_559.doubleValue())))
            .andExpect(jsonPath("$.[*].c560").value(hasItem(DEFAULT_C_560.doubleValue())))
            .andExpect(jsonPath("$.[*].c561").value(hasItem(DEFAULT_C_561.doubleValue())))
            .andExpect(jsonPath("$.[*].c600").value(hasItem(DEFAULT_C_600.toString())))
            .andExpect(jsonPath("$.[*].c611").value(hasItem(DEFAULT_C_611.toString())))
            .andExpect(jsonPath("$.[*].c621").value(hasItem(DEFAULT_C_621.toString())))
            .andExpect(jsonPath("$.[*].c633").value(hasItem(DEFAULT_C_633.toString())))
            .andExpect(jsonPath("$.[*].c634").value(hasItem(DEFAULT_C_634.toString())))
            .andExpect(jsonPath("$.[*].c640").value(hasItem(DEFAULT_C_640.doubleValue())))
            .andExpect(jsonPath("$.[*].unitMisu").value(hasItem(DEFAULT_UNIT_MISU.toString())))
            .andExpect(jsonPath("$.[*].dataContOgge").value(hasItem(DEFAULT_DATA_CONT_OGGE.toString())))
            .andExpect(jsonPath("$.[*].qntaLiqu").value(hasItem(DEFAULT_QNTA_LIQU.doubleValue())))
            .andExpect(jsonPath("$.[*].impoLiqu").value(hasItem(DEFAULT_IMPO_LIQU.doubleValue())))
            .andExpect(jsonPath("$.[*].riduRitaDepo").value(hasItem(DEFAULT_RIDU_RITA_DEPO.doubleValue())))
            .andExpect(jsonPath("$.[*].riduModu").value(hasItem(DEFAULT_RIDU_MODU.doubleValue())))
            .andExpect(jsonPath("$.[*].discFina").value(hasItem(DEFAULT_DISC_FINA.doubleValue())))
            .andExpect(jsonPath("$.[*].sanzCond").value(hasItem(DEFAULT_SANZ_COND.doubleValue())))
            .andExpect(jsonPath("$.[*].statIstr").value(hasItem(DEFAULT_STAT_ISTR.intValue())))
            .andExpect(jsonPath("$.[*].liveAmmi").value(hasItem(DEFAULT_LIVE_AMMI.intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].dataAggi").value(hasItem(DEFAULT_DATA_AGGI.toString())))
            .andExpect(jsonPath("$.[*].c640Qnta").value(hasItem(DEFAULT_C_640_QNTA.doubleValue())))
            .andExpect(jsonPath("$.[*].decoStat").value(hasItem(DEFAULT_DECO_STAT.intValue())))
            .andExpect(jsonPath("$.[*].flagEsit").value(hasItem(DEFAULT_FLAG_ESIT.toString())))
            .andExpect(jsonPath("$.[*].dataScar").value(hasItem(DEFAULT_DATA_SCAR.toString())))
            .andExpect(jsonPath("$.[*].c557").value(hasItem(DEFAULT_C_557.doubleValue())))
            .andExpect(jsonPath("$.[*].c109a").value(hasItem(DEFAULT_C_109_A.toString())))
            .andExpect(jsonPath("$.[*].c400").value(hasItem(DEFAULT_C_400)))
            .andExpect(jsonPath("$.[*].c401").value(hasItem(DEFAULT_C_401.toString())))
            .andExpect(jsonPath("$.[*].c402").value(hasItem(DEFAULT_C_402.doubleValue())))
            .andExpect(jsonPath("$.[*].c403").value(hasItem(DEFAULT_C_403.toString())))
            .andExpect(jsonPath("$.[*].c404").value(hasItem(DEFAULT_C_404.toString())))
            .andExpect(jsonPath("$.[*].c405").value(hasItem(DEFAULT_C_405)))
            .andExpect(jsonPath("$.[*].c406").value(hasItem(DEFAULT_C_406.doubleValue())))
            .andExpect(jsonPath("$.[*].c407").value(hasItem(DEFAULT_C_407.toString())))
            .andExpect(jsonPath("$.[*].c558a").value(hasItem(DEFAULT_C_558_A.doubleValue())))
            .andExpect(jsonPath("$.[*].c558b").value(hasItem(DEFAULT_C_558_B.doubleValue())))
            .andExpect(jsonPath("$.[*].c558c").value(hasItem(DEFAULT_C_558_C.doubleValue())))
            .andExpect(jsonPath("$.[*].c558d").value(hasItem(DEFAULT_C_558_D.doubleValue())))
            .andExpect(jsonPath("$.[*].c558e").value(hasItem(DEFAULT_C_558_E.doubleValue())))
            .andExpect(jsonPath("$.[*].c558f").value(hasItem(DEFAULT_C_558_F.doubleValue())))
            .andExpect(jsonPath("$.[*].c620").value(hasItem(DEFAULT_C_620.toString())));
    }
}
