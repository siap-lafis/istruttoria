package it.almaviva.siap.istruttoria.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Pagamento.
 */
@Entity
@Table(name = "pagamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pagamento")
public class Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cod_intervento")
    private String codIntervento;

    @Column(name = "qnta_dich")
    private Float qntaDich;

    @Column(name = "qnta_amme")
    private Float qntaAmme;

    @Column(name = "qnta_liqu")
    private Float qntaLiqu;

    @Column(name = "impo_dich")
    private Float impoDich;

    @Column(name = "impo_amme")
    private Float impoAmme;

    @Column(name = "impo_liqu")
    private Float impoLiqu;

    @Column(name = "stat_liqu")
    private Integer statLiqu;

    @Column(name = "unit_misu")
    private String unitMisu;

    @Column(name = "codi_nume_capi_spes")
    private String codiNumeCapiSpes;

    @Column(name = "data_elab")
    private String dataElab;

    @Column(name = "codi_esi_gcol")
    private Integer codiEsiGcol;

    @Column(name = "perc_sanz_gcol")
    private Float percSanzGcol;

    @Column(name = "perc_sanz_azie")
    private Float percSanzAzie;

    @Column(name = "valo_medi_tito")
    private Float valoMediTito;

    @Column(name = "impo_trat_modu")
    private Float impoTratModu;

    @Column(name = "fasc_modu")
    private Integer fascModu;

    @Column(name = "impo_trat_fina")
    private Float impoTratFina;

    @OneToOne
    @JoinColumn(unique = true)
    private ElencoPagamento elencoPagamento;
    
    @Column(name = "ID_ATTO_AMMI")
    private Integer idAttoAmmi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodIntervento() {
        return codIntervento;
    }

    public void setCodIntervento(String codIntervento) {
        this.codIntervento = codIntervento;
    }

    public Float getQntaDich() {
        return qntaDich;
    }

    public void setQntaDich(Float qntaDich) {
        this.qntaDich = qntaDich;
    }

    public Float getQntaAmme() {
        return qntaAmme;
    }

    public void setQntaAmme(Float qntaAmme) {
        this.qntaAmme = qntaAmme;
    }

    public Float getQntaLiqu() {
        return qntaLiqu;
    }

    public void setQntaLiqu(Float qntaLiqu) {
        this.qntaLiqu = qntaLiqu;
    }

    public Float getImpoDich() {
        return impoDich;
    }

    public void setImpoDich(Float impoDich) {
        this.impoDich = impoDich;
    }

    public Float getImpoAmme() {
        return impoAmme;
    }

    public void setImpoAmme(Float impoAmme) {
        this.impoAmme = impoAmme;
    }

    public Float getImpoLiqu() {
        return impoLiqu;
    }

    public void setImpoLiqu(Float impoLiqu) {
        this.impoLiqu = impoLiqu;
    }

    public Integer getStatLiqu() {
        return statLiqu;
    }

    public void setStatLiqu(Integer statLiqu) {
        this.statLiqu = statLiqu;
    }

    public String getUnitMisu() {
        return unitMisu;
    }

    public void setUnitMisu(String unitMisu) {
        this.unitMisu = unitMisu;
    }

    public String getCodiNumeCapiSpes() {
        return codiNumeCapiSpes;
    }

    public void setCodiNumeCapiSpes(String codiNumeCapiSpes) {
        this.codiNumeCapiSpes = codiNumeCapiSpes;
    }

    public String getDataElab() {
        return dataElab;
    }

    public void setDataElab(String dataElab) {
        this.dataElab = dataElab;
    }

    public Integer getCodiEsiGcol() {
        return codiEsiGcol;
    }

    public void setCodiEsiGcol(Integer codiEsiGcol) {
        this.codiEsiGcol = codiEsiGcol;
    }

    public Float getPercSanzGcol() {
        return percSanzGcol;
    }

    public void setPercSanzGcol(Float percSanzGcol) {
        this.percSanzGcol = percSanzGcol;
    }

    public Float getPercSanzAzie() {
        return percSanzAzie;
    }

    public void setPercSanzAzie(Float percSanzAzie) {
        this.percSanzAzie = percSanzAzie;
    }

    public Float getValoMediTito() {
        return valoMediTito;
    }

    public void setValoMediTito(Float valoMediTito) {
        this.valoMediTito = valoMediTito;
    }

    public Float getImpoTratModu() {
        return impoTratModu;
    }

    public void setImpoTratModu(Float impoTratModu) {
        this.impoTratModu = impoTratModu;
    }

    public Integer getFascModu() {
        return fascModu;
    }

    public void setFascModu(Integer fascModu) {
        this.fascModu = fascModu;
    }

    public Float getImpoTratFina() {
        return impoTratFina;
    }

    public void setImpoTratFina(Float impoTratFina) {
        this.impoTratFina = impoTratFina;
    }

    public ElencoPagamento getElencoPagamento() {
        return elencoPagamento;
    }

    public void setElencoPagamento(ElencoPagamento elencoPagamento) {
        this.elencoPagamento = elencoPagamento;
    }
    
    

    public Integer getIdAttoAmmi() {
		return idAttoAmmi;
	}

	public void setIdAttoAmmi(Integer idAttoAmmi) {
		this.idAttoAmmi = idAttoAmmi;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pagamento pagamento = (Pagamento) o;
        if(pagamento.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pagamento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pagamento{" +
            "id=" + id +
            ", codIntervento='" + codIntervento + "'" +
            ", qntaDich='" + qntaDich + "'" +
            ", qntaAmme='" + qntaAmme + "'" +
            ", qntaLiqu='" + qntaLiqu + "'" +
            ", impoDich='" + impoDich + "'" +
            ", impoAmme='" + impoAmme + "'" +
            ", impoLiqu='" + impoLiqu + "'" +
            ", statLiqu='" + statLiqu + "'" +
            ", unitMisu='" + unitMisu + "'" +
            ", codiNumeCapiSpes='" + codiNumeCapiSpes + "'" +
            ", dataElab='" + dataElab + "'" +
            ", codiEsiGcol='" + codiEsiGcol + "'" +
            ", percSanzGcol='" + percSanzGcol + "'" +
            ", percSanzAzie='" + percSanzAzie + "'" +
            ", valoMediTito='" + valoMediTito + "'" +
            ", impoTratModu='" + impoTratModu + "'" +
            ", fascModu='" + fascModu + "'" +
            ", impoTratFina='" + impoTratFina + "'" +
            '}';
    }
}
