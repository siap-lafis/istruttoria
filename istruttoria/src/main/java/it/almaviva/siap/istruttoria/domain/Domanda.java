package it.almaviva.siap.istruttoria.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Domanda.
 */
@Entity
@Table(name = "domanda")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "domanda")
public class Domanda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_domanda")
    private Integer idDomanda;

    @Column(name = "data_pres")
    private String dataPres;

    @Column(name = "data_inse")
    private String dataInse;

    @Column(name = "cod_settore")
    private String codSettore;

    @Column(name = "anno")
    private Integer anno;

    @OneToOne
    @JoinColumn(unique = true)
    private Soggetto soggetto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdDomanda() {
        return idDomanda;
    }

    public void setIdDomanda(Integer idDomanda) {
        this.idDomanda = idDomanda;
    }

    public String getDataPres() {
        return dataPres;
    }

    public void setDataPres(String dataPres) {
        this.dataPres = dataPres;
    }

    public String getDataInse() {
        return dataInse;
    }

    public void setDataInse(String dataInse) {
        this.dataInse = dataInse;
    }

    public String getCodSettore() {
        return codSettore;
    }

    public void setCodSettore(String codSettore) {
        this.codSettore = codSettore;
    }

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public Soggetto getSoggetto() {
        return soggetto;
    }

    public void setSoggetto(Soggetto soggetto) {
        this.soggetto = soggetto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Domanda domanda = (Domanda) o;
        if(domanda.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, domanda.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Domanda{" +
            "id=" + id +
            ", idDomanda='" + idDomanda + "'" +
            ", dataPres='" + dataPres + "'" +
            ", dataInse='" + dataInse + "'" +
            ", codSettore='" + codSettore + "'" +
            ", anno='" + anno + "'" +
            '}';
    }
}
