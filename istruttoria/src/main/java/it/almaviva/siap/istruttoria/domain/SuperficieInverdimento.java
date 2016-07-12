package it.almaviva.siap.istruttoria.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SuperficieInverdimento.
 */
@Entity
@Table(name = "superficie_inverdimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "superficieinverdimento")
public class SuperficieInverdimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "supe_semi")
    private Integer supeSemi;

    @Column(name = "supe_prim_colt")
    private Integer supePrimColt;

    @Column(name = "supe_seco_colt")
    private Integer supeSecoColt;

    @Column(name = "supe_altr_colt")
    private Integer supeAltrColt;

    @Column(name = "supe_prim_max")
    private Integer supePrimMax;

    @Column(name = "supe_seco_max")
    private Integer supeSecoMax;

    @Column(name = "supe_prim_diff_1")
    private Integer supePrimDiff1;

    @Column(name = "supe_prim_diff_2")
    private Integer supePrimDiff2;

    @Column(name = "tasso_diff_prim")
    private Float tassoDiffPrim;

    @Column(name = "supe_prim_ridu")
    private Integer supePrimRidu;

    @Column(name = "supe_seco_diff_1")
    private Integer supeSecoDiff1;

    @Column(name = "supe_seco_diff_2")
    private Integer supeSecoDiff2;

    @Column(name = "tasso_diff_seco")
    private Float tassoDiffSeco;

    @Column(name = "supe_seco_ridu")
    private Integer supeSecoRidu;

    @Column(name = "tota_tasso_diff")
    private Float totaTassoDiff;

    @Column(name = "supe_ridu_dive")
    private Integer supeRiduDive;

    @Column(name = "supe_efa")
    private Integer supeEfa;

    @Column(name = "supe_efa_obbl")
    private Integer supeEfaObbl;

    @Column(name = "supe_efa_diff")
    private Integer supeEfaDiff;

    @Column(name = "tasso_diff_efa")
    private Float tassoDiffEfa;

    @Column(name = "supe_ridu_efa")
    private Integer supeRiduEfa;

    @Column(name = "tota_ridu")
    private Integer totaRidu;

    @Column(name = "supe_paga_semi")
    private Integer supePagaSemi;

    @Column(name = "supe_prat_sens")
    private Integer supePratSens;

    @Column(name = "supe_prat_nsens")
    private Integer supePratNsens;

    @Column(name = "supe_perm")
    private Integer supePerm;

    @Column(name = "supe_inve")
    private Integer supeInve;

    @OneToOne
    @JoinColumn(unique = true)
    private Domanda domanda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSupeSemi() {
        return supeSemi;
    }

    public void setSupeSemi(Integer supeSemi) {
        this.supeSemi = supeSemi;
    }

    public Integer getSupePrimColt() {
        return supePrimColt;
    }

    public void setSupePrimColt(Integer supePrimColt) {
        this.supePrimColt = supePrimColt;
    }

    public Integer getSupeSecoColt() {
        return supeSecoColt;
    }

    public void setSupeSecoColt(Integer supeSecoColt) {
        this.supeSecoColt = supeSecoColt;
    }

    public Integer getSupeAltrColt() {
        return supeAltrColt;
    }

    public void setSupeAltrColt(Integer supeAltrColt) {
        this.supeAltrColt = supeAltrColt;
    }

    public Integer getSupePrimMax() {
        return supePrimMax;
    }

    public void setSupePrimMax(Integer supePrimMax) {
        this.supePrimMax = supePrimMax;
    }

    public Integer getSupeSecoMax() {
        return supeSecoMax;
    }

    public void setSupeSecoMax(Integer supeSecoMax) {
        this.supeSecoMax = supeSecoMax;
    }

    public Integer getSupePrimDiff1() {
        return supePrimDiff1;
    }

    public void setSupePrimDiff1(Integer supePrimDiff1) {
        this.supePrimDiff1 = supePrimDiff1;
    }

    public Integer getSupePrimDiff2() {
        return supePrimDiff2;
    }

    public void setSupePrimDiff2(Integer supePrimDiff2) {
        this.supePrimDiff2 = supePrimDiff2;
    }

    public Float getTassoDiffPrim() {
        return tassoDiffPrim;
    }

    public void setTassoDiffPrim(Float tassoDiffPrim) {
        this.tassoDiffPrim = tassoDiffPrim;
    }

    public Integer getSupePrimRidu() {
        return supePrimRidu;
    }

    public void setSupePrimRidu(Integer supePrimRidu) {
        this.supePrimRidu = supePrimRidu;
    }

    public Integer getSupeSecoDiff1() {
        return supeSecoDiff1;
    }

    public void setSupeSecoDiff1(Integer supeSecoDiff1) {
        this.supeSecoDiff1 = supeSecoDiff1;
    }

    public Integer getSupeSecoDiff2() {
        return supeSecoDiff2;
    }

    public void setSupeSecoDiff2(Integer supeSecoDiff2) {
        this.supeSecoDiff2 = supeSecoDiff2;
    }

    public Float getTassoDiffSeco() {
        return tassoDiffSeco;
    }

    public void setTassoDiffSeco(Float tassoDiffSeco) {
        this.tassoDiffSeco = tassoDiffSeco;
    }

    public Integer getSupeSecoRidu() {
        return supeSecoRidu;
    }

    public void setSupeSecoRidu(Integer supeSecoRidu) {
        this.supeSecoRidu = supeSecoRidu;
    }

    public Float getTotaTassoDiff() {
        return totaTassoDiff;
    }

    public void setTotaTassoDiff(Float totaTassoDiff) {
        this.totaTassoDiff = totaTassoDiff;
    }

    public Integer getSupeRiduDive() {
        return supeRiduDive;
    }

    public void setSupeRiduDive(Integer supeRiduDive) {
        this.supeRiduDive = supeRiduDive;
    }

    public Integer getSupeEfa() {
        return supeEfa;
    }

    public void setSupeEfa(Integer supeEfa) {
        this.supeEfa = supeEfa;
    }

    public Integer getSupeEfaObbl() {
        return supeEfaObbl;
    }

    public void setSupeEfaObbl(Integer supeEfaObbl) {
        this.supeEfaObbl = supeEfaObbl;
    }

    public Integer getSupeEfaDiff() {
        return supeEfaDiff;
    }

    public void setSupeEfaDiff(Integer supeEfaDiff) {
        this.supeEfaDiff = supeEfaDiff;
    }

    public Float getTassoDiffEfa() {
        return tassoDiffEfa;
    }

    public void setTassoDiffEfa(Float tassoDiffEfa) {
        this.tassoDiffEfa = tassoDiffEfa;
    }

    public Integer getSupeRiduEfa() {
        return supeRiduEfa;
    }

    public void setSupeRiduEfa(Integer supeRiduEfa) {
        this.supeRiduEfa = supeRiduEfa;
    }

    public Integer getTotaRidu() {
        return totaRidu;
    }

    public void setTotaRidu(Integer totaRidu) {
        this.totaRidu = totaRidu;
    }

    public Integer getSupePagaSemi() {
        return supePagaSemi;
    }

    public void setSupePagaSemi(Integer supePagaSemi) {
        this.supePagaSemi = supePagaSemi;
    }

    public Integer getSupePratSens() {
        return supePratSens;
    }

    public void setSupePratSens(Integer supePratSens) {
        this.supePratSens = supePratSens;
    }

    public Integer getSupePratNsens() {
        return supePratNsens;
    }

    public void setSupePratNsens(Integer supePratNsens) {
        this.supePratNsens = supePratNsens;
    }

    public Integer getSupePerm() {
        return supePerm;
    }

    public void setSupePerm(Integer supePerm) {
        this.supePerm = supePerm;
    }

    public Integer getSupeInve() {
        return supeInve;
    }

    public void setSupeInve(Integer supeInve) {
        this.supeInve = supeInve;
    }

    public Domanda getDomanda() {
        return domanda;
    }

    public void setDomanda(Domanda domanda) {
        this.domanda = domanda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SuperficieInverdimento superficieInverdimento = (SuperficieInverdimento) o;
        if(superficieInverdimento.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, superficieInverdimento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SuperficieInverdimento{" +
            "id=" + id +
            ", supeSemi='" + supeSemi + "'" +
            ", supePrimColt='" + supePrimColt + "'" +
            ", supeSecoColt='" + supeSecoColt + "'" +
            ", supeAltrColt='" + supeAltrColt + "'" +
            ", supePrimMax='" + supePrimMax + "'" +
            ", supeSecoMax='" + supeSecoMax + "'" +
            ", supePrimDiff1='" + supePrimDiff1 + "'" +
            ", supePrimDiff2='" + supePrimDiff2 + "'" +
            ", tassoDiffPrim='" + tassoDiffPrim + "'" +
            ", supePrimRidu='" + supePrimRidu + "'" +
            ", supeSecoDiff1='" + supeSecoDiff1 + "'" +
            ", supeSecoDiff2='" + supeSecoDiff2 + "'" +
            ", tassoDiffSeco='" + tassoDiffSeco + "'" +
            ", supeSecoRidu='" + supeSecoRidu + "'" +
            ", totaTassoDiff='" + totaTassoDiff + "'" +
            ", supeRiduDive='" + supeRiduDive + "'" +
            ", supeEfa='" + supeEfa + "'" +
            ", supeEfaObbl='" + supeEfaObbl + "'" +
            ", supeEfaDiff='" + supeEfaDiff + "'" +
            ", tassoDiffEfa='" + tassoDiffEfa + "'" +
            ", supeRiduEfa='" + supeRiduEfa + "'" +
            ", totaRidu='" + totaRidu + "'" +
            ", supePagaSemi='" + supePagaSemi + "'" +
            ", supePratSens='" + supePratSens + "'" +
            ", supePratNsens='" + supePratNsens + "'" +
            ", supePerm='" + supePerm + "'" +
            ", supeInve='" + supeInve + "'" +
            '}';
    }
}
