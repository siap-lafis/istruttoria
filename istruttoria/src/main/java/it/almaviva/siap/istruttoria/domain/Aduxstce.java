package it.almaviva.siap.istruttoria.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.SQLData;
import java.util.Objects;

/**
 * A Aduxstce.
 */
@Entity
@Table(name = "ADUXSTCE_TAB")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "aduxstce")
//@NamedStoredProcedureQueries({	   
//	   @NamedStoredProcedureQuery(name = "getControlliIstruttoriDU", 
//	                              procedureName = "aduaax001.getControlliIstruttoriDU",
//	                              parameters = {
//	                                 @StoredProcedureParameter(mode = ParameterMode.IN, name = "idAttoAmmiIn", type = Long.class),
//	                                 @StoredProcedureParameter(mode = ParameterMode.IN, name = "idDecrIn", type = Long.class),
//	                                 @StoredProcedureParameter(mode = ParameterMode.IN, name = "numeCampIn", type = Integer.class),
//	                                 @StoredProcedureParameter(mode = ParameterMode.IN, name = "dataIn", type = SQLData.class)
//	                              })
//	})
public class Aduxstce implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_STCE")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_atto_ammi")
    private Long idAttoAmmi;

    @Column(name = "id_decr")
    private Long idDecr;

    @Column(name = "nume_decr")
    private Long numeDecr;

    @Column(name = "id_inte")
    private Long idInte;

    @Column(name = "codi_inte")
    private String codiInte;

    @Column(name = "data_stor")
    private String dataStor;

    @Column(name = "f100")
    private String f100;

    @Column(name = "c110")
    private String c110;

    @Column(name = "c109")
    private String c109;

    @Column(name = "f200")
    private String f200;

    @Column(name = "f201")
    private String f201;

    @Column(name = "f202a")
    private String f202a;

    @Column(name = "f202b")
    private String f202b;

    @Column(name = "f202c")
    private String f202c;

    @Column(name = "f207")
    private String f207;

    @Column(name = "f300")
    private String f300;

    @Column(name = "c300a")
    private String c300a;

    @Column(name = "f300b")
    private String f300b;

    @Column(name = "c551")
    private Float c551;

    @Column(name = "c552")
    private Float c552;

    @Column(name = "c553")
    private String c553;

    @Column(name = "c554")
    private Float c554;

    @Column(name = "c558")
    private Float c558;

    @Column(name = "c559")
    private Float c559;

    @Column(name = "c560")
    private Float c560;

    @Column(name = "c561")
    private Float c561;

    @Column(name = "c600")
    private String c600;

    @Column(name = "c611")
    private String c611;

    @Column(name = "c621")
    private String c621;

    @Column(name = "c633")
    private String c633;

    @Column(name = "c634")
    private String c634;

    @Column(name = "c640")
    private Float c640;

    @Column(name = "unit_misu")
    private String unitMisu;

    @Column(name = "data_cont_ogge")
    private String dataContOgge;

    @Column(name = "qnta_liqu")
    private Float qntaLiqu;

    @Column(name = "impo_liqu")
    private Float impoLiqu;

    @Column(name = "ridu_rita_depo")
    private Float riduRitaDepo;

    @Column(name = "ridu_modu")
    private Float riduModu;

    @Column(name = "disc_fina")
    private Float discFina;

    @Column(name = "sanz_cond")
    private Float sanzCond;

    @Column(name = "stat_istr")
    private Long statIstr;

    @Column(name = "live_ammi")
    private Long liveAmmi;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "data_aggi")
    private String dataAggi;

    @Column(name = "c640_qnta")
    private Float c640Qnta;

    @Column(name = "deco_stat")
    private Long decoStat;

    @Column(name = "flag_esit")
    private String flagEsit;

    @Column(name = "data_scar")
    private String dataScar;

    @Column(name = "c557")
    private Float c557;

    @Column(name = "c109a")
    private String c109a;

    @Column(name = "c400")
    private Integer c400;

    @Column(name = "c401")
    private String c401;

    @Column(name = "c402")
    private Float c402;

    @Column(name = "c403")
    private String c403;

    @Column(name = "c404")
    private String c404;

    @Column(name = "c405")
    private Integer c405;

    @Column(name = "c406")
    private Float c406;

    @Column(name = "c407")
    private String c407;

    @Column(name = "c558a")
    private Float c558a;

    @Column(name = "c558b")
    private Float c558b;

    @Column(name = "c558c")
    private Float c558c;

    @Column(name = "c558d")
    private Float c558d;

    @Column(name = "c558e")
    private Float c558e;

    @Column(name = "c558f")
    private Float c558f;

    @Column(name = "c620")
    private String c620;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAttoAmmi() {
        return idAttoAmmi;
    }

    public void setIdAttoAmmi(Long idAttoAmmi) {
        this.idAttoAmmi = idAttoAmmi;
    }

    public Long getIdDecr() {
        return idDecr;
    }

    public void setIdDecr(Long idDecr) {
        this.idDecr = idDecr;
    }

    public Long getNumeDecr() {
        return numeDecr;
    }

    public void setNumeDecr(Long numeDecr) {
        this.numeDecr = numeDecr;
    }

    public Long getIdInte() {
        return idInte;
    }

    public void setIdInte(Long idInte) {
        this.idInte = idInte;
    }

    public String getCodiInte() {
        return codiInte;
    }

    public void setCodiInte(String codiInte) {
        this.codiInte = codiInte;
    }

    public String getDataStor() {
        return dataStor;
    }

    public void setDataStor(String dataStor) {
        this.dataStor = dataStor;
    }

    public String getf100() {
        return f100;
    }

    public void setf100(String f100) {
        this.f100 = f100;
    }

    public String getc110() {
        return c110;
    }

    public void setc110(String c110) {
        this.c110 = c110;
    }

    public String getc109() {
        return c109;
    }

    public void setc109(String c109) {
        this.c109 = c109;
    }

    public String getf200() {
        return f200;
    }

    public void setf200(String f200) {
        this.f200 = f200;
    }

    public String getf201() {
        return f201;
    }

    public void setf201(String f201) {
        this.f201 = f201;
    }

    public String getf202a() {
        return f202a;
    }

    public void setf202a(String f202a) {
        this.f202a = f202a;
    }

    public String getf202b() {
        return f202b;
    }

    public void setf202b(String f202b) {
        this.f202b = f202b;
    }

    public String getf202c() {
        return f202c;
    }

    public void setf202c(String f202c) {
        this.f202c = f202c;
    }

    public String getf207() {
        return f207;
    }

    public void setf207(String f207) {
        this.f207 = f207;
    }

    public String getf300() {
        return f300;
    }

    public void setf300(String f300) {
        this.f300 = f300;
    }

    public String getc300a() {
        return c300a;
    }

    public void setc300a(String c300a) {
        this.c300a = c300a;
    }

    public String getf300b() {
        return f300b;
    }

    public void setf300b(String f300b) {
        this.f300b = f300b;
    }

    public Float getc551() {
        return c551;
    }

    public void setc551(Float c551) {
        this.c551 = c551;
    }

    public Float getc552() {
        return c552;
    }

    public void setc552(Float c552) {
        this.c552 = c552;
    }

    public String getc553() {
        return c553;
    }

    public void setc553(String c553) {
        this.c553 = c553;
    }

    public Float getc554() {
        return c554;
    }

    public void setc554(Float c554) {
        this.c554 = c554;
    }

    public Float getc558() {
        return c558;
    }

    public void setc558(Float c558) {
        this.c558 = c558;
    }

    public Float getc559() {
        return c559;
    }

    public void setc559(Float c559) {
        this.c559 = c559;
    }

    public Float getc560() {
        return c560;
    }

    public void setc560(Float c560) {
        this.c560 = c560;
    }

    public Float getc561() {
        return c561;
    }

    public void setc561(Float c561) {
        this.c561 = c561;
    }

    public String getc600() {
        return c600;
    }

    public void setc600(String c600) {
        this.c600 = c600;
    }

    public String getc611() {
        return c611;
    }

    public void setc611(String c611) {
        this.c611 = c611;
    }

    public String getc621() {
        return c621;
    }

    public void setc621(String c621) {
        this.c621 = c621;
    }

    public String getc633() {
        return c633;
    }

    public void setc633(String c633) {
        this.c633 = c633;
    }

    public String getc634() {
        return c634;
    }

    public void setc634(String c634) {
        this.c634 = c634;
    }

    public Float getc640() {
        return c640;
    }

    public void setc640(Float c640) {
        this.c640 = c640;
    }

    public String getUnitMisu() {
        return unitMisu;
    }

    public void setUnitMisu(String unitMisu) {
        this.unitMisu = unitMisu;
    }

    public String getDataContOgge() {
        return dataContOgge;
    }

    public void setDataContOgge(String dataContOgge) {
        this.dataContOgge = dataContOgge;
    }

    public Float getQntaLiqu() {
        return qntaLiqu;
    }

    public void setQntaLiqu(Float qntaLiqu) {
        this.qntaLiqu = qntaLiqu;
    }

    public Float getImpoLiqu() {
        return impoLiqu;
    }

    public void setImpoLiqu(Float impoLiqu) {
        this.impoLiqu = impoLiqu;
    }

    public Float getRiduRitaDepo() {
        return riduRitaDepo;
    }

    public void setRiduRitaDepo(Float riduRitaDepo) {
        this.riduRitaDepo = riduRitaDepo;
    }

    public Float getRiduModu() {
        return riduModu;
    }

    public void setRiduModu(Float riduModu) {
        this.riduModu = riduModu;
    }

    public Float getDiscFina() {
        return discFina;
    }

    public void setDiscFina(Float discFina) {
        this.discFina = discFina;
    }

    public Float getSanzCond() {
        return sanzCond;
    }

    public void setSanzCond(Float sanzCond) {
        this.sanzCond = sanzCond;
    }

    public Long getStatIstr() {
        return statIstr;
    }

    public void setStatIstr(Long statIstr) {
        this.statIstr = statIstr;
    }

    public Long getLiveAmmi() {
        return liveAmmi;
    }

    public void setLiveAmmi(Long liveAmmi) {
        this.liveAmmi = liveAmmi;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDataAggi() {
        return dataAggi;
    }

    public void setDataAggi(String dataAggi) {
        this.dataAggi = dataAggi;
    }

    public Float getc640Qnta() {
        return c640Qnta;
    }

    public void setc640Qnta(Float c640Qnta) {
        this.c640Qnta = c640Qnta;
    }

    public Long getDecoStat() {
        return decoStat;
    }

    public void setDecoStat(Long decoStat) {
        this.decoStat = decoStat;
    }

    public String getFlagEsit() {
        return flagEsit;
    }

    public void setFlagEsit(String flagEsit) {
        this.flagEsit = flagEsit;
    }

    public String getDataScar() {
        return dataScar;
    }

    public void setDataScar(String dataScar) {
        this.dataScar = dataScar;
    }

    public Float getc557() {
        return c557;
    }

    public void setc557(Float c557) {
        this.c557 = c557;
    }

    public String getc109a() {
        return c109a;
    }

    public void setc109a(String c109a) {
        this.c109a = c109a;
    }

    public Integer getc400() {
        return c400;
    }

    public void setc400(Integer c400) {
        this.c400 = c400;
    }

    public String getc401() {
        return c401;
    }

    public void setc401(String c401) {
        this.c401 = c401;
    }

    public Float getc402() {
        return c402;
    }

    public void setc402(Float c402) {
        this.c402 = c402;
    }

    public String getc403() {
        return c403;
    }

    public void setc403(String c403) {
        this.c403 = c403;
    }

    public String getc404() {
        return c404;
    }

    public void setc404(String c404) {
        this.c404 = c404;
    }

    public Integer getc405() {
        return c405;
    }

    public void setc405(Integer c405) {
        this.c405 = c405;
    }

    public Float getc406() {
        return c406;
    }

    public void setc406(Float c406) {
        this.c406 = c406;
    }

    public String getc407() {
        return c407;
    }

    public void setc407(String c407) {
        this.c407 = c407;
    }

    public Float getc558a() {
        return c558a;
    }

    public void setc558a(Float c558a) {
        this.c558a = c558a;
    }

    public Float getc558b() {
        return c558b;
    }

    public void setc558b(Float c558b) {
        this.c558b = c558b;
    }

    public Float getc558c() {
        return c558c;
    }

    public void setc558c(Float c558c) {
        this.c558c = c558c;
    }

    public Float getc558d() {
        return c558d;
    }

    public void setc558d(Float c558d) {
        this.c558d = c558d;
    }

    public Float getc558e() {
        return c558e;
    }

    public void setc558e(Float c558e) {
        this.c558e = c558e;
    }

    public Float getc558f() {
        return c558f;
    }

    public void setc558f(Float c558f) {
        this.c558f = c558f;
    }

    public String getc620() {
        return c620;
    }

    public void setc620(String c620) {
        this.c620 = c620;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Aduxstce aduxstce = (Aduxstce) o;
        if(aduxstce.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, aduxstce.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Aduxstce{" +
            "id=" + id +
            ", idAttoAmmi='" + idAttoAmmi + "'" +
            ", idDecr='" + idDecr + "'" +
            ", numeDecr='" + numeDecr + "'" +
            ", idEnte='" + idInte + "'" +
            ", codiInte='" + codiInte + "'" +
            ", dataStor='" + dataStor + "'" +
            ", f100='" + f100 + "'" +
            ", c110='" + c110 + "'" +
            ", c109='" + c109 + "'" +
            ", f200='" + f200 + "'" +
            ", f201='" + f201 + "'" +
            ", f202a='" + f202a + "'" +
            ", f202b='" + f202b + "'" +
            ", f202c='" + f202c + "'" +
            ", f207='" + f207 + "'" +
            ", f300='" + f300 + "'" +
            ", c300a='" + c300a + "'" +
            ", f300b='" + f300b + "'" +
            ", c551='" + c551 + "'" +
            ", c552='" + c552 + "'" +
            ", c553='" + c553 + "'" +
            ", c554='" + c554 + "'" +
            ", c558='" + c558 + "'" +
            ", c559='" + c559 + "'" +
            ", c560='" + c560 + "'" +
            ", c561='" + c561 + "'" +
            ", c600='" + c600 + "'" +
            ", c611='" + c611 + "'" +
            ", c621='" + c621 + "'" +
            ", c633='" + c633 + "'" +
            ", c634='" + c634 + "'" +
            ", c640='" + c640 + "'" +
            ", unitMisu='" + unitMisu + "'" +
            ", dataContOgge='" + dataContOgge + "'" +
            ", qntaLiqu='" + qntaLiqu + "'" +
            ", impoLiqu='" + impoLiqu + "'" +
            ", riduRitaDepo='" + riduRitaDepo + "'" +
            ", riduModu='" + riduModu + "'" +
            ", discFina='" + discFina + "'" +
            ", sanzCond='" + sanzCond + "'" +
            ", statIstr='" + statIstr + "'" +
            ", liveAmmi='" + liveAmmi + "'" +
            ", userName='" + userName + "'" +
            ", dataAggi='" + dataAggi + "'" +
            ", c640Qnta='" + c640Qnta + "'" +
            ", decoStat='" + decoStat + "'" +
            ", flagEsit='" + flagEsit + "'" +
            ", dataScar='" + dataScar + "'" +
            ", c557='" + c557 + "'" +
            ", c109a='" + c109a + "'" +
            ", c400='" + c400 + "'" +
            ", c401='" + c401 + "'" +
            ", c402='" + c402 + "'" +
            ", c403='" + c403 + "'" +
            ", c404='" + c404 + "'" +
            ", c405='" + c405 + "'" +
            ", c406='" + c406 + "'" +
            ", c407='" + c407 + "'" +
            ", c558a='" + c558a + "'" +
            ", c558b='" + c558b + "'" +
            ", c558c='" + c558c + "'" +
            ", c558d='" + c558d + "'" +
            ", c558e='" + c558e + "'" +
            ", c558f='" + c558f + "'" +
            ", c620='" + c620 + "'" +
            '}';
    }
}
