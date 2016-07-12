package it.almaviva.siap.istruttoria.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Superficie.
 */
@Entity
@Table(name = "superficie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "superficie")
public class Superficie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cod_nazionale")
    private String codNazionale;

    @Column(name = "foglio")
    private Integer foglio;

    @Column(name = "cod_intervento")
    private String codIntervento;

    @Column(name = "cod_coltura")
    private String codColtura;

    @Column(name = "supe_dich")
    private Integer supeDich;

    @Column(name = "supe_ammi")
    private Integer supeAmmi;

    @Column(name = "supe_ammi_netta")
    private Integer supeAmmiNetta;

    @OneToOne
    @JoinColumn(unique = true)
    private Domanda domanda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodNazionale() {
        return codNazionale;
    }

    public void setCodNazionale(String codNazionale) {
        this.codNazionale = codNazionale;
    }

    public Integer getFoglio() {
        return foglio;
    }

    public void setFoglio(Integer foglio) {
        this.foglio = foglio;
    }

    public String getCodIntervento() {
        return codIntervento;
    }

    public void setCodIntervento(String codIntervento) {
        this.codIntervento = codIntervento;
    }

    public String getCodColtura() {
        return codColtura;
    }

    public void setCodColtura(String codColtura) {
        this.codColtura = codColtura;
    }

    public Integer getSupeDich() {
        return supeDich;
    }

    public void setSupeDich(Integer supeDich) {
        this.supeDich = supeDich;
    }

    public Integer getSupeAmmi() {
        return supeAmmi;
    }

    public void setSupeAmmi(Integer supeAmmi) {
        this.supeAmmi = supeAmmi;
    }

    public Integer getSupeAmmiNetta() {
        return supeAmmiNetta;
    }

    public void setSupeAmmiNetta(Integer supeAmmiNetta) {
        this.supeAmmiNetta = supeAmmiNetta;
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
        Superficie superficie = (Superficie) o;
        if(superficie.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, superficie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Superficie{" +
            "id=" + id +
            ", codNazionale='" + codNazionale + "'" +
            ", foglio='" + foglio + "'" +
            ", codIntervento='" + codIntervento + "'" +
            ", codColtura='" + codColtura + "'" +
            ", supeDich='" + supeDich + "'" +
            ", supeAmmi='" + supeAmmi + "'" +
            ", supeAmmiNetta='" + supeAmmiNetta + "'" +
            '}';
    }
}
