package it.almaviva.siap.istruttoria.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CapoPagato.
 */
@Entity
@Table(name = "capo_pagato")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "capopagato")
public class CapoPagato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "marca_capo")
    private String marcaCapo;

    @Column(name = "ammissibile")
    private String ammissibile;

    @Column(name = "num_uba")
    private Integer numUba;

    @Column(name = "mancanza_analisi_latte")
    private String mancanzaAnalisiLatte;

    @Column(name = "medie_latte_soma")
    private Integer medieLatteSoma;

    @Column(name = "medie_latte_germ")
    private Integer medieLatteGerm;

    @Column(name = "medie_latte_prot")
    private Float medieLatteProt;

    @Column(name = "cod_asl")
    private String codAsl;

    @Column(name = "flag_sess")
    private String flagSess;

    @Column(name = "data_nasc")
    private String dataNasc;

    @Column(name = "codi_razz")
    private String codiRazz;

    @Column(name = "data_iniz_dete")
    private String dataInizDete;

    @Column(name = "data_fine_dete")
    private String dataFineDete;

    @OneToOne
    @JoinColumn(unique = true)
    private Pagamento pagamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarcaCapo() {
        return marcaCapo;
    }

    public void setMarcaCapo(String marcaCapo) {
        this.marcaCapo = marcaCapo;
    }

    public String getAmmissibile() {
        return ammissibile;
    }

    public void setAmmissibile(String ammissibile) {
        this.ammissibile = ammissibile;
    }

    public Integer getNumUba() {
        return numUba;
    }

    public void setNumUba(Integer numUba) {
        this.numUba = numUba;
    }

    public String getMancanzaAnalisiLatte() {
        return mancanzaAnalisiLatte;
    }

    public void setMancanzaAnalisiLatte(String mancanzaAnalisiLatte) {
        this.mancanzaAnalisiLatte = mancanzaAnalisiLatte;
    }

    public Integer getMedieLatteSoma() {
        return medieLatteSoma;
    }

    public void setMedieLatteSoma(Integer medieLatteSoma) {
        this.medieLatteSoma = medieLatteSoma;
    }

    public Integer getMedieLatteGerm() {
        return medieLatteGerm;
    }

    public void setMedieLatteGerm(Integer medieLatteGerm) {
        this.medieLatteGerm = medieLatteGerm;
    }

    public Float getMedieLatteProt() {
        return medieLatteProt;
    }

    public void setMedieLatteProt(Float medieLatteProt) {
        this.medieLatteProt = medieLatteProt;
    }

    public String getCodAsl() {
        return codAsl;
    }

    public void setCodAsl(String codAsl) {
        this.codAsl = codAsl;
    }

    public String getFlagSess() {
        return flagSess;
    }

    public void setFlagSess(String flagSess) {
        this.flagSess = flagSess;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getCodiRazz() {
        return codiRazz;
    }

    public void setCodiRazz(String codiRazz) {
        this.codiRazz = codiRazz;
    }

    public String getDataInizDete() {
        return dataInizDete;
    }

    public void setDataInizDete(String dataInizDete) {
        this.dataInizDete = dataInizDete;
    }

    public String getDataFineDete() {
        return dataFineDete;
    }

    public void setDataFineDete(String dataFineDete) {
        this.dataFineDete = dataFineDete;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CapoPagato capoPagato = (CapoPagato) o;
        if(capoPagato.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, capoPagato.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CapoPagato{" +
            "id=" + id +
            ", marcaCapo='" + marcaCapo + "'" +
            ", ammissibile='" + ammissibile + "'" +
            ", numUba='" + numUba + "'" +
            ", mancanzaAnalisiLatte='" + mancanzaAnalisiLatte + "'" +
            ", medieLatteSoma='" + medieLatteSoma + "'" +
            ", medieLatteGerm='" + medieLatteGerm + "'" +
            ", medieLatteProt='" + medieLatteProt + "'" +
            ", codAsl='" + codAsl + "'" +
            ", flagSess='" + flagSess + "'" +
            ", dataNasc='" + dataNasc + "'" +
            ", codiRazz='" + codiRazz + "'" +
            ", dataInizDete='" + dataInizDete + "'" +
            ", dataFineDete='" + dataFineDete + "'" +
            '}';
    }
}
